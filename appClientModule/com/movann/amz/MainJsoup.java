package com.movann.amz;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.movann.amz.bean.AmzListing;
import com.movann.util.ExcelUtil;

public class MainJsoup {

	public static void main(String[] args) {
		try {
			String seedURL = "http://www.amazon.com/gp/bestsellers/home-garden/10671043011/ref=pd_zg_hrsr_home-garden_1_4_last";
			//seedURL = "http://www.1688.com";
			List <AmzListing>prodList = getListingInfo(seedURL);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String fileURL = "/Users/stephenli/Documents/output/amzListingSummary_" + sdf.format(new Date()) + ".xlsx";
			saveToExcel (fileURL, prodList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static List <AmzListing> getListingInfo(String seedURL) throws IOException,InterruptedException {
		// TODO Auto-generated method stub
		//String seedURL = "https://www.amazon.com/gp/bestsellers/home-garden/10671043011/ref=pd_zg_hrsr_home-garden_1_4_last";
		Document listDoc = Jsoup.connect(seedURL).get();
		Elements productList = listDoc.getElementsByClass("a-link-normal a-text-normal");
		if(productList == null) {
			throw new IOException ("Self Defined IO Exception: Could not list from seed URL: " + seedURL);
		}
		List <AmzListing>prodList = new ArrayList<AmzListing>();
		System.out.println(productList.size());
		for (Element productTD : productList) {
			AmzListing amzListing = new AmzListing();
			//StringBuffer keyPointsSb = new StringBuffer(); //key points
			//StringBuffer descSb = new StringBuffer(); //Description
			if(productTD != null) {
				String prodURL = "http://www.amazon.com" + productTD.attr("href");
				System.out.println(prodURL);
				Document prodDoc = Jsoup.connect(prodURL).get();
				Element titleEle = prodDoc.getElementById("productTitle");
				System.out.println(titleEle.text());
				amzListing.setTitle(titleEle.text());
				Element keyPointsDiv = prodDoc.getElementById("feature-bullets");
				Elements keyPoints = keyPointsDiv.getElementsByClass("a-list-item"); 
				for (Element keyPoint : keyPoints) {
					System.out.println(keyPoint.text());
					if(keyPoint.parent().id().equals("replacementPartsFitmentBullet")) {
						System.out.println("ignore replacementPartsFitmentBullet");
					} else {
						System.out.println("Append " + keyPoint.text());
						//keyPointsSb.append(keyPoint.text()).append("/r/n");
						amzListing.addKeyPoint(keyPoint.text());
					}
				}
				Element aplusEle = prodDoc.getElementById("aplus");
				if (aplusEle != null) {
					System.out.println(aplusEle.text());
					//descSb.append(aplusEle.text());
					amzListing.setAplus(aplusEle.text());
				} 
				Element descEle = prodDoc.getElementById("productDescription");
				if (descEle != null) {
					System.out.println(descEle.text());
					//descSb.append(descEle.text());
					amzListing.setDesc(descEle.text());
				} 
				prodList.add(amzListing);
				/*if(prodList.size() >= 3) {
					break;
				}*/
				Random random = new Random();
				int sleepTime = (Math.abs(random.nextInt()))%30 + 30;
				//System.out.println("sleep time = " + sleepTime);
				Thread.sleep(sleepTime * 1000);
				
			}
			
		}
		return prodList;
	}
	
	private static void saveToExcel (String URL, List <AmzListing>prodList) throws IOException {
		Workbook workBook = ExcelUtil.initWorkBook();
		Sheet sheet = workBook.createSheet();
		String [] headers = "Title,Key Points,Description".split(",");
		ExcelUtil.writeHeaders(headers, sheet);
		int i=1;
		for (AmzListing prod : prodList) {
			String[] columns = new String [3];
			columns [0] = prod.getTitle();
			columns [1] = prod.getKeyPointsAsString();
			columns [2] = prod.getAllDescriptions();
			ExcelUtil.writeLine(columns, sheet, i++);
		}
		ExcelUtil.saveAndCloseExcel(workBook, URL);
	}

}
