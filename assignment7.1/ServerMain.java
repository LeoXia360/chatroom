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

import assignment7.HandleAClient;

public class ServerMain extends Application {
	private int portNumber = 8101;
	private TextArea ta = new TextArea(); 
	// Number a client 
	private int clientNo = 0; 
	
	public static void main (String [] args) {
		launch(args);
	}
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage){
		// Create a scene and place it in the stage 
		Scene scene = new Scene(new ScrollPane(ta), 450, 200); 
		primaryStage.setTitle("MultiThreadServer"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage
		
		new Thread( () -> { 
			try {  // Create a server socket 
				ServerSocket serverSocket = new ServerSocket(portNumber); 
				ta.appendText("MultiThreadServer started at " 
						+ new Date() + '\n'); 


				while (true) { 
					// Listen for a new connection request 
					Socket socket = serverSocket.accept(); 

					// Increment clientNo 
					clientNo++; 

					Platform.runLater( () -> { 
						// Display the client number 
						ta.appendText("Starting thread for client " + clientNo +
								" at " + new Date() + '\n'); 

						// Find the client's host name, and IP address 
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is "
								+ inetAddress.getHostName() + "\n");
						ta.appendText("Client " + clientNo + "'s IP Address is " 
								+ inetAddress.getHostAddress() + "\n");	}); 


					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket, ta)).start();
				} 
			} 
			catch(IOException ex) { 
				System.err.println(ex);
			}
		}).start();
	}
	
	public int getportNumber (){
		return this.portNumber;
	}
}
