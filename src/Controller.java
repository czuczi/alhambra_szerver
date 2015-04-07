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

//JAVÍTVA
	public List<Player> logout(String playerName){
		List<Player> playersBackToRoomManagerPage = new LinkedList<Player>();
		Player actPlayer = null;
		Room actRoom = null;
		for(Player player : playerList){				//ki lépett ki
			if(player.getName().equals(playerName)){
				actPlayer = player;
				break;
			}
		}
		
		if (!roomList.isEmpty()) {
			for (Room room : roomList) { // kiket kell visszaléptetni a roomMangerPage-re
				if (room.getPlayerList().contains(actPlayer)) {
					if(actPlayer.getGame() != null){			//játékban volt
						actRoom = room;
						playersBackToRoomManagerPage.addAll(room.getPlayerList());
						playersBackToRoomManagerPage.remove(actPlayer);
						roomList.remove(actRoom);
						break;
					}else{										//ha csak a szoba nézetből lép ki x-el
						room.getPlayerList().remove(actPlayer);
						if(room.getPlayerList().size() == 0){		//ha az utolsó ember lép ki kijelentkezéssel
							roomList.remove(room);
						}
						break;
					}
				}
			}
		}

		if (!gameList.isEmpty()) {
			for (Game game : gameList) {
				if (game.getRoom().equals(actRoom)) {
					gameList.remove(game);
					break;
				}
			}
		}
		playerList.remove(actPlayer);
		return playersBackToRoomManagerPage;
	}
	
}
