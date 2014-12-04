import java.util.HashMap;
import java.util.Map;


public class BuildingMarket {
	
	private Map<BuildingCard,String> buildingMarket;

	public BuildingMarket() {
		this.buildingMarket = new HashMap<>();
	}

	public Map<BuildingCard, String> getBuildingMarket() {
		return buildingMarket;
	}
	
	public BuildingCard removeBuldingCard(){
		return null;
	}
	
	public void refillBuildingCard(){
		
	}

}
