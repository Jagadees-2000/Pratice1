package First;

import java.io.FileOutputStream;
import java.io.IOException;

//import javax.lang.model.element.Element;
//import javax.lang.model.util.Elements;
//import javax.swing.text.Document;

//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.Sheet;


public class ApolloJobScraper {

	
	

    public static void main(String[] args) {
        String url = "https://www.apollohospitals.com/corporate/careers";
        String excelFile = "Apollo_Jobs.xlsx";

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements jobs = doc.select(".job-opening-block");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Apollo Jobs");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Job Title");
            header.createCell(1).setCellValue("Location");
            header.createCell(2).setCellValue("Description");

            int rowNum = 1;

            for (Element job : jobs) {
                String title = job.select("h3").text();
                String location = job.select("span.location").text();
                String desc = job.select("p").text();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(title);
                row.createCell(1).setCellValue(location);
                row.createCell(2).setCellValue(desc);
            }

            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fileOut = new FileOutputStream(excelFile);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("✅ Job data saved to " + excelFile);

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
	
	
}
