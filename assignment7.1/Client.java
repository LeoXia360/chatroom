package assignment7;

import java.awt.Button;
import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage; 

public class Client extends Application{
	int port = 8001;
	String host = "localhost";
	DataInputStream in;
	DataOutputStream out;
	Socket socket;
	double aNumber = 5;
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	String name;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		makeChatBox(primaryStage);
	}
	
//	public void promteName (Stage primaryStage){
//		GridPane root = new GridPane();
//		root.setVgap(10);
//		root.setHgap(10);
//		root.setAlignment(Pos.CENTER);
//		
//		TextField getName = new TextField();
//		Label promoteName = new Label("Enter Name: ");
//		
//		Button enterChat = new Button("Enter Chat");
//		enterChat.setOnAction(new EventHandler<ActionEvent>(){
//			@Override
//			public void handle(ActionEvent event){
//				
//			}
//		}
//	}
	
	public void makeChatBox(Stage primaryStage){
		// Panel p to hold the label and text field 
		BorderPane paneForTextField = new BorderPane(); 
		paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
		paneForTextField.setStyle("-fx-border-color: green");  

		TextField tf = new TextField(); 
		tf.setAlignment(Pos.BOTTOM_LEFT); 
		paneForTextField.setCenter(tf); 

		BorderPane mainPane = new BorderPane(); 
		// Text area to display contents 
		TextArea ta = new TextArea(); 
		mainPane.setCenter(new ScrollPane(ta)); 
		mainPane.setBottom(paneForTextField); 


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

				// Send the radius to the server 
				toServer.writeUTF(message); 
				toServer.flush(); 

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
			Socket socket = new Socket("localhost", 8101);  

			// Create an input stream to receive data from the server 
			fromServer = new DataInputStream(socket.getInputStream()); 

			// Create an output stream to send data to the server 
			toServer = new DataOutputStream(socket.getOutputStream()); 
		} 
		catch (IOException ex) { 
			ta.appendText(ex.toString() + '\n');
		}
	}
}
