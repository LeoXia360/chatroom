package chatroom.assignment7;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import chatroom.assignment7.ChatServer.ClientHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; 
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage; 


public class StartServer extends Application{
	private TextArea ta = new TextArea(); 
	private String[] text = new String[]{"a","b","c"};

	// Number a client 
	private int clientNo = 0; 
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		// Create a scene and place it in the stage 
		Scene scene = new Scene(new ScrollPane(ta), 450, 200); 
		primaryStage.setTitle("MultiThreadServer"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 
	
		
		
		new Thread( () -> { 
			try {  // Create a server socket
				
				ChatServer.main(text);
				ServerSocket serverSocket = new ServerSocket(8000); 
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
					new Thread(new ClientHandler(socket)).start();
				} 
			}
			catch(Exception e) { 
				System.err.println(e);
			}
		}).start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
