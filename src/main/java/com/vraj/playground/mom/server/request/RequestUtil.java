/**
 * 
 */
package com.vraj.playground.mom.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.vraj.playground.mom.logger.MomLogger;
import com.vraj.playground.mom.logger.MomServerLoggerFactory;

/**
 * Util class for casting mom request objects.
 * 
 * @author vrajori
 *
 */
public class RequestUtil {

	public static final String NO_METHOD = "NONE";

	private static final MomLogger LOGGER = MomServerLoggerFactory.getLogger(RequestUtil.class);

	/**
	 * Methods disects the bufferedReader content and builds a mom request
	 * object
	 * 
	 * @param br
	 * @return
	 * @throws IOException
	 */
	public static MomRequest toMomRequest(BufferedReader br) throws IOException {
		MomRequest request = new MomRequest();
		String lineOne = br.readLine();
		if (StringUtils.isEmpty(lineOne)) {
			return request;
		}
		request.setHttpMethod(extractMethodType(lineOne));
		extractQueryAndEndpoint(request, lineOne);
		extractHeaders(request, br);
		extractPayload(request, br);
		LOGGER.log("REQUEST_LANDED", request);
		return request;
	}

	private static void extractPayload(MomRequest request, BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		while (br.ready()) {
			String line = br.readLine();
			sb.append(line);
		}
		request.setPayload(sb.toString());
	}

	private static void extractHeaders(MomRequest request, BufferedReader br) throws IOException {
		boolean endOfHeaders = false;
		Map<String, String> headers = new HashMap<>();
		while (!endOfHeaders) {
			String headerVal = br.readLine();
			if (StringUtils.isEmpty(headerVal)) {
				endOfHeaders = true;
				continue;
			}
			String[] keyVal = headerVal.split(":");
			headers.put(keyVal[0], keyVal[1]);
		}
		request.setHeaders(headers);
	}

	private static String extractMethodType(String lineOne) {
		if (StringUtils.isEmpty(lineOne)) {
			return NO_METHOD;
		}

		return lineOne.split(" ")[0];
	}

	private static void extractQueryAndEndpoint(MomRequest request, String lineOne) {
		if (StringUtils.isEmpty(lineOne)) {
			return;
		}
		Map<String, String> queryMap = new HashMap<>();
		String url = lineOne.split(" ")[1];
		String query = url.split("\\?")[1];
		String endpoint = url.split("\\?")[0];
		if (StringUtils.isNotEmpty(query)) {
			String[] params = query.split("\\&");
			for (String param : params) {
				String[] keyVal = param.split("=");
				queryMap.put(keyVal[0], keyVal[1]);
			}
		}
		request.setQuery(queryMap);
		request.setEndpoint(endpoint);
	}
}
