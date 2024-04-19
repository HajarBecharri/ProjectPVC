package org.example.voyageur;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.example.InterfaceAgent.InterfaceVoyageur;
import org.example.problematic.Ville;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgentVoyageur extends GuiAgent {
	private static final long serialVersionUID = 1L;
	private InterfaceVoyageur interfaceAgent;

	protected void setup() {
		System.out.println("Bienvenue ! C'est le voyageur Agent");
		interfaceAgent = new InterfaceVoyageur();
		interfaceAgent.setVisible(true);
		interfaceAgent.setAgentVoyageur(this);
		System.out.println("Le Voyageur est pret !");
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				MessageTemplate message1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
						MessageTemplate.MatchOntology("ca marche"));
				ACLMessage acl1 = receive(message1);
				MessageTemplate message2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchOntology("Tableau des villes ordonnees"));
				ACLMessage villesOrdonneesReciever = receive(message2);
				  if (villesOrdonneesReciever != null) {
					try {
						Ville[] villeOrdonnee = (Ville[]) villesOrdonneesReciever.getContentObject();
						interfaceAgent.setVilles(villeOrdonnee);
						interfaceAgent.getMap().setDistances(new ArrayList<Ville>(Arrays.asList(villeOrdonnee)));
						interfaceAgent.getMap().setPlusCourt(true);
						interfaceAgent.getPvc().setEtapeCourante(villeOrdonnee.length);
						interfaceAgent.getMap().setEtapeActuelle(interfaceAgent.getPvc().getEtapeCourante());
						interfaceAgent.getMap().repaint();

					} catch (UnreadableException ex) {
						System.out.println(ex);
					}

				}else if (acl1 != null) {
					System.out.println("L'emetteur de message : " + acl1.getSender());
					System.out.println("Le destinataire de message : " + acl1.getInReplyTo());
					System.out.println("L'acte de communication : " + acl1.getPerformative());
					System.out.println("LE contenu de message : " + acl1.getContent());
					System.out.println("Langage : " + acl1.getLanguage());
					System.out.println("L'ontology : " + acl1.getOntology());

				} else
					block();

			}
		});
	}

	@Override
	public void onGuiEvent(GuiEvent event) {
		switch (event.getType()) {
		case 1:
			System.out.println("En Gui Event");
			Map<String, Object> params = (Map<String, Object>) event.getParameter(0);

			List<Ville> villes = (List<Ville>) params.get("v1");

			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(new AID("calculateur", AID.ISLOCALNAME));

			try {
				aclMessage.setContentObject((Serializable) villes);

			} catch (IOException ex) {
				System.out.println(ex);
			}
			aclMessage.setOntology("Tableau des Non villes ordonnes");
			send(aclMessage);
			break;

		default:
			break;

		}
	}
}