package assignment7;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
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

public class ChatClient extends Application{
	TextField outgoing;
	TextArea incoming;
	private BufferedReader reader;
	PrintWriter writer;


	// IO streams 
	DataOutputStream toServer = null; 
	DataInputStream fromServer = null;
	private String name = null;
	Scanner sc = new Scanner(System.in);
	static ArrayList<String> names = new ArrayList<String>();
	

	@Override // Override the start method in the Application class 
	public void start(Stage primaryStage) { 
		// Panel p to hold the label and text field 
		try {
			setUpNetworking();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(10);
		root.setAlignment(Pos.CENTER);
		
		TextField getName = new TextField();
		Label nameLabel = new Label("Enter Name: ");
		
		Button startChat = new Button("Start Chat");
		startChat.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent Event){
				name = getName.getText();
				primaryStage.close();
				System.out.println(name);
				BorderPane paneForTextField = new BorderPane(); 
				paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
				paneForTextField.setStyle("-fx-border-color: green"); 
				paneForTextField.setLeft(new Label("Enter a message: ")); 

				outgoing = new TextField(); 
				outgoing.setAlignment(Pos.BOTTOM_LEFT); 
				paneForTextField.setCenter(outgoing); 

				BorderPane mainPane = new BorderPane(); 
				// Text area to display contents 
				incoming = new TextArea(); 
				mainPane.setCenter(new ScrollPane(incoming)); 
				mainPane.setBottom(paneForTextField); 


				// Create a scene and place it in the stage 
				Scene scene = new Scene(mainPane, 450, 200); 
				primaryStage.setTitle("Client"); // Set the stage title 
				primaryStage.setScene(scene); // Place the scene in the stage 
				primaryStage.show(); // Display the stage 

				outgoing.setOnAction(e -> { 
					try {
						//writer.println(e);
						//writer.flush();
						//System.out.println(outgoing.getText());
						
						writer.println(name + ": " + outgoing.getText());
						writer.flush();
						outgoing.setText("");
						outgoing.requestFocus();
						
						//outgoing.setText("");
						//outgoing.requestFocus();
						//System.out.println("printing");
						//new Client().run();
					} catch (Exception b) {
						b.printStackTrace();
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
					incoming.appendText(ex.toString() + '\n');
				}
			}
		});
		
		root.add(getName, 1, 0);
		root.add(nameLabel, 0, 0);
		root.add(startChat, 0, 1);
		primaryStage.setScene(new Scene(root, 400, 200));
		primaryStage.show();
	}
	
	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("127.0.0.1", 4242);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}

	class SendButtonListener{
		public void actionPerformed(ActionEvent ev) {
			writer.println(outgoing.getText());
			writer.flush();
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}

	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					
						incoming.appendText(message + "\n");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
