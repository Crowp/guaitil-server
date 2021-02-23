package com.guaitilsoft.services;


import java.io.OutputStream;
import java.util.List;

public interface ReportService<T> {
    void exportPDF(OutputStream outputStream, List<T> list, String template);
    void exportXLSX(OutputStream outputStream, List<T> list, String template);
}
