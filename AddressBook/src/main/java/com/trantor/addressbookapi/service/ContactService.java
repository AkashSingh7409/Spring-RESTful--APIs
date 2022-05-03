package com.trantor.addressbookapi.service;

import com.trantor.addressbookapi.dto.ContactDto;
import com.trantor.addressbookapi.exception.custom.ContactNotFoundException;

import java.util.List;

public interface ContactService {
    ContactDto saveCustomer(ContactDto dto);

    List<ContactDto> getCustomer(String firstName) throws ContactNotFoundException;

    List<ContactDto> getAllCustomer();

    String updateCustomer(Long id);
}
