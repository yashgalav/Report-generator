package com.reportgenerator.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.reportgenerator.Constants.ReportGeneratorConstants;
import com.reportgenerator.models.Input;
import com.reportgenerator.models.Output;
import com.reportgenerator.models.Reference;
import com.reportgenerator.utils.ReportGeneratorUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ReportGeneratorService {


    private String uploadPath = ReportGeneratorConstants.UPLOAD_PATH;


    public void generateReport(String inputFileName, String referenceFileName) throws IOException {
        Logger.getLogger("Report Gereration").log(Level.INFO,"Report Generation Started");
        if(inputFileName == null || referenceFileName == null)
            throw new NullPointerException();


        File inputFile = new File(uploadPath + inputFileName);
        File referenceFile = new File(uploadPath + referenceFileName);

        if(!ReportGeneratorUtils.isFileValidate(inputFile) || !ReportGeneratorUtils.isFileValidate(referenceFile)) {
            Logger.getLogger("file").log(Level.WARNING,"File is Empty");
            throw new FileNotFoundException();
        }
        List<Input> inputs = parseInputFile(inputFile);
        List<Reference> references = parseReferenceFile(referenceFile);

        Map<String, Input> inputMap = inputs.stream().collect(Collectors.toMap(i -> i.getRefkey1()+ ":" +i.getRefkey2(), Function.identity()));
        Map<String, Reference> referenceMap = references.stream().collect(Collectors.toMap(r -> r.getRefkey1()+ ":" +r.getRefkey2(), Function.identity()));

        createReport(inputMap, referenceMap);

        Logger.getLogger("Report Gereration").log(Level.INFO,"Report Generated Succefully");
    }

    private void createReport(Map<String, Input> inputMap, Map<String, Reference> referenceMap) {

        List<Output> outputs = new ArrayList<>();

        for(String key : inputMap.keySet()){
            Output output = new Output();
            Input input = inputMap.get(key);
            Reference reference = referenceMap.get(key);

            if(input == null || reference == null)
                continue;

            Long out1 = Long.parseLong(input.getField1()) + Long.parseLong(input.getField2());
            String out2 = reference.getRefdata1();
            Long out3 = Long.parseLong(reference.getRefdata2()) + Long.parseLong(reference.getRefdata3());
            Double out4 = Long.parseLong(reference.getRefdata2()) * Math.max(input.getField5() , reference.getRefdata4());
            Double out5 = Math.max(input.getField5() , reference.getRefdata4());

            output.setOutfeild1(String.valueOf(out1));
            output.setOutfeild2(out2);
            output.setOutfeild3(String.valueOf(out3));
            output.setOutfeild4(out4);
            output.setOutfeild5(out5);

            outputs.add(output);
        }



        try (Writer writer = new FileWriter(new File(uploadPath + ReportGeneratorConstants.OUTPUT_FILE_NAME))) {
            StatefulBeanToCsv<Output> beanToCsv = new StatefulBeanToCsvBuilder<Output>(writer)
                    .withSeparator(',')
                    .build();

            beanToCsv.write(outputs);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, "Error writing CSV file", e);
        }
    }

    private List<Reference> parseReferenceFile(File referenceFile) throws IOException {
        Reader reader = new FileReader(referenceFile);
        List<Reference> references = new CsvToBeanBuilder<Reference>(reader)
                .withType(Reference.class)
                .build()
                .parse();
        return references;
    }

    private List<Input> parseInputFile(File inputFile) throws IOException {
        Reader reader = new FileReader(inputFile);
        List<Input> inputs = new CsvToBeanBuilder<Input>(reader)
                .withType(Input.class)
                .build()
                .parse();
        return inputs;
    }
}
