package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
	public static final int PORT_NUMBER = 8081;

	protected Socket socket;
	
	protected String host;
	
	protected boolean isUppercase = false;
	
	protected final ArrayList<String> uppercaseWords = new ArrayList<String>();

	private Server(Socket socket) {
		this.socket = socket;
		host = socket.getInetAddress().getHostAddress();
		System.out.println("New client connected from " + host);
		start();
	}

	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String request;
			
			// input/output logic
			while ((request = br.readLine()) != null) {
				String response = "";
				
				if (request.equals("CONNECT")) {
					// notify client that server is connected to it
					response = "server: got connection from client " + host;
				} else if (request.equals("READY")) {
					// notify client that server is ready
					response = "server: Server is ready";
				} else if (request.equals("UPPERCASE")) {
					// intiate upper case logic
					isUppercase = true;
					response = "200 OK";
					System.out.println(host + " sends UPPERCASE");
				}  else if (request.equals("LOWERCASE")) {
					// TODO implement
					System.out.println(host + " sends LOWERCASE");
				}  else if (request.equals("REVERSE")) {
					// TODO implement
					System.out.println(host + " sends REVERSE");
				}  else if (request.equals("EXIT")) {
					// TODO implement
					System.out.println(host + " sends EXIT");
				} else if (isUppercase) {
					// while upper case is active
					if (request.equals(".")) {
						for (String word: uppercaseWords) {
							response += word.toUpperCase() + "-";
						}
						uppercaseWords.clear(); // empty list
						response = response.substring(0, response.length()); // remove last '-'
						isUppercase = false;
					} else {	
						uppercaseWords.add(request);
					}
				} else {
					response = "400: Not a valid command!";
					System.out.println(response);
				}
				
				response += '\n';
				out.write(response.getBytes());
			}

		} catch (IOException ex) {
			System.out.println(host + " has disconnected");
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Server started");
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT_NUMBER);
			while (true) {
				/**
				 * create a new {@link SocketServer} object for each connection
				 * this will allow multiple client connections
				 */
				new Server(server.accept());
			}
		} catch (IOException ex) {
			System.out.println("Unable to start server.");
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}