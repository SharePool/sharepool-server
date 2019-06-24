package com.shareppol.sharepoolanalytics.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "server-client", url = "localhost:8080")
public interface ServerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/id", consumes = MediaType.APPLICATION_JSON_VALUE)
    Long getUserIdByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String userToken);

}
