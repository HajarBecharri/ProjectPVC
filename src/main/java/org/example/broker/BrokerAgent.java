package org.example.intermediaire;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import org.example.Outils.Ville;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class AgentIntermediaire extends Agent {

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
                MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("ca marche"));
               
                //Recevoir les messages des autres agents
                ACLMessage reponse1 = receive(mt1);
                
                MessageTemplate mt2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchOntology("Calcul du chemin optimal"));
                ACLMessage reponse2 = receive(mt2);

                if(reponse1 != null){
                    try{
                        //On r�cup�re le contenu de reponse1 (ACLMessage)
                        List<Ville> villes = (List<Ville>)reponse1.getContentObject();

                        ACLMessage reponse3 = new ACLMessage(ACLMessage.REQUEST);

                        //Modification des param�tres de la requete ACLMessage
                        reponse3.addReceiver(new AID("calculateur", AID.ISLOCALNAME));
                        //On met la liste des ville dans le message
                        reponse3.setContentObject((Serializable) villes);
                        reponse3.setOntology("Calcul");
                        //Envoi de message
                        send(reponse3);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(reponse2 != null){
                    try {

                        Ville[] villesOrdonnees = (Ville[]) reponse2.getContentObject();

                        ACLMessage reponse3 = new ACLMessage(ACLMessage.INFORM);

                        //Modification des param�tres de la requete ACLMessage
                        reponse3.addReceiver(new AID("Voyageur", AID.ISLOCALNAME));
                        //On met le tableau des villes ordonn�es dans le message
                        reponse3.setContentObject((Serializable) villesOrdonnees);
                        reponse3.setOntology("Tableau des villes ordonnees");
                        //Envoi de message
                        send(reponse3);

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
