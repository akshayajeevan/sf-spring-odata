package com.akshaya.sf.spring.odata.utils;

import org.apache.commons.lang3.StringUtils;

import com.akshaya.sf.spring.odata.configs.SalesforceProperties;

public class SalesforceDataUtils {
	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SalesforceDataUtils.class);
	private SalesforceDataUtils() {	}
	
	/**
	 * Verify the object is allowed to query
	 * @param objectName name of the salesforce object
	 * @param salesforceProperties SalesforceProperties class instance
	 * @return {@code true} if allowed otherwise {@code false}
	 * @throws Exception
	 */
	public static boolean isObjectAllowed(String objectName, SalesforceProperties salesforceProperties) throws Exception {
		if(StringUtils.isBlank(objectName)) return false;
		return salesforceProperties.getAllowedSobjects().contains(objectName);
	}
	
	/**
	 * Build SOQL query string to get all fields of give object with LIMIT 200
	 * @param objectName name of the salesforce object
	 * @return query string
	 * @throws Exception
	 */
	public static String allSOQLBuilder(String objectName) throws Exception {
		//SELECT FIELDS(ALL) FROM Account LIMIT 200
		StringBuilder queryString = new StringBuilder("SELECT FIELDS(ALL) FROM ");
		queryString.append(objectName + " ");
		queryString.append("LIMIT 200");
		return queryString.toString();
	}
	
	public static String selectedSOQLBuilder(String objectName, String selectedFields) throws Exception {
		StringBuilder queryString = new StringBuilder("SELECT " + selectedFields + " FROM ");
		queryString.append(objectName + " ");
		return queryString.toString();
	}

}
