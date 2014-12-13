import java.util.LinkedList;
import java.util.List;


public class Controller {

	private List<Player> playerList;
	private List<Room> roomList;
	private List<Game> gameList;
	
	public Controller() {
		playerList = new LinkedList<>();
		roomList = new LinkedList<>();
		gameList = new LinkedList<>();
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public List<Room> getRoomList() {
		return roomList;
	}
	
	public List<Game> getGameList() {
		return gameList;
	}
	
	public boolean login(String playerName){
		List<String> playerNames = new LinkedList<>();
		for(Player player : playerList) {
			playerNames.add(player.getName());
		}
		if(playerNames.contains(playerName)) {
			return false;
		}
		else {
			Player p = new Player(playerName);
			playerList.add(p);
			return true;
		}
	}
	
	public void logout(String playerName){
		for(Player player : playerList){
			if(player.getName().equals(playerName)){
				playerList.remove(player);
			}
		}
	}
	
}
