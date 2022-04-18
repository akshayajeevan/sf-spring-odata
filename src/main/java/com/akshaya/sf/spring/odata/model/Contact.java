package com.akshaya.sf.spring.odata.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
	public static final String CONTACT_QUERY = "SELECT Name, Title, Department FROM Contact";

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Title")
    private String title;

    @JsonProperty(value = "Department")
    private String department;

    private SalesforceDataAttributes attributes;

    public String getId() {
        if (attributes != null && attributes.getUrl() != null) {
            return StringUtils.substringAfterLast(attributes.getUrl(), "/");
        }
        return null;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public SalesforceDataAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(SalesforceDataAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return String.format("SalesforceAttributes [name=%s, title=%s, department=%s]", name, title, department);
	}


}
