import java.util.LinkedList;
import java.util.List;


public class Controller {

	private List<Player> playerList;
	private List<Room> roomList;
	public Controller() {
		playerList = new LinkedList<>();
		roomList = new LinkedList<>();
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public List<Room> getRoomList() {
		return roomList;
	}
	
	public boolean login(){
		return false;
	}
	
	public void logout(){
		
	}
	
}
