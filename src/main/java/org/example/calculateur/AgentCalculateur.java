package org.example.calculateur;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.List;
import org.example.problematic.Ville;
import org.example.problematic.statePVC;


public class AgentCalculateur extends Agent {

	// ------------- Pour initialiser l'agent calculateur --------------
	@Override
	protected void setup() {

		System.out.println("L'agent calculateur est d�marr�");

		// cr�ation d'une instance de ParallelBehaviour pour ex�cuter plusieurs
		// Behaviours en parall�le
		ParallelBehaviour comportementparallele = new ParallelBehaviour();
		// L'ajout de sous-Behaviour
		addBehaviour(comportementparallele);

		// l'ajout d'un CyclicBehaviour pour afficher un message � chaque fois qu'il
		// s'execute
		comportementparallele.addSubBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				// Pr�paration du template pour recevoir des messages
				MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
						MessageTemplate.MatchOntology("Tableau des Non villes ordonnes"));
				// Recevoir les messages des autres agents
				ACLMessage villesNonOrdonneesReciever = receive(mt1);
				if (villesNonOrdonneesReciever != null) {
					try {
						// On r�cup�re le contenu de reponse1 (ACLMessage)
						List<Ville> villes = (List<Ville>) villesNonOrdonneesReciever.getContentObject();
						// On cr�� une instance de la classe PVC
						statePVC pvc = new statePVC();
						// On r�cup�re les villes dont la distance entre eux est minimale
						Ville[] villesOrdonnees = pvc.getPlusCourteDist(villes);
						ACLMessage villesOrdonneesSender = new ACLMessage(ACLMessage.INFORM);
						// Modification des param�tres de la requete ACLMessage
						villesOrdonneesSender.addReceiver(new AID("Voyageur", AID.ISLOCALNAME));
						// On met le tableau des villes ordonnees dans le message
						villesOrdonneesSender.setContentObject(villesOrdonnees);
						villesOrdonneesSender.setOntology("Tableau des villes ordonnees");
						// Envoi de message
						send(villesOrdonneesSender);
					} catch (UnreadableException e) {
//                        e.printStackTrace();
						System.out.println(e);
					} catch (IOException e) {
//                        e.printStackTrace();
						System.out.println(e);
					}
				}

				// On bloque le Behaviour pour ne pas planifier son execution
				else
					block();

			}
		});
	}

}
