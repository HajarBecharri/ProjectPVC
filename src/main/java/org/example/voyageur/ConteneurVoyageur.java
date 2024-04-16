package voyageur;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ConteneurVoyageur {

	public static  void main(String[] args){
        try{
            // On récupère l'instance singleton du JADE Runtime
            Runtime runtime=Runtime.instance();
            //Crée une implémentation de profil
            ProfileImpl profileImpl=new ProfileImpl(false);
            //On spécifie l'hôte réseau sur lequel s'exécute le conteneur principal JADE
            profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
            //On Crée un conteneur périphérique JADE
            AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
            //On créé l'instance de l'agent
            AgentController agentController = agentContainer.createNewAgent("Voyageur", AgentVoyageur.class.getName(), new Object[]{});

            //Démarré l'instance de l'agent
            agentController.start();

        } catch (ControllerException e){
            e.printStackTrace();
        }
    }
	
}
