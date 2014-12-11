import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Receiver;

public class ClientThread extends Thread {

	private Socket mySocket;
	private InputStream is = null;
	private BufferedReader bf = null;
	private DataOutputStream os = null;
	private String interaction = "";
	private String requestName;

	private String nickName;

	public ClientThread(Socket mySocket) {
		this.mySocket = mySocket;
	}

	public void sendMessage(String message) {
		try {
			os.writeBytes(message+"\n");
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean recieveMessage(BufferedReader bf) {
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
		
		try {
			is = mySocket.getInputStream();
			bf = new BufferedReader(new InputStreamReader(is));
			os = new DataOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			if(!recieveMessage(bf)){
				break;
			}
			String[] elements = interaction.split(";");
			requestName = elements[0];
			switch (requestName) {
			
			case "login":
				if (Server.controller.login(elements[2])) {										//LOGIN KÉRELEM. SIKERES
					System.out.println("login success");
					nickName = elements[2].toString();
					
					String roomNamesList = "";	//szoba lista
					for(Room room : Server.controller.getRoomList()){
						roomNamesList += ";"+room.getName();
					}
					
					sendMessage("showRoomManagerPage;" + elements[1] +roomNamesList);
				} else {																		//LOGIN KÉRELEM. SIKERTELEN
					sendMessage("showLoginPage;" + elements[1]);
					System.out.println("login failed");
				}
				break;
				
			case "logout":																		//LOGOUT
				System.out.println("logout success");
				Server.controller.logout(nickName);
				sendMessage("showLoginPage;" + elements[1]);
				break;
				
			case "newRoom":																		//NEW ROOM
				boolean isSuccess = false;
				Player player = null;
				for(Player aktPlayer : Server.controller.getPlayerList()){
					if(aktPlayer.getName().equals(nickName)){
						isSuccess = aktPlayer.createRoom(elements[2], Integer.parseInt(elements[3]));
						player = aktPlayer;
					}
				}
				if(isSuccess){
					String playerNamesInRoom = "";
					for(Player aktPlayer : player.getRoom().getPlayerList()){
						playerNamesInRoom += ";"+aktPlayer.getName();
					}
					sendMessage("showRoomPage;"+elements[1]+playerNamesInRoom);
				} else{
					sendMessage("showRoomManagerPage;"+elements[1]+";newRoom");
				}
				break;
				
			case "connectRoom":
				isSuccess = false;
				player = null;
				for(Player aktPlayer : Server.controller.getPlayerList()){
					if(aktPlayer.getName().equals(nickName)){
						isSuccess = aktPlayer.joinRoom(nickName, elements[2]);
						player = aktPlayer;
					}
				}
				if(isSuccess){
					String playerNamesInRoom = "";
					for(Player aktPlayer : player.getRoom().getPlayerList()){
						playerNamesInRoom += ";"+aktPlayer.getName();
					}
					sendMessage("showRoomPage;"+elements[1]+playerNamesInRoom);
				} else{
					sendMessage("showRoomManagerPage;"+elements[1] + ";joinRoom");
				}
				break;
				
			default:
				break;
			}
		}

	}

}
