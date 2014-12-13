import java.util.LinkedList;
import java.util.List;


public class MoneyPickerView {
	
	private List<MoneyCard> moneyCards;
	
	public MoneyPickerView() {
		moneyCards = new LinkedList<>();
	}

	public void removeMoneyCard(MoneyCard mc) {
		moneyCards.remove(mc);
	}
	
	public boolean refillMoney(MoneyDeck deck) {
		
		int missing = 4 - moneyCards.size();
		
		if(deck.canRemoveMoneyCard(missing))
		{
			for(int i = 0; i < missing;i++)
			{
				moneyCards.add(deck.removeMoneyCard());
			}
			
			return true;
		}
		else 
			return false;
	}

	public List<MoneyCard> getMoneyCards() {
		return moneyCards;
	}

	public void setMoneyCards(List<MoneyCard> moneyCards) {
		this.moneyCards = moneyCards;
	}
	
	
 }
