import java.util.List;


public class MoneyPickerView {
	
	private List<MoneyCard> moneyCards;
	
	//biztos vissza kell adni az mc-t?
	
	public MoneyCard removeMoneyCard(MoneyCard mc) {
		
		MoneyCard money = mc;
		moneyCards.remove(money);
		
		return money;
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
 }
