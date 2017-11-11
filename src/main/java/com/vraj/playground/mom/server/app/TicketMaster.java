package com.vraj.playground.mom.server.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.vraj.playground.mom.logger.MomLogger;
import com.vraj.playground.mom.logger.MomServerLoggerFactory;
import com.vraj.playground.mom.server.request.MomRequest;
import com.vraj.playground.mom.server.response.MomResponse;

/**
 * This is a sample pluggable app (<b> ticketmaster </b>) imitating the app
 * logic that shall be triggered once the request is received by server and
 * delegated to app biz layer. <br>
 * 
 * <pre>
 * This app serves 3 actions, namely 
 *  - Creates a support ticket and assigns it to a staff member.
 *  - Fetches staff member, given support ticker number.
 *  - Deletes a ticket once completed.
 * </pre>
 * 
 * To make things slightly convinient, this sample app understand
 * {@link MomRequest} structure and expects the same for it to take any action.
 * 
 * 
 * @author vrajori
 *
 */
public class TicketMaster {

	public static Map<String, String> appStorage = new HashMap<>();
	public static final String TICKET_MASTER = "TICKET_MASTER";

	// Leveraging same server logger
	private static MomLogger LOG = MomServerLoggerFactory.getLogger(TicketMaster.class);

	public MomResponse createTicket(MomRequest request) {
		MomResponse response = new MomResponse();
		if (request == null) {
			response.setMessage("Null Request sent");
			Error err = new Error("Null Request sent");
			response.setErrors(Arrays.asList(err));
			response.setStatus("FAIL");
			LOG.log(TICKET_MASTER, "Null Request landed");
			return response;
		}

		String id = request.getQuery().get("id");
		String name = request.getQuery().get("name");

		appStorage.put(id, name);

		response.setStatus("OK");
		response.setMessage("Ticket#: " + id + " assigned to: " + name);
		LOG.log("SUCCESS");
		return response;
	}

	public MomResponse fetchTicketOwner(MomRequest request) {
		MomResponse response = new MomResponse();
		if (request == null) {
			response.setMessage("Null Request sent");
			Error err = new Error("Null Request sent");
			response.setErrors(Arrays.asList(err));
			response.setStatus("FAIL");
			LOG.log(TICKET_MASTER, "Null Request landed");
			return response;
		}

		String owner = appStorage.get(request.getQuery().get("id"));
		response.setStatus("OK");
		String message = "Ticket#: " + request.getQuery().get("id");
		if (StringUtils.isEmpty(owner)) {
			message = message + ", is not assigned to any one yet!";
		} else {
			message = message + " is assigned to: " + owner;
		}
		response.setMessage(message);
		LOG.log("SUCCESS");
		return response;
	}

	public MomResponse deleteTicket(MomRequest request) {
		MomResponse response = new MomResponse();
		if (request == null) {
			response.setMessage("Null Request sent");
			Error err = new Error("Null Request sent");
			response.setErrors(Arrays.asList(err));
			response.setStatus("FAIL");
			LOG.log(TICKET_MASTER, "Null Request landed");
			return response;
		}

		String owner = appStorage.get(request.getQuery().get("id"));
		String message = "Ticket#: " + request.getQuery().get("id");
		if (StringUtils.isEmpty(owner)) {
			message = message + ", unable to delete as its not assigned to any one yet!";
		} else {
			message = message + ", record deleted";
			appStorage.remove(request.getQuery().get("id"));
		}
		response.setMessage(message);
		LOG.log("SUCCESS");
		return response;
	}
}
