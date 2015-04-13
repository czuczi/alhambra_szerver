import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class BuildingDeck {
	
	private List<BuildingCard> deck;

	public void createBuildingDeck() {
	
		deck = new LinkedList<>();
		
		//BLUE
		
		BuildingCard bc_1 = new BuildingCard("blue", true, true, true, false, 2, "/buildingCards/blue_2_tlr");
		deck.add(bc_1);
		
		BuildingCard bc_2 = new BuildingCard("blue", false, true, false, true, 3, "/buildingCards/blue_3_bl");
		deck.add(bc_2);
		
		BuildingCard bc_3 = new BuildingCard("blue", false, false, true, true, 4, "/buildingCards/blue_4_br");
		deck.add(bc_3);
		
		BuildingCard bc_4 = new BuildingCard("blue", true, true, false, false, 5, "/buildingCards/blue_5_tl");
		deck.add(bc_4);
		
		BuildingCard bc_5 = new BuildingCard("blue", true, false, false, false, 6, "/buildingCards/blue_6_t");
		deck.add(bc_5);
		
		BuildingCard bc_6 = new BuildingCard("blue", false, false, true, false, 7, "/buildingCards/blue_7_r");
		deck.add(bc_6);
		
		BuildingCard bc_7 = new BuildingCard("blue", false, false, false, false, 8, "/buildingCards/blue_8");
		deck.add(bc_7);
		
		//BROWN
		
		BuildingCard bc_8 = new BuildingCard("brown", true, false, true, true, 4, "/buildingCards/brown_4_tbr");
		deck.add(bc_8);
		
		BuildingCard bc_9 = new BuildingCard("brown", true, true, false, false, 5, "/buildingCards/brown_5_tl");
		deck.add(bc_9);
		
		BuildingCard bc_10 = new BuildingCard("brown", false, true, false, true, 6, "/buildingCards/brown_6_bl");
		deck.add(bc_10);
		
		BuildingCard bc_11 = new BuildingCard("brown", true, false, true, false, 6, "/buildingCards/brown_6_tr");
		deck.add(bc_11);
		
		BuildingCard bc_12 = new BuildingCard("brown", false, false, true, true, 7, "/buildingCards/brown_7_br");
		deck.add(bc_12);
		
		BuildingCard bc_13 = new BuildingCard("brown", false, false, true, false, 8, "/buildingCards/brown_8_r");
		deck.add(bc_13);
		
		BuildingCard bc_14 = new BuildingCard("brown", true, false, false, false, 8, "/buildingCards/brown_8_t");
		deck.add(bc_14);
		
		BuildingCard bc_15 = new BuildingCard("brown", false, false, false, false, 9, "/buildingCards/brown_9");
		deck.add(bc_15);
		
		BuildingCard bc_16 = new BuildingCard("brown", false, false, false, false, 10, "/buildingCards/brown_10");
		deck.add(bc_16);

		//GREEN
		
		BuildingCard bc_17 = new BuildingCard("green", false, true, true, true, 6, "/buildingCards/green_6_blr");
		deck.add(bc_17);
		
		BuildingCard bc_18 = new BuildingCard("green", true, true, false, true, 7, "/buildingCards/green_7_tbl");
		deck.add(bc_18);
		
		BuildingCard bc_19 = new BuildingCard("green", false, true, false, true, 8, "/buildingCards/green_8_bl");
		deck.add(bc_19);
		
		BuildingCard bc_20 = new BuildingCard("green", true, true, false, false, 8, "/buildingCards/green_8_tl");
		deck.add(bc_20);
		
		BuildingCard bc_21 = new BuildingCard("green", true, false, true, false, 8, "/buildingCards/green_8_tr");
		deck.add(bc_21);
		
		BuildingCard bc_22 = new BuildingCard("green", false, false, true, false, 9, "/buildingCards/green_9_r");
		deck.add(bc_22);
		
		BuildingCard bc_23 = new BuildingCard("green", false, false, false, false, 10, "/buildingCards/green_10");
		deck.add(bc_23);
		
		BuildingCard bc_24 = new BuildingCard("green", false, true, false, false, 10, "/buildingCards/green_10_l");
		deck.add(bc_24);
		
		BuildingCard bc_25 = new BuildingCard("green", true, false, false, false, 10, "/buildingCards/green_10_t");
		deck.add(bc_25);
		
		BuildingCard bc_26 = new BuildingCard("green", false, false, false, false, 11, "/buildingCards/green_11");
		deck.add(bc_26);
		
		BuildingCard bc_27 = new BuildingCard("green", false, false, false, true, 12, "/buildingCards/green_12_b");
		deck.add(bc_27);
	
		//PURPLE
		
		BuildingCard bc_28 = new BuildingCard("purple", true, true, true, false, 7, "/buildingCards/purple_7_tlr");
		deck.add(bc_28);
		
		BuildingCard bc_29 = new BuildingCard("purple", true, false, true, true, 8, "/buildingCards/purple_8_tbr");
		deck.add(bc_29);
		
		BuildingCard bc_30 = new BuildingCard("purple", false, false, true, true, 9, "/buildingCards/purple_9_br");
		deck.add(bc_30);
		
		BuildingCard bc_31 = new BuildingCard("purple", true, true, false, false, 9, "/buildingCards/purple_9_tl");
		deck.add(bc_31);
		
		BuildingCard bc_32 = new BuildingCard("purple", true, false, true, false, 9, "/buildingCards/purple_9_tr");
		deck.add(bc_32);
		
		BuildingCard bc_33 = new BuildingCard("purple", false, true, false, false, 10, "/buildingCards/purple_10_l");
		deck.add(bc_33);
		
		BuildingCard bc_34 = new BuildingCard("purple", false, false, false, false, 11, "/buildingCards/purple_11");
		deck.add(bc_34);
		
		BuildingCard bc_35 = new BuildingCard("purple", false, false, false, true, 11, "/buildingCards/purple_11_b");
		deck.add(bc_35);
		
		BuildingCard bc_36 = new BuildingCard("purple", true, false, false, false, 11, "/buildingCards/purple_11_t");
		deck.add(bc_36);
		
		BuildingCard bc_37 = new BuildingCard("purple", false, false, false, false, 12, "/buildingCards/purple_12");
		deck.add(bc_37);
		
		BuildingCard bc_38 = new BuildingCard("purple", false, false, true, false, 13, "/buildingCards/purple_13_r");
		deck.add(bc_38);
		
		//RED
		
		BuildingCard bc_39 = new BuildingCard("red", false, true, true, true, 3, "/buildingCards/red_3_blr");
		deck.add(bc_39);
		
		BuildingCard bc_40 = new BuildingCard("red", true, false, true, false, 4, "/buildingCards/red_4_tr");
		deck.add(bc_40);
		
		BuildingCard bc_41 = new BuildingCard("red", false, true, false, true, 5, "/buildingCards/red_5_bl");
		deck.add(bc_41);
		
		BuildingCard bc_42 = new BuildingCard("red", false, false, true, true, 6, "/buildingCards/red_6_br");
		deck.add(bc_42);
		
		BuildingCard bc_43 = new BuildingCard("red", false, true, false, false, 7, "/buildingCards/red_7_l");
		deck.add(bc_43);
		
		BuildingCard bc_44 = new BuildingCard("red", false, false, false, true, 8, "/buildingCards/red_8_b");
		deck.add(bc_44);
		
		BuildingCard bc_45 = new BuildingCard("red", false, false, false, false, 9, "/buildingCards/red_9");
		deck.add(bc_45);
		
		//WHITE
		
		BuildingCard bc_46 = new BuildingCard("white", true, true, false, true, 5, "/buildingCards/white_5_tbl");
		deck.add(bc_46);
		
		BuildingCard bc_47 = new BuildingCard("white", false, false, true, true, 6, "/buildingCards/white_6_br");
		deck.add(bc_47);
		
		BuildingCard bc_48 = new BuildingCard("white", false, true, false, true, 7, "/buildingCards/white_7_bl");
		deck.add(bc_48);
		
		BuildingCard bc_49 = new BuildingCard("white", true, false, true, false, 7, "/buildingCards/white_7_tr");
		deck.add(bc_49);
		
		BuildingCard bc_50 = new BuildingCard("white", true, true, false, false, 8, "/buildingCards/white_8_tl");
		deck.add(bc_50);
		
		BuildingCard bc_51 = new BuildingCard("white", false, false, false, true, 9, "/buildingCards/white_9_b");
		deck.add(bc_51);
		
		BuildingCard bc_52 = new BuildingCard("white", false, true, false, false, 9, "/buildingCards/white_9_l");
		deck.add(bc_52);
		
		BuildingCard bc_53 = new BuildingCard("white", false, false, false, false, 10, "/buildingCards/white_10");
		deck.add(bc_53);
		
		BuildingCard bc_54 = new BuildingCard("white", false, false, false, false, 11, "/buildingCards/white_11");
		deck.add(bc_54);
		
		for(int i = 0; i < 5; i++)
			Collections.shuffle(deck);
		
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
