package com.akshaya.sf.spring.odata.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akshaya.sf.spring.odata.service.ContactService;
import com.akshaya.sf.spring.odata.service.SalesforceDataService;

@RestController
@RequestMapping(path = "/sf", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class SFRestApiController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SFRestApiController.class);

	@Autowired
	private ContactService contactService;

	@Autowired
	private SalesforceDataService salesforceDataService;

	@GetMapping(path = "/fetch")
	public ResponseEntity<List<Object>> getContactsWithBearerTokenUtils() {
		log.debug("trying with BearerTokenUtilities....");
		try {
			List<Object> response = contactService.getContacts();
			return new ResponseEntity<List<Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping(path = "/odata/{object}") public ResponseEntity<List<Object>>
	 * getAllSalesforceData(@PathVariable String object) {
	 * log.debug("getAllSalesforceData called......"); try { if
	 * (StringUtils.isBlank(object)) { return new
	 * ResponseEntity<>(HttpStatus.BAD_REQUEST); } List<Object> response =
	 * salesforceDataService.getSFObjectAllRecords(object); return new
	 * ResponseEntity<List<Object>>(response, HttpStatus.OK); } catch (Exception e)
	 * { log.error(e.getMessage(), e); return new
	 * ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@GetMapping(path = "/odata/{object}")
	public ResponseEntity<List<Object>> getSalesforceData(@PathVariable String object,
			@RequestParam(name = "$select") Optional<String> select) {
		log.debug("getSalesforceData called......");
		String selectedFields = "";
		try {
			if (StringUtils.isBlank(object)) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(select.isPresent()) {
				selectedFields = select.get();
			}
			List<Object> response = salesforceDataService.getSFObjectRecords(object, selectedFields);
			return new ResponseEntity<List<Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
