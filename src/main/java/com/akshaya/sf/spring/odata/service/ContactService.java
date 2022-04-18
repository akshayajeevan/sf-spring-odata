package com.akshaya.sf.spring.odata.service;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.akshaya.sf.spring.odata.configs.SalesforceProperties;
import com.akshaya.sf.spring.odata.model.Contact;
import com.akshaya.sf.spring.odata.model.SalesforceAuthResponse;
import com.akshaya.sf.spring.odata.model.SalesforceQueryResponse;
import com.akshaya.sf.spring.odata.utils.BearerTokenUtilities;
import com.akshaya.sf.spring.odata.utils.SalesforceAuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ContactService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ContactService.class);
	public static final String QUERY_PATH = "/services/data/v52.0/";

	private final CloseableHttpClient closeableHttpClient;
	private final ObjectMapper objectMapper;
	private final SalesforceAuthUtils tokenUtils;

	@Autowired
	private SalesforceProperties salesforceProperties;

	public ContactService(CloseableHttpClient closeableHttpClient, ObjectMapper objectMapper, SalesforceAuthUtils tokenUtils) {
		this.closeableHttpClient = closeableHttpClient;
		this.objectMapper = objectMapper;
		this.tokenUtils = tokenUtils;
	}

	@Cacheable("contacts")
	public List<Object> getContacts() throws Exception {
		SalesforceAuthResponse salesforceLoginResult = BearerTokenUtilities.loginToSalesforce(closeableHttpClient,
				salesforceProperties, objectMapper);
		
		//salesforceProperties.get
		log.debug("");

		URIBuilder builder = new URIBuilder(salesforceLoginResult.getInstanceUrl());
		builder.setPath(QUERY_PATH + "query").setParameter("q", Contact.CONTACT_QUERY);

		HttpGet get = new HttpGet(builder.build());
		get.setHeader("Authorization", "Bearer " + salesforceLoginResult.getAccessToken());

		HttpResponse httpResponse = closeableHttpClient.execute(get);
		// HttpUtils.checkResponse(httpResponse);

		SalesforceQueryResponse salesforceResponse = objectMapper.readValue(httpResponse.getEntity().getContent(),
				SalesforceQueryResponse.class);

		List<Object> contacts = salesforceResponse.getRecords();

		log.debug("getContacts - contacts={}", contacts);
		return contacts;
	}

	public List<Object> newGetContacts() throws Exception {
		String token = tokenUtils.getAccessToken();

		URIBuilder builder = new URIBuilder("https://lindecomown-dev-ed.my.salesforce.com");
		builder.setPath(QUERY_PATH + "query").setParameter("q", Contact.CONTACT_QUERY);

		HttpGet get = new HttpGet(builder.build());
		get.setHeader("Authorization", "Bearer " + token);

		HttpResponse httpResponse = closeableHttpClient.execute(get);
		// HttpUtils.checkResponse(httpResponse);

		SalesforceQueryResponse salesforceResponse = objectMapper.readValue(httpResponse.getEntity().getContent(),
				SalesforceQueryResponse.class);

		List<Object> contacts = salesforceResponse.getRecords();

		log.debug("newGetContacts - contacts={}", contacts);
		return contacts;
	}

}
