import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;




public class BuildingArea {
	private BuildingCard [][] buildingArea;
	private int bejarhatoOldalakSzama = 0;
	private List<BuildingCard> vizsgalando;

	
	
	
	public BuildingArea() {
		this.buildingArea = new BuildingCard [21][21];
		BuildingCard kozep = new BuildingCard("STARTAREA", false, false, false, false, -1, null);
		buildingArea[10][10] = kozep;
	}

	public void addBuildingCard(BuildingCard buildingCard, int a, int b){
		this.buildingArea[a][b] = buildingCard;
		kulsoFalBeallito();
	}
	
	public void removeBuildingCard(int a, int b){
		this.buildingArea[a][b] = null;
		kulsoFalBeallito();
	}
	
	public boolean canAddBuildingCard(BuildingCard buildingCard, int a, int b){
		int kozeppontX = 0;
		int kozeppontY = 0;
		bejarhatoOldalakSzama = 0;
		boolean lyuk = false;
		
		System.out.println(a +"\t" +b);
		
		if(buildingArea[a][b] == null){		// üres a mező
			for(int i=0; i<buildingArea.length; i++){		//kezdőmező pozíciója
				for(int j=0; j<buildingArea[i].length; j++){
					if(buildingArea[i][j] != null && buildingArea[i][j].getType().equals("STARTAREA")){
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
										if(isHole(a+1, b, a, b)) {
											lyuk = true;
										}
									}
								}
								
								else if(a == buildingArea.length - 1 && !(b == 0 || b == buildingArea[a].length - 1)) { //utolsó sorban helyezzük el
									if(buildingArea[a-1][b] == null) {
										if(isHole(a-1, b, a, b)) {
											lyuk = true;
										}
									}
								}
								
								else if(b == 0 && !(a == 0 || a == buildingArea.length - 1)) { //0. oszlopban helyezzük el
									if(buildingArea[a][b+1] == null) {
										if(isHole(a, b+1, a, b)) {
											lyuk = true;
										}
									}
								}
								
								else if(b == buildingArea[a].length - 1 && !(a == 0 || a == buildingArea.length - 1)) { //utolsó oszlopban helyezzük el
									if(buildingArea[a][b-1] == null) {
										if(isHole(a, b-1, a, b)) {
											lyuk = true;
										}
									}
								}
								
								else {
									if(buildingArea[a][b-1] == null) {
										System.out.println("bal lyuk vizsgálat");
										if(isHole(a, b-1, a, b)) {
											lyuk = true;
											System.out.println("lyuk balra");
										}
									}
									if(buildingArea[a][b+1] == null) {
										System.out.println("jobb lyuk vizsgálat");
										if(isHole(a, b+1, a, b)) {
											lyuk = true;
											System.out.println("lyuk jobbra");
										}
									}
									if(buildingArea[a-1][b] == null) {
										System.out.println("top lyuk vizsgálat");
										if(isHole(a-1, b, a, b)) {
											lyuk = true;
											System.out.println("lyuk felette");
										}
									}
									if(buildingArea[a+1][b] == null) {
										System.out.println("lent lyuk vizsgálat");
										if(isHole(a+1, b, a, b)) {
											lyuk = true;
											System.out.println("lyuk alatta");
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
		buildingArea[a][b] = null; 								// eltávolítás(MINDIG VISSZATESSZÜK)

		for (int i = 0; i < buildingArea.length; i++) { 				// kezdőmező pozíciója
			for (int j = 0; j < buildingArea[i].length; j++) {
				if (buildingArea[i][j] != null && buildingArea[i][j].getType().equals("STARTAREA")) {
					kozeppontX = i;
					kozeppontY = j;
					break;
				}
			}
			if (kozeppontX != 0 && kozeppontY != 0) {
				break;
			}
		}

		if(kozeppontX == a && kozeppontY == b) {
			return false;
		}
		
		vizsgalando.add(buildingArea[kozeppontX][kozeppontY]); 					// középpont betétele
		BuildingCard tmp;

		while (!vizsgalando.isEmpty()) {
			tmp = vizsgalando.get(0);
			for (int i = 0; i < buildingArea.length; i++) {					 	// vizsgált lap pozíciójának kinyerése
				for (int j = 0; j < buildingArea[i].length; j++) {
					if (buildingArea[i][j] != null && buildingArea[i][j].equals(tmp)) {
						vizsgalandoLapX = i;
						vizsgalandoLapY = j;
					}
				}
			}
			if (vizsgalandoLapX == 0) { 										// ha a legfelső sor
				if (vizsgalandoLapY == 0) { 									// bal felső sarok
					fillMoveToRight(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
					fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
					bejart.add(tmp.getId());
					vizsgalando.remove(tmp);
				} else {
					if (vizsgalandoLapY == buildingArea[0].length - 1) { 			// jobb felső sarok
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					} else { 												// simán csak felső sor
						fillMoveToRight(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					}
				}
			} else {
				if (vizsgalandoLapY == buildingArea[0].length - 1) { 			// ha jobb szélső sor
					if (vizsgalandoLapX == buildingArea.length - 1) { 			// jobb alsósarok
						fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					} else { 												// simán jobb szélső sor
						fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToBottom(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						fillMoveToLeft(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
						bejart.add(tmp.getId());
						vizsgalando.remove(tmp);
					}
				} else {
					if (vizsgalandoLapX == buildingArea.length - 1) { 					// ha legalsó sor
						if (vizsgalandoLapY == 0) { 								// bal alsó sarok
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						} else { 													// simán alsó sor
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							fillMoveToLeft(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						}
					} else {
						if (vizsgalandoLapY == 0) { 								// simán bal szélső sor
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							fillMoveToBottom(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						} else { 													// ha nem szélső lap
							fillMoveToTop(tmp, vizsgalandoLapX, vizsgalandoLapY, bejart);
							fillMoveToRight(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							fillMoveToBottom(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							fillMoveToLeft(tmp, vizsgalandoLapX,vizsgalandoLapY, bejart);
							bejart.add(tmp.getId());
							vizsgalando.remove(tmp);
						}
					}
				}
			}
		}
		int buildingCounter = 0;
		for (int i = 0; i < buildingArea.length; i++) { 		// épületlapok száma az építési felületen
			for (int j = 0; j < buildingArea[i].length; j++) {
				if (buildingArea[i][j] != null) {
					buildingCounter++;
				}
			}
		}
		
		buildingArea[a][b] = buildingCard; 					// visszatesszük
		
		if (bejart.size() < buildingCounter) { 				// ha kevesebbet jártunk be mint ahány van
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
	
	public void fillMoveToTop(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY, List<String> bejart){
		if(!tmp.isTop_wall()){		//ha fent nincs fal
			if(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY] != null){		//van felső szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY]) && !bejart.contains(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY].getId())){
					vizsgalando.add(buildingArea[vizsgalandoLapX - 1][vizsgalandoLapY]);
				} else ;
			} else ;
		}else ;
	}
	
	public void fillMoveToLeft(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY, List<String> bejart){
		if(!tmp.isLeft_wall()){		//ha baloldalt nincs fal
			if(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1] != null){		//ha van baloldali szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1]) && !bejart.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1].getId())){
					vizsgalando.add(buildingArea[vizsgalandoLapX][vizsgalandoLapY - 1]);
				} else ;
			} else ;
		} else ;
	}
	
	public void fillMoveToBottom(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY, List<String> bejart){
		if(!tmp.isBottom_wall()){		//ha lent nincs fal
			if(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY] != null){		//ha van alsó szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY]) && !bejart.contains(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY].getId())){
					vizsgalando.add(buildingArea[vizsgalandoLapX + 1][vizsgalandoLapY]);
				} else ;
			} else ;
		} else ;
	}
	
	public void fillMoveToRight(BuildingCard tmp, int vizsgalandoLapX, int vizsgalandoLapY, List<String> bejart){
		if(!tmp.isRight_wall()){		//ha jobb oldalt nincs fal
			if(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1] != null){		//ha van jobb oldali szomszéd
				if(!vizsgalando.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1]) && !bejart.contains(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1].getId())){
					vizsgalando.add(buildingArea[vizsgalandoLapX][vizsgalandoLapY + 1]);
				} else ;
			} else ;
		} else ;
	}
	
	
	public boolean isEmptyPlaceForBot(int i, int j){
		if(buildingArea[i][j] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isHole(int a, int b, int dontX, int dontY){	//az épületlap melletti üres hely koordinátáját kapja meg
		List<int[]> bejart = new LinkedList<int[]>();
		List<int[]> bejarando = new LinkedList<int[]>();
		int vizsgaltX = 0, vizsgaltY = 0;
		int[] koordinataTomb = new int[2];
		BuildingCard bc = new BuildingCard("d", false, false, false, false, 5, "tmp"); 
		buildingArea[dontX][dontY] = bc;
		
		koordinataTomb[0] = a;
		koordinataTomb[1] = b;
		bejarando.add(koordinataTomb);
		
		while(!bejarando.isEmpty()){
			int[] tmpTomb = new int[2];
			vizsgaltX = bejarando.get(0)[0];
			vizsgaltY = bejarando.get(0)[1];
			
			if(vizsgaltX == 0 || vizsgaltY == 0 || vizsgaltX == buildingArea.length-1 || vizsgaltY == buildingArea.length-1){	//ha elérjük a tábla szélét, akkor nem lehet lyuk
				buildingArea[dontX][dontY] = null;
				return false;
			}else{
				if(emptyTopEmptyNeighbour(vizsgaltX, vizsgaltY)){		//üres a felső szomszéd
					tmpTomb = new int[2];
					tmpTomb[0] = vizsgaltX-1;
					tmpTomb[1] = vizsgaltY;
					if(!intTombContains(bejarando, tmpTomb) && !intTombContains(bejart, tmpTomb)){		//csak akkor adjuk hozzá ha még nem tartalmazza
						bejarando.add(tmpTomb);
					}
				}
				if(emptyRightEmptyNeighbour(vizsgaltX, vizsgaltY)){		//üres jobb szomszéd
					tmpTomb = new int[2];
					tmpTomb[0] = vizsgaltX;
					tmpTomb[1] = vizsgaltY+1;
					if(!intTombContains(bejarando, tmpTomb) && !intTombContains(bejart, tmpTomb)){		//csak akkor adjuk hozzá ha még nem tartalmazza
						bejarando.add(tmpTomb);
					}
				}
				if(emptyBottomEmptyNeighbour(vizsgaltX, vizsgaltY)){		//üres alsó szomszéd
					tmpTomb = new int[2];
					tmpTomb[0] = vizsgaltX+1;
					tmpTomb[1] = vizsgaltY;
					if(!intTombContains(bejarando, tmpTomb) && !intTombContains(bejart, tmpTomb)){		//csak akkor adjuk hozzá ha még nem tartalmazza
						bejarando.add(tmpTomb);
					}
				}
				if(emptyLeftEmptyNeighbour(vizsgaltX, vizsgaltY)){		//üres bal szomszéd
					tmpTomb = new int[2];
					tmpTomb[0] = vizsgaltX;
					tmpTomb[1] = vizsgaltY-1;
					if(!intTombContains(bejarando, tmpTomb) && !intTombContains(bejart, tmpTomb)){			//csak akkor adjuk hozzá ha még nem tartalmazza
						bejarando.add(tmpTomb);
					}
				}
				if(!intTombContains(bejart, bejarando.get(0))){
					bejart.add(bejarando.get(0));
				}
				bejarando.remove(0);
			}
		}
		buildingArea[dontX][dontY] = null;
		return true;
	}
	
	public boolean intTombContains(List<int[]> lista, int[] tomb){
		for(int[] akt: lista){
			if(akt[0] == tomb[0] && akt[1] == tomb[1]){
				return true;
			}
		}
		return false;
	}
	
	public boolean emptyTopEmptyNeighbour(int a, int b){
		if(buildingArea[a-1][b] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean emptyRightEmptyNeighbour(int a, int b){
		if(buildingArea[a][b+1] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean emptyBottomEmptyNeighbour(int a, int b){
		if(buildingArea[a+1][b] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean emptyLeftEmptyNeighbour(int a, int b){
		if(buildingArea[a][b-1] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public int wallCounter(){
		List<BuildingCard> vizsgalt = new LinkedList<BuildingCard>();
		List<BuildingCard> vizsgalando = new LinkedList<BuildingCard>();
		
		List<List<BuildingCard>> osszefuggoFalKartyak = new LinkedList<List<BuildingCard>>();
		int maxWall = 0;
		int aktFalHossz;
		
		for(int i = 0; i < 21; i++){
			for(int j = 0; j < 21; j++){
				if(buildingArea[i][j] != null && !vizsgalt.contains(buildingArea[i][j])){		//ha nem üres mező és még nem vizsgáltuk
					if(buildingArea[i][j].isOutSideBottomWall() || (buildingArea[i][j].isOutSideLeftWall() || buildingArea[i][j].isOutSideRightWall() || buildingArea[i][j].isOutSideTopWall())){
						vizsgalando.add(buildingArea[i][j]);
						for(int k = 0; k < vizsgalando.size(); k++){ 
							BuildingCard tmpb = vizsgalando.get(k);
							GetASequence(tmpb, vizsgalt, vizsgalando);
						}
						Iterator<BuildingCard> it2 = vizsgalando.iterator();
						List<BuildingCard> tmp = new LinkedList<BuildingCard>();
						while(it2.hasNext()){
							tmp.add(it2.next());
						}
						System.out.println("FAAAAAAAAASZKIVAN EZZEL A GECIVEL!!!!!!!!");
						vizsgalando.clear();
						osszefuggoFalKartyak.add(tmp);
						
						
					}
					
					
				}
			}
		}
		for(int k = 0; k< osszefuggoFalKartyak.size(); k++){
			aktFalHossz = 0;
			for(BuildingCard akt : osszefuggoFalKartyak.get(k)){
				if(akt.isOutSideBottomWall()){
					aktFalHossz += 1;
				}
				if(akt.isOutSideLeftWall()){
					aktFalHossz += 1;
				}
				if(akt.isOutSideRightWall()){
					aktFalHossz += 1;
				}
				if(akt.isOutSideTopWall()){
					aktFalHossz += 1;
				}
			}
			if(maxWall < aktFalHossz){
				maxWall = aktFalHossz;
			}
		}
		
		for(int k=0; k < osszefuggoFalKartyak.size(); k++){
			for(BuildingCard akt : osszefuggoFalKartyak.get(k)){
				System.out.println(akt.getImage());
			}
			System.out.println();
		}
		
		
		return maxWall;
	}
	
	public void GetASequence(BuildingCard aktualis, List<BuildingCard> vizsgalt, List<BuildingCard> vizsgalando){
		int i = 0, j = 0;

		for(int x = 0; x < 21; x++){
			for(int y = 0; y < 21; y++){
				if(buildingArea[x][y] != null){
					if(buildingArea[x][y].equals(aktualis)){
						i = x;
						j = y;
					}
				}
			}
		}
		vizsgalt.add(aktualis);
		
		//
		if (aktualis.isOutSideTopWall()) {
			if (isRightNeighbour(i, j)) { // TOP-----JOBBRA
				if (buildingArea[i][j + 1].isOutSideTopWall()) { // fal jobbra megy
					if (!vizsgalt.contains(buildingArea[i][j + 1])) { // még nem vizsgáltuk
						vizsgalando.add(buildingArea[i][j + 1]);
			// 			System.out.println("hozzaadva: "+buildingArea[i][j+1].getImage());
					}
				} 
			}
			if (buildingArea[i-1][j+1] != null) {
				if (buildingArea[i - 1][j + 1].isOutSideLeftWall() || buildingArea[i - 1][j + 1].isOutSideBottomWall()) { // fal jobbra fel átlósan megy
					if(!vizsgalt.contains(buildingArea[i-1][j+1])){
						vizsgalando.add(buildingArea[i-1][j+1]);
			//			System.out.println("hozzaadva: "+buildingArea[i-1][j+1].getImage());
					}
				}
			}	
			
			if(isLeftNeighbour(i, j)){						//TOP-----BALRA
				if(buildingArea[i][j-1].isOutSideTopWall()){		//fal balra megy
					if(!vizsgalt.contains(buildingArea[i][j-1])){
						vizsgalando.add(buildingArea[i][j-1]);
			//			System.out.println("hozzaadva: "+buildingArea[i][j-1].getImage());
					}
				}
			}
			if(buildingArea[i-1][j-1] != null){
				if(buildingArea[i-1][j-1].isOutSideRightWall() || buildingArea[i-1][j-1].isOutSideBottomWall()){		//fal balra fel átlósan megy
					if(!vizsgalt.contains(buildingArea[i-1][j-1])){
						vizsgalando.add(buildingArea[i-1][j-1]);
			//			System.out.println("hozzaadva: "+buildingArea[i-1][j-1].getImage());
					}
				}
			}
		}
		
		//BAL KÜLSŐ FALA IS VAN
		if(aktualis.isOutSideLeftWall()){
			if(isBottomNeighbour(i, j)){
				if(buildingArea[i+1][j].isOutSideLeftWall()){			//fal lefelé megy
					if(!vizsgalt.contains(buildingArea[i+1][j])){
						vizsgalando.add(buildingArea[i+1][j]);
					}
				}
			}
			if(buildingArea[i+1][j-1] != null){
				if(buildingArea[i+1][j-1].isOutSideTopWall() || buildingArea[i+1][j-1].isOutSideRightWall()){			//fal balra le átlósan megy
					if(!vizsgalt.contains(buildingArea[i+1][j-1])){
						vizsgalando.add(buildingArea[i+1][j-1]);
					}
				}
			}
			if(isTopNeighbour(i, j)){
				if(buildingArea[i-1][j].isOutSideLeftWall()){			//fal fel megy
					if(!vizsgalt.contains(buildingArea[i-1][j])){
						vizsgalando.add(buildingArea[i-1][j]);
					}
				}
			}
			if(buildingArea[i-1][j-1] != null){
				if(buildingArea[i-1][j-1].isOutSideBottomWall() || buildingArea[i-1][j-1].isOutSideRightWall()){		//fal fel átlósan megy
					if(!vizsgalt.contains(buildingArea[i-1][j-1])){
						vizsgalando.add(buildingArea[i-1][j-1]);
					}
				}
			}
		}
		
		//JOBB KÜLSŐ FAL IS VAN
		if(aktualis.isOutSideRightWall()){
			if(isBottomNeighbour(i, j)){
				if(buildingArea[i+1][j].isOutSideRightWall()){			//fal lefelé megy
					if(!vizsgalt.contains(buildingArea[i+1][j])){
						vizsgalando.add(buildingArea[i+1][j]);
					}
				}
			}
			if(buildingArea[i+1][j+1] != null){
				if(buildingArea[i+1][j+1].isOutSideLeftWall() || buildingArea[i+1][j+1].isOutSideTopWall()){		//fal lefelé átlósan megy
					if(!vizsgalt.contains(buildingArea[i+1][j+1])){
						vizsgalando.add(buildingArea[i+1][j+1]);
					}
				}
			}
			if(isTopNeighbour(i, j)){
				if(buildingArea[i-1][j].isOutSideRightWall()){		//fal felfelé megy
					if(!vizsgalt.contains(buildingArea[i-1][j])){
						vizsgalando.add(buildingArea[i-1][j]);
					}
				}
			}
			if(buildingArea[i-1][j+1] != null){
				if(buildingArea[i-1][j+1].isOutSideBottomWall() || buildingArea[i-1][j+1].isOutSideLeftWall()){		//fal fel átlósan megy
					if(!vizsgalt.contains(buildingArea[i-1][j+1])){
						vizsgalando.add(buildingArea[i-1][j+1]);
					}
				}
			}
		}
		
		//ALSÓ KÜLSŐ FAL IS VAN
		if(aktualis.isOutSideBottomWall()){
			if(isRightNeighbour(i, j)){
				if(buildingArea[i][j+1].isOutSideBottomWall()){				//fal jobbra megy
					if(!vizsgalt.contains(buildingArea[i][j+1])){
						vizsgalando.add(buildingArea[i][j+1]);
					}
				}
			}
			if(buildingArea[i+1][j+1] != null){
				if(buildingArea[i+1][j+1].isOutSideLeftWall() || buildingArea[i+1][j+1].isOutSideTopWall()){	//fal jobbra le átlósan megy
					if(!vizsgalt.contains(buildingArea[i+1][j+1])){
						vizsgalando.add(buildingArea[i+1][j+1]);
					}
				}
			}
			if(isLeftNeighbour(i, j)){
				if(buildingArea[i][j-1].isOutSideBottomWall()){			//fal balra megy
					if(!vizsgalt.contains(buildingArea[i][j-1])){
						vizsgalando.add(buildingArea[i][j-1]);
					}
				}
			}
			if(buildingArea[i+1][j-1] != null){
				if(buildingArea[i+1][j-1].isOutSideRightWall() || buildingArea[i+1][j-1].isOutSideTopWall()){		//fal balra le átlósan megy
					if(!vizsgalt.contains(buildingArea[i+1][j-1])){
						vizsgalando.add(buildingArea[i+1][j-1]);
					}
				}
			}
		}	
	}
	
	public void kulsoFalBeallito(){
		for(int i = 0; i < 21; i++){
			for(int j = 0; j < 21; j++){
				if(buildingArea[i][j] != null){
					if(buildingArea[i][j].isTop_wall() && !isTopNeighbour(i, j)){
						buildingArea[i][j].setOutSideTopWall(true);
					}else{
						buildingArea[i][j].setOutSideTopWall(false);
					}
					
					if(buildingArea[i][j].isRight_wall() && !isRightNeighbour(i, j)){
						buildingArea[i][j].setOutSideRightWall(true);
					}else{
						buildingArea[i][j].setOutSideRightWall(false);
					}
					
					if(buildingArea[i][j].isBottom_wall() && !isBottomNeighbour(i, j)){
						buildingArea[i][j].setOutSideBottomWall(true);
					}else{
						buildingArea[i][j].setOutSideBottomWall(false);
					}
					
					if(buildingArea[i][j].isLeft_wall() && !isLeftNeighbour(i, j)){
						buildingArea[i][j].setOutSideLeftWall(true);
					}else{
						buildingArea[i][j].setOutSideLeftWall(false);
					}
				}
			}
		}
	}
	
	public boolean isTopNeighbour(int i, int j){
		if(i == 0){
			return false;
		}
		if(buildingArea[i-1][j] != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isRightNeighbour(int i, int j){
		if(j == buildingArea.length){
			return false;
		}
		if(buildingArea[i][j+1] != null ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isBottomNeighbour(int i, int j){
		if(i == buildingArea.length){
			return false;
		}
		if(buildingArea[i+1][j] != null && buildingArea[i+1][j].getValue() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isLeftNeighbour(int i, int j){
		if(j == 0){
			return false;
		}
		if(buildingArea[i][j-1] != null && buildingArea[i][j-1].getValue() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public BuildingCard[][] getBuildingArea() {
		return buildingArea;
	}
	
}
