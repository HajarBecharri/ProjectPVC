package Outils;

public class Distance {
	
	private Ville parcours[];
	private double distance = 0;
	
	//------ Constructeur ------------
	public Distance(Chemin chemin, Ville villes[]) {
		
		//--- get les villes parcourues dans chemin -------
		int villesParcourues[] = chemin.getVilleParcourus();
		
		this.parcours = new Ville[villes.length];
		
		//---- remplir la table des villes parcourues ----
		for(int i=0; i < villesParcourues.length ; i++) 
		{
			
			this.parcours[i] = villes[villesParcourues[i]];
		}
	
		
	}
	
	//------ getters -------
	public Ville[] getParcours() {
		return parcours;
	}

	public double getDistance() {
		
		if (this.distance > 0) {
			return this.distance;
		}
	
		//------ calcul de la distance totale entre les villes parcourues ----
		
		double distanceTotale = 0;
		
		for (int indexVille = 0 ; indexVille + 1 < this.parcours.length ; indexVille++) {
			distanceTotale += this.parcours[indexVille].getDistance(this.parcours[indexVille + 1]);
		}
		
		distanceTotale += this.parcours[this.parcours.length - 1].getDistance(this.parcours[0]);
		
		this.distance = distanceTotale;
		
		return distanceTotale;
	}
		
	
	//----- setters -----
	public void setParcours(Ville[] parcours) {
		this.parcours = parcours;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}
}
