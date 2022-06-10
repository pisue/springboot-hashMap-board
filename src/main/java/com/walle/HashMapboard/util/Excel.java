package com.walle.HashMapboard.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Excel {

    //어려움, 우아한형제들 기술블로그 참고해야함
    //Excel 생성
    public void createExcel(ArrayList<Map<String, Object>> list, ArrayList<String> columnNames){

    }

    //Excel 출력
    public void printExcel(File ExcelFile){
        try{
            FileInputStream file = new FileInputStream(ExcelFile);

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly

                    switch (cell.getCellType())
                    {
                        case NUMERIC://CellType.: //"STRING" : //Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case STRING: //Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }

                }
                System.out.println("");
            }
            file.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
