/**
 * 
 */
package com.movann.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author stephenli
 *
 */
public class ExcelUtil {
	
	public static Workbook initWorkBook() {

		Workbook workbook = new XSSFWorkbook();
		
	    return workbook;
	}
	
	public static void writeHeaders(String [] headers, Sheet sheet) {
		writeLine (headers, sheet, 0);
	}
	
	public static void writeLine(String [] columns, Sheet sheet, int rowNumber) {
		Row row = sheet.createRow(rowNumber);
		 for(int i = 0; i < columns.length; i++) {
			 Cell cell = row.createCell(i);
			 if(columns[i] != null) {
				 cell.setCellValue(columns[i]);
			 } 
		 }
	}
	
	public static void saveAndCloseExcel (Workbook workbook, String url) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(url);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
	}
	
}
