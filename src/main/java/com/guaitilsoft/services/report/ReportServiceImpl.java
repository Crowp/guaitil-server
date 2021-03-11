package com.guaitilsoft.services.report;

import com.guaitilsoft.exceptions.ApiRequestException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl<T> implements ReportService<T> {

    private JasperPrint makeFile(List<T> list, String template) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(template);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "GuaitilSoft");

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @Override
    public void exportPDF(OutputStream outputStream, List<T> list, String template) {
        try {
            JasperPrint jasperPrint = makeFile(list, template);
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        }catch (Exception e){
            throw new ApiRequestException("El archivo no se pudo crear " + e.getMessage());
        }
    }

    @Override
    public void exportXLSX(OutputStream outputStream, List<T> list, String template) {
        try {
            JasperPrint jasperPrint = makeFile(list, template);
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setWhitePageBackground(false);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            xlsxExporter.setConfiguration(configuration);
            xlsxExporter.exportReport();

        }catch (Exception e){
            throw new ApiRequestException("El archivo no se pudo crear " + e.getMessage());
        }
    }
}
