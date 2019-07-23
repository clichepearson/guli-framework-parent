import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelWriteTest {

    @Test
    public void testW03() throws Exception{
        //新建工作簿
        Workbook book = new XSSFWorkbook();
        //新建表
        Sheet sheet = book.createSheet("讲师信息表");
        //新建行
        Row row = sheet.createRow(0);
        //新建列
        Cell cell = row.createCell(0);
        //填充信息
        cell.setCellValue("刘德华");
        //写出到文件
        FileOutputStream out = new FileOutputStream("d:/test/test-07.xlsx");

        book.write(out);

        out.close();

    }
    @Test
    public void testBatchW03() throws Exception{
        
        Workbook book = new SXSSFWorkbook();

        Sheet sheet = book.createSheet("讲师信息");

        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);

            Cell cell = row.createCell(0);

            cell.setCellValue(rowNum);

        }

        FileOutputStream out = new FileOutputStream("d:/test/test-07batch.xlsx");

        book.write(out);

        out.close();
    }
    @Test
    public void testRead07() throws Exception{
        FileInputStream in = new FileInputStream("d:/test/test-07.xlsx");

        Workbook book = new XSSFWorkbook(in);

        Sheet sheetAt = book.getSheetAt(0);

        Row row = sheetAt.getRow(0);

        Cell cell = row.getCell(0);

        System.out.println(cell.getStringCellValue());

        in.close();

    }
}
