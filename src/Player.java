import java.util.LinkedList;
import java.util.List;

public class Player {

	private String name = null;
	private int score;
	private StorageArea storageArea = null;
	private BuildingArea buildingArea = null;
	private List<MoneyCard> moneyCards = null;
	private Room room = null;
	private Game game = null;
	private boolean megegyszerjohet;
	
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
			storageArea.addBuildingCard(cardSeged);
			return true;
		} else {
			buildingArea.getBuildingArea()[a][b] = cardSeged;
			return false;
		}
	}
	
	public boolean buyBuildingCardToStorageArea(BuildingCard buildingCard){
		storageArea.addBuildingCard(buildingCard);
		game.getBuildingMarket().removeBuildingCard(buildingCard);
		
		return true;
	}
	
	public int buyGiftToStorageArea(int i){
		storageArea.addBuildingCard(game.getGiftCardsOfPlayers().get(name).get(i));
		game.getGiftCardsOfPlayers().get(name).remove(i);
		
		return game.getGiftCardsOfPlayers().get(name).size();
	}
	
	public int buyGiftToAlhambra(BuildingCard bc, int x, int y, int giftIndex){
		if(buildingArea.canAddBuildingCard(bc, x, y)){			//ha megfelel az építési szabályoknak (ha sikeres, listaméretet ad vissza, ha nem, -1-et)
			buildingArea.addBuildingCard(bc, x, y);
			game.getGiftCardsOfPlayers().get(name).remove(giftIndex);	
			
			return game.getGiftCardsOfPlayers().get(name).size();
		}else{
			return -1;
		}
	}
	
	public boolean buyBuildingCardToAlhambra(BuildingCard buildingCard, int a, int b){
		if(buildingArea.canAddBuildingCard(buildingCard, a, b)) {
			System.out.println("HOZZÁADTA AZ ALHAMBRÁHOZ");
			buildingArea.addBuildingCard(buildingCard, a, b);
			game.getBuildingMarket().removeBuildingCard(buildingCard);
			return true;
		} else {
			return false;
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
				if(table[i][j] != null && table[i][j].getType().equals("blue"))
					blue++;
				else if(table[i][j] != null && table[i][j].getType().equals("red"))
					red++;
				else if(table[i][j] != null && table[i][j].getType().equals("brown"))
					brown++;
				else if(table[i][j] != null && table[i][j].getType().equals("white"))
					white++;
				else if(table[i][j] != null && table[i][j].getType().equals("green"))
					green++;
				else if(table[i][j] != null && table[i][j].getType().equals("purple"))
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
	
//ÚJ
	public List<Integer> getNumberOfMoneyCards(){
		List<Integer> result = new LinkedList<Integer>();
		int blue = 0;
		int green = 0;
		int orange = 0;
		int yellow = 0;
		
		for(MoneyCard akt : moneyCards){
			if(akt.getType().equals("Blue")){
				blue++;
			}else{
				if(akt.getType().equals("Green")){
					green++;
				}else{
					if(akt.getType().equals("Orange")){
						orange++;
					}else{
						if(akt.getType().equals("Yellow")){
							yellow++;
						}
					}
				}
			}
		}
		result.add(blue);
		result.add(green);
		result.add(orange);
		result.add(yellow);
		
		return result;
	}
	
	

	public boolean isMegegyszerjohet() {
		return megegyszerjohet;
	}

	public void setMegegyszerjohet(boolean megegyszerjohet) {
		this.megegyszerjohet = megegyszerjohet;
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
