package com.trantor.addressbookapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trantor.addressbookapi.dto.ContactDto;
import com.trantor.addressbookapi.externalservice.impl.ExternalServiceImpl;
import com.trantor.addressbookapi.service.impl.ContactServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    ExternalServiceImpl externalService;

    @PostMapping("/saveContact/{isRemote}")
    public ResponseEntity<ContactDto> saveCustomer(@RequestBody @Valid ContactDto dto, @PathVariable("isRemote") String flag) throws JsonProcessingException {
        if (flag.equalsIgnoreCase("y")) {
            return new ResponseEntity<>(externalService.saveContact(dto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(contactService.saveCustomer(dto), HttpStatus.OK);
        }
    }

    @GetMapping("/getContact/{firstName}/{isRemote}")
    public ResponseEntity<List<ContactDto>> getCustomer(@PathVariable("firstName") String firstName, @PathVariable("isRemote") String flag) throws JsonProcessingException {
        if ((flag.equalsIgnoreCase("y"))) {
         return new ResponseEntity<>(List.of(externalService.externalSearchByFirstName(firstName)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(contactService.getCustomer(firstName), HttpStatus.OK);
        }
    }

    @GetMapping("/getAllContact/{isRemote}")
    public ResponseEntity<List<ContactDto>> getAllCustomer(@PathVariable("isRemote") String isRemote)

    {
        if (isRemote.equalsIgnoreCase("y")) {
            return new ResponseEntity<>(List.of(externalService.getAllContact()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(contactService.getAllCustomer(), HttpStatus.OK);
        }

    }

    @PutMapping("/updateContact/{contactId}/{isRemote}")
    public ResponseEntity<String> updateCustomer(@PathVariable("contactId") Long contactId, @PathVariable("isRemote") String flag) {
        if (flag.equalsIgnoreCase("y")) {
            return new ResponseEntity<>(externalService.updateCustomer(contactId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(contactService.updateCustomer(contactId), HttpStatus.OK);
        }
    }


}
