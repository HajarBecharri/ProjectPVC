package Outils;
/*
	Cette classe sert à trouver des chemins qui represente une solution donnée est proche de la 
	solution optimale du problème souhaité. 
	Il détermine l'adéquation d'une solution.
*/

import java.util.Arrays;

public class Chemin {

	
	private int [] villeParcourus; // ensemble des villes déjà visité
	private double score = -1;  // score convenable au chemin  
	
	public Chemin(int[] villeParcourus)
	{
		this.villeParcourus = villeParcourus;
	}

	public Chemin(int nbreVilleParcourus)
	{
		//initialisation du tableaux
		int[] villerempli;
		villerempli = new int[nbreVilleParcourus];
//			villeParcourus =new int[nbreVilleParcourus];
		//créer un chemin aléatoire  
		for(int ville = 0; ville < nbreVilleParcourus; ville++)
		{
		
			villerempli[ville] = ville; 
		}
		
		this.villeParcourus = villerempli;
	}
	
	public int[] getVilleParcourus() {
		return villeParcourus;
	}

	public void setVilleParcourus(int[] villeParcourus) {
		this.villeParcourus = villeParcourus;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getnbreVilleParcourus() {
		return this.villeParcourus.length;
	}
	@Override
	public String toString() {
		return "Chemin [villeParcourus=" + Arrays.toString(villeParcourus) + ", score=" + score + "]";
	}
	
	// verifier si une ville existe sur un chemin 
	public boolean verifierVille(int ville)
	{
		for(int i = 0; i < this.villeParcourus.length; i++)
			if(this.villeParcourus[i] == ville)
				return true;
		
		
		return false;
	}
	
	public void setVille(int offset, int gene) {
		this.villeParcourus[offset] = gene;
	}

	
	public int getVille(int offset) {
		return this.villeParcourus[offset];
	}
	
	
}
