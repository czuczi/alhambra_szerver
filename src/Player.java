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
	
	public boolean createRoom(String roomName, int max){
		System.out.println(roomName +"\t\t"+ Server.controller.getRoomList().size());
		for(Room aktRoom : Server.controller.getRoomList()){
			if(aktRoom.getName().equals(roomName)){
				return false;
			}
		}
		room = new Room(roomName, max);
		room.getPlayerList().add(this);
		Server.controller.getRoomList().add(room);
		return true;
	}
	
	public boolean joinRoom(String playerName, String RoomName){
		for (Room aktRoom : Server.controller.getRoomList()) {
			if(aktRoom.getName().equals(RoomName)) {
				if(aktRoom.getPlayerList().size() == aktRoom.getMaxNumber()) {
					return false;
				} else {
					for (Player player : Server.controller.getPlayerList()) {
						if(player.getName().equals(playerName)) {
							aktRoom.getPlayerList().add(player);
							room = aktRoom;
							return true;
						}
					}
				}
				break;
			}
		}
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
