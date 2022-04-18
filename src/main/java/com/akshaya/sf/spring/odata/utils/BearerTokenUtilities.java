package com.akshaya.sf.spring.odata.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.akshaya.sf.spring.odata.configs.SalesforceProperties;
import com.akshaya.sf.spring.odata.model.SalesforceAuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BearerTokenUtilities {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BearerTokenUtilities.class);

	private BearerTokenUtilities() {
		
	}
	
	private static final String TOKEN_URL = "https://login.salesforce.com/services/oauth2/token";

	public static SalesforceAuthResponse loginToSalesforce(CloseableHttpClient closeableHttpClient,
			SalesforceProperties salesforceProperties, ObjectMapper objectMapper)
			throws Exception {
		List<NameValuePair> loginParams = new ArrayList<>();
		loginParams.add(new BasicNameValuePair("client_id", salesforceProperties.getConsumerKey()));
		loginParams.add(new BasicNameValuePair("client_secret", salesforceProperties.getConsumerSecret()));
		loginParams.add(new BasicNameValuePair("grant_type", "password"));
		loginParams.add(new BasicNameValuePair("username", salesforceProperties.getUsername()));
		loginParams.add(new BasicNameValuePair("password", salesforceProperties.getPassword()));

		HttpPost post = new HttpPost(TOKEN_URL);
		post.setEntity(new UrlEncodedFormEntity(loginParams));

		HttpResponse httpResponse = closeableHttpClient.execute(post);
		SalesforceAuthResponse salesforceLoginResult = objectMapper.readValue(httpResponse.getEntity().getContent(),
				SalesforceAuthResponse.class);

		log.debug("loginToSalesforce() - salesforceLoginResult={}", salesforceLoginResult);
		return salesforceLoginResult;
	}

}
