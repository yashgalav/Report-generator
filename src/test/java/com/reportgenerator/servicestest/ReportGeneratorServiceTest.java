package com.reportgenerator.servicestest;

import com.reportgenerator.Constants.ReportGeneratorConstants;
import com.reportgenerator.services.ReportGeneratorService;
import com.reportgenerator.utils.ReportGeneratorUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static  org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class ReportGeneratorServiceTest {


    private String uploadPath = ReportGeneratorConstants.UPLOAD_PATH;


    @Test
    void testGenerateReportWithEmptyFiles() throws IOException {

        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        File emptyFile = new File(uploadPath+"empty.csv");
        try (FileWriter writer = new FileWriter(emptyFile)) {
            writer.write("");
        }

        assertThrows(FileNotFoundException.class, () -> {
            reportGeneratorService.generateReport("empty.csv", "reference.csv");
        });

        assertThrows(FileNotFoundException.class, () -> {
            reportGeneratorService.generateReport("inputfile.csv", "empty.csv");
        });

        emptyFile.delete();

    }

    @Test
    void testGenerateReportWithMissingFiles() {
        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        assertThrows(NullPointerException.class, () -> {
            reportGeneratorService.generateReport(null, "reference.csv");
        });

        assertThrows(NullPointerException.class, () -> {
            reportGeneratorService.generateReport("inputfile.csv", null);
        });
    }


    @Test
    void testGenerateReport() throws IOException {
        ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

        reportGeneratorService.generateReport(ReportGeneratorConstants.INPUT_FILE_NAME, ReportGeneratorConstants.REFERENCE_FILE_NAME);

        File outputFile = new File(uploadPath + ReportGeneratorConstants.OUTPUT_FILE_NAME);
        assertTrue(outputFile.exists());

        List<String> outputLines = Files.readAllLines(Paths.get(outputFile.getPath()));
        assertEquals(4, outputLines.size());

        String expectedHeader = "\"OUTFEILD1\",\"OUTFEILD2\",\"OUTFEILD3\",\"OUTFEILD4\",\"OUTFEILD5\"";
        String expectedLine1 = "\"3\",\"2\",\"10\",\"4.88\",\"1.22\"";
        String expectedLine2 = "\"7\",\"4\",\"7\",\"6.12\",\"1.02\"";
        String expectedLine3 = "\"8\",\"3\",\"6\",\"20.151500000000002\",\"4.0303\"";

        assertEquals(expectedHeader, outputLines.get(0));
        assertEquals(expectedLine1, outputLines.get(1));
        assertEquals(expectedLine2, outputLines.get(2));
        assertEquals(expectedLine3, outputLines.get(3));
    }

}