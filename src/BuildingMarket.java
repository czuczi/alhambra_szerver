import java.util.HashMap;
import java.util.Map;


public class BuildingMarket {
	
	private Map<String,BuildingCard> buildingMarket;

	public BuildingMarket() {
		this.buildingMarket = new HashMap<>();
		buildingMarket.put("blue", null);
		buildingMarket.put("green", null);
		buildingMarket.put("orange", null);
		buildingMarket.put("yellow", null);
	}
	
	//sdd 
	public void removeBuldingCard(BuildingCard bc){
		
		for(String k : buildingMarket.keySet())
		{
			
			if(buildingMarket.get(k).equals(bc))
			{
				String key = k;
				buildingMarket.remove(k);
				buildingMarket.put(key, null);
				
			}
		}
		
	}
	
	
	//elfogyott a lap vagy csak simán nem vett épületlapot a falsra?
	//nem jó még
	public boolean refillBuildingCard(BuildingDeck bd){
		
		for(String k : buildingMarket.keySet())
		{
			if(buildingMarket.get(k) == null && bd.canRemoveBuildingCard())
			{
				buildingMarket.put(k, bd.removeBuildingCard());
				
			}
			
		}
		
		return false;
	}
	
	public Map<String, BuildingCard> getBuildingMarket() {
		return buildingMarket;
	}

}
