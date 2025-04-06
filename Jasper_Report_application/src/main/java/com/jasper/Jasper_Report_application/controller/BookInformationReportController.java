package com.jasper.Jasper_Report_application.controller;

import com.jasper.Jasper_Report_application.service.BookReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookInformation")
public class BookInformationReportController {

    @Autowired
    private BookReportService bookReportService;

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReport(@RequestParam String format) {
        try {
            byte[] report = bookReportService.exportReport(format);

            HttpHeaders headers = new HttpHeaders();
            if ("pdf".equalsIgnoreCase(format)) {
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "book_report.pdf");
            } else if ("html".equalsIgnoreCase(format)) {
                headers.setContentType(MediaType.TEXT_HTML);
                headers.setContentDispositionFormData("filename", "book_report.html");
            }

            return ResponseEntity.ok().headers(headers).body(report);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(("Error generating report: " + e.getMessage()).getBytes());
        }
    }
}
