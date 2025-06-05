/*
 * Prototype.java                                   			23 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Tache tacheA = new Tache("tacheA", "Description tacheA", 3.0);
        Tache tacheB = new Tache("tacheB", "Description tacheB", 2.0);
        Tache tacheC = new Tache("tacheC", "Description tacheC", 4.0);
        Tache tacheD = new Tache("tacheD", "Description tacheD", 8.0);

        // Création des événements
        Evenement evenementInitial = new Evenement();
        Evenement evenement1 = new Evenement(1, new ArrayList<>(List.of(evenementInitial)), new ArrayList<>(List.of(tacheA)));
        Evenement evenement2 = new Evenement(2, new ArrayList<>(List.of(evenementInitial)), new ArrayList<>(List.of(tacheB)));
        Evenement evenement3 = new Evenement(3, new ArrayList<>(List.of(evenement1, evenement2)), new ArrayList<>(List.of(tacheC, tacheD)));

        // Définition des successeurs
        evenementInitial.addEvenementSuccesseur(evenement1);
        evenementInitial.addEvenementSuccesseur(evenement2);
        evenement1.addEvenementSuccesseur(evenement3);
        evenement2.addEvenementSuccesseur(evenement3);

        evenementInitial.addTacheSuccesseur(tacheA);
        evenementInitial.addTacheSuccesseur(tacheB);
        evenement1.addTacheSuccesseur(tacheC);
        evenement2.addTacheSuccesseur(tacheD);

        // Création du graphe
        ArrayList<Tache> taches = new ArrayList<>(List.of(tacheA, tacheB, tacheC, tacheD));
        ArrayList<Evenement> evenements = new ArrayList<>(List.of(evenementInitial, evenement1, evenement2, evenement3));
        Graphe graphe = new Graphe("Graphe Test", "jours", taches, evenements);

        // Calcul des dates au plus tôt
        for (Evenement evenement : evenements) {
            evenement.calculerDatePlusTot();
        }

        // Calcul de la date de fin du projet
        double dateFinProjet = graphe.calculerFinProjet();

        // Calcul des dates au plus tard
        for (int i = evenements.size() - 1; i >= 0; i--) {
            evenements.get(i).calculerDatePlusTard(dateFinProjet);
        }

        tacheA.calculerMargeLibre(evenementInitial, evenement1);
        tacheA.calculerMargeTotale(evenementInitial, evenement1);

        tacheB.calculerMargeLibre(evenementInitial, evenement2);
        tacheB.calculerMargeTotale(evenementInitial, evenement2);

        tacheC.calculerMargeLibre(evenement1, evenement3);
        tacheC.calculerMargeTotale(evenement1, evenement3);

        tacheD.calculerMargeLibre(evenement2, evenement3);
        tacheD.calculerMargeTotale(evenement2, evenement3);

        // Affichage des dates au plus tôt et au plus tard
        System.out.println("Dates au plus tôt et au plus tard :");
        for (Evenement evenement : evenements) {
            System.out.println("Événement " + evenement.getId() + " :");
            System.out.println("  Date au plus tôt : " + evenement.getDateAuPlusTot());
            System.out.println("  Date au plus tard : " + evenement.getDateAuPlusTard());
        }

        System.out.println("\nDate de fin du projet : " + dateFinProjet);

        System.out.println("\nMarge Totale et Libre :");
        for (Evenement evenement : evenements) {
            for (Tache tache : evenement.getTacheSuccesseurList()) {
                System.out.println("Tâche " + tache.getLibelle() + " :");
                System.out.println("  Marge libre : " + tache.getMargeLibre());
                System.out.println("  Marge totale : " + tache.getMargeTotale());
            }
        }

        // Événements critiques
        System.out.println("\nÉvénements Critiques :");
        for (Evenement evenement : evenements) {
            if (evenement.estCritique()) {
                System.out.println("  Événement " + evenement.getId() + " est critique");
            }
        }

        // Chemins Critiques
        System.out.println("\nChemins Critiques :");
        List<List<Evenement>> cheminsCritiques = evenementInitial.trouverCheminsCritiques(evenementInitial);
        for (List<Evenement> chemin : cheminsCritiques) {
            System.out.println(chemin.stream()
                    .map(e -> "Événement " + e.getId())
                    .collect(Collectors.joining(" -> ")));
        }

        // Test de la méthode existeCircuit
        boolean circuitExiste = graphe.existeCircuit();
        System.out.println("\nCircuit existant : " + circuitExiste);

        graphe.trierTaches();
        System.out.println("Tâches triées dans le graphe : " + graphe.toString());
    }
}