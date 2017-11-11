/**
 * 
 */
package com.vraj.playground.mom.server.handlers;

import com.vraj.playground.mom.server.request.MomRequest;
import com.vraj.playground.mom.server.response.MomResponse;

/**
 * Mom server handler contract for all http requests that lands on this server.
 * 
 * @author vrajori
 *
 */
public interface RequestHandler {

	/**
	 * Handles request and generates response.
	 * 
	 * @param request
	 * @return
	 */
	public MomResponse handle(MomRequest request);
}
