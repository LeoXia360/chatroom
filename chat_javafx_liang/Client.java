package chatroom.chat_javafx_liang;

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 


public class Client extends Application { 
	// IO streams 
	DataOutputStream toServer = null; 
	DataInputStream fromServer = null;
	private String name = null;
	Scanner sc = new Scanner(System.in);
	static ArrayList<String> names = new ArrayList<String>();
	
	public Client(){
		while(true){
			System.out.println("Enter a name for your chat");
			this.name = sc.nextLine();
			System.out.println(names);
			
			//THIS DOESN"T WORK
			if(!names.contains(this.name)){
				names.add(this.name);
				System.out.println(names);
				System.out.println("Your chat room name is now: " + this.name);
				break;
			}
		}
	}


	@Override // Override the start method in the Application class 
	public void start(Stage primaryStage) { 
		// Panel p to hold the label and text field 
		BorderPane paneForTextField = new BorderPane(); 
		paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
		paneForTextField.setStyle("-fx-border-color: green"); 
		paneForTextField.setLeft(new Label("Enter a command or message: ")); 

		TextField tf = new TextField(); 
		tf.setAlignment(Pos.BOTTOM_RIGHT); 
		paneForTextField.setCenter(tf); 

		BorderPane mainPane = new BorderPane(); 
		// Text area to display contents 
		TextArea ta = new TextArea(); 
		mainPane.setCenter(new ScrollPane(ta)); 
		mainPane.setTop(paneForTextField); 


		// Create a scene and place it in the stage 
		Scene scene = new Scene(mainPane, 450, 200); 
		primaryStage.setTitle("Client"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 

		tf.setOnAction(e -> { 
			try { 
				// Get the radius from the text field 
				//double radius = Double.parseDouble(tf.getText().trim()); 
				String message = tf.getText();
				//if command is "send <name>" then it will send to the client name 
				if(message.toLowerCase().contains("send")){
					//still need to check if this is a valid name!
					toServer.writeUTF(message.substring(5).toUpperCase());
					toServer.flush();
				}else{
					//send an actual message to the server
					toServer.writeUTF(message); 
					toServer.flush(); 
				}

//				// Get area from the server 
				String area = fromServer.readUTF(); 
//
//				// Display to the text area 
				ta.appendText("Radius is " + area + "\n"); 
				ta.appendText("Area received from the server is "
						+ area + '\n');

			} 
			catch (IOException ex) { 
				System.err.println(ex); 
			} 
		}); 

		try { 
			// Create a socket to connect to the server 
			@SuppressWarnings("resource")
			Socket socket = new Socket("localhost", 8002); 
			// Socket socket = new Socket("130.254.204.36", 8000); 
			// Socket socket = new Socket("drake.Armstrong.edu", 8000); 

			// Create an input stream to receive data from the server 
			fromServer = new DataInputStream(socket.getInputStream()); 

			// Create an output stream to send data to the server 
			toServer = new DataOutputStream(socket.getOutputStream()); 
		} 
		catch (IOException ex) { 
			ta.appendText(ex.toString() + '\n');
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
