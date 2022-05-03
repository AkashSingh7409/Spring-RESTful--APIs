package com.trantor.addressbookapi.excelservice;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface ContactExcelService {
    ByteArrayInputStream load();

    void saveExcelFileData(MultipartFile file);
}
