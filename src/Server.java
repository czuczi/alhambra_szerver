import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	
	public static Controller controller = new Controller();
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		Socket actSocket = null;
		
		try {
			serverSocket = new ServerSocket(9993);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		
		
		while(true)
		{
			try {
				actSocket = serverSocket.accept();
				new ClientThread(actSocket).start();
					
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}

}
