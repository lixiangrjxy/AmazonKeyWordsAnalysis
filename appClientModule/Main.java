import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//test changes
public class Main {
	
    public static final String FILE_PATH_URL = "./excel/url.xlsx";
    public static final String FILE_PATH_RESULT = "./excel/result.xlsx";
    static HttpClient client = HttpClientBuilder.create().build();

	public static void main(String[] args) {
		Sheet sheet;
		
		try {
			sheet = getURLSheet();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();

	            // Now let's iterate over the columns of the current row
	            Iterator<Cell> cellIterator = row.cellIterator();

	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                String cellValue = cell.getStringCellValue();
	                String []urls = cellValue.split(" ");
	                
	                System.out.print(cellValue + "\t");
	                
	                readWebPage(urls[0]);
	                
	                Thread.sleep(30000);
	                
	            }
	            System.out.println();
	        }
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Sheet getURLSheet() throws EncryptedDocumentException, InvalidFormatException, IOException {
		// Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(FILE_PATH_URL));
        Sheet sheet = null;
        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
            
        }
        return sheet;
	}
	
	private static void readWebPage(String url) throws ClientProtocolException, IOException {
		 HttpGet request = null;

	        try {
	            //HttpClient client = HttpClientBuilder.create().build();
	            request = new HttpGet(url);

	            request.addHeader("User-Agent", "Apache HTTPClient");
	            HttpResponse response = client.execute(request);

	            HttpEntity entity = response.getEntity();
	            String content = EntityUtils.toString(entity);
	            System.out.println(content);
	           // Dom dom = null;

	        } finally {

	            if (request != null) {

	                request.releaseConnection();
	            }
	        }
	        
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}