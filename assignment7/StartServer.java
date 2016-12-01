package chatroom.assignment7;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
	private static TextArea ta = new TextArea(); 
	private String[] text = new String[]{"a","b","c"};
	static Socket socket;
	private static int clientNo = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		// Create a scene and place it in the stage 
		Scene scene = new Scene(new ScrollPane(ta), 450, 200); 
		primaryStage.setTitle("Leo's Server"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 
		new Thread( () -> { 

			try {  // Create a server socket
				System.out.println("here1");
				//STUCK IN AN INFINITE LOOP
				System.out.println("here2");
				ServerSocket serverSocket = new ServerSocket(8000); 
				System.out.println("here");
				ta.appendText("MultiThreadServer started at " 
						+ new Date() + '\n'); 
				ChatServer server = new ChatServer();
				
				while (true) { 
					// Listen for a new connection request 

					// Increment clientNo 
					

				}

			}
			catch(Exception e) { 
				System.err.println(e);
			}
		}).start();
	}
	
	
	public static void newClient(Socket socket){
		clientNo++; 
		System.out.println("New client added");

			// Display the client number 
			ta.appendText("Starting thread for client " + clientNo +
					" at " + new Date() + '\n'); 

			// Find the client's host name, and IP address 
			InetAddress inetAddress = socket.getInetAddress();
			ta.appendText("Client " + clientNo + "'s host name is "
					+ inetAddress.getHostName() + "\n");
			ta.appendText("Client " + clientNo + "'s IP Address is " 
					+ inetAddress.getHostAddress() + "\n");
	}
	
	public static void main(String[] args){
		launch(args);
	}

}
