package com.akshaya.sf.spring.odata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class MyRestController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyRestController.class);

	@GetMapping(value = "/test")
    public ResponseEntity<String> testApi() {
        try {
            return new ResponseEntity<>("Hey, I am from spring boot", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
