import java.util.LinkedList;
import java.util.List;


public class BuildingArea {
	private BuildingCard [][] buildingArea;
	private int bejarhatoOldalakSzama = 0;
	private List<BuildingCard> vizsgalando;

	
	
	
	public BuildingArea() {
		this.buildingArea = new BuildingCard [21][21];
	}

	public void addBuildingCard(BuildingCard buildingCard, int a, int b){
		
		this.buildingArea[a][b] = buildingCard;
	}
	
	public void removeBuildingCard(int a, int b){
		this.buildingArea[a][b] = null;
	}
	
	public boolean canAddBuildingCard(BuildingCard buildingCard, int a, int b){
		int kozeppontX = 0;
		int kozeppontY = 0;
		bejarhatoOldalakSzama = 0;
		boolean lyuk = false;
		
		if(buildingArea[a][b] == null){		// üres a mező
			for(int i=0; i<buildingArea.length; i++){		//kezdőmező pozíciója
				for(int j=0; j<buildingArea[i].length; j++){
					if(buildingArea[i][j].getType().equals("STARTAREA")){
						kozeppontX = i;
						kozeppontY = j;
						break;
					}
				}
				if(kozeppontX != 0 && kozeppontY != 0){
					break;
				}
			}
			if(a == kozeppontX-1 && b == kozeppontY){		//kezdőmező fölé tesszük
				if(!buildingCard.isBottom_wall()){			//nincs lent fal
					if(!isLeftNeighbourWallOk(buildingCard, a, b)){
						return false;
					} else{
						if(!isToptNeighbourWallOk(buildingCard, a, b)){
							return false;
						} else{
							if(!isRighttNeighbourWallOk(buildingCard, a, b)){
								return false;
							} else{
								return true;
							}
						}
					}
				} else{
					return false;							//útban van a fal
				}
			} else{
				if(a == kozeppontX && b == kozeppontY+1){		//kezdőmező jobb oldalára tesszük
					if(!buildingCard.isLeft_wall()){			//elhelyezendő kártyán nincs bal oldalon fal
						if(!isToptNeighbourWallOk(buildingCard, a, b)){
							return false;
						} else{
							if(!isRighttNeighbourWallOk(buildingCard, a, b)){
								return false;
							} else{
								if(!isBottomtNeighbourWallOk(buildingCard, a, b)){
									return false;
								} else{
									return true;
								}
							}
						}
					} else{
						return false;
					}
				} else{
					if(a == kozeppontX+1 && b == kozeppontY){		//kezdőmező alá tesszük
						if(!buildingCard.isTop_wall()){				//elhelyezendő kártyán nincs fent fal
							if(!isLeftNeighbourWallOk(buildingCard, a, b)){
								return false;
							} else{
								if(!isBottomtNeighbourWallOk(buildingCard, a, b)){
									return false;
								} else{
									if(!isRighttNeighbourWallOk(buildingCard, a, b)){
										return false;
									} else{
										return true;
									}
								}
							}
						} else{
							return false;
						}
					} else{
						if(a == kozeppontX && b == kozeppontY-1){		//kezdőmező bal oldalára tesszük
							if(!buildingCard.isRight_wall()){			//elhelyezendő kártyán nincs jobb oldalon fal
								if(!isBottomtNeighbourWallOk(buildingCard, a, b)){
									return false;
								} else{
									if(!isLeftNeighbourWallOk(buildingCard, a, b)){
										return false;
									} else{
										if(!isToptNeighbourWallOk(buildingCard, a, b)){
											return false;
										} else{
											return true;
										}
									}
								}
							} else{
								return false;
							}
						} else{					//TETSZŐLEGES HELYRE TESSZÜK
							if((b > 0 && buildingArea[a][b-1] != null) || (b < buildingArea[a].length - 1 && buildingArea[a][b+1] != null) || (a > 0 && buildingArea[a-1][b] != null) || (a < buildingArea.length - 1 && buildingArea[a+1][b] != null)){  //van legalább egy szomszéd
								
								
								//a sarokban elhelyezésnél nincs lyuk
								if(a == 0 && b == 0) {
									lyuk = false;
								}
								
								else if(a == 0 && b == buildingArea[a].length - 1) {
									lyuk = false;
								}
								
								else if(a == buildingArea.length && b == 0) {
									lyuk = false;
								}
								
								else if(a == buildingArea.length && b == buildingArea[a].length - 1) {
									lyuk = false;
								}
								
								else if(a == 0 && !(b == 0 || b == buildingArea[a].length - 1)) { //0. sorban helyezzük el
									if(buildingArea[a+1][b] == null) {
										if((buildingArea[a][b-1] != null || buildingArea[a+1][b-1] != null) && (buildingArea[a][b+1] != null || buildingArea[a+1][b+1] != null)) {
											lyuk = true;
										}
									}
								}
								
								else if(a == buildingArea.length - 1 && !(b == 0 || b == buildingArea[a].length - 1)) { //utolsó sorban helyezzük el
									if(buildingArea[a-1][b] == null) {
										if((buildingArea[a][b-1] != null || buildingArea[a-1][b-1] != null) && (buildingArea[a][b+1] != null || buildingArea[a-1][b+1] != null)) {
											lyuk = true;
										}
									}
								}
								
								else if(b == 0 && !(a == 0 || a == buildingArea.length - 1)) { //0. oszlopban helyezzük el
									if(buildingArea[a][b+1] == null) {
										if((buildingArea[a-1][b] != null || buildingArea[a-1][b+1] != null) && (buildingArea[a+1][b] != null || buildingArea[a+1][b+1] != null)) {
											lyuk = true;
										}
									}
								}
								
								else if(b == buildingArea[a].length - 1 && !(a == 0 || a == buildingArea.length - 1)) { //utolsó oszlopban helyezzük el
									if(buildingArea[a][b-1] == null) {
										if((buildingArea[a-1][b] != null || buildingArea[a-1][b-1] != null) && (buildingArea[a+1][b] != null || buildingArea[a+1][b-1] != null)) {
											lyuk = true;
										}
									}
								}
								
								else {
									if(buildingArea[a+1][b] == null && buildingArea[a-1][b] == null) {
										if((buildingArea[a-1][b-1] != null || buildingArea[a][b-1] != null || buildingArea[a+1][b-1] != null) && (buildingArea[a-1][b+1] != null || buildingArea[a][b+1] != null || buildingArea[a+1][b+1] != null)) {
											lyuk = true;
										}
									}
									if(buildingArea[a][b+1] == null && buildingArea[a][b-1] == null) {
										if((buildingArea[a-1][b-1] != null || buildingArea[a-1][b] != null || buildingArea[a-1][b+1] != null) && (buildingArea[a+1][b-1] != null || buildingArea[a+1][b] != null || buildingArea[a+1][b+1] != null)) {
											lyuk = true;
										}
									}
								}
								
								if(!isToptNeighbourWallOk(buildingCard, a, b)){		//lehelyezendő lap fölött van szomszéd és jó a fal
									return false;
								} else{
									if(!isRighttNeighbourWallOk(buildingCard, a, b)){		//lehelyezendő lap jobb oldalán van szomszéd és jó a fal
										return false;
									} else{
										if(!isLeftNeighbourWallOk(buildingCard, a, b)){
											return false;
										} else{
											if(!isBottomtNeighbourWallOk(buildingCard, a, b)){
												return false;
											} else{
												if(bejarhatoOldalakSzama != 0){			//van olyan szoszédja amelyen át elérhető a start mezőről
													return true && !lyuk;
												} else{
													return false;
												}
											}
										}
									}
								}
							} else{
								return false;
							}
						}
					}
				}
			}
		}else{
			return false;
		}	
	}
	
	public boolean canRemoveBuildingCard(int a, int b){
		
		BuildingCard buildingCard = buildingArea[a][b];
		vizsgalando = new LinkedList<>();
		List<String> bejart = new LinkedList<>();
		int kozeppontX = 0;
		int kozeppontY = 0;
		int vizsgalandoLapX = 0;
		int vizsgalandoLapY = 0;
		
		if (a > 0 && a < buildingArea.length && b > 0 && b < buildingArea[a].length) { // ha nem szélen van
			if (!(buildingArea[a][b - 1] == null || buildingArea[a][b + 1] == null || buildingArea[a - 1][b] == null || buildingArea[a + 1][b] == null)) { // teljesen körbe van építve(ha kivesszük, lyuk lesz)
				return false;
			} else
				;
		} else
			;
		buildingArea[a][b] = null; 								// eltávolítás(HA NEM JÓ, VISSZATESSZÜK)

		for (int i = 0; i < buildingArea.length; i++) { 				// kezdőmező pozíciója
			for (int j = 0; j < buildingArea[i].length; j++) {
				if (buildingArea[i][j].getType().equals("STARTAREA")) {
					kozeppontX = i;
					kozeppontY = j;
					break;
				}
			}
			if (kozeppontX != 0 && kozeppontY != 0) {
				break;
			}
		}

		vizsgalando.add(buildingArea[kozeppontX][kozeppontY]); 					// középpont betétele
		BuildingCard tmp;

		while (!vizsgalando.isEmpty()) {
			tmp = vizsgalando.get(0);
			for (int i = 0; i < buildingArea.length; i++) {					 	// vizsgált lap pozíciójának kinyerése
				for (int j = 0; j < buildingArea[i].length; j++) {
					if (buildingArea[i][j].equals(tmp)) {
						vizsgalandoLapX = i;
						vizsgalandoLapY = j;
					}
				}
			}
			if (vizsgalandoLapX == 0) { 										// ha a legfelső sor
				if (vizsgalandoLapY == 0) { 									// bal felső sarok
					fillMoveToRight(tmp, vizsgalandoLapX, vizsgalandoLapY);
					fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY);
					bejart.add(tmp.getId());
					vizsgalando.remove(tmp);
				} else {
					if (vizsgalandoLapY == buildingArea[0].length) { 			// jobb felső sarok
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					} else { 												// simán csak felső sor
						fillMoveToRight(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					}
				}
			} else {
				if (vizsgalandoLapY == buildingArea[0].length) { 			// ha jobb szélső sor
					if (vizsgalandoLapX == buildingArea.length) { 			// jobb alsósarok
						fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					} else { 												// simán jobb szélső sor
						fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					}
				} else {
					if (vizsgalandoLapX == buildingArea.length) { 					// ha legalsó sor
						if (vizsgalandoLapY == 0) { 								// bal alsó sarok
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						} else { 													// simán alsó sor
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY);
							fillMoveToLeft(tmp, vizsgalandoLapX,vizsgalandoLapY);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						}
					} else {
						if (vizsgalandoLapY == 0) { 								// simán bal szélső sor
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY);
							fillMoveToBottom(tmp, vizsgalandoLapX,vizsgalandoLapY);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						} else { 													// ha nem sélső lap
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY);
							fillMoveToBottom(tmp, vizsgalandoLapX,vizsgalandoLapY);
							fillMoveToLeft(tmp, vizsgalandoLapX,vizsgalandoLapY);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						}
					}
				}
			}
		}
		int buildingCounter = 0;
		for (int i = 0; i < buildingArea.length; i++) { 		// épületlapok száma az
																// építési felületen
			for (int j = 0; j < buildingArea[i].length; j++) {
				if (buildingArea[i][j] != null) {
					buildingCounter++;
				}
			}
		}
		if (bejart.size() < buildingCounter) { 				// ha kevesebbet jártunk be mint
															// ahány van
			buildingArea[a][b] = buildingCard; 				// visszatesszük
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean isLeftNeighbourWallOk(BuildingCard buildingCard, int a, int b){
		if(buildingArea[a][b-1] != null){				//van baloldali szomszéd
			if((buildingArea[a][b-1].isRight_wall() && buildingCard.isLeft_wall()) || (!buildingArea[a][b-1].isRight_wall() && !buildingCard.isLeft_wall())){		//fal-fal  vagy  üres-üres
				if(!buildingArea[a][b-1].isRight_wall() && !buildingCard.isLeft_wall()){	//balról elérhető a startmezőről
					bejarhatoOldalakSzama++;
				}
				return true;
			}else{
				return false;					
			}
		}else{
			return true;
		}
	}
	
	public boolean isToptNeighbourWallOk(BuildingCard buildingCard, int a, int b){
		if(buildingArea[a-1][b] != null){				//van felső szomszéd
			if((buildingArea[a-1][b].isBottom_wall() && buildingCard.isTop_wall()) || (!buildingArea[a-1][b].isBottom_wall() && !buildingCard.isTop_wall())){		//fal-fal  vagy  üres-üres
				if(!buildingArea[a-1][b].isBottom_wall() && !buildingCard.isTop_wall()){	//fentről elérhető a startmezőről(nincs fal)
					bejarhatoOldalakSzama++;
				}
				return true;
			}else{
				return false;					
			}
		}else{
			return true;
		}
	}
	
	public boolean isRighttNeighbourWallOk(BuildingCard buildingCard, int a, int b){
		if(buildingArea[a][b+1] != null){				//van jobb szomszéd
			if((buildingArea[a][b+1].isLeft_wall() && buildingCard.isRight_wall()) || (!buildingArea[a][b+1].isLeft_wall() && !buildingCard.isRight_wall())){		//fal-fal  vagy  üres-üres
				if(!buildingArea[a][b+1].isLeft_wall() && !buildingCard.isRight_wall()){	//jobbról elérhető az startmezőről
					bejarhatoOldalakSzama++;
				}
				return true;
			}else{
				return false;					
			}
		}else{
			return true;
		}
	}
	
	public boolean isBottomtNeighbourWallOk(BuildingCard buildingCard, int a, int b){
		if(buildingArea[a+1][b] != null){				//van alsó szomszéd
			if((buildingArea[a+1][b].isTop_wall() && buildingCard.isBottom_wall()) || (!buildingArea[a+1][b].isTop_wall() && !buildingCard.isBottom_wall())){		//fal-fal  vagy  üres-üres
				if(!buildingArea[a+1][b].isTop_wall() && !buildingCard.isBottom_wall()){	//lentről elérhető a startmezőről
					bejarhatoOldalakSzama++;
				}
				return true;
			}else{
				return false;					
			}
		}else{
			return true;
		}
	}
	
	public void fillMoveToTop(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY){
		if(!tmp.isTop_wall()){		//ha fent nincs fal
			if(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY] != null){		//van felső szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY])){
					vizsgalando.add(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY]);
				} else ;
			} else ;
		}else ;
	}
	
	public void fillMoveToLeft(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY){
		if(!tmp.isLeft_wall()){		//ha baloldalt nincs fal
			if(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1] != null){		//ha van baloldali szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1])){
					vizsgalando.add(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1]);
				} else ;
			} else ;
		} else ;
	}
	
	public void fillMoveToBottom(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY){
		if(!tmp.isBottom_wall()){		//ha lent nincs fal
			if(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY] != null){		//ha van alsó szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY])){
					vizsgalando.add(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY]);
				} else ;
			} else ;
		} else ;
	}
	
	public void fillMoveToRight(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY){
		if(!tmp.isRight_wall()){		//ha jobb oldalt nincs fal
			if(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1] != null){		//ha van jobb oldali szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1])){
					vizsgalando.add(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1]);
				} else ;
			} else ;
		} else ;
	}
	
	
	
	
	public BuildingCard[][] getBuildingArea() {
		return buildingArea;
	}
	
}
