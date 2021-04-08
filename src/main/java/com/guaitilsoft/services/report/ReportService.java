package com.guaitilsoft.services.report;

import java.util.List;

public interface ReportService<T> {
    byte[] exportPDF(List<T> list, String template);

    byte[] exportXLSX(List<T> list, String template);
}
