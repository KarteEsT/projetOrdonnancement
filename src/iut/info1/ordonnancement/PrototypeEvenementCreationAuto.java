/*
 * PrototypeEvenementCreationAuto.java                              5 juin 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;


/**
 * Test de la création automatique d'événements
 */
public class PrototypeEvenementCreationAuto {
    /**
     * Méthode principale pour exécuter le prototype.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Création des tâches
        Tache tacheA = new Tache("tacheA", "Description tacheA", 3.0);
        Tache tacheB = new Tache("tacheB", "Description tacheB", 2.0);
        Tache tacheC = new Tache("tacheC", "Description tacheC", 4.0);
        Tache tacheD = new Tache("tacheD", "Description tacheD", 8.0);

        // Ajout des dépendances
        tacheC.ajouterTacheRequise(tacheA);
        tacheD.ajouterTacheRequise(tacheB);

        ArrayList<Tache> taches = new ArrayList<>(List.of(tacheA, tacheB, tacheC, tacheD));

        // Création du graphe
        Graphe graphe = new Graphe("Graphe Test", "jours", taches, new ArrayList<>());

        // Initialisation automatique du graphe
        graphe.initialiserGraphe();

        // Affichage du graphe
        System.out.println(graphe);
        for (Evenement evenement : graphe.getEvenement()) {
            System.out.println("Événement " + evenement.getId() + ": "
                    + evenement.getTachePredecesseurList());
            if (evenement.getId() != 0) {

                System.out.println(evenement.getTachePredecesseurList().get(0) == evenement.getTachePredecesseurList().get(1));
            }
        }
        
    }
}
