package com.reportgenerator.services;

import com.reportgenerator.Constants.ReportGeneratorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReportGeneratorJobService {

    @Autowired
    private ReportGeneratorService reportGeneratorService;

    @Scheduled(cron = "${report.generation.schedule}")
    public void reportGenerationJob() {
        try {
            // Generate the report
            reportGeneratorService.generateReport(ReportGeneratorConstants.INPUT_FILE_NAME, ReportGeneratorConstants.REFERENCE_FILE_NAME);
        } catch (IOException e) {
            // Log error
            e.printStackTrace();
        }
    }
}
