import java.util.LinkedList;
import java.util.List;

public class Player {

	private String name;
	private int score;
	private StorageArea storageArea;
	private BuildingArea buildingArea;
	private List<MoneyCard> moneyCards;
	private Room room;
	private Game game;
	
	public Player(String name) {
		this.name = name;
		storageArea = new StorageArea();
		buildingArea = new BuildingArea();
		moneyCards = new LinkedList<>();
		score = 0;
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
		this.room.getPlayerList().remove(this);
		if(this.room.getPlayerList().isEmpty()){
			for(Room aktRoom : Server.controller.getRoomList()){
				if(aktRoom.getName().equals(this.getRoom().getName())){
					Server.controller.getRoomList().remove(aktRoom);
				}
			}
		}
		this.room = null;
	}
	
	public void pickMoneyCard(MoneyCard moneyCard){
		game.getMoneyPickerView().removeMoneyCard(moneyCard);
		moneyCards.add(moneyCard);
	}
	
	public boolean rebuildAlhambraAdd(BuildingCard buildingCard, int a, int b){
		if(buildingArea.canAddBuildingCard(buildingCard, a, b)) {
			buildingArea.addBuildingCard(buildingCard, a, b);
			storageArea.removeBuildingCard(buildingCard);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean rebuildAlhambraRemove(int a, int b){
		if(buildingArea.canRemoveBuildingCard(a, b)) {
			storageArea.addBuildingCard(buildingArea.getBuildingArea()[a][b]);
			buildingArea.removeBuildingCard(a, b);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean rebuildAlhambraSwitch(BuildingCard buildingCard, int a, int b){
		BuildingCard cardSeged = buildingArea.getBuildingArea()[a][b].clone();
		buildingArea.removeBuildingCard(a, b);
		if(buildingArea.canAddBuildingCard(buildingCard, a, b)) {
			buildingArea.addBuildingCard(buildingCard, a, b);
			storageArea.removeBuildingCard(buildingCard);
			return true;
		} else {
			buildingArea.getBuildingArea()[a][b] = cardSeged;
			return false;
		}
	}
	
	public boolean buyBuildingCardToStorageArea(BuildingCard buildingCard){
		int moneyInGivenType = 0;
		String moneyType = null;
		for (String type : game.getBuildingMarket().getBuildingMarket().keySet()) {
			if(game.getBuildingMarket().getBuildingMarket().get(type).equals(buildingCard)) {
				moneyType = type;
			}
		}
		for (MoneyCard moneyCard : moneyCards) {
			if(moneyCard.getType().equals(moneyType)) {
				moneyInGivenType += moneyCard.getValue();
			}
		}
		if(moneyInGivenType < buildingCard.getValue()) {
			return false;
		} else {
			storageArea.addBuildingCard(buildingCard);
			game.getBuildingMarket().removeBuildingCard(buildingCard);
			return true;
		}
	}
	
	public boolean buyBuildingCardToAlhambra(BuildingCard buildingCard, int a, int b){
		int moneyInGivenType = 0;
		String moneyType = null;
		for (String type : game.getBuildingMarket().getBuildingMarket().keySet()) {
			if(game.getBuildingMarket().getBuildingMarket().get(type).equals(buildingCard)) {
				moneyType = type;
			}
		}
		for (MoneyCard moneyCard : moneyCards) {
			if(moneyCard.getType().equals(moneyType)) {
				moneyInGivenType += moneyCard.getValue();
			}
		}
		if(moneyInGivenType < buildingCard.getValue()) {
			return false;
		} else {
			if(buildingArea.canAddBuildingCard(buildingCard, a, b)) {
				buildingArea.addBuildingCard(buildingCard, a, b);
				game.getBuildingMarket().removeBuildingCard(buildingCard);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public List<Integer> getNumberOfBuildingCards(){
		
		BuildingArea ba = this.buildingArea;
		BuildingCard [][] table = new BuildingCard [21][21];
		table = ba.getBuildingArea();
		
		int blue = 0;
		int red = 0;
		int brown = 0;
		int white = 0;
		int green = 0;
		int purple = 0;
		
		for(int i = 0; i < 21;i++)
		{
			for(int j = 0; j < 21; j++)
			{
				if(table[i][j].getType().equals("blue"))
					blue++;
				else if(table[i][j].getType().equals("red"))
					red++;
				else if(table[i][j].getType().equals("brown"))
					brown++;
				else if(table[i][j].getType().equals("white"))
					white++;
				else if(table[i][j].getType().equals("green"))
					green++;
				else if(table[i][j].getType().equals("purple"))
					purple++;
			}
		}
		
		List<Integer> list = new LinkedList<>();
		list.add(blue);
		list.add(red);
		list.add(brown);
		list.add(white);
		list.add(green);
		list.add(purple);
		
		return list;
		
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void incrementScore(int score) {
		this.score += score;
	}
	
	
}
