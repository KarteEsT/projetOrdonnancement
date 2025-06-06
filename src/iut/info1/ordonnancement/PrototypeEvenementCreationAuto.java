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
        Tache tacheA = new Tache("tacheA", "Description tacheA", 19.0);
        Tache tacheB = new Tache("tacheB", "Description tacheB", 14.0);
        Tache tacheC = new Tache("tacheC", "Description tacheC", 2.0);
        Tache tacheD = new Tache("tacheD", "Description tacheD", 6.0);
        Tache tacheE = new Tache("tacheE", "Description tacheE", 1.0);
        Tache tacheF = new Tache("tacheF", "Description tacheF", 7.0);
        Tache tacheG = new Tache("tacheG", "Description tacheG", 6.0);
        Tache tacheH = new Tache("tacheH", "Description tacheH", 6.0);
        Tache tacheI = new Tache("tacheI", "Description tacheI", 5.0);
        Tache tacheJ = new Tache("tacheJ", "Description tacheJ", 26.0);
        
        // Ajout des dépendances
        tacheE.ajouterTacheRequise(tacheA);
        tacheF.ajouterTacheRequise(tacheB);
        tacheC.ajouterTacheRequise(tacheB);
        tacheD.ajouterTacheRequise(tacheC);
        tacheG.ajouterTacheRequise(tacheE);
        tacheG.ajouterTacheRequise(tacheF);
        tacheH.ajouterTacheRequise(tacheE);
        tacheH.ajouterTacheRequise(tacheF);
        tacheI.ajouterTacheRequise(tacheH);
        tacheJ.ajouterTacheRequise(tacheG);
        tacheJ.ajouterTacheRequise(tacheI);
        tacheJ.ajouterTacheRequise(tacheD);

        ArrayList<Tache> taches = new ArrayList<>(List.of(tacheA, tacheB, tacheC, tacheD, tacheE, tacheF, tacheG, tacheH, tacheI, tacheJ));

        // Création du graphe
        Graphe graphe = new Graphe("Graphe Test", "jours", taches);

        // Initialisation automatique du graphe
        graphe.initialiserGraphe();
        
        System.out.println("Graphe créé avec succès !\n\n" + graphe);
    }
}
