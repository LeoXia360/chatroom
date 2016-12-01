
package assignment7;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

public class ClientObserver extends PrintWriter implements Observer {
	public ClientObserver(OutputStream out) {
		super(out);
	}
	@Override
	public void update(Observable o, Object arg) {
		String n = (String) arg;
		if(n.contains("send")){
			String temp = n.substring(n.indexOf("send"));
			n = temp.substring(5, temp.indexOf(":"));
			
			
			System.out.println("This is n: " + n);
		}
		this.println(arg); //writer.println(arg);
		this.flush(); //writer.flush();
	}

}