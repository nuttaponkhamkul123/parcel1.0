package parcel;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.ss.usermodel.Font;  
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperator {
  private static String[] columns = new String[] { "ว/ด/ป", "เอกสารรับ-จ่าย", "จำนวนที่มี", "รับ", "จ่าย", "คงเหลือ", 
      "ชื่อผู้เบิก", "หมายเหตุ" };
  
  private List<History> history;
  
  private Parcel p;
  
  public ExcelOperator(ArrayList<History> history, Parcel p) throws InvalidFormatException, IOException {
    this.history = history;
    this.p = p;
  }
  
  public void operate() throws IOException, InvalidFormatException {
    System.out.println("Starting Operate");
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("History");
    XSSFFont xSSFFont1 = workbook.createFont();
    xSSFFont1.setBold(true);
    xSSFFont1.setFontHeightInPoints((short)14);
    XSSFCellStyle headerCellStyle = workbook.createCellStyle();
    headerCellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
    headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerCellStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
    headerCellStyle.setBorderBottom(BorderStyle.THIN);
    headerCellStyle.setBorderTop(BorderStyle.THIN);
    headerCellStyle.setBorderRight(BorderStyle.THIN);
    headerCellStyle.setBorderLeft(BorderStyle.THIN);
    headerCellStyle.setFont(xSSFFont1);
    XSSFFont xSSFFont2 = workbook.createFont();
    xSSFFont2.setBold(true);
    xSSFFont2.setFontHeightInPoints((short)18);
    XSSFCellStyle titleCellStyle = workbook.createCellStyle();
    titleCellStyle.setFont(xSSFFont2);
    XSSFRow xSSFRow1 = sheet.createRow(0);
    Cell titleCell = xSSFRow1.createCell(2);
    titleCell.setCellValue("ทะเบียนควบคุมพัสดุ(\"Stock Card\")");
    titleCell.setCellStyle((CellStyle)titleCellStyle);
    XSSFFont xSSFFont3 = workbook.createFont();
    xSSFFont3.setBold(false);
    xSSFFont3.setFontHeightInPoints((short)14);
    XSSFCellStyle DetailRowStyle = workbook.createCellStyle();
    DetailRowStyle.setFont(xSSFFont3);
    XSSFRow xSSFRow2 = sheet.createRow(1);
    Cell firstRow1 = xSSFRow2.createCell(0);
    firstRow1.setCellValue("รหัสพัสดุ");
    firstRow1.setCellStyle((CellStyle)DetailRowStyle);
    Cell firstRow2 = xSSFRow2.createCell(1);
    firstRow2.setCellValue(" .........." + this.p.getId() + "..........");
    firstRow2.setCellStyle((CellStyle)DetailRowStyle);
    Cell firstRow3 = xSSFRow2.createCell(2);
    firstRow3.setCellValue("หน่วยนับ" );
    firstRow3.setCellStyle((CellStyle)DetailRowStyle);
    Cell firstRow4 = xSSFRow2.createCell(3);
    firstRow4.setCellValue(" ...." + this.p.getMeasure() + "....");
    firstRow4.setCellStyle((CellStyle)DetailRowStyle);
    XSSFRow xSSFRow3 = sheet.createRow(2);
    Cell secondRow1 = xSSFRow3.createCell(0);
    secondRow1.setCellValue("ชื่อพัสดุ(ทางการ)");
    secondRow1.setCellStyle((CellStyle)DetailRowStyle);
    Cell secondRow2 = xSSFRow3.createCell(1);
    secondRow2.setCellValue(" ......................." + this.p.getPublicName() + "....................");
    secondRow2.setCellStyle((CellStyle)DetailRowStyle);
    XSSFRow xSSFRow4 = sheet.createRow(3);
    Cell thirdRow1 = xSSFRow4.createCell(0);
    thirdRow1.setCellValue("ชื่อพัสดุ(ไม่ทางการ)");
    thirdRow1.setCellStyle((CellStyle)DetailRowStyle);
    Cell thirdRow2 = xSSFRow4.createCell(1);
    thirdRow2.setCellValue(" ......................." + this.p.getName() + "....................");
    thirdRow2.setCellStyle((CellStyle)DetailRowStyle);
    XSSFRow xSSFRow5 = sheet.createRow(6);
    for (int i = 0; i < columns.length; i++) {
      Cell cell = xSSFRow5.createCell(i);
      cell.setCellValue(columns[i]);
      cell.setCellStyle((CellStyle)headerCellStyle);
    } 
    XSSFCellStyle rowCellStyle = workbook.createCellStyle();
    rowCellStyle.setBorderBottom(BorderStyle.THIN);
    rowCellStyle.setBorderTop(BorderStyle.THIN);
    rowCellStyle.setBorderRight(BorderStyle.THIN);
    rowCellStyle.setBorderLeft(BorderStyle.THIN);
    int rowNum = 7;
    for (History his : this.history) {
      XSSFRow xSSFRow = sheet.createRow(rowNum++);
      xSSFRow.createCell(0).setCellValue(his.getDate());
      xSSFRow.getCell(0).setCellStyle((CellStyle)rowCellStyle);
      xSSFRow.createCell(1).setCellValue(his.getDocument());
      xSSFRow.getCell(1).setCellStyle((CellStyle)rowCellStyle);
      if (his.getStatus() == 1) {
        xSSFRow.createCell(2).setCellValue(his.getQuantity());
        xSSFRow.getCell(2).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(3).setCellValue(his.getRemaining());
        xSSFRow.getCell(3).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(4).setCellValue("-");
        xSSFRow.getCell(4).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(5).setCellValue((his.getQuantity() + his.getRemaining()));
        xSSFRow.getCell(5).setCellStyle((CellStyle)rowCellStyle);
      } else {
        xSSFRow.createCell(2).setCellValue(his.getQuantity());
        xSSFRow.getCell(2).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(3).setCellValue("-");
        xSSFRow.getCell(3).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(4).setCellValue(his.getRemaining());
        xSSFRow.getCell(4).setCellStyle((CellStyle)rowCellStyle);
        xSSFRow.createCell(5).setCellValue((his.getQuantity() - his.getRemaining()));
        xSSFRow.getCell(5).setCellStyle((CellStyle)rowCellStyle);
      } 
      xSSFRow.createCell(6).setCellValue(his.getPicker());
      xSSFRow.getCell(6).setCellStyle((CellStyle)rowCellStyle);
      xSSFRow.createCell(7).setCellValue(his.getNote());
      xSSFRow.getCell(7).setCellStyle((CellStyle)rowCellStyle);
    } 
    sheet.setFitToPage(false);
    for (int j = 0; j < columns.length; j++) {
      if (j != 2)
        sheet.autoSizeColumn(j); 
    } 
    File file1 = null;
    JFileChooser chooser = new JFileChooser() {
        public void approveSelection() {
          File f = getSelectedFile();
          if (!f.getAbsolutePath().endsWith(".xlsx")) {
            File temp = f;
            f = new File(String.valueOf(temp.getAbsolutePath()) + ".xlsx");
          } 
          if (f.exists() && getDialogType() == 1) {
            JLabel l = new JLabel("ไฟล์ชื่อซ้ำ จะวางทับหรือไม่?");
            l.setFont(new Font("Angsana New", 0, 25));
            int choice = JOptionPane.showConfirmDialog(null, l, "Existing file", 0);
            if (choice == 0)
              super.approveSelection(); 
          } 
          super.approveSelection();
        }
      };
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file (.xlsx)", new String[] { "xlsx" });
    chooser.setFileFilter(filter);
    if (chooser.showSaveDialog(null) == 0)
      try {
        File file = null;
        file1 = chooser.getSelectedFile();
        String fileName = "";
        if (!file1.getAbsolutePath().endsWith(".xlsx")) {
          fileName = file1 + ".xlsx";
        } else {
          file = file1;
        } 
        FileOutputStream fileOut = new FileOutputStream(file + "");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }  
  }
}

