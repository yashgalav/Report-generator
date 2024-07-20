package com.reportgenerator.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Input {

    @CsvBindByName(column = "field1")
    private String field1;

    @CsvBindByName(column = "field2")
    private String field2;

    @CsvBindByName(column = "field3")
    private String field3;

    @CsvBindByName(column = "field5")
    private Double field5;

    @CsvBindByName(column = "refkey1")
    private String refkey1;

    @CsvBindByName(column = "refkey2")
    private String refkey2;
}
