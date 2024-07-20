package com.reportgenerator.utils;

import java.io.File;

public class ReportGeneratorUtils {

    public static Boolean isFileValidate(File file){
        if(!file.exists() || !file.isFile())
            return false;
        else{
            return file.length() != 0;
        }
    }
}
