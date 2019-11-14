package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String args[]) {
		String host = "127.0.0.1";
		int port = 8081;
		new Client(host, port);
	}

	public Client(String host, int port) {
		try {
			String serverHostname = new String("127.0.0.1");

			System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");

			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;

			try {
				echoSocket = new Socket(serverHostname, 8081);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + serverHostname);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Unable to get streams from server");
				System.exit(1);
			}

			/** {@link UnknownHost} object used to read from console */
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			// server got the connection
			out.println("CONNECT");
			System.out.println(in.readLine());
			
			// server is ready
			out.println("READY");
			System.out.println(in.readLine());

			// input/output logic
			while (true) {
				System.out.print("client: ");
				String userInput = stdIn.readLine();
				if ("EXIT".equals(userInput)) {
					break;
				}
				out.println(userInput);
				String serverInput = in.readLine();
				if (!serverInput.equals("")) {
					if (serverInput.contains("-")) {
						String[] words = serverInput.split("-");
						
						for (String word : words) {
							System.out.println("server: " + word);
						}
					} else {
						System.out.println("server: " + serverInput);
					}
					
				}
			}

			/** Closing all the resources */
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
