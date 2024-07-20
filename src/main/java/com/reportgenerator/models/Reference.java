package com.reportgenerator.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Reference {

    @CsvBindByName(column = "refkey1")
    private String refkey1;

    @CsvBindByName(column = "refdata1")
    private String refdata1;

    @CsvBindByName(column = "refkey2")
    private String refkey2;

    @CsvBindByName(column = "refdata2")
    private String refdata2;

    @CsvBindByName(column = "refdata3")
    private String refdata3;

    @CsvBindByName(column = "refdata4")
    private Double refdata4;
}
