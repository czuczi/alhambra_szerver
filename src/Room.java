import java.util.LinkedList;
import java.util.List;


public class Room {
	private String name;
	private int maxNumber;
	private List<Player> playerList;
	
	
	
	public Room(String name, int maxNumber) {
		this.name = name;
		this.maxNumber = maxNumber;
		this.playerList = new LinkedList<>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	public List<Player> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	
	public void startGame() {
		Game game = new Game(this);
		game.initGameAttributes();
		
		Server.controller.getGameList().add(game);
	}
}
