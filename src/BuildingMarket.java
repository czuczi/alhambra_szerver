import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class BuildingMarket {
	
	private Map<String, BuildingCard> buildingMarket;

	public BuildingMarket() {
		this.buildingMarket = new TreeMap<String, BuildingCard>();
		buildingMarket.put("Blue", null);
		buildingMarket.put("Green", null);
		buildingMarket.put("Orange", null);
		buildingMarket.put("Yellow", null);
	}
	
	//sdd 
	public void removeBuildingCard(BuildingCard bc){
		
		for(String k : buildingMarket.keySet())
		{
			
			if(buildingMarket.get(k) != null && buildingMarket.get(k).equals(bc))
			{
				String key = k;
				buildingMarket.remove(k);
				buildingMarket.put(key, null);
				break;
			}
		}
		
	}
//JAVÍTVA	
	public boolean refillBuildingCard(BuildingDeck bd){
		int emptyPlaces = 0;
		
		for(String k : buildingMarket.keySet()){		//ujretoltendo helyek szama
			if(buildingMarket.get(k) == null){
				emptyPlaces++;
			}
		}
		
		if(bd.getDeck().size() < emptyPlaces){			//nem tudom újratölteni
			return false;
		}else{
			for (String k : buildingMarket.keySet()) {
				if (buildingMarket.get(k) == null) { 			// ha van üres hely
					buildingMarket.put(k, bd.removeBuildingCard());
				} else {
					;
				}
			}
		}
		return true;
	}
	
	public Map<String, BuildingCard> getBuildingMarket() {
		return buildingMarket;
	}

}
