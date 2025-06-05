/*
 * ChargeurConsole.java                                           23 mai 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Permet de charger des taches depuis la console
 */
public class ChargeurConsole {
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return graphe
     */
    public Graphe chargerDepuisConsole() {
        Scanner scanner = new Scanner(System.in);

        // Pour entrez le titre
        String titre;
        do {
            System.out.print("Entrez le titre du graphe : ");
            titre = scanner.nextLine().strip();
            if (titre.isEmpty()) {
                System.out.println("Le titre du graphe ne peut pas être vide.");
            }
        } while (titre.isEmpty());

     // Pour entrez l'unité
        String unite;
        do {
            System.out.print("Entrez l'unité de temps du graphe : ");
            unite = scanner.nextLine().strip();
            if (unite.isEmpty()) {
                System.out.println("L'unité de temps du graphe ne peut" +
                                   " pas être vide.");
            }
        } while (unite.isEmpty());

        // Pour entrez les taches
        ArrayList<Tache> taches = new ArrayList<>();
        boolean ajouterTache = true;

        while (ajouterTache) {
            System.out.print("Voulez-vous ajouter une tâche ? (oui/non) : ");
            String reponse = scanner.nextLine().strip().toLowerCase();

            if (reponse.equals("oui")) {
                String nomTache;
                do {
                    System.out.print("Entrez le nom de la tâche : ");
                    nomTache = scanner.nextLine().strip();
                    if (nomTache.isEmpty()) {
                        System.out.println("Le nom de la tâche ne peut pas " + 
                                           "être vide.");
                    }
                } while (nomTache.isEmpty());

                // Pour entrez la description de la tache
                String descriptionTache;
                do {
                    System.out.print("Entrez la description de la tâche : ");
                    descriptionTache = scanner.nextLine().strip();
                    if (descriptionTache.isEmpty()) {
                        System.out.println("La description de la tâche ne " + 
                                           "peut pas être vide.");
                    }
                } while (descriptionTache.isEmpty());

                // Pour entrez la durée de la tache
                double dureeTache = 0;
                boolean dureeInvalide;
                do {
                    System.out.print("Entrez la durée de la tâche : ");
                    try {
                        dureeTache = Double.parseDouble(
                                                    scanner.nextLine().strip());
                        if (dureeTache <= 0) {
                            System.out.println("La durée de la tâche doit " + 
                                               "être un nombre positif.");
                            dureeInvalide = true;
                        } else {
                            dureeInvalide = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Veuillez entrer un nombre valide" + 
                                           " pour la durée.");
                        dureeInvalide = true;
                    }
                } while (dureeInvalide);

                System.out.print("Entrez les noms des tâches prédécesseurs " + 
                                 "(séparés par des virgules, " + 
                                 "laissez vide si aucun) : ");
                String input = scanner.nextLine().strip();
                String[] nomsPredecesseurs = input.isEmpty() ? 
                                               new String[0] : 
                                               input.split(",");
                for (int i = 0; i < nomsPredecesseurs.length; i++) {
                    nomsPredecesseurs[i] = nomsPredecesseurs[i].strip();
                }

                ArrayList<Tache> tachesRequises = new ArrayList<>();
                for (String nomPredecesseur : nomsPredecesseurs) {
                    for (Tache tacheExistante : taches) {
                        if (tacheExistante.getLibelle()
                                          .equals(nomPredecesseur)) {
                            tachesRequises.add(tacheExistante);
                        }
                    }
                }

                // Ajoute les taches au graphes
                Tache nouvelleTache = new Tache(nomTache, descriptionTache, 
                                                dureeTache, tachesRequises);
                taches.add(nouvelleTache);
            } else {
                ajouterTache = false;
            }
        }

        ArrayList<Evenement> evenements = new ArrayList<>();

        // Return the created graph
        return new Graphe(titre, unite, taches, evenements);
    }
}