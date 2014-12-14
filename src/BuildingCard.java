import java.util.List;
import java.util.UUID;

public class BuildingCard implements Cloneable {

	private String id;
	private String type;
	private boolean top_wall;
	private boolean left_wall;
	private boolean right_wall;
	private boolean bottom_wall;
	private List<BuildingCard> surroundingBuildings;
	private int price;
	private String image;

	@Override
	protected BuildingCard clone() {
		try {
			return (BuildingCard)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BuildingCard(String type, boolean top_wall,
			boolean left_wall, boolean right_wall, boolean bottom_wall, int value, String image) {
		this.id = UUID.randomUUID().toString();
		this.type = type;
		this.top_wall = top_wall;
		this.left_wall = left_wall;
		this.right_wall = right_wall;
		this.bottom_wall = bottom_wall;
		this.price = value;
		this.image = image;
	}
	
	public String getImage() {
		return image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTop_wall() {
		return top_wall;
	}

	public void setTop_wall(boolean top_wall) {
		this.top_wall = top_wall;
	}

	public boolean isLeft_wall() {
		return left_wall;
	}

	public void setLeft_wall(boolean left_wall) {
		this.left_wall = left_wall;
	}

	public boolean isRight_wall() {
		return right_wall;
	}

	public void setRight_wall(boolean right_wall) {
		this.right_wall = right_wall;
	}

	public boolean isBottom_wall() {
		return bottom_wall;
	}

	public void setBottom_wall(boolean bottom_wall) {
		this.bottom_wall = bottom_wall;
	}

	public List<BuildingCard> getSurroundingBuildings() {
		return surroundingBuildings;
	}

	public void setSurroundingBuildings(List<BuildingCard> surroundingBuildings) {
		this.surroundingBuildings = surroundingBuildings;
	}

	public int getValue() {
		return price;
	}

	public void setValue(int value) {
		this.price = value;
	}

	@Override
	public String toString() {
		return "BuildingCard [id=" + id + ", type=" + type + ", top_wall="
				+ top_wall + ", left_wall=" + left_wall + ", right_wall="
				+ right_wall + ", bottom_wall=" + bottom_wall
				+ ", surroundingBuildings=" + surroundingBuildings + ", price="
				+ price + ", image=" + image + "]";
	}

}
