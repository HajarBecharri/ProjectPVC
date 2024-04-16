package org.example.voyageur;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.example.problematic.Ville;
import org.example.problematic.PVC;
import jade.gui.GuiEvent;
import java.awt.Font;
import javax.swing.JLabel;

public class InterfaceVoyageur extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public JPanel mapPanel;
	private MapPanel map;

	private AgentVoyageur agentVoyageur;
	private Ville villes[];
	private PVC pvc = new PVC();
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

		setTitle("Probl\u00E8me de Voyageur de Commerce");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 515);
		setResizable(false);
		contentPane = new JPanel();
//		contentPane.setBackground(new Color(165, 214, 247));

		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mapPanel = new JPanel();
		mapPanel.setBackground(new Color(255, 245, 238));
		mapPanel.setBounds(10, 10, 451, 454);
		contentPane.add(mapPanel);
		mapPanel.setLayout(null);
		
				map = new MapPanel();
				map.setBounds(0, 0, 451, 454);
				mapPanel.add(map);
				map.setBorder(BorderFactory.createLineBorder(new Color(63, 54, 151), 2));
				map.repaint();

		/* ----- Bouton pour calculer la solution ----- */
		JButton getCheminBtn = new JButton("Calculer");

		getCheminBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		getCheminBtn.setForeground(new Color(255, 255, 255));
		getCheminBtn.setBackground(new Color(205, 133, 63));
		
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
		getCheminBtn.setBounds(471, 10, 137, 39);
		contentPane.add(getCheminBtn);

		/* ----- Bouton revenir en arri�re ----- */
		JButton undoBtn = new JButton("Precedent");

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
		contentPane.add(undoBtn);

		/* ----- Bouton revenir en avant ----- */
		JButton redoBtn = new JButton("Suivant");

		redoBtn.setForeground(new Color(245, 245, 220));
		redoBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		redoBtn.setBorder(BorderFactory.createLineBorder(new Color(63, 54, 151), 1));
		redoBtn.setBackground(new Color(58, 109, 214));
		redoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(villes.length);
				System.out.println(getPvc().getEtapeCourante());

				if (getPvc().getEtapeCourante() < villes.length - 1) {
					map.setEtapeActuelle(map.getEtapeActuelle() + 1);
				}
				System.out.println(map.getEtapeActuelle() + "etape");
				map.repaint();
			}
		});
		redoBtn.setBounds(471, 128, 137, 39);
		contentPane.add(redoBtn);
		
		lblNewLabel = new JLabel("");

		lblNewLabel.setBounds(493, 341, 137, 146);
		contentPane.add(lblNewLabel);
	}

	/* ----- Getters & Setters ----- */
	public Ville[] getVilles() {
		return villes;
	}

	public void setVilles(Ville[] villes) {
		this.villes = villes;
	}

	public PVC getPvc() {
		return pvc;
	}

	public void setPvc(PVC pvc) {
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
