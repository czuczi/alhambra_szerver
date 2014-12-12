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
		
		return !deck.isEmpty();
	}
	
	public List<BuildingCard> getDeck() {
		return deck;
	}
}
