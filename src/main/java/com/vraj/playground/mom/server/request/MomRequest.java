/**
 * 
 */
package com.vraj.playground.mom.server.request;

import java.util.Map;

/**
 * Basic outline of all request landing on mom server.
 * 
 * @author vrajori
 *
 */
public class MomRequest {

	private String httpMethod;

	private Map<String, String> headers;

	private Map<String, String> query;

	private String endpoint;

	private String payload;

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "MomRequest [httpMethod=" + httpMethod + ", headers=" + headers + ", query=" + query + ", endpoint="
				+ endpoint + ", payload=" + payload + "]";
	}

}
