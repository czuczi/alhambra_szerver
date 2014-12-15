import java.util.HashMap;
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
	private boolean wasEvaluation;

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

	public MoneyDeck getMoneyDeck() {
		return moneyDeck;
	}

	public void setMoneyDeck(MoneyDeck moneyDeck) {
		this.moneyDeck = moneyDeck;
	}

	public void setBuildingDeck(BuildingDeck buildingDeck) {
		this.buildingDeck = buildingDeck;
	}

	public Player getActPlayer() {
		return actPlayer;
	}

	public void setActPlayer(Player actPlayer) {
		this.actPlayer = actPlayer;
	}
	
	public BuildingMarket getBuildingMarket() {
		return buildingMarket;
	}

	public void setBuildingMarket(BuildingMarket buildingMarket) {
		this.buildingMarket = buildingMarket;
	}

	public boolean isWasEvaluation() {
		return wasEvaluation;
	}

	public void setWasEvaluation(boolean wasEvaluation) {
		this.wasEvaluation = wasEvaluation;
	}

	public void initGameAttributes() {
		buildingDeck = new BuildingDeck();
		buildingMarket = new BuildingMarket();
		moneyDeck = new MoneyDeck();
		moneyPickerView = new MoneyPickerView(this);
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

	public void evaluation(int evaluate_number) {
				
		List<Player> player = room.getPlayerList();
		
		int max_blue;
		int max_red;
		int max_brown;
		int max_white;
		int max_green;
		int max_purple;
		
		wasEvaluation = true;
		switch (evaluate_number) {
	// első értékelés	
		case 1:
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			List<Player> scored_blue = new LinkedList<>();
			List<Player> scored_red = new LinkedList<>();
			List<Player> scored_brown = new LinkedList<>();
			List<Player> scored_white = new LinkedList<>();
			List<Player> scored_green = new LinkedList<>();
			List<Player> scored_purple = new LinkedList<>();
			
			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue)
					max_blue = list.get(0);
				if(list.get(1) > max_red)
					max_red = list.get(1);
				if(list.get(2) > max_brown)
					max_brown = list.get(2);
				if(list.get(3)> max_white)
					max_white = list.get(3);
				if(list.get(4) > max_green)
					max_green = list.get(4);
				if(list.get(5) > max_purple)
					max_purple = list.get(5);		
			}
			
			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
					scored_blue.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
					scored_red.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple.add(p);
			}
			
			for(Player p : scored_blue)
			{
				if(scored_blue.size() != 0)
					p.incrementScore(1/scored_blue.size());
			}
			for(Player p : scored_red)
			{
				if(scored_red.size() != 0)
					p.incrementScore(2/scored_red.size());
			}
			for(Player p : scored_brown)
			{
				if(scored_brown.size() != 0)
					p.incrementScore(3/scored_brown.size());
			}
			for(Player p : scored_white)
			{
				if(scored_white.size() != 0)
					p.incrementScore(4/scored_white.size());
			}
			for(Player p : scored_green)
			{
				if(scored_green.size() != 0)
					p.incrementScore(5/scored_green.size());
			}
			for(Player p : scored_purple)
			{
				if(scored_purple.size() != 0)
					p.incrementScore(6/scored_purple.size());
			}
			
			for(Player p : player)
			{
				p.incrementScore(getLongestOutsideWallByPlayer(p));
			}
			break;
	//második értékelés		
		case 2:
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			List<Player> scored_blue_first = new LinkedList<>();
			List<Player> scored_red_first = new LinkedList<>();
			List<Player> scored_brown_first = new LinkedList<>();
			List<Player> scored_white_first = new LinkedList<>();
			List<Player> scored_green_first = new LinkedList<>();
			List<Player> scored_purple_first = new LinkedList<>();
			
			List<Player> scored_blue_second = new LinkedList<>();
			List<Player> scored_red_second = new LinkedList<>();
			List<Player> scored_brown_second = new LinkedList<>();
			List<Player> scored_white_second = new LinkedList<>();
			List<Player> scored_green_second = new LinkedList<>();
			List<Player> scored_purple_second = new LinkedList<>();
			

			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue)
					max_blue = list.get(0);
				if(list.get(1) > max_red)
					max_red = list.get(1);
				if(list.get(2) > max_brown)
					max_brown = list.get(2);
				if(list.get(3)> max_white)
					max_white = list.get(3);
				if(list.get(4) > max_green)
					max_green = list.get(4);
				if(list.get(5) > max_purple)
					max_purple = list.get(5);		
			}
			
			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
						scored_blue_first.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
						scored_red_first.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown_first.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white_first.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green_first.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple_first.add(p);
			}
			
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue && !scored_blue_first.contains(p))
					max_blue = list.get(0);
				if(list.get(1) > max_red && !scored_red_first.contains(p))
					max_red = list.get(1);
				if(list.get(2) > max_brown && !scored_brown_first.contains(p))
					max_brown = list.get(2);
				if(list.get(3)> max_white && !scored_white_first.contains(p))
					max_white = list.get(3);
				if(list.get(4) > max_green && !scored_green_first.contains(p))
					max_green = list.get(4);
				if(list.get(5) > max_purple && !scored_purple_first.contains(p))
					max_purple = list.get(5);		
			}

			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
						scored_blue_second.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
						scored_red_second.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown_second.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white_second.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green_second.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple_second.add(p);
			}
			
			if(scored_blue_first.size() == 1)
			{
				scored_blue_first.get(0).incrementScore(8);
				if(scored_blue_second.size() != 0)
				{
					for(Player p : scored_blue_second)
						p.incrementScore(1/scored_blue_second.size());
				}
	
			}
			
			if(scored_blue_first.size() > 1)
			{
					for(Player p : scored_blue_first)
						p.incrementScore(9/scored_blue_second.size());
	
			}
			
			if(scored_red_first.size() == 1)
			{
				scored_red_first.get(0).incrementScore(9);
				if(scored_red_second.size() != 0)
				{
					for(Player p : scored_red_second)
						p.incrementScore(2/scored_red_second.size());
				}
	
			}
			
			if(scored_red_first.size() > 1)
			{
					for(Player p : scored_red_first)
						p.incrementScore(11/scored_red_second.size());
	
			}
			
			if(scored_brown_first.size() == 1)
			{
				scored_brown_first.get(0).incrementScore(10);
				if(scored_brown_second.size() != 0)
				{
					for(Player p : scored_brown_second)
						p.incrementScore(3/scored_brown_second.size());
				}
	
			}
			
			if(scored_brown_first.size() > 1)
			{
					for(Player p : scored_brown_first)
						p.incrementScore(13/scored_brown_second.size());
	
			}
			
			if(scored_white_first.size() == 1)
			{
				scored_white_first.get(0).incrementScore(11);
				if(scored_white_second.size() != 0)
				{
					for(Player p : scored_white_second)
						p.incrementScore(4/scored_white_second.size());
				}
	
			}
			
			if(scored_white_first.size() > 1)
			{
					for(Player p : scored_white_first)
						p.incrementScore(15/scored_white_second.size());
	
			}
			
			if(scored_green_first.size() == 1)
			{
				scored_green_first.get(0).incrementScore(12);
				if(scored_green_second.size() != 0)
				{
					for(Player p : scored_green_second)
						p.incrementScore(5/scored_green_second.size());
				}
	
			}
			
			if(scored_green_first.size() > 1)
			{
					for(Player p : scored_green_first)
						p.incrementScore(17/scored_green_second.size());
	
			}
		
			if(scored_purple_first.size() == 1)
			{
				scored_purple_first.get(0).incrementScore(13);
				if(scored_purple_second.size() != 0)
				{
					for(Player p : scored_purple_second)
						p.incrementScore(6/scored_purple_second.size());
				}
	
			}
			
			if(scored_purple_first.size() > 1)
			{
					for(Player p : scored_purple_first)
						p.incrementScore(19/scored_purple_second.size());
	
			}
			
			for(Player p : player)
			{
				p.incrementScore(getLongestOutsideWallByPlayer(p));
			}
			
			break;
			
// harmadik értékelés			
		case 3:
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			List<Player> scored_blue_1 = new LinkedList<>();
			List<Player> scored_red_1 = new LinkedList<>();
			List<Player> scored_brown_1 = new LinkedList<>();
			List<Player> scored_white_1 = new LinkedList<>();
			List<Player> scored_green_1 = new LinkedList<>();
			List<Player> scored_purple_1 = new LinkedList<>();
			
			List<Player> scored_blue_2 = new LinkedList<>();
			List<Player> scored_red_2 = new LinkedList<>();
			List<Player> scored_brown_2 = new LinkedList<>();
			List<Player> scored_white_2 = new LinkedList<>();
			List<Player> scored_green_2 = new LinkedList<>();
			List<Player> scored_purple_2 = new LinkedList<>();
			
			List<Player> scored_blue_3 = new LinkedList<>();
			List<Player> scored_red_3 = new LinkedList<>();
			List<Player> scored_brown_3 = new LinkedList<>();
			List<Player> scored_white_3 = new LinkedList<>();
			List<Player> scored_green_3 = new LinkedList<>();
			List<Player> scored_purple_3 = new LinkedList<>();
			

			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue)
					max_blue = list.get(0);
				if(list.get(1) > max_red)
					max_red = list.get(1);
				if(list.get(2) > max_brown)
					max_brown = list.get(2);
				if(list.get(3)> max_white)
					max_white = list.get(3);
				if(list.get(4) > max_green)
					max_green = list.get(4);
				if(list.get(5) > max_purple)
					max_purple = list.get(5);		
			}
			
			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
						scored_blue_1.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
						scored_red_1.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown_1.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white_1.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green_1.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple_1.add(p);
			}
			
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue && !scored_blue_1.contains(p))
					max_blue = list.get(0);
				if(list.get(1) > max_red && !scored_red_1.contains(p))
					max_red = list.get(1);
				if(list.get(2) > max_brown && !scored_brown_1.contains(p))
					max_brown = list.get(2);
				if(list.get(3)> max_white && !scored_white_1.contains(p))
					max_white = list.get(3);
				if(list.get(4) > max_green && !scored_green_1.contains(p))
					max_green = list.get(4);
				if(list.get(5) > max_purple && !scored_purple_1.contains(p))
					max_purple = list.get(5);		
			}

			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
						scored_blue_2.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
						scored_red_2.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown_2.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white_2.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green_2.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple_2.add(p);
			}
			
			max_blue = 0;
			max_red = 0;
			max_brown = 0;
			max_white = 0;
			max_green = 0;
			max_purple = 0;
			
			for(Player p : player)
			{
				List<Integer> list = new LinkedList<>();
				list  = p.getNumberOfBuildingCards();
				
				if(list.get(0) > max_blue && !scored_blue_1.contains(p) && !scored_blue_2.contains(p))
					max_blue = list.get(0);
				if(list.get(1) > max_red && !scored_red_1.contains(p) && !scored_red_2.contains(p))
					max_red = list.get(1);
				if(list.get(2) > max_brown && !scored_brown_1.contains(p) && !scored_brown_2.contains(p))
					max_brown = list.get(2);
				if(list.get(3)> max_white && !scored_white_1.contains(p) && !scored_white_2.contains(p))
					max_white = list.get(3);
				if(list.get(4) > max_green && !scored_green_1.contains(p) && !scored_green_2.contains(p))
					max_green = list.get(4);
				if(list.get(5) > max_purple && !scored_purple_1.contains(p) && !scored_purple_2.contains(p))
					max_purple = list.get(5);		
			}

			for(Player p : player)
			{
				if(p.getNumberOfBuildingCards().get(0) == max_blue && max_blue != 0)
						scored_blue_3.add(p);
				if(p.getNumberOfBuildingCards().get(1) == max_red && max_red != 0)
						scored_red_3.add(p);
				if(p.getNumberOfBuildingCards().get(2) == max_brown && max_brown != 0)
					scored_brown_3.add(p);
				if(p.getNumberOfBuildingCards().get(3) == max_white && max_white != 0)
					scored_white_3.add(p);
				if(p.getNumberOfBuildingCards().get(4) == max_green && max_green != 0)
					scored_green_3.add(p);
				if(p.getNumberOfBuildingCards().get(5) == max_purple && max_purple != 0)
					scored_purple_3.add(p);
			}
			
			
			if(scored_blue_1.size() == 1)
			{
				scored_blue_1.get(0).incrementScore(16);
				if(scored_blue_2.size() == 1)
				{
					scored_blue_2.get(0).incrementScore(8);
					if(scored_blue_3.size() != 0)
					{
						for(Player p : scored_blue_3)
							p.incrementScore(1/scored_blue_3.size());
					}
					
				}
				
				if(scored_blue_2.size() > 1)
				{
					for(Player p : scored_blue_2)
						p.incrementScore(9/scored_blue_2.size());
				}

			}
			
			if(scored_blue_1.size() == 2)
			{
				scored_blue_1.get(0).incrementScore(8);
				scored_blue_1.get(1).incrementScore(8);
				
				if(scored_blue_3.size() != 0)
				{
					for(Player p : scored_blue_3)
						p.incrementScore(1/scored_blue_3.size());
				}
				
			}
			
			if(scored_blue_1.size() > 2)
			{
				for(Player p : scored_blue_1)
					p.incrementScore(25/scored_blue_3.size());
			}
			
			
			if(scored_red_1.size() == 1)
			{
				scored_red_1.get(0).incrementScore(17);
				if(scored_red_2.size() == 1)
				{
					scored_red_2.get(0).incrementScore(9);
					if(scored_red_3.size() != 0)
					{
						for(Player p : scored_red_3)
							p.incrementScore(2/scored_red_3.size());
					}
					
				}
				
				if(scored_red_2.size() > 1)
				{
					for(Player p : scored_red_2)
						p.incrementScore(11/scored_red_2.size());
				}

			}
			
			if(scored_red_1.size() == 2)
			{
				scored_red_1.get(0).incrementScore(8);
				scored_red_1.get(1).incrementScore(8);
				
				if(scored_red_3.size() != 0)
				{
					for(Player p : scored_red_3)
						p.incrementScore(2/scored_red_3.size());
				}
				
			}
			
			if(scored_red_1.size() > 2)
			{
				for(Player p : scored_red_1)
					p.incrementScore(28/scored_red_3.size());
			}
			
			
			if(scored_brown_1.size() == 1)
			{
				scored_brown_1.get(0).incrementScore(18);
				if(scored_brown_2.size() == 1)
				{
					scored_brown_2.get(0).incrementScore(10);
					if(scored_brown_3.size() != 0)
					{
						for(Player p : scored_brown_3)
							p.incrementScore(3/scored_brown_3.size());
					}
					
				}
				
				if(scored_brown_2.size() > 1)
				{
					for(Player p : scored_brown_2)
						p.incrementScore(13/scored_brown_2.size());
				}

			}
			
			if(scored_brown_1.size() == 2)
			{
				scored_brown_1.get(0).incrementScore(9);
				scored_brown_1.get(1).incrementScore(9);
				
				if(scored_brown_3.size() != 0)
				{
					for(Player p : scored_brown_3)
						p.incrementScore(3/scored_brown_3.size());
				}
				
			}
			
			if(scored_brown_1.size() > 2)
			{
				for(Player p : scored_brown_1)
					p.incrementScore(31/scored_brown_3.size());
			}
			
			if(scored_white_1.size() == 1)
			{
				scored_white_1.get(0).incrementScore(19);
				if(scored_white_2.size() == 1)
				{
					scored_white_2.get(0).incrementScore(11);
					if(scored_white_3.size() != 0)
					{
						for(Player p : scored_white_3)
							p.incrementScore(4/scored_white_3.size());
					}
					
				}
				
				if(scored_white_2.size() > 1)
				{
					for(Player p : scored_white_2)
						p.incrementScore(15/scored_white_2.size());
				}

			}
			
			if(scored_white_1.size() == 2)
			{
				scored_white_1.get(0).incrementScore(9);
				scored_white_1.get(1).incrementScore(9);
				
				if(scored_white_3.size() != 0)
				{
					for(Player p : scored_white_3)
						p.incrementScore(4/scored_white_3.size());
				}
				
			}
			
			if(scored_white_1.size() > 2)
			{
				for(Player p : scored_white_1)
					p.incrementScore(34/scored_white_3.size());
			}
			
			
			if(scored_green_1.size() == 1)
			{
				scored_green_1.get(0).incrementScore(20);
				if(scored_green_2.size() == 1)
				{
					scored_green_2.get(0).incrementScore(12);
					if(scored_green_3.size() != 0)
					{
						for(Player p : scored_green_3)
							p.incrementScore(5/scored_green_3.size());
					}
					
				}
				
				if(scored_green_2.size() > 1)
				{
					for(Player p : scored_white_2)
						p.incrementScore(17/scored_green_2.size());
				}

			}
			
			if(scored_green_1.size() == 2)
			{
				scored_green_1.get(0).incrementScore(10);
				scored_green_1.get(1).incrementScore(10);
				
				if(scored_green_3.size() != 0)
				{
					for(Player p : scored_green_3)
						p.incrementScore(5/scored_green_3.size());
				}
				
			}
			
			if(scored_green_1.size() > 2)
			{
				for(Player p : scored_green_1)
					p.incrementScore(37/scored_green_3.size());
			}
			
			if(scored_purple_1.size() == 1)
			{
				scored_purple_1.get(0).incrementScore(21);
				if(scored_purple_2.size() == 1)
				{
					scored_purple_2.get(0).incrementScore(13);
					if(scored_purple_3.size() != 0)
					{
						for(Player p : scored_purple_3)
							p.incrementScore(6/scored_purple_3.size());
					}
					
				}
				
				if(scored_purple_2.size() > 1)
				{
					for(Player p : scored_purple_2)
						p.incrementScore(19/scored_purple_2.size());
				}

			}
			
			if(scored_purple_1.size() == 2)
			{
				scored_purple_1.get(0).incrementScore(10);
				scored_purple_1.get(1).incrementScore(10);
				
				if(scored_purple_3.size() != 0)
				{
					for(Player p : scored_purple_3)
						p.incrementScore(6/scored_purple_3.size());
				}
				
			}
			
			if(scored_purple_1.size() > 2)
			{
				for(Player p : scored_purple_1)
					p.incrementScore(40/scored_purple_3.size());
			}
			
			for(Player p : player)
			{
				p.incrementScore(getLongestOutsideWallByPlayer(p));
			}
			
			break;

		default:
			break;
		}
		
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
