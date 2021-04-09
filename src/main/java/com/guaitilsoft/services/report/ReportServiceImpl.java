package com.guaitilsoft.services.report;

import com.guaitilsoft.exceptions.ApiRequestException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl<T> implements ReportService<T> {

    private final Path reportsPath;

    @Autowired
    public ReportServiceImpl(@Value("${reports-path}") String pathString) {
        this.reportsPath = Paths.get(pathString);
    }

    private JasperPrint makeFile(List<T> list, String template) throws IOException, JRException {
        Path filePath = reportsPath.resolve(template);
        Resource resource = new UrlResource(filePath.toUri());
        File file = resource.getFile();
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "GuaitilSoft");
        parameters.put("logo", reportsPath.resolve("logoReport/Logo.png").toString());
        parameters.put("coffee", reportsPath.resolve("logoReport/coffee_stain.png").toString());

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @Override
    public byte[] exportPDF( List<T> list, String template) {
        try {
            JasperPrint jasperPrint = makeFile(list, template);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch (Exception e){
            throw new ApiRequestException("El archivo no se pudo crear " + e.getMessage());
        }
    }

    @Override
    public byte[] exportXLSX(List<T> list, String template) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
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

            return outputStream.toByteArray();
        }catch (Exception e){
            throw new ApiRequestException("El archivo no se pudo crear " + e.getMessage());
        }
    }
}
