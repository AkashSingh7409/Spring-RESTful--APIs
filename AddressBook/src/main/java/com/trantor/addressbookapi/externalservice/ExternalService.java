package com.trantor.addressbookapi.externalservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trantor.addressbookapi.dto.ContactDto;

public interface ExternalService {

    ContactDto[] getAllContact();
    public ContactDto[] externalSearchByFirstName(String firstName) throws JsonProcessingException;
    ContactDto saveContact(ContactDto dto) throws JsonProcessingException;
    String updateCustomer(Long id);
}
