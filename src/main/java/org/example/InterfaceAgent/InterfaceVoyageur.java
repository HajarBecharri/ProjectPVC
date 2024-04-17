package org.example.InterfaceAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jade.gui.GuiEvent;
import org.example.InterfaceAgent.MapPanel;
import org.example.voyageur.AgentVoyageur;

import org.example.problematic.statePVC ;
import org.example.problematic.Ville ;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class InterfaceVoyageur extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public JPanel mapPanel;
	private MapPanel map;

	private AgentVoyageur agentVoyageur;
	private Ville villes[];
	private statePVC pvc = new statePVC();
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceVoyageur frame = new InterfaceVoyageur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	/* ----- Constructeur ----- */
	public InterfaceVoyageur() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterfaceVoyageur.class.getResource("/images/city5.png")));
		setTitle("Logistic Application ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 515);
		setResizable(false);
		contentPane = new JPanel();
//		contentPane.setBackground(new Color(165, 214, 247));
		contentPane.setBackground(new Color(250, 249, 248));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mapPanel = new JPanel();
		mapPanel.setBackground(new Color(250, 249, 248));
		mapPanel.setBounds(20, 45, 580, 400);
		contentPane.add(mapPanel);
		mapPanel.setLayout(null);

		map = new MapPanel();
		map.setBounds(0, 25, 575, 360);
		mapPanel.add(map);
		map.setBorder(BorderFactory.createLineBorder(new Color(63, 54, 151), 2));
		map.repaint();

		/* ----- Bouton pour calculer la solution ----- */
		JButton getCheminBtn = new JButton("Valider");
		getCheminBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		getCheminBtn.setForeground(new Color(255, 255, 255));
		getCheminBtn.setBackground(new Color(11, 93, 216));

		getCheminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("On va commencer le calcul :)");
				GuiEvent gev = new GuiEvent(contentPane.getUI(), 1);
				List<Ville> villes = map.getVilles();
				java.util.Map<String, Object> params = new HashMap<>();
				params.put("v1", villes);

				gev.addParameter(params);
				agentVoyageur.onGuiEvent(gev);
			}
		});
		getCheminBtn.setBounds(493, 440, 100, 30);
		contentPane.add(getCheminBtn);

		/* ----- Bouton revenir en arri�re ----- */
		JButton undoBtn = new JButton("Precedent");
		undoBtn.setIcon(new ImageIcon(InterfaceVoyageur.class.getResource("/images/redoo.png")));
		undoBtn.setForeground(new Color(245, 255, 250));
		undoBtn.setBackground(new Color(58, 109, 214));
		undoBtn.setBorder(BorderFactory.createLineBorder(new Color(63, 54, 151), 1));
		undoBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		undoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPvc().EtapePrecedente();
				int i = getPvc().getEtapeCourante();
				System.out.println(i + "etape");
				map.setEtapeActuelle(i);
				map.repaint();
			}
		});
		undoBtn.setBounds(471, 71, 137, 39);
//		contentPane.add(undoBtn);

		/* ----- Bouton revenir en avant ----- */
		JButton redoBtn = new JButton("Suivant");
		redoBtn.setIcon(new ImageIcon(InterfaceVoyageur.class.getResource("/images/undoo.png")));
		redoBtn.setForeground(new Color(245, 245, 220));
		redoBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		redoBtn.setBorder(BorderFactory.createLineBorder(new Color(63, 54, 151), 1));
		redoBtn.setBackground(new Color(58, 109, 214));
		redoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getPvc().getEtapeCourante() < villes.length - 1) {
					map.setEtapeActuelle(map.getEtapeActuelle() + 1);
				}
				System.out.println(map.getEtapeActuelle() + "etape");
				map.repaint();
			}
		});
		redoBtn.setBounds(471, 128, 137, 39);
//		contentPane.add(redoBtn);

		lblNewLabel = new JLabel("Veuillez choisir les villes que vous voulez visiter ");
		lblNewLabel.setBorder(new EmptyBorder(25, 5, 0, 0));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
	}

	/* ----- Getters & Setters ----- */
	public Ville[] getVilles() {
		return villes;
	}

	public void setVilles(Ville[] villes) {
		this.villes = villes;
	}

	public statePVC getPvc() {
		return pvc;
	}

	public void setPvc(statePVC pvc) {
		this.pvc = pvc;
	}

	public AgentVoyageur getAgentVoyageur() {
		return agentVoyageur;
	}

	public void setAgentVoyageur(AgentVoyageur agentVoyageur) {
		this.agentVoyageur = agentVoyageur;
	}

	public MapPanel getMap() {
		return map;
	}

	public void setMap(MapPanel map) {
		this.map = map;
	}
}
