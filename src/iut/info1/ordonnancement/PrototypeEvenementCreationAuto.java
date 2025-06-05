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
        Graphe graphe = new Graphe("Graphe Test", "jours", taches);

        // Initialisation automatique du graphe
        graphe.initialiserGraphe();

        // Affichage du graphe
        System.out.println(graphe);
        
        for (Evenement evenement : graphe.getEvenements()) {
            System.out.println("Événement " + evenement.getId() + ": "
                    + evenement.getTachePredecesseurList());
        }
        //Essai d'un nouveau graphe
        
        Tache tacheE = new Tache("A", "Description tacheE", 3.0);
        Tache tacheF = new Tache("B", "Description tacheF", 6.0);
        Tache tacheG = new Tache("C", "Description tacheG", 4.0);
        Tache tacheH = new Tache("D", "Description tacheH", 5.0);
        Tache tacheI = new Tache("E", "Description tacheI", 2.0);
        
        tacheG.ajouterTacheRequise(tacheE);
        tacheH.ajouterTacheRequise(tacheF);
        tacheI.ajouterTacheRequise(tacheG);
        tacheI.ajouterTacheRequise(tacheH);
        
        Graphe graphe2 = new Graphe("Graphe Test 2", "jours",
                new ArrayList<>(List.of(tacheE, tacheF, tacheG, tacheH, tacheI)));
        
        // Initialisation automatique du graphe
        graphe2.initialiserGraphe();
        // Affichage du graphe
        System.out.println("\n\n\n\n\n\n\n\n" + graphe2);
    }
}
