package com.trantor.addressbookapi.exception.custom;


public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
