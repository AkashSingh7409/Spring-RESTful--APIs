package com.trantor.addressbookapi.controller;

import com.trantor.addressbookapi.excelhelper.ExcelUploadHelper;
import com.trantor.addressbookapi.excelservice.ContactExcelServiceImpl;
import com.trantor.addressbookapi.externalservice.ExcelServiceUplode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    ContactExcelServiceImpl contactExcelServiceimpl;

    @GetMapping("/download_AddressBook")
    public ResponseEntity<Resource> downLoadExcelSheet() {

        String filename = "AddressBook.xlsx";
        InputStreamResource file = new InputStreamResource(contactExcelServiceimpl.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    @Autowired
    private ExcelServiceUplode excelServiceUplode;

    @PostMapping("/upload_AddressBook")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {
        Logger logger = LoggerFactory.getLogger(ExcelController.class);
        if (ExcelUploadHelper.hasExcelFormat(file)) {
            // true
            long startTime = System.nanoTime();
            this.excelServiceUplode.saveDataToDb(file);
            long endTime = System.nanoTime();
            logger.info("------------- {}" , (double) (endTime - startTime) / 1_000_000_000);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

}



