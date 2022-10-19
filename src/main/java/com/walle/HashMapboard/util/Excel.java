package com.walle.HashMapboard.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class Excel {
    public static void excelDownload(HttpServletResponse response List fileList, List<String> columnList, List<String> keyList){

        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);

        for(int i=0; i<columnList.size(); i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnList.get(i));
        }

        for(int i = 0; i < fileList.size(); i++) {
            Map map = (Map) fileList.get(i);
            Row bodyRow = sheet.createRow(rowIndex++);

            for (int j = 0; j < keyList.size(); j++) {
                Cell bodyCell = bodyRow.createCell(j);
                bodyCell.setCellValue(map.get(keyList.get(j)).toString());
            }
        }

        return workbook;
    }
    

}
    