package chatroom.assignment7.oldfiles;

import chatroom.assignment7.Client;

public class ClientMainv {
	public static void main (String [] args) {
		Client client = new Client();
				
		new Thread(new Runnable () {
			@Override
			public void run() {
				client.runme();
			}
		}).start();
		
	}
}
