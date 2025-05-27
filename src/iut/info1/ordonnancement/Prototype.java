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
        Tache tacheA = new Tache("tacheA", "Description tacheA", 3);
        Tache tacheB = new Tache("tacheB", "Description tacheB", 2);
        Tache tacheC = new Tache("tacheC", "Description tacheC", 4);
        Tache tacheD = new Tache("tacheD", "Description tacheD", 8);

        

        // Définition des dépendances (T2 dépend de T1, T3 dépend de T2)

        // Ajout d'une dépendance circulaire pour tester (T1 dépend de T3)
        //tache1.ajouterTacheRequise(tache2);

        // Création de la liste des tâches
        ArrayList<Tache> taches = new ArrayList<>();
        taches.add(tacheA);
        taches.add(tacheB);
        taches.add(tacheC);
        taches.add(tacheD);
        
        // On mets les différentes taches dans des listes
        
        tacheC.ajouterTacheRequise(tacheA); // tacheC dépend de tacheA
        tacheD.ajouterTacheRequise(tacheB); // tacheD dépend de tacheB
        
        ArrayList<Tache> tachesEvenement1 = new ArrayList<>();
        
        tachesEvenement1.add(tacheA); // tacheA est dans l'événement 1
        
        ArrayList<Tache> tachesEvenement2 = new ArrayList<>();
        
        tachesEvenement2.add(tacheB); // tacheB est dans l'événement 2
        
        ArrayList<Tache> tachesEvenement3 = new ArrayList<>();
        
        tachesEvenement3.add(tacheC); // tacheC est dans l'événement 3
        tachesEvenement3.add(tacheD); // tacheD est dans l'événement 3
        
        // Création des événements
        
        Evenement evenementInitial = new Evenement();
        
        ArrayList<Evenement> evenement1Predecesseur = new ArrayList<>();
        
        evenement1Predecesseur.add(evenementInitial); // evenement1 dépend de l'événement initial
        
        Evenement evenement1 = new Evenement(1, evenement1Predecesseur, tachesEvenement1);
        
        ArrayList<Evenement> evenement2Predecesseur = new ArrayList<>();
        
        evenement2Predecesseur.add(evenementInitial); // evenement2 dépend de l'événement initial
        Evenement evenement2 = new Evenement(2, evenement2Predecesseur , tachesEvenement2);
        
        ArrayList<Evenement> evenement3Predecesseur = new ArrayList<>();
        
        evenement3Predecesseur.add(evenement1);
        evenement3Predecesseur.add(evenement2); // evenement3 dépend de evenement2 et evenement1
        
        Evenement evenement3 = new Evenement(3, evenement3Predecesseur , tachesEvenement3);
        

        // Création du graphe
        Graphe graphe = new Graphe("Graphe Test", "jours", taches, new ArrayList<>());
        
    
        // Test de la méthode existeCircuit
        boolean circuitExiste = graphe.existeCircuit();
        System.out.println("Circuit existant : " + circuitExiste);
        System.out.println(graphe.toString());
        graphe.trierTaches();
        System.out.println("Tâches triées dans le graphe : " + graphe.toString());
                
        // Test des méthodes de calcul de date au plus tôt et au plus tard
        
        evenement1.calculerDatePlusTot();
        evenement2.calculerDatePlusTot();
        evenement3.calculerDatePlusTot();
        System.out.println("Dates au plus tôt :");
        System.out.println("Événement 1 : " + evenement1.getDateAuPlusTot());
        System.out.println("Événement 2 : " + evenement2.getDateAuPlusTot());
        System.out.println("Événement 3 : " + evenement3.getDateAuPlusTot());
        
        
        
        /*evenement1.calculerDateAuPlusTard();
        evenement2.calculerDateAuPlusTard();
        evenement3.calculerDateAuPlusTard();
        System.out.println("Dates au plus tard :");
        System.out.println("Événement 1 : " + evenement1.getDateAuPlusTard());
        System.out.println("Événement 2 : " + evenement2.getDateAuPlusTard());
        System.out.println("Événement 3 : " + evenement3.getDateAuPlusTard()); */
        
        
        //Essai tache requise pas dans graphe
        Graphe graphe2 = new Graphe("Graphe Test 2", "jours", new ArrayList<>(), new ArrayList<>());
        Tache t1 = new Tache("T1", "Description T1", 3);
        Tache t2 = new Tache("T2", "Description T2", 2);
        
        t1.ajouterTacheRequise(t2); // t2 n'est pas dans le graphe)
        
        graphe2.ajouterPlusieursTaches(t2,t1);
        
        
    }
}