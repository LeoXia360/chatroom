package assignment7;

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


public class StartServer extends Application{
	private TextArea ta = new TextArea(); 
	private String[] text = new String[]{"a","b","c"};
	
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
