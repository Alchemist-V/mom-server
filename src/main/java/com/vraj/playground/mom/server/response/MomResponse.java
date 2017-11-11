/**
 * 
 */
package com.vraj.playground.mom.server.response;

import java.util.Arrays;
import java.util.List;

/**
 * Basic outline of response dispatched from MomServer.
 * 
 * @author vrajori
 *
 */
public class MomResponse {

	private String status;

	private String message;

	private List<Error> errors;

	public static MomResponse noAppResponse() {
		MomResponse response = new MomResponse();
		Error err = new Error("No app logic found for this request");
		response.setStatus("FAIL");
		response.setErrors(Arrays.asList(err));
		response.setMessage("No app logic found for this request");
		return response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "MomResponse [status=" + status + ", message=" + message + ", errors=" + errors + "]";
	}

}
