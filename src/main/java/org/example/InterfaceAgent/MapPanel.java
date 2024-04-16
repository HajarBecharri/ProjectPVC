package org.example.InterfaceAgent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.example.problematic.Ville;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MapPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private List<Ville> villes = new ArrayList<Ville>(); // Liste des villes
	private List<Ville> distances = new ArrayList<Ville>(); // Liste des distances entres les villes
	private char[] nomsVilles = new char[26];
	private boolean plusCourt = false;
	private int etapeActuelle;

	public Image imageBackground;
	public Image imageVille;
	public int WIDTH_MAP = 500;
	public int HEIGHT_MAP = 300;

	/**
	 * Create the panel.
	 */
	/* ----- Constructeur ----- */
	public MapPanel() {
		setBackground(new Color(245, 255, 250));
		this.setBounds(0, 0, WIDTH_MAP, HEIGHT_MAP);
		setLayout(null);

		for (int i = 0; i < 25; i++) {
			this.nomsVilles[i] = (char) ('a' + new Random().nextInt(26));
		}

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				try {
					imageVille = new ImageIcon(MapPanel.class.getResource("/images/loc5.png")).getImage();
				} catch (final Exception ex) {
					imageVille = null;
				}

				villes.add(new Ville(villes.size() + 1, Character.toString(nomsVilles[villes.size()]), x, y, false));

//				System.out.println("Ville : " + Character.toString(nomsVilles[villes.size()]) + "\n(x,y) : (" + e.getX() + ", " + e.getY()
//						+ ")\nNbre de villes : " + villes.size() + "\n");
				repaint();
			}
		});
	}

	/* ----- Getters & Setters ----- */
	public List<Ville> getVilles() {
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

	public List<Ville> getDistances() {
		return distances;
	}

	public void setDistances(List<Ville> distances) {
		this.distances = distances;
	}

	public boolean isPlusCourt() {
		return plusCourt;
	}

	public void setPlusCourt(boolean plusCourt) {
		this.plusCourt = plusCourt;
	}

	public int getEtapeActuelle() {
		return etapeActuelle;
	}

	public void setEtapeActuelle(int etapeActuelle) {
		this.etapeActuelle = etapeActuelle;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);

		for (Ville ville : villes) {
			paintVille(g2d, ville);
		}
		
		paintPlusCourtDistance(g2d);
	}

	/* ----- L'arriere plan (la carte) du panel ----- */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		this.imageBackground = new ImageIcon(MapPanel.class.getResource("/images/map.png")).getImage();

		if (imageBackground != null) {
			g.drawImage(imageBackground, 0, 0, this);
		}
	}

	/* ----- Dessin des villes ----- */
	public void paintVille(Graphics2D g, Ville ville) {
		int x = (int) ville.getPosX();
		int y = (int) ville.getPosY();

		// Black background
		g.setColor(Color.RED);
		g.fillOval(x - 20, y - 5, 1, 1);

		/* ----- Image de la ville ----- */
		g.drawImage(imageVille, x - 20, y - 35, null);
	}

	/* ----- Dessin d'une distance entre deux villes ----- */
	public void paintDistance(Graphics2D g, Ville ville1, Ville ville2,int i) {
		if (ville1 != null && ville2 != null) {
			int x1 = (int) ville1.getPosX();
			int y1 = (int) ville1.getPosY();
			int x2 = (int) ville2.getPosX();
			int y2 = (int) ville2.getPosY();

			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(2));
			g.drawLine(x1, y1, x2, y2);
			// Déterminez les coordonnées du point milieu entre les deux villes
			int midX = (x1 + x2) / 2;
			int midY = (y1 + y2) / 2;

			// Dessinez le nombre à côté de la ligne
			// Set the font size and style
			Font font = new Font("Arial", Font.BOLD, 16); // Adjust size and style as needed

			// Draw the number next to the line
			g.setFont(font);
			g.setColor(new Color(58, 109, 214));
			g.drawString(Integer.toString(i), midX, midY);
		}

	}

	/* ----- Dessin du plus court chemin ----- */
	public void paintPlusCourtDistance(Graphics2D g2d) {
		List<Ville> mesDistances = this.distances;
		for (int i = 0; i < mesDistances.size() - 1; i++) {
			if (i >= etapeActuelle) {
				System.out.println("Etape actuelle : " + etapeActuelle);
				return;
			}
			paintDistance(g2d, mesDistances.get(i), mesDistances.get(i + 1),i);
			// Pause the loop execution for 2 seconds after each iteration

		}
	}
}
