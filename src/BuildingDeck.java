import java.util.List;


public class BuildingDeck {
	
	private List<BuildingCard> deck;

	
	//ötletek?
	public void createBuildingDeck() {
		
	}
	
	public BuildingCard removeBuildingCard() {
			
		return deck.remove(0);
	}
	
	public boolean canRemoveBuildingCard() {
		
		if(deck.size() > 0)
			return true;
		else
			return false;
	}
	
	public List<BuildingCard> getDeck() {
		return deck;
	}
}
