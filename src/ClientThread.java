import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class ClientThread extends Thread{
	
	private Socket mySocket;
	private InputStream is = null;
	private BufferedReader bf = null;
	private DataOutputStream os = null;
	private String interaction;
	private String requestName;

	public ClientThread(Socket mySocket) {
		this.mySocket = mySocket;
	}
	
	public void sendMessage(String message){
		try {
			os.writeBytes(message);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean recieveMessage(BufferedReader bf){
		try {
			interaction = bf.readLine();
			return true;
			
		} catch (IOException e) {
			try {
				mySocket.close();
				is.close();
				bf.close();
				os.close();
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void run() {
		try 
			{
				is = mySocket.getInputStream();
				bf = new BufferedReader(new InputStreamReader(is));
				os = new DataOutputStream(mySocket.getOutputStream());
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
		
		while(true)
		{
			if(!recieveMessage(bf))
				break;
			String[] elements = interaction.split(";");
			requestName = elements[0];
			switch (requestName) {
			case "login":
				if(Server.controller.login(elements[1])) {
					sendMessage("showRoomManagerPage\n");
				}
				else {
					sendMessage("showLoginPage\n");
					System.out.println(elements[1]);
				}
				break;

			default:
				break;
			}
		}

	}
	
}
