package org.example.Outils;

import java.io.Serializable;

public class Ville implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ----- les variables
	private int idVille;
	private String nomVille;
	private double posX;
	private double posY;
	private boolean visite = false;

	// ------- Constructeur ------
	public Ville(int idVille, String nomVille, double posX, double posY, boolean visite) {
		super();
		this.idVille = idVille;
		this.nomVille = nomVille;
		this.posX = posX;
		this.posY = posY;
		this.visite = visite;
	}

	// ------- Getetrs & Setters -------
	public int getIdVille() {
		return idVille;
	}

	public void setIdVille(int idVille) {
		this.idVille = idVille;
	}

	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public boolean isVisite() {
		return visite;
	}

	public void setVisite(boolean visite) {
		this.visite = visite;
	}

	// ------- distance entre deux villes
	public double getDistance(Ville ville) {

		double x = Math.abs(this.posX - ville.getPosX());
		double y = Math.abs(this.posY - ville.getPosY());
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

//		
//		double deltaXSq = Math.pow((ville.getPosX() - this.getPosX()), 2);
//		double deltaYSq = Math.pow((ville.getPosY() - this.getPosY()), 2);
//
//		// Calculer le plus court chemin
//		double distance = Math.sqrt(Math.abs(deltaXSq + deltaYSq));
//		return distance;

	}

}
