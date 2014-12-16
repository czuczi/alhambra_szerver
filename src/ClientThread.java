import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Receiver;

public class ClientThread extends Thread {

	private Socket mySocket;
	private InputStream is = null;
	private BufferedReader bf = null;
	private DataOutputStream os = null;
	private String interaction = "";
	private String requestName;

	private String nickName;
	private Player player;

	public ClientThread(Socket mySocket) {
		this.mySocket = mySocket;
	}

	public String getNickName() {
		return nickName;
	}

	public void end() {
		try {
			mySocket.close();
			is.close();
			bf.close();
			os.close();
			interaction = null;
			requestName = null;
			nickName = null;
			player = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		try {
			os.writeBytes(message + "\n");
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public boolean recieveMessage(BufferedReader bf) {
		try {
			interaction = bf.readLine();
			return true;
		} catch (IOException e) {
			try {
				mySocket.close();
				is.close();
				bf.close();
				os.close();
				Server.controller.logout(nickName);
				List<ClientThread> clientThreadsToRemove = new LinkedList<>();
				if(player != null) {
					if(player.getGame() != null) {
						for(ClientThread aktThread : Server.clientThreadList){
							if(aktThread.getPlayer().getGame().equals(player.getGame())) {
								clientThreadsToRemove.add(aktThread);
							}
						}
						
						Server.clientThreadList.removeAll(clientThreadsToRemove);
						
						for (ClientThread clientThread : clientThreadsToRemove) {
							clientThread.end();
						}
					}
				}
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void run() {

		try {
			is = mySocket.getInputStream();
			bf = new BufferedReader(new InputStreamReader(is));
			os = new DataOutputStream(mySocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			if (bf == null || !recieveMessage(bf)) {
				break;
			}
			String[] elements = interaction.split(";");
			requestName = elements[0];
			switch (requestName) {

			case "login":
				if (Server.controller.login(elements[2])) { 								// LOGIN KÉRELEM.SIKERES
					System.out.println("login success");
					nickName = elements[2].toString();

					player = null;
					for (Player aktPlayer : Server.controller.getPlayerList()) {
						if (aktPlayer.getName().equals(nickName)) {
							player = aktPlayer;
						}
					}
					sendMessage("showRoomManagerPage;" + elements[1]+ getAllRoomNames());
				} else { 																	// LOGIN KÉRELEM. SIKERTELEN
					sendMessage("showLoginPage;" + elements[1]);
					System.out.println("login failed");
				}
				break;

			case "logout": 																	// LOGOUT
				System.out.println("logout success");
				Server.controller.logout(nickName);
				sendMessage("showLoginPage;" + elements[1]);
				break;

			case "newRoom": 																// NEW ROOM
				boolean isSuccess = false;
				for (Player aktPlayer : Server.controller.getPlayerList()) {
					if (aktPlayer.getName().equals(nickName)) {
						isSuccess = aktPlayer.createRoom(elements[2], Integer.parseInt(elements[3]));
					}
				}
				if(isSuccess){
					for (ClientThread clientThread : Server.clientThreadList) {
						clientThread.sendMessage("refreshRoomList" + getAllRoomNames());
					}
					
					String playerNamesInRoom = "";
					for (Player aktPlayer : player.getRoom().getPlayerList()) {
						playerNamesInRoom += ";" + aktPlayer.getName();
					}
					sendMessage("showRoomPage;" + elements[1]+ playerNamesInRoom);
				} else {
					sendMessage("showRoomManagerPage;" + elements[1]+ ";newRoom");
				}
				break;

			case "joinRoom":
				isSuccess = false;
				for (Player aktPlayer : Server.controller.getPlayerList()) {
					if (aktPlayer.getName().equals(nickName)) {
						isSuccess = aktPlayer.joinRoom(nickName, elements[2]);
					}
				}
				if (isSuccess) {
					Game newGame = null;
					String currentPlayer="";
					if(player.getRoom().getMaxNumber() == player.getRoom().getPlayerList().size()) {
						player.getRoom().startGame();
						
						for(Game aktGame : Server.controller.getGameList()){
							if(aktGame.getRoom().getName().equals(player.getRoom().getName())){
								newGame = aktGame;
								newGame.setActPlayer(newGame.getNextPlayer());
								break;
							}
						}
					}
					String playerNamesInRoom = "";
					for (Player aktPlayer : player.getRoom().getPlayerList()) {
						playerNamesInRoom += ";" + aktPlayer.getName();
					}

					for (Player playerInRoom : player.getRoom().getPlayerList()) {
						for (ClientThread clientThread : Server.clientThreadList) {
							if (clientThread.getNickName().equals(playerInRoom.getName())) {
								if (clientThread.getNickName().equals(nickName)) {
									clientThread.sendMessage("showRoomPage;"+ elements[1] + playerNamesInRoom);
								} else {
									clientThread.sendMessage("showRoomPage;RoomPage"+ playerNamesInRoom);
								}
								if (playerInRoom.getRoom().getMaxNumber() == playerInRoom.getRoom().getPlayerList().size()) {
									clientThread.player.setGame(newGame);
									clientThread.sendMessage("showGameTablePage;RoomPage");
								}
							}
						}
					}

				} else {
					sendMessage("showRoomManagerPage;" + elements[1]+ ";joinRoom");
				}
				break;

			case "leaveRoom":																			//SZOBA ELHAGYÁS KÉRELEM
				Room tmpRoom = player.getRoom();
				player.leaveRoom();
				
				for (ClientThread clientThread : Server.clientThreadList) {
					clientThread.sendMessage("refreshRoomList" + getAllRoomNames());
				}
				sendMessage("showRoomManagerPage;" + elements[1] + getAllRoomNames());
				
				String playerNamesInRoom = "";
				for (Player aktPlayer : tmpRoom.getPlayerList()) {
					playerNamesInRoom += ";" + aktPlayer.getName();
				}
				for(Player aktPlayer : tmpRoom.getPlayerList()){
					for(ClientThread clientThread : Server.clientThreadList){
						if(aktPlayer.getName().equals(clientThread.getNickName())){
							clientThread.sendMessage("showRoomPage;" + elements[1] + playerNamesInRoom);
						}
					}
				}
				break;
				
			case "amIActPlayer":		//KI AZ AKTUÁLIS JÁTÉKOS
				System.out.println(player.getName());
				if(player.getGame().getActPlayer().getName().equals(nickName)){
					sendMessage("isActPlayer;yes");
				}else{
					sendMessage("isActPlayer;no");
				}
				break;

			case "tableAttributesRefresh":
				//player money
				sendMessage("yourMoneyCards"+getPlayerMoneyCardForSend());
				
				//moneyPickerView
				sendMessage("moneyPickerViewCards"+getMoneyPickerViewCardsForSend());
				
				//buildingMarket
				sendMessage("buildingMarketCards"+getBuildingMarketCardsForSend());
				
				//storageArea
				sendMessage("storageAreaCards"+getStorageAreaCardsForSend());
				
				//buildingArea
				sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
				break;
				
			case "pickMoneyCards":
				//pickMoneyCards
				List<MoneyCard> pickerView = player.getGame().getMoneyPickerView().getMoneyCards();
				int szum = 0;
				for(int i=1; i<elements.length; i++){
					szum += pickerView.get(Integer.parseInt(elements[i])).getValue();
				}
				if(szum > 5 && elements.length > 2){
					sendMessage("pickMoneyCardsFailed");
				}else{
					for(int i=1, j=0; i<elements.length; j++, i++){
						player.pickMoneyCard(pickerView.get(Integer.parseInt(elements[i])-j));		//Miracle. Don't touch it!!!!!
					}
					sendMessage("yourMoneyCards"+getPlayerMoneyCardForSend());
					player.getGame().getMoneyPickerView().refillMoney(player.getGame().getMoneyDeck());
					sendMessage("moneyPickerViewCards"+getMoneyPickerViewCardsForSend());
					
					if(player.getGame().isWasEvaluation()){
						//BROADCAST ÜZENET AZ EREDMÉNYRŐL
						player.getGame().setWasEvaluation(false);
					}
					actPlayerChange();
				}
				
				break;
				
			case "buyBuildingCard":
				List<MoneyCard> selectedMoneyCards = new LinkedList<>();
				List<MoneyCard> torlendo = new LinkedList<>();
				BuildingCard buildingCard = player.getGame().getBuildingMarket().getBuildingMarket().get(elements[1]);
				int osszeg = 0;
				
				for(int i=2; i<elements.length; i++){
					selectedMoneyCards.add(player.getMoneyCards().get(Integer.parseInt(elements[i])));
				}
				for (MoneyCard moneyCard : selectedMoneyCards) {
					if(moneyCard.getType().equals(elements[1])) {
						osszeg += moneyCard.getValue();
					}else{
						torlendo.add(moneyCard);
					}
				}
				selectedMoneyCards.removeAll(torlendo);			//más típusú pénzek törlése a kijelölésből
				if(osszeg < buildingCard.getValue()) {			
					sendMessage("isEnoughMoney;no");
				}else{
					sendMessage("isEnoughMoney;yes");			//van elég pénz a vásárláshoz
					recieveMessage(bf);
					String[] elements2 = interaction.split(";");
					
					if(elements2[0].equals("buyToAlhambra")){
						if(player.buyBuildingCardToAlhambra(buildingCard, Integer.parseInt(elements2[1]), Integer.parseInt(elements2[2]))){
							player.getMoneyCards().removeAll(selectedMoneyCards);
						}else{
							sendMessage("invalidBuyToAlhambra");
							break;
						}
						sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
					}else{
						if(elements2[0].equals("buyToStorageArea")){
							player.buyBuildingCardToStorageArea(buildingCard);
							player.getMoneyCards().removeAll(selectedMoneyCards);
						}
					}
					sendMessage("yourMoneyCards"+getPlayerMoneyCardForSend());		
					sendMessage("buildingMarketCards"+getBuildingMarketCardsForSend());
					sendMessage("storageAreaCards"+getStorageAreaCardsForSend());
					sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
					
					if(!(osszeg == buildingCard.getValue())){
						player.getGame().getBuildingMarket().refillBuildingCard(player.getGame().getBuildingDeck());
						actPlayerChange();
						
					}
				}
				break;
				
			case "removeToStorage":
				System.out.println("removeToStorage");
				if(player.rebuildAlhambraRemove(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]))){
					sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
					sendMessage("storageAreaCards"+getStorageAreaCardsForSend());
				}else{
					sendMessage("removeFailed");
				}
				break;
				
			case "rebuildAddToAlhambra":
				System.out.println("rebuildAddToAlhambra");
				BuildingCard builingCardAdd = player.getStorageArea().getBuildingCardList().get(Integer.parseInt(elements[3]));
				if(player.rebuildAlhambraAdd(builingCardAdd, Integer.parseInt(elements[1]), Integer.parseInt(elements[2]))){
					sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
					sendMessage("storageAreaCards"+getStorageAreaCardsForSend());
					
				}else{
					sendMessage("invalidBuyToAlhambra");
				}
				
				break;
				
			case "switchBuilding":
				System.out.println("switchBuilding");
				BuildingCard buildingCardFromStorage = player.getStorageArea().getBuildingCardList().get(Integer.parseInt(elements[3]));
				if(player.rebuildAlhambraSwitch(buildingCardFromStorage, Integer.parseInt(elements[1]), Integer.parseInt(elements[2]))){
					
					sendMessage("buildingAreaCards"+getBuildingAreaCardsForSend());
					sendMessage("storageAreaCards"+getStorageAreaCardsForSend());
				}else{
					sendMessage("switchFailed");
				}
				break;
				
			default:
				break;
			}
		}

	}

	public void broadcastForAllPlayersInRoom(String message) {
		
		for (Player playerInRoom : player.getRoom().getPlayerList()) {
			for (ClientThread clientThread : Server.clientThreadList) {
				if (clientThread.getNickName().equals(playerInRoom.getName())) {
					clientThread.sendMessage(message);
				}
			}
		}
	}
	
	public String getAllRoomNames(){
		String roomNamesList = ""; // szoba lista
		for (Room room : Server.controller.getRoomList()) {
			roomNamesList += ";" + room.getName();
		}
		return roomNamesList;
	}
	
	public String getPlayerMoneyCardForSend(){
		String playerMoneyCards ="";
		for(MoneyCard aktMoneyCard : player.getMoneyCards()){
			playerMoneyCards += ";"+aktMoneyCard.getType()+";"+aktMoneyCard.getValue();
		}
		return playerMoneyCards;
	}
	
	public String getMoneyPickerViewCardsForSend(){
		String moneyPickerViewCards = "";
		for(MoneyCard aktMoneyCard : player.getGame().getMoneyPickerView().getMoneyCards()){
			moneyPickerViewCards += ";" + aktMoneyCard.getType() + ";" + aktMoneyCard.getValue();
		}
		return moneyPickerViewCards;
	}
	
	public String getBuildingMarketCardsForSend(){
		String buildingMarketCards = "";
		List<BuildingCard> buildingCards = new LinkedList<>();
		for(String k : player.getGame().getBuildingMarket().getBuildingMarket().keySet()){
			if(player.getGame().getBuildingMarket().getBuildingMarket().get(k) != null){
				buildingCards.add(player.getGame().getBuildingMarket().getBuildingMarket().get(k));	
			}else{
				buildingCards.add(null);
			}
		}	
		for(BuildingCard aktBuildingCard : buildingCards){
			if(aktBuildingCard != null){
				buildingMarketCards += ";"+aktBuildingCard.getImage();
			}else{
				buildingMarketCards += ";./resource/buildingCards/back";
			}
		}
		return buildingMarketCards;
	}
	
	public String getStorageAreaCardsForSend(){
		String storageAreaCards = "";
		List<BuildingCard> buildingCards = new LinkedList<>();
		for(BuildingCard aktBuildingCard : player.getStorageArea().getBuildingCardList()){
			storageAreaCards += ";"+aktBuildingCard.getImage();
		}
		return storageAreaCards;
	}
	
	public String getBuildingAreaCardsForSend(){
		String buildingAreaCards = "";
		BuildingCard[][] matrix = player.getBuildingArea().getBuildingArea();
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[i].length; j++){
				if(matrix[i][j] == null){
					buildingAreaCards += ";"+j+";"+i+";null";
				}else{
					buildingAreaCards += ";"+j+";"+i+";"+matrix[i][j].getImage();
				}
			}
		}
		return buildingAreaCards;
	}
	
	public void actPlayerChange(){
		player.getGame().setActPlayer(player.getGame().getNextPlayer());
		sendMessage("isActPlayer;no");
		for(ClientThread aktThread : Server.clientThreadList){
			if(player.getGame().getActPlayer().getName().equals(aktThread.player.getName())){
				aktThread.sendMessage("isActPlayer;yes");
			}
		}
		player.getGame().getBuildingMarket().refillBuildingCard(player.getGame().getBuildingDeck());
		player.getGame().getMoneyPickerView().refillMoney(player.getGame().getMoneyDeck());
	}
}
