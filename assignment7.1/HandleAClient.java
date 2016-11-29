package assignment7;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HandleAClient implements Runnable{
	private Socket socket;
	private TextArea ta;
	
	public HandleAClient(Socket socket, TextArea ta){
		this.socket = socket;
		this.ta = ta;
	}
	
	public void run(){
		try {
			// Create data input and output streams
			DataInputStream inputFromClient = new DataInputStream( socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream( socket.getOutputStream());
			// Continuously serve the client
			while (true) { 
				// Receive radius from the client 
				String message = inputFromClient.readUTF();
				System.out.println(message);
				
				outputToClient.writeUTF(message);
				Platform.runLater(() -> { 
					ta.appendText("message received from client: " +
							message + '\n'); 
				});
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
