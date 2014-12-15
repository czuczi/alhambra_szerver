import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


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
		Set<Player> playersToRemove = new HashSet<>();
		Player actPlayer = null;
		Room actRoom = null;
		for(Player player : playerList){
			if(player.getName().equals(playerName)){
				actPlayer = player;
				playersToRemove.add(actPlayer);
				break;
			}
		}
		
		if(!roomList.isEmpty()) {
			for(Room room : roomList) {
				if(room.getPlayerList().contains(actPlayer)) {
					actRoom = room;
					playersToRemove.addAll(room.getPlayerList());
					roomList.remove(actRoom);
					break;
				}
			}
		}
		
		if(!gameList.isEmpty()) {
			for(Game game : gameList) {
				if(game.getRoom().equals(actRoom)) {
					gameList.remove(game);
					break;
				}
			}
		}
		
		playerList.removeAll(playersToRemove);
	}
	
}
