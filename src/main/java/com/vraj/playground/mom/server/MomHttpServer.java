/**
 * 
 */
package com.vraj.playground.mom.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.vraj.playground.mom.logger.MomLogger;
import com.vraj.playground.mom.logger.MomServerLoggerFactory;

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
			try (Socket socket = serverSocket.accept()) {
				LOGGER.log("SOCKET_CREATED", "Socket created on port: " + PORT);
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);

			} catch (IOException e) {
				LOGGER.log("INPUT_STREAM_READ_FAILURE",
						"Exception while reading input stream on server socket: " + serverSocket.toString(),
						e.getMessage());
				continue;
			}
		}
	}

}
