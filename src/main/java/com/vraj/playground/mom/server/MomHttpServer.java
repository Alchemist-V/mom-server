/**
 * 
 */
package com.vraj.playground.mom.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;

import com.vraj.playground.mom.logger.MomLogger;
import com.vraj.playground.mom.logger.MomServerLoggerFactory;
import com.vraj.playground.mom.server.handlers.DeleteHandler;
import com.vraj.playground.mom.server.handlers.GetHandler;
import com.vraj.playground.mom.server.handlers.PostHandler;
import com.vraj.playground.mom.server.handlers.RequestHandler;
import com.vraj.playground.mom.server.request.MomRequest;
import com.vraj.playground.mom.server.request.RequestUtil;
import com.vraj.playground.mom.server.response.MomResponse;

/**
 * Entry point for http calls.
 * 
 * @author vrajori
 *
 */
public class MomHttpServer {

	public static final int PORT = 9005;
	public static MomLogger LOGGER = MomServerLoggerFactory.getLogger(MomHttpServer.class);

	private static ServerSocket serverSocket;
	private static Socket socket;

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(PORT);
			LOGGER.log("SERVER_SOCKET_CREATED", "Server socket created on port: " + PORT);
		} catch (IOException e) {
			LOGGER.log("SERVER_SOCKET_CREATION_FAILED", "Exception while creating server socket on port: " + PORT,
					e.getMessage());
		}

		for (;;) {
			BufferedReader br = null;
			BufferedWriter bw = null;
			try (Socket socket = serverSocket.accept()) {
				LOGGER.log("SOCKET_CREATED", "Socket created on port: " + PORT);
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
				br = new BufferedReader(isr);
				String response = disectAndDelegate(br).toString();
				LOGGER.log("RESPONSE", response);
				socket.getOutputStream().write(response.getBytes("UTF-8"));
				br.close();
			} catch (IOException e) {
				LOGGER.log("INPUT_STREAM_READ_FAILURE",
						"Exception while reading input stream on server socket: " + serverSocket.toString(),
						e.getMessage());
				continue;
			} catch (Exception ex) {
				LOGGER.log("INPUT_STREAM_READ_FAILURE",
						"Exception while reading input stream on server socket: " + serverSocket.toString(),
						ex.getMessage());
				continue;
			}
		}
	}

	/**
	 * Method that disects the request landed and delegates processing to
	 * appropriate handler
	 * 
	 * @param BufferedReader
	 * @throws IOException
	 * 
	 */
	private static String disectAndDelegate(BufferedReader br) throws IOException {

		// Disect the request.
		MomRequest request = RequestUtil.toMomRequest(br);

		// Delegate to request Handlers.
		RequestHandler handler;
		switch (request.getHttpMethod()) {
		case "POST":
			handler = new PostHandler();
			return handler.handle(request).toString();
		case "GET":
			handler = new GetHandler();
			return handler.handle(request).toString();
		case "DELETE":
			handler = new DeleteHandler();
			return handler.handle(request).toString();
		}
		return MomResponse.noAppResponse().toString();
	}

}
