import java.util.LinkedList;
import java.util.List;


public class StorageArea {

	private List<BuildingCard> buildingCardList;

	public StorageArea() {
		this.buildingCardList = new LinkedList<>();
	}

	public List<BuildingCard> getBuildingCardList() {
		return buildingCardList;
	}

	public void addBuildingCard(BuildingCard buildingCard){
		buildingCardList.add(buildingCard);
	}
	
	public void removeBuildingCard(BuildingCard buildingCard){
		buildingCardList.remove(buildingCard);
	}
	
}
