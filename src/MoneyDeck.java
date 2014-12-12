import java.util.Collections;
import java.util.List;


public class MoneyDeck {
	
	private List<MoneyCard> deck;

	public void createMoneyDeck() {
		
		for(int j = 0; j < 3; j++)
		{
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Denar", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Dirham", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Dukat", i+1);
				deck.add(mc);
			}
			
			for(int i = 0; i < 9; i++)
			{
				MoneyCard mc = new MoneyCard("Gulden", i+1);
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
