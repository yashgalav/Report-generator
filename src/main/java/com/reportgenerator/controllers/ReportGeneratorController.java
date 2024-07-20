package com.reportgenerator.controllers;

import com.reportgenerator.services.ReportGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ReportGeneratorController {

    @Autowired
    private ReportGeneratorService reportGeneratorService;

    @PostMapping("/report-generate")
    public ResponseEntity<String> generateReport(@RequestParam(value= "inputFileName") String inputFileName,
                                        @RequestParam(value ="referenceFileName") String referenceFileName) throws IOException {
        reportGeneratorService.generateReport(inputFileName, referenceFileName);
        return ResponseEntity.ok("Report generated successfully");
    }

}
