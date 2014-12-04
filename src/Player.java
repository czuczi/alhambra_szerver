import java.util.LinkedList;
import java.util.List;

public class Player {

	private String name;
	private int score;
	private BuildingMarket buildingMarket;
	private StorageArea storageArea;
	private BuildingArea buildingArea;
	private List<MoneyCard> moneyCards;
	private Room room;
	
	public Player(String name) {
		this.name = name;
		buildingMarket = new BuildingMarket();
		storageArea = new StorageArea();
		buildingArea = new BuildingArea();
		moneyCards = new LinkedList<>();
	}
	
	public boolean createRoom(){
		
		return false;
		
	}
	
	public boolean joinRoom(){
		
		return false;
		
	}
	
	public void leaveRoom(){
		
	}
	
	public MoneyCard pickMoneyCard(){
		return null;
	}
	
	public boolean rebuildAlhambraAdd(){
		return false;
	}
	
	public boolean rebuildAlhambraRemove(){
		return false;
	}
	
	public boolean rebuildAlhambraSwitch(){
		return false;
	}
	
	public boolean buyBuildingCardToStorageArea(){
		return false;
	}
	
	public boolean buyBuildingCardToAlhambra(){
		return false;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public BuildingMarket getBuildingMarket() {
		return buildingMarket;
	}

	public StorageArea getStorageArea() {
		return storageArea;
	}

	public BuildingArea getBuildingArea() {
		return buildingArea;
	}

	public List<MoneyCard> getMoneyCards() {
		return moneyCards;
	}

	public Room getRoom() {
		return room;
	}
	
}
