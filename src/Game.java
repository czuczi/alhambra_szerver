import java.util.LinkedList;
import java.util.List;


public class Game {
	private Room room;
	private MoneyDeck moneyDeck;
	private BuildingDeck buildingDeck;
	private MoneyPickerView moneyPickerView;
	private LinkedList<Player> playersOrder;
	private int actPlayerIndex;
	
	public Game(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public LinkedList<Player> getPlayersOrder() {
		return playersOrder;
	}

	public int getActPlayerIndex() {
		return actPlayerIndex;
	}

	public MoneyPickerView getMoneyPickerView() {
		return moneyPickerView;
	}
	
	public void initGameAttributes() {
		
	}
	
	public void giveMoneyToPlayer() {
		
	}
	
	public Player getNextPlayer() {
		return null;
	}
	
	public void evaluation() {
		
	}
}
