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
	public void removeBuildingCard(BuildingCard bc){
		
		for(String k : buildingMarket.keySet())
		{
			
			if(buildingMarket.get(k).equals(bc))
			{
				String key = k;
				buildingMarket.remove(k);
				buildingMarket.put(key, null);
				return;
			}
		}
		
	}
	
	public boolean refillBuildingCard(BuildingDeck bd){
		
		for(String k : buildingMarket.keySet())
		{
			if(buildingMarket.get(k) == null){			//ha van üres hely
				if(bd.canRemoveBuildingCard()){			//és tudok húzni
					buildingMarket.remove(k);
					buildingMarket.put(k, bd.removeBuildingCard());
				}else{							//nem tudok húzni ---> vége a játéknak
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Map<String, BuildingCard> getBuildingMarket() {
		return buildingMarket;
	}

}
