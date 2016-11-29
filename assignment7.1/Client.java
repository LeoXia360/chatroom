package assignment7;

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

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 

public class Client {
	int port = 8001;
	String host = "localhost";
	DataInputStream in;
	DataOutputStream out;
	Socket socket;
	double aNumber = 5;

	
	void runme () {
		Scanner sc = new Scanner(System.in);
		
		try {
			// Define client socket, and initialize in and out streams.
			socket = new Socket(host, port);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			Double msg;
			while (true) {
				try {
					// ask user to enter a double
					System.out.print("Enter a number to send to server: ");
					msg = sc.nextDouble();
				} catch (Exception e) {
					System.out.println("Try again.");
					continue;
				}
				
				// send the double to the server
				out.writeDouble(msg);
				out.flush();
				
				// read the server's response, and print it out.
				System.out.println("Client: The server says the square is: " + 
						in.readDouble());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
