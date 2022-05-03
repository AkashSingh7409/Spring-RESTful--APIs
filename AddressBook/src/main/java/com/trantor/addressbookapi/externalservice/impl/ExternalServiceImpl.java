package com.trantor.addressbookapi.externalservice.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trantor.addressbookapi.dto.ContactDto;
import com.trantor.addressbookapi.externalservice.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    RestTemplate restTemplateConfig;

    ObjectMapper objectMapper = null;

    @Value("${external.url}")
    String url;

    @Override
    public ContactDto[] getAllContact() {
        final String uri = url + "/getAllCustomer/isRemote=n";
        restTemplateConfig = new RestTemplate();
        return restTemplateConfig.getForObject(uri, ContactDto[].class);
    }

    public ContactDto[] externalSearchByFirstName(String firstName) throws JsonProcessingException {
        String findOne = restTemplateConfig.getForObject(url + "/getCustomer/isRemote=n/" + firstName, String.class);
        return objectMapper.readValue(findOne, ContactDto[].class);
    }

    @Override
    public ContactDto saveContact(ContactDto dto) throws JsonProcessingException {
        final String uri = url + "/saveContact/isRemote=n";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(value, httpHeaders);
        return restTemplateConfig.postForObject(uri, httpEntity, ContactDto.class);
    }

    @Override
    public String updateCustomer(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplateConfig.exchange(url + "/updateCustomer/" + id + "/isRemote=n", HttpMethod.PUT, entity, String.class);
        return "Updated SuccessFully";
    }
}



