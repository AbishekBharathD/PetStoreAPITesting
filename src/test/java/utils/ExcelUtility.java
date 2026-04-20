package utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public static Workbook workBook;
	public static Sheet sheet;
	
	public static void loadExcel(String fileName, String sheetName) {
		try {
			FileInputStream fin = new FileInputStream("./src/test/resources/testData/"+fileName);
			workBook = new XSSFWorkbook(fin);
			sheet = workBook.getSheet(sheetName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static String getCellData(int rowNum, int colNum) {
	        Cell cell = sheet.getRow(rowNum).getCell(colNum);
			DataFormatter formatter = new DataFormatter();
	        return  formatter.formatCellValue(cell);
	    }
	
}
