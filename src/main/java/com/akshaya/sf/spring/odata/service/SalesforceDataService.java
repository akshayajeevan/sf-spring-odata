package com.akshaya.sf.spring.odata.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshaya.sf.spring.odata.configs.SalesforceProperties;
import com.akshaya.sf.spring.odata.model.SalesforceQueryResponse;
import com.akshaya.sf.spring.odata.utils.SalesforceAuthUtils;
import com.akshaya.sf.spring.odata.utils.SalesforceDataUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesforceDataService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SalesforceDataService.class);
	public static final String QUERY_PATH = "/services/data/v52.0/";

	private final CloseableHttpClient closeableHttpClient;
	private final ObjectMapper objectMapper;
	private final SalesforceAuthUtils authUtils;

	@Autowired
	private SalesforceProperties salesforceProperties;

	public SalesforceDataService(CloseableHttpClient closeableHttpClient, ObjectMapper objectMapper,
			SalesforceAuthUtils authUtils) {
		this.closeableHttpClient = closeableHttpClient;
		this.objectMapper = objectMapper;
		this.authUtils = authUtils;
	}

	public List<Object> getSFObjectAllRecords(String object) throws Exception {
		log.debug("getSFObjectRecords - object= {}", object);
		List<Object> response = new ArrayList<Object>();
		boolean isAllowed = SalesforceDataUtils.isObjectAllowed(object, salesforceProperties);
		if (isAllowed) {
			String token = authUtils.getAccessToken();

			log.debug("getSFObjectRecords - token= {}", token);

			URIBuilder builder = new URIBuilder(salesforceProperties.getInstanceUrl());
			builder.setPath(salesforceProperties.getApiUrl() + "query").setParameter("q",
					SalesforceDataUtils.allSOQLBuilder(object));

			HttpGet get = new HttpGet(builder.build());
			get.setHeader("Authorization", "Bearer " + token);

			HttpResponse httpResponse = closeableHttpClient.execute(get);

			SalesforceQueryResponse salesforceResponse = objectMapper.readValue(httpResponse.getEntity().getContent(),
					SalesforceQueryResponse.class);

			response = salesforceResponse.getRecords();
		}
		return response;

	}

	public List<Object> getSFObjectRecords(String object, String selectedFields) throws Exception {
		log.debug("getSFObjectRecords - object= {}, selectedFields= {}", object, selectedFields);
		List<Object> response = new ArrayList<Object>();
		String queryStr = "";
		boolean isAllowed = SalesforceDataUtils.isObjectAllowed(object, salesforceProperties);
		if (isAllowed) {
			String token = authUtils.getAccessToken();
			log.debug("getSFObjectRecords - token= {}", token);

			if (StringUtils.isBlank(selectedFields)) {
				queryStr = SalesforceDataUtils.allSOQLBuilder(object);
			} else {
				queryStr = SalesforceDataUtils.selectedSOQLBuilder(object, selectedFields);
			}

			URIBuilder builder = new URIBuilder(salesforceProperties.getInstanceUrl());
			builder.setPath(salesforceProperties.getApiUrl() + "query").setParameter("q", queryStr);

			HttpGet get = new HttpGet(builder.build());
			get.setHeader("Authorization", "Bearer " + token);

			HttpResponse httpResponse = closeableHttpClient.execute(get);
			
			JsonNode responseJSONTree = objectMapper.readTree(httpResponse.getEntity().getContent());
			if(responseJSONTree.isArray()) {
				//error case
				response = objectMapper.readValue(responseJSONTree.toString(), ArrayList.class);
			}
			if(responseJSONTree.isObject()) {
				//TODO - handle object - happy case
				SalesforceQueryResponse salesforceResponse = objectMapper.readValue(responseJSONTree.toString(), SalesforceQueryResponse.class);
				response = salesforceResponse.getRecords();
			}

			log.debug("getSFObjectRecords - records={}", response);
		}
		return response;

	}

}
