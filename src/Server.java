import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	
	public static Controller controller = new Controller();
	public static List<ClientThread> clientThreadList = new LinkedList<>();
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		Socket actSocket = null;
		
		try {
			serverSocket = new ServerSocket(9999);
			serverSocket.setReuseAddress(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		
		
		while(true)
		{
			try {
				actSocket = serverSocket.accept();
				ClientThread clientThread = new ClientThread(actSocket);
				clientThread.start();
				clientThreadList.add(clientThread);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}

}
