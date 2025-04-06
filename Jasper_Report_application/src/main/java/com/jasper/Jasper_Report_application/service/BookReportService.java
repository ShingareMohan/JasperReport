package com.jasper.Jasper_Report_application.service;

import com.jasper.Jasper_Report_application.entities.BookInformation;
import com.jasper.Jasper_Report_application.repository.BookInformationRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookReportService {

    @Autowired
    private BookInformationRepo bookRepo;

    public byte[] exportReport(String format) throws Exception {
        // Load book data
        List<BookInformation> books = bookRepo.findAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(books);

        // Load Jasper report file
        InputStream reportStream = new ClassPathResource("reports/book_report.jrxml").getInputStream();

        InputStream reportStream1 = getClass().getResourceAsStream("/reports/book_report.jrxml");
        if (reportStream == null) {
            throw new RuntimeException("Jasper Report template not found in classpath: /reports/book_report.jrxml");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JasperReport jasperReport1 = JasperCompileManager.compileReport(reportStream1);

        // Parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Jasper Report Service");

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report
        if ("pdf".equalsIgnoreCase(format)) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } else if ("html".equalsIgnoreCase(format)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);
            return outputStream.toByteArray();
        } else {
            throw new IllegalArgumentException("Unsupported report format: " + format);
        }
    }
}
