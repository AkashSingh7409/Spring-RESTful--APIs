package com.trantor.addressbookapi.excelhelper;

import com.trantor.addressbookapi.model.ContactEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelDownloadHelper {

    ExcelDownloadHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    static String[] headers = {"ContactId", "FirstName", "LastName", "EmailAddress", "CreatedBy", "CreatedDate", "UpdatedBy", "UpdatedDate", "IsActive"};
    static String sheetOne = "AddressBook";

    public static ByteArrayInputStream contactEntityToExcel(List<ContactEntity> contactList) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(sheetOne);

            CellStyle styleOne = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            styleOne.setFont(font);


            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {

                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                headerRow.setRowStyle(styleOne);
            }


            int rowIdx = 1;
            for (ContactEntity contact : contactList) {
                Row row = sheet.createRow(rowIdx++);

                CellStyle style = workbook.createCellStyle();

                if (contact.getContactId() % 2 == 0) {

                    // Setting Background color
                    style.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
                    style.setFillPattern(FillPatternType.BIG_SPOTS);
                } else {
                    style.setFillBackgroundColor(IndexedColors.ROSE.getIndex());
                    style.setFillPattern(FillPatternType.DIAMONDS);
                }


                Cell cell1 = row.createCell(0);
                cell1.setCellValue(contact.getContactId());
                cell1.setCellStyle(style);

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(contact.getFirstName());
                cell2.setCellStyle(style);

                Cell cell3 = row.createCell(2);
                cell3.setCellValue(contact.getLastName());
                cell3.setCellStyle(style);

                Cell cell4 = row.createCell(3);
                cell4.setCellValue(contact.getEmailAddress());
                cell4.setCellStyle(style);

                Cell cell5 = row.createCell(4);
                cell5.setCellValue(contact.getIsActive());
                cell5.setCellStyle(style);

                Cell cell6 = row.createCell(5);
                cell6.setCellValue(contact.getCreatedBy());
                cell6.setCellStyle(style);

                // This is date cell
                Cell cell = row.createCell(6);
                cell.setCellValue(contact.getCreatedDate().toString());
                cell.setCellStyle(style);

                Cell cell7 = row.createCell(7);
                cell7.setCellValue(contact.getUpdatedBy());
                cell7.setCellStyle(style);

                // This is date cell
                Cell cellOne = row.createCell(8);
                cellOne.setCellValue(contact.getUpdatedDate().toString());
                cellOne.setCellStyle(style);

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new IllegalArgumentException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}