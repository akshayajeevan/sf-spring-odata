package com.akshaya.sf.spring.odata.configs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "salesforce")
public class SalesforceProperties {
	
	@NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String consumerKey;

    @NotNull
    private String consumerSecret;
    
    @NotNull
    private String apiUrl;
    
    @NotNull
    private String instanceUrl;
    
    @NotNull
    private List<String> allowedSobjects;
    
    @NotNull
    private Map<String, SObjectDetails> sobject = new HashMap<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}
	
	public List<String> getAllowedSobjects() {
		return allowedSobjects;
	}

	public void setAllowedSobjects(List<String> allowedSobjects) {
		this.allowedSobjects = allowedSobjects;
	}

	public Map<String, SObjectDetails> getSobject() {
		return sobject;
	}

	public void setSobject(Map<String, SObjectDetails> sobject) {
		this.sobject = sobject;
	}

	public static class SObjectDetails {
        private List<String> fields;

		public List<String> getFields() {
			return fields;
		}

		public void setFields(List<String> fields) {
			this.fields = fields;
		}
        
    }


}
