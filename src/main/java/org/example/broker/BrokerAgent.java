package org.example.broker;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import org.example.problematic.Ville;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class BrokerAgent extends Agent {

    //------------- Pour initialiser un agent --------------
    @Override
    protected void setup(){

        System.out.println("L'agent intermediaire est demarree");

        //cr�ation d'une instance de ParallelBehaviour pour ex�cuter plusieurs Behaviours en parall�le
        ParallelBehaviour comportementparallele = new ParallelBehaviour();
        //L'ajout de sous-Behaviour
        addBehaviour(comportementparallele);

        // l'ajout d'un  CyclicBehaviour pour afficher un message � chaque fois qu'il s'execute
        comportementparallele.addSubBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {

                //Pr�paration du template pour recevoir des messages
                MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("Tableau des Non villes ordonnes"));
               
                //Recevoir les villes Non ordonnees de agent voyageur
                ACLMessage villesNonOrdonneesRciever = receive(mt1);
                //Recevoir les villes ordonnees de agent calculateur apres que Broker envoie tableau des villes non ordonnes pour le trier
                MessageTemplate mt2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchOntology("Tableau des villes ordonnes"));
                ACLMessage villesOrdonneesReciever = receive(mt2);

                if(villesNonOrdonneesRciever != null){
                    try{
                        //envoyer les villes Non ordonnees a l agent calculateur pour le trier
                        List<Ville> villes = (List<Ville>)villesNonOrdonneesRciever.getContentObject();

                        ACLMessage villesNonordonnesSend = new ACLMessage(ACLMessage.REQUEST);
                        villesNonordonnesSend.addReceiver(new AID("calculateur", AID.ISLOCALNAME));
                        //On met la liste des ville dans le message
                        villesNonordonnesSend.setContentObject((Serializable) villes);
                        villesNonordonnesSend.setOntology("Tableau des villes non ordonnees");
                        //Envoi de message
                        send(villesNonordonnesSend);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(villesOrdonneesReciever != null){
                    try {
                        //envoyer les villes Non ordonnees a l agent voyegeur apres d etre ordonner puis envoyer par calculateur
                        Ville[] villesOrdonnees = (Ville[]) villesOrdonneesReciever.getContentObject();

                        ACLMessage villesordonneesSender = new ACLMessage(ACLMessage.INFORM);

                        villesordonneesSender.addReceiver(new AID("Voyageur", AID.ISLOCALNAME));
                        //On met le tableau des villes ordonn�es dans le message
                        villesordonneesSender.setContentObject((Serializable) villesOrdonnees);
                        villesordonneesSender.setOntology("Tableau des villes ordonnees");
                        //Envoi de message
                        send(villesordonneesSender);

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //On bloque le Behaviour pour ne pas planifier son execution
                else block();

            }
        });
    }
}
