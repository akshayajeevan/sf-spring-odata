package com.akshaya.sf.spring.odata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesforceAuthResponse {
	
	@JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "instance_url")
    private String instanceUrl;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	@Override
	public String toString() {
		return "SalesforceLoginResult [accessToken=" + accessToken + ", instanceUrl=" + instanceUrl + "]";
	}
    
}
