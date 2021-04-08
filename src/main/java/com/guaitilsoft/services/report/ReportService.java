package com.guaitilsoft.services.report;

import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface ReportService<T> {
    byte[] exportPDF(List<T> list, String template) throws IOException, JRException;

    byte[] exportXLSX(List<T> list, String template);
}
