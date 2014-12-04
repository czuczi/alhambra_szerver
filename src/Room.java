import java.util.List;


public class Room {
	private String name;
	private int maxNumber;
	private List<Player> playerList;
	
	
	
	public Room(String name, int maxNumber) {
		this.name = name;
		this.maxNumber = maxNumber;
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
		//létrehozol egy game-et, meghívjuk a metódusait
	}
}
