package com.trantor.addressbookapi.externalservice;

import com.trantor.addressbookapi.excelhelper.ExcelUploadHelper;
import com.trantor.addressbookapi.model.ContactEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class ExcelServiceUplode {

    @Autowired
    HikariDataSource hikariDataSource;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public void saveDataToDb(MultipartFile file) {
        Logger logger = LoggerFactory.getLogger(ExcelServiceUplode.class);
        String sql = String.format(
                "INSERT INTO %s (first_Name, last_Name, email_Address, created_By) " + "VALUES (?, ?, ?, ?)",

                ContactEntity.class.getAnnotation(Table.class).name());
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int counter = 0;

            List<ContactEntity> contacts = ExcelUploadHelper.excelToContactEntity(file.getInputStream());
            long startTime = System.nanoTime();
            for (ContactEntity product : contacts) {
                statement.clearParameters();
                statement.setString(1, product.getFirstName());
                statement.setString(2, product.getLastName());
                statement.setString(3, product.getEmailAddress());
                statement.setString(4, product.getCreatedBy());
                statement.addBatch();
                if ((counter + 1) % batchSize == 0 || (counter + 1) == contacts.size()) {
                    statement.executeBatch();
                    statement.clearBatch();
                }
                counter++;
            }
            long endTime = System.nanoTime();
            logger.info("Total time taken to save database:{} ", ((double) (endTime - startTime) / 1_000_000_000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

