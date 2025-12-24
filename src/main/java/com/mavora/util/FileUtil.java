package com.mavora.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author malaka senanayake
 */
public class FileUtil {
    private static final String EXCEL_FILE_NAME = "";
    private static final String DB_FILE_NAME = "";

    //------------------------------------------------------------------------------------------------------------------
    public static String getNameToExcelFile(String excelFileName) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd@HH-mm-ss");
        Date d = new Date();
        String filename = excelFileName + "_" + (df.format(d)) + ".xls";
        return (filename);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static String getNameToSqlFile(String reportName) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd@HH-mm-ss");
        Date d = new Date();
        String filename = reportName + "_" + (df.format(d)) + ".sql";
        return (filename);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static void openFile(String filePath) {
        try {
            String[] cmdarray = new String[]{"cmd.exe", "/c", filePath};
            Runtime.getRuntime().exec(cmdarray);
        } catch (IOException ex) {
            System.out.println("exception in File.openFile" + ex);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}
