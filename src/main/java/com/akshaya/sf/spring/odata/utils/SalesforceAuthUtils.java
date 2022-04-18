package com.akshaya.sf.spring.odata.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import com.akshaya.sf.spring.odata.configs.SalesforceProperties;

@Component
public class SalesforceAuthUtils {
	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SalesforceAuthUtils.class);
	
	@Autowired
	private SalesforceProperties salesforceProperties;

	private final OAuth2AuthorizedClientManager authorizedClientManager;

	public SalesforceAuthUtils(OAuth2AuthorizedClientManager authorizedClientManager) {
		this.authorizedClientManager = authorizedClientManager;
	}

	public String getAccessToken() throws Exception {
		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
				.withClientRegistrationId("salesforce")
				.principal(salesforceProperties.getConsumerKey())
				.build();

		OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);
		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
		return accessToken.getTokenValue();
	}

}
