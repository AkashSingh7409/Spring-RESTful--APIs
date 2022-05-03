package com.trantor.addressbookapi.excelservice;

import com.google.common.base.Stopwatch;
import com.trantor.addressbookapi.excelhelper.ExcelDownloadHelper;
import com.trantor.addressbookapi.excelhelper.ExcelUploadHelper;
import com.trantor.addressbookapi.exception.custom.EmptyDatabaseException;
import com.trantor.addressbookapi.model.ContactEntity;
import com.trantor.addressbookapi.repositories.ContactRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContactExcelServiceImpl implements ContactExcelService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public ByteArrayInputStream load() {
        List<ContactEntity> contactList = contactRepository.findAll();
        if (contactList.isEmpty()) {
            throw new EmptyDatabaseException();
        }
        return ExcelDownloadHelper.contactEntityToExcel(contactList);
    }

    @Override
    public void saveExcelFileData(MultipartFile file) {

        try {
            List<ContactEntity> contactEntityList = ExcelUploadHelper.excelToContactEntity(file.getInputStream());
            Stopwatch time= Stopwatch.createStarted();
            contactRepository.saveAll(contactEntityList);
            Logger logger = LoggerFactory.getLogger(ContactExcelServiceImpl.class);
            logger.info("serviceReadTime : {}" , time.elapsed(TimeUnit.MILLISECONDS));
        } catch (IOException e) {
            RuntimeException runtimeException = new RuntimeException("fail to store excel data: " + e.getMessage());
            throw runtimeException;
        }
    }


}
