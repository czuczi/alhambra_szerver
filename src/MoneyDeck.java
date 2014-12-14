import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class MoneyDeck {
	
	private List<MoneyCard> deck;

	public void createMoneyDeck() {
		
		deck = new LinkedList<>();
		
		for(int j = 0; j < 3; j++)
		{
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Blue", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Green", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Orange", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Yellow", i+1);
				deck.add(mc);
			}
		}
		
		for(int i = 0; i < 5; i++)
			Collections.shuffle(deck);
	}
	
	public MoneyCard removeMoneyCard() {
		
		return deck.remove(0);
	}
	
	public boolean canRemoveMoneyCard(int darabszam) {
		return deck.size() >= darabszam;
	}
	
	public List<MoneyCard> getDeck() {
		return deck;
	}
}
