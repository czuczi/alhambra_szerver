import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread extends Thread{
	
	private Socket mySocket;
	private InputStream is = null;
	private BufferedReader bf = null;
	private DataOutputStream os = null;
	private String interaction;
	private String requestName;

	public ClientThread(Socket mySocket) {
		super();
		this.mySocket = mySocket;
	}
	
	public void sendMessage(String message){
		try {
			os.writeBytes(message);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void recieveMessage(BufferedReader bf){
		try {
			interaction = bf.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
			recieveMessage(bf);
			String[] elements = interaction.split(";");
			requestName = elements[0];
			switch (requestName) {
			case "login":
				System.out.println(elements[1]);
				break;

			default:
				break;
			}
		}
		
	}
	
}
