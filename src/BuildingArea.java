
public class BuildingArea {
	private BuildingCard [][] buildingArea;
	private int bejarhatoOldalakSzama = 0;

	public BuildingArea() {
		this.buildingArea = new BuildingCard [5][5];
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
		
		if(buildingArea[a][b-1] == null || buildingArea[a][b+1] == null || buildingArea[a-1][b] == null || buildingArea[a+1][b] == null){ //van legalább egy üres oldala(ha kivesszük, nem lesz lyuk)
			
		} else{
			return false;
		}
		
		return false;
		
	}
	
	public void extendAreaTop(){
		
	}
	
	public void extendAreaBottom(){
		
	}
	
	public void extendAreaLeft(){
		
	}
	
	public void extendAreaRight(){
		
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
	
	
	
	
	
	
	public BuildingCard[][] getBuildingArea() {
		return buildingArea;
	}
	
}
