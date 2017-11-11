/**
 * 
 */
package com.vraj.playground.mom.server.handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.vraj.playground.mom.logger.MomLogger;
import com.vraj.playground.mom.logger.MomServerLoggerFactory;
import com.vraj.playground.mom.server.app.TicketMaster;
import com.vraj.playground.mom.server.request.MomRequest;
import com.vraj.playground.mom.server.response.MomResponse;

/**
 * 
 * Post request handler.
 * 
 * @author vrajori
 *
 */
public class PostHandler implements RequestHandler {

	private MomLogger LOG = MomServerLoggerFactory.getLogger(PostHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vraj.playground.mom.server.handlers.RequestHandler#handle(com.vraj.
	 * playground.mom.server.request.MomRequest)
	 */
	@Override
	public MomResponse handle(MomRequest request) {
		if (request.getEndpoint().equals("/ticket")) {
			return new TicketMaster().createTicket(request);
		}
		LOG.log("NO_APP_LOGIC_FOUND", "No app configured that can cater to this request.");
		return MomResponse.noAppResponse();
	}

}
