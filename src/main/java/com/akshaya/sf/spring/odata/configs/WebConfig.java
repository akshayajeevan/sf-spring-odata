package com.akshaya.sf.spring.odata.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebConfig.class);
	
	@Autowired
	private SalesforceProperties salesforceConfigurationProperties;
	
	@Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {return HttpClients.createDefault();}
    
    @Bean
	public OAuth2AuthorizedClientManager getAuthorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientService authorizedClientService) {

		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.refreshToken().password().build();

		AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
				clientRegistrationRepository, authorizedClientService);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		Map<String, Object> passwordAttributes = new HashMap<>();
		passwordAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME,
				salesforceConfigurationProperties.getUsername());
		passwordAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME,
				salesforceConfigurationProperties.getPassword());

		authorizedClientManager.setContextAttributesMapper(request -> passwordAttributes);

		return authorizedClientManager;
	}


}
