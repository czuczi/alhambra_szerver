import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
	private Room room;
	private MoneyDeck moneyDeck;
	private BuildingDeck buildingDeck;
	private BuildingMarket buildingMarket;
	private MoneyPickerView moneyPickerView;
	private LinkedList<Player> playersOrder;
	private int actPlayerIndex;
	private Player actPlayer;

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

	public BuildingMarket getBuildingMarket() {
		return buildingMarket;
	}
	
	
	public Player getActPlayer() {
		return actPlayer;
	}

	public void setActPlayer(Player actPlayer) {
		this.actPlayer = actPlayer;
	}

	public void initGameAttributes() {
		buildingDeck = new BuildingDeck();
		buildingMarket = new BuildingMarket();
		moneyDeck = new MoneyDeck();
		moneyPickerView = new MoneyPickerView();
		playersOrder = new LinkedList<>();

		buildingDeck.createBuildingDeck();
		buildingMarket.refillBuildingCard(buildingDeck);
		moneyDeck.createMoneyDeck();

		giveMoneyToPlayer();

		Player starter = null;
		int min = Integer.MAX_VALUE;

		for (Player player : room.getPlayerList()) {
			playersOrder.add(player);
			player.setGame(this);
		}

		int[] amountOfCards = new int[playersOrder.size()];

		int minAmountOfCards = Integer.MAX_VALUE;
		for (int i = 0; i < playersOrder.size(); i++) {
			amountOfCards[i] = playersOrder.get(i).getMoneyCards().size();
			if (amountOfCards[i] < minAmountOfCards) {
				minAmountOfCards = amountOfCards[i];
			}
		}

		int minDB = 0;

		for (int i = 0; i < amountOfCards.length; i++) {
			if (amountOfCards[i] == minAmountOfCards) {
				minDB++;
				starter = playersOrder.get(i);
			}
		}

		if (minDB > 1) {
			for (int i = 0; i < playersOrder.size(); i++) {
				int sum = 0;
				if(playersOrder.get(i).getMoneyCards().size() == minAmountOfCards) {
					for (MoneyCard moneyCard : playersOrder.get(i).getMoneyCards()) {
						sum += moneyCard.getValue();
					}
					if (sum < min) {
						min = sum;
						starter = playersOrder.get(i);
					}
				}
			}
		}

		actPlayerIndex = playersOrder.indexOf(starter);

		moneyPickerView.refillMoney(moneyDeck);
		int leftDeckSize = moneyDeck.getDeck().size();
		Random random = new Random();
		int randNumber = random.nextInt(leftDeckSize/5);
		moneyDeck.getDeck().add(leftDeckSize/5 + randNumber, new MoneyCard("evaluation1", -1));
		randNumber = random.nextInt(leftDeckSize/5);
		moneyDeck.getDeck().add(3*leftDeckSize/5 + randNumber, new MoneyCard("evaluation2", -1));
	}

	public void giveMoneyToPlayer() {
		for (Player player : room.getPlayerList()) {
			int moneySum = 0;
			while (moneySum < 20) {
				MoneyCard moneyCard = moneyDeck.removeMoneyCard();
				moneySum += moneyCard.getValue();
				player.getMoneyCards().add(moneyCard);
			}
		}
	}

	public Player getNextPlayer() {
		if (actPlayerIndex == playersOrder.size()) {
			actPlayerIndex = 1;
		} else {
			actPlayerIndex++;
		}
		return playersOrder.get(actPlayerIndex - 1);
	}

	public void evaluation() {

	}
	
	private int getLongestOutsideWallByPlayer(Player player) {
		
		int startX = -1, startY = -1, x = -1, y = -1;
		int maxWall = 0, actWall = 0;
		boolean actWallHelper = true;
		
		BuildingCard[][] matrix = player.getBuildingArea().getBuildingArea();
		List<BuildingCard> outsideCards = new LinkedList<>();
		List<BuildingCard> outsideCardsHelper;
		
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] != null) {
					if((startX == -1 && startY == -1) || (i <= x)) {	//megkeressük a(z) (egyik) legfelső épületlapot
						startX = x = i;
						startY = y = j;
					}
					if(!outsideCards.contains(matrix[i][j])) {
						outsideCards.add(matrix[i][j]);
					}
					break;
				}
			}
		}
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = matrix[0].length - 1; j > 0; j--) {
				if(matrix[i][j] != null) {
					if(!outsideCards.contains(matrix[i][j])) {
						outsideCards.add(matrix[i][j]);
					}
					break;
				}
			}
		}
		
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[j][i] != null) {
					if(!outsideCards.contains(matrix[j][i])) {
						outsideCards.add(matrix[j][i]);
					}
					break;
				}
			}
		}
		
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j = matrix.length - 1; j > 0; j--) {
				if(matrix[j][i] != null) {
					if(!outsideCards.contains(matrix[j][i])) {
						outsideCards.add(matrix[j][i]);
					}
					break;
				}
			}
		}
		
		outsideCardsHelper = new LinkedList<>(outsideCards);
		
		while(!outsideCards.isEmpty()) {
			if(x > 0 && y > 0) {
				if(matrix[x-1][y-1] != null && outsideCards.contains(matrix[x-1][y-1]) && matrix[x-1][y-1].isRight_wall() && matrix[x][y].isTop_wall()) {	//megyünk balra fel
					x--;
					y--;
					outsideCards.remove(matrix[x-1][y-1]);
					continue;
				}
			}
			if(y > 0) {
				if(matrix[x][y-1] != null && outsideCards.contains(matrix[x][y-1]) && matrix[x][y-1].isTop_wall() && matrix[x][y].isTop_wall()) {	//megyünk balra
					y--;
					outsideCards.remove(matrix[x][y-1]);
					continue;
				}
			}
			if(y > 0 && x < matrix.length - 1) {
				if(matrix[x+1][y-1] != null && outsideCards.contains(matrix[x+1][y-1]) && matrix[x+1][y-1].isTop_wall() && matrix[x][y].isLeft_wall()) {	//megyünk balra le
					x++;
					y--;
					outsideCards.remove(matrix[x+1][y-1]);
					continue;
				}
			}
			if(x > 0) {
				if(matrix[x-1][y] != null && outsideCards.contains(matrix[x-1][y]) && matrix[x-1][y].isRight_wall() && matrix[x][y].isRight_wall()) {	//megyünk fel
					x--;
					outsideCards.remove(matrix[x-1][y]);
					continue;
				}
			}
			if(x < matrix.length - 1) {
				if(matrix[x+1][y] != null && outsideCards.contains(matrix[x+1][y]) && matrix[x+1][y].isLeft_wall() && matrix[x][y].isLeft_wall()) {	//megyünk le
					x++;
					outsideCards.remove(matrix[x+1][y]);
					continue;
				}
			}
			if(x < matrix.length - 1 && y < matrix[0].length - 1) {
				if(matrix[x+1][y+1] != null && outsideCards.contains(matrix[x+1][y+1]) && matrix[x+1][y+1].isLeft_wall() && matrix[x][y].isBottom_wall()) {	//megyünk jobbra le
					x++;
					y++;
					outsideCards.remove(matrix[x+1][y+1]);
					continue;
				}
			}
			if(y < matrix[0].length - 1) {
				if(matrix[x][y+1] != null && outsideCards.contains(matrix[x][y+1]) && matrix[x][y+1].isBottom_wall() && matrix[x][y].isBottom_wall()) {	//megyünk jobbra
					y++;
					outsideCards.remove(matrix[x][y+1]);
					continue;
				}
			}
			if(x > 0 && y < matrix[0].length - 1) {
				if(matrix[x-1][y+1] != null && outsideCards.contains(matrix[x-1][y+1]) && matrix[x-1][y+1].isBottom_wall() && matrix[x][y].isRight_wall()) {	//megyünk jobbra fel
					x--;
					y++;
					outsideCards.remove(matrix[x-1][y+1]);
					continue;
				}
			}
				startX = x;	//kerestünk egy olyan x-et
				startY = y;	//és egy olyan y-t
				break;		//ahol a fal megszakad
		}
		
		
		
		outsideCards = new LinkedList<>(outsideCardsHelper);
		
		while(!outsideCards.isEmpty()) {
			if(x > 0 && y > 0) {
				if(matrix[x-1][y-1] != null && outsideCards.contains(matrix[x-1][y-1]) && matrix[x-1][y-1].isRight_wall() && matrix[x][y].isTop_wall()) {	//megyünk balra fel
					actWall++;
					actWall++;
					x--;
					y--;
					actWallHelper = true;
					outsideCards.remove(matrix[x-1][y-1]);
					continue;
				}
			}
			if(y > 0) {
				if(matrix[x][y-1] != null && outsideCards.contains(matrix[x][y-1]) && matrix[x][y-1].isTop_wall() && matrix[x][y].isTop_wall()) {	//megyünk balra
					if(actWallHelper == true) {
						actWall++;
						actWallHelper = false;
					}
					actWall++;
					y--;
					outsideCards.remove(matrix[x][y-1]);
					continue;
				}
			}
			if(y > 0 && x < matrix.length - 1) {
				if(matrix[x+1][y-1] != null && outsideCards.contains(matrix[x+1][y-1]) && matrix[x+1][y-1].isTop_wall() && matrix[x][y].isLeft_wall()) {	//megyünk balra le
					actWall++;	//ekkor plusz két fal jött a képbe
					actWall++;
					x++;
					y--;
					actWallHelper = true;
					outsideCards.remove(matrix[x+1][y-1]);
					continue;
				}
			}
			if(x > 0) {
				if(matrix[x-1][y] != null && outsideCards.contains(matrix[x-1][y]) && matrix[x-1][y].isRight_wall() && matrix[x][y].isRight_wall()) {	//megyünk fel
					if(actWallHelper == true) {
						actWall++;
						actWallHelper = false;
					}
					actWall++;
					x--;
					outsideCards.remove(matrix[x-1][y]);
					continue;
				}
			}
			if(x < matrix.length - 1) {
				if(matrix[x+1][y] != null && outsideCards.contains(matrix[x+1][y]) && matrix[x+1][y].isLeft_wall() && matrix[x][y].isLeft_wall()) {	//megyünk le
					if(actWallHelper == true) {
						actWall++;
						actWallHelper = false;
					}
					actWall++;
					x++;
					outsideCards.remove(matrix[x+1][y]);
					continue;
				}
			}
			if(x < matrix.length - 1 && y < matrix[0].length - 1) {
				if(matrix[x+1][y+1] != null && outsideCards.contains(matrix[x+1][y+1]) && matrix[x+1][y+1].isLeft_wall() && matrix[x][y].isBottom_wall()) {	//megyünk jobbra le
					actWall++;
					actWall++;
					x++;
					y++;
					actWallHelper = true;
					outsideCards.remove(matrix[x+1][y+1]);
					continue;
				}
			}
			if(y < matrix[0].length - 1) {
				if(matrix[x][y+1] != null && outsideCards.contains(matrix[x][y+1]) && matrix[x][y+1].isBottom_wall() && matrix[x][y].isBottom_wall()) {	//megyünk jobbra
					if(actWallHelper == true) {
						actWall++;
						actWallHelper = false;
					}
					actWall++;
					y++;
					outsideCards.remove(matrix[x][y+1]);
					continue;
				}
			}
			if(x > 0 && y < matrix[0].length - 1) {
				if(matrix[x-1][y+1] != null && outsideCards.contains(matrix[x-1][y+1]) && matrix[x-1][y+1].isBottom_wall() && matrix[x][y].isRight_wall()) {	//megyünk jobbra fel
					actWall++;
					actWall++;
					x--;
					y++;
					actWallHelper = true;
					outsideCards.remove(matrix[x-1][y+1]);
					continue;
				}
			}
			
				if(actWall > maxWall) {
					maxWall = actWall;	//maximumbeállítás faltörés esetén
				}
				actWall = 0;
				actWallHelper = true;
				
				if(x > 0 && y > 0) {
					if(matrix[x-1][y-1] != null && outsideCards.contains(matrix[x-1][y-1])) {	//megyünk balra fel
						x--;
						y--;
						outsideCards.remove(matrix[x-1][y-1]);
						continue;
					}
				}
				if(y > 0) {
					if(matrix[x][y-1] != null && outsideCards.contains(matrix[x][y-1])) {	//megyünk balra
						y--;
						outsideCards.remove(matrix[x][y-1]);
						continue;
					}
				}
				if(y > 0 && x < matrix.length - 1) {
					if(matrix[x+1][y-1] != null && outsideCards.contains(matrix[x+1][y-1])) {	//megyünk balra le
						x++;
						y--;
						outsideCards.remove(matrix[x+1][y-1]);
						continue;
					}
				}
				if(x > 0) {
					if(matrix[x-1][y] != null && outsideCards.contains(matrix[x-1][y])) {	//megyünk fel
						x--;
						outsideCards.remove(matrix[x-1][y]);
						continue;
					}
				}
				if(x < matrix.length - 1) {
					if(matrix[x+1][y] != null && outsideCards.contains(matrix[x+1][y])) {	//megyünk le
						x++;
						outsideCards.remove(matrix[x+1][y]);
						continue;
					}
				}
				if(x < matrix.length - 1 && y < matrix[0].length - 1) {
					if(matrix[x+1][y+1] != null && outsideCards.contains(matrix[x+1][y+1])) {	//megyünk jobbra le
						x++;
						y++;
						outsideCards.remove(matrix[x+1][y+1]);
						continue;
					}
				}
				if(y < matrix[0].length - 1) {
					if(matrix[x][y+1] != null && outsideCards.contains(matrix[x][y+1])) {	//megyünk jobbra
						y++;
						outsideCards.remove(matrix[x][y+1]);
						continue;
					}
				}
				if(x > 0 && y < matrix[0].length - 1) {
					if(matrix[x-1][y+1] != null && outsideCards.contains(matrix[x-1][y+1])) {	//megyünk jobbra fel
						x--;
						y++;
						outsideCards.remove(matrix[x-1][y+1]);
						continue;
					}
				}
		}
		
		return maxWall;
	}
}
