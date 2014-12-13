import java.util.HashMap;
import java.util.Map;


public class BuildingMarket {
	
	private Map<String, BuildingCard> buildingMarket;

	public BuildingMarket() {
		this.buildingMarket = new HashMap<String, BuildingCard>();
		buildingMarket.put("Blue", null);
		buildingMarket.put("Green", null);
		buildingMarket.put("Orange", null);
		buildingMarket.put("Yellow", null);
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
				
			}
		}
		
	}
	
	public boolean refillBuildingCard(BuildingDeck bd){
		
		for(String k : buildingMarket.keySet())
		{
			System.out.println(buildingMarket.get(k));
			if(buildingMarket.get(k) == null){			//ha van üres hely
				if(bd.canRemoveBuildingCard()){			//és tudok húzni
					buildingMarket.put(k, bd.removeBuildingCard());
				}else{							//nem tudok húzni ---> vége a játéknak
					return false;
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	public Map<String, BuildingCard> getBuildingMarket() {
		return buildingMarket;
	}

}
