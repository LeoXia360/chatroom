package assignment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

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
import javafx.stage.Stage;

public class ChatServer extends Observable {
	private TextArea ta;
	static HashMap<String, ClientObserver> h = new HashMap<String, ClientObserver>();
	//static ArrayList<ClientObserver> client_array = new ArrayList<ClientObserver>();
	public ChatServer() {
		try {
			setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSock = new ServerSocket(4242);
		while (true) {
			Socket clientSocket = serverSock.accept();
			ServerMain.newClient(clientSocket);
			ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
			//client_array.add(writer);
			h.put(key, writer);
			Thread t = new Thread(new ClientHandler(clientSocket));
			t.start();
			this.addObserver(writer);
			//asdfasd
			System.out.println("got a connection");
		}
	}
	class ClientHandler implements Runnable {
		private BufferedReader reader;

		public ClientHandler(Socket clientSocket) {
			Socket sock = clientSocket;
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("server read "+message);
					setChanged();
					notifyObservers(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}