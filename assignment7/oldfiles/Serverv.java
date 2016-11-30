package chatroom.assignment7.oldfiles;

/*
 * Author: Vallath N.
 * Simple demonstration of client and server communicating with doubles.
 */
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.logging.Handler;

public class Serverv {

	int port = 9001;
	DataInputStream in;
	DataOutputStream out;
	ServerSocket server;
	Socket socket;
	private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();


	void runme() {
		try {
			// Create a server socket, and define in and out streams for it
			server = new ServerSocket(port);
			socket = server.accept();
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			BufferedReader d = new BufferedReader(new InputStreamReader(in));
			
			// loop keeps reading from client, squares it, and sends the result
			// back to the client.
			while (true) {
				String input = d.readLine();
				System.out.println(input);
				if(input == null || input.equals("exitchat")){
					System.out.println("exiting chat");
					break;
				}
				Double msg = in.readDouble();
				System.out.println("Server received " + msg);
				out.writeDouble(msg*msg);
				out.flush();
				System.out.println("Server: I wrote " + msg*msg + " to client.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
