package chatroom.assignment7.oldfiles;

import chatroom.assignment7.Server;

public class ServerMainv {
	public static void main (String [] args) {
		Server server = new Server();
		server.runme();
				
//		// Thread not necessary, since there is only one thread.
//		new Thread(new Runnable () {
//			@Override
//			public void run() {
//				server.runme();
//			}
//		}).start();	
		
	}
}
