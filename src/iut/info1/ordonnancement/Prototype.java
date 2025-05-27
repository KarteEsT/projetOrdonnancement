/*
 * Prototype.java                                   23 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;

/**
 * Permet de lancer l'application
 * 
 * @author Gabriel Le Goff
 * @author Gabriel Robache
 * @author Roveri Esteban
 * @author Sauvaire Léo
 * @author Massicard Maël
 */
public class Prototype {
    
    
    /**
     * Méthode principale pour exécuter le prototype.
     * @param args
     */
    public static void main(String[] args) {
        // Création des tâches
        Tache tache1 = new Tache("T1", "Description T1", 3);
        Tache tache2 = new Tache("T2", "Description T2", 2);
        Tache tache3 = new Tache("T3", "Description T3", 1);
        Tache tache4 = new Tache("T4", "Description T4", 4);

        Tache tache5 = new Tache("T5", "Description T5", 1);
        Tache tache6 = new Tache("T6", "Description T6", 4);

        // Définition des dépendances (T2 dépend de T1, T3 dépend de T2)

        // Ajout d'une dépendance circulaire pour tester (T1 dépend de T3)
        //tache1.ajouterTacheRequise(tache2);

        // Création de la liste des tâches
        ArrayList<Tache> taches = new ArrayList<>();
        taches.add(tache2);
        taches.add(tache1);
        taches.add(tache3);
        taches.add(tache4);
        taches.add(tache6);

        // Création du graphe
        Graphe graphe = new Graphe("Graphe Test", "jours", taches, new ArrayList<>());
        
        tache2.ajouterTacheRequise(tache1);
        tache3.ajouterTacheRequise(tache2);
        graphe.ajouterTache(tache5);
        

        // Test de la méthode existeCircuit
        boolean circuitExiste = graphe.existeCircuit();
        System.out.println("Circuit existant : " + circuitExiste);
        System.out.println(graphe.toString());
        graphe.trierTaches();
        System.out.println("Tâches triées dans le graphe : " + graphe.toString());
        
        
        
        //Essai tache requise pas dans graphe
        Graphe graphe2 = new Graphe("Graphe Test 2", "jours", new ArrayList<>(), new ArrayList<>());
        Tache t1 = new Tache("T1", "Description T1", 3);
        Tache t2 = new Tache("T2", "Description T2", 2);
        
        t1.ajouterTacheRequise(t2); // t2 n'est pas dans le graphe)
        
        graphe2.ajouterPlusieursTaches(t2,t1);
    }
}