import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
		buildingDeck = new BuildingDeck();
		moneyDeck = new MoneyDeck();
		moneyPickerView = new MoneyPickerView();
		playersOrder = new LinkedList<>();

		buildingDeck.createBuildingDeck();
		moneyDeck.createMoneyDeck();

		giveMoneyToPlayer();

		Player starter = null;
		int min = Integer.MAX_VALUE;

		for (Player player : room.getPlayerList()) {
			playersOrder.add(player);
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
			actPlayerIndex = 0;
		} else {
			actPlayerIndex++;
		}
		return playersOrder.get(actPlayerIndex - 1);
	}

	public void evaluation() {

	}
}
