package com.trantor.addressbookapi.service.impl;

import com.trantor.addressbookapi.dto.ContactDto;
import com.trantor.addressbookapi.exception.custom.ContactNotFoundException;
import com.trantor.addressbookapi.mapper.SourceToDestMapper;
import com.trantor.addressbookapi.model.ContactEntity;
import com.trantor.addressbookapi.repositories.ContactRepository;
import com.trantor.addressbookapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository customerRepo;

    @Override
    public ContactDto saveCustomer(ContactDto contactDto){
        return SourceToDestMapper.MAPPER.entityToDto(customerRepo.save(SourceToDestMapper.MAPPER.dtoToEntity(contactDto)));
    }

    @Override
    public List<ContactDto> getCustomer(String firstName) {
        List<ContactEntity> list = customerRepo.findByFirstName(firstName);
        if (list.isEmpty()) {
            throw new ContactNotFoundException("Contact Is not in database");
        }
        return SourceToDestMapper.MAPPER.entityToDtoList(list);

    }

    @Override
    public List<ContactDto> getAllCustomer() {
        return SourceToDestMapper.MAPPER.entityToDtoList(customerRepo.findAll());

    }

    @Override
    public String updateCustomer(Long id) {
        Optional<ContactEntity> optionalContactEntity = customerRepo.findById(id);
        if (optionalContactEntity.isPresent()) {
            ContactEntity entity = optionalContactEntity.get();
            entity.setIsActive("N");
            customerRepo.save(entity);
            return "updated successfully";
        } else {
            return "Invalid Id number";
        }

    }
}
