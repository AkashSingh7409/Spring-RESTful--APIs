package com.trantor.addressbookapi.excelhelper;

import com.google.common.base.Stopwatch;
import com.trantor.addressbookapi.model.ContactEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class ExcelUploadHelper {

    static String[] headers = {"ContactId", "FirstName", "LastName", "EmailAddress"};
    static String sheetName = "AddressBook";

    public static boolean hasExcelFormat(MultipartFile file) {
        String type = file.getContentType();
        if(type != null){
            return  (type.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) ? true : null;
        }
        return false;
    }

    public static List<ContactEntity> excelToContactEntity(InputStream is) throws IOException {
        Stopwatch time = Stopwatch.createStarted();
        List<ContactEntity> contactEntityList = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(sheetName);
            int rowNumber = 0;
            Iterator<Row> rows = sheet.iterator();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                int cellIdx = 0;
                ContactEntity contact = new ContactEntity();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            contact.setContactId((long) (currentCell.getNumericCellValue()));
                            break;
                        case 1:
                            contact.setFirstName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            contact.setLastName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            contact.setIsActive(currentCell.getStringCellValue());
                            break;
                        case 4:
                            contact.setEmailAddress(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                contactEntityList.add(contact);
            }
            workbook.close();

        } catch (IOException e) {
            RuntimeException runtimeException = new RuntimeException("fail to parse Excel file: " + e.getMessage());
            throw runtimeException;
        }
        Logger logger = LoggerFactory.getLogger(ExcelUploadHelper.class);
        logger.info("readerTime : {}" , time.elapsed(TimeUnit.MILLISECONDS));
        return contactEntityList;

    }
}