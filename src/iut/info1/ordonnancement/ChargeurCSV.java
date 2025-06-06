/*
 * ChargeurCSV.java                                 29 avril 2025
 * IUT de Rodez, info1, 2024-2025, pas de copyright
 */

package iut.info1.ordonnancement;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programme permettant la lecture et l'écriture de fichiers
 * 
 * @author Léo Sauvaire
 * @author Gabriel Robache
 * @author Gabriel Le Goff
 * @author Mael Massicard
 * @author Esteban Roveri
 * @version 1.0
 */
public class ChargeurCSV {

    /**
     * Exporte un graphe PERT dans un fichier CSV avec ';' comme séparateur.
     * @param graphe le graphe à exporter
     * @param nomFichier le chemin du fichier CSV
     * @throws IOException en cas d'erreur d'écriture
     */
    public static void exporterGrapheCSV(Graphe graphe, String nomFichier)
                                         throws IOException {
        try (FileWriter writer = new FileWriter(nomFichier)) {
            writer.write("Titre;" + graphe.getTitre() + "\n");
            writer.write("Unité;" + graphe.getUnite() + "\n\n");

            writer.write("Libelle;Description;Durée;TachesRequises\n");

            for (Tache tache : graphe.getTaches()) {
                String dependances = "";
                for (Tache requise : tache.getTachesRequises()) {
                    dependances += requise.getLibelle() + " ";
                }

                // Supprimer l'espace final
                if (!dependances.isEmpty()) {
                    dependances = dependances.substring(0, 
                                                      dependances.length() - 1);
                }

                writer.write(tache.getLibelle() + ";" + tache.getDescription() + 
                             ";" + tache.getDuree() + ";" + dependances + "\n");
            }
        }
    }
    
    /**
     * Charge un graphe PERT depuis un fichier CSV. 
     * Le fichier doit respecter le
     * format suivant : 
     * - La première ligne contient le titre du graphe. 
     * - La deuxième ligne contient l'unité de temps. 
     * - Les lignes suivantes contiennent
     * les tâches avec leur libellé, durée et dépendances.
     *
     * @param nomFichier le chemin du fichier CSV
     * @return un objet Graphe contenant les informations du fichier
     * @throws FileNotFoundException si le fichier n'existe pas
     * @throws IOException si une erreur de lecture survient
     */
    public static Graphe chargerGrapheDepuisCSV(String nomFichier) 
                                    throws FileNotFoundException, IOException {
        try (BufferedReader reader = 
                            new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            String titre = null;
            String unite = null;
            ArrayList<Tache> taches = new ArrayList<>();

            // Pour traiter les dépendances après avoir créé toutes les tâches
            ArrayList<String[]> dependancesEnAttente = new ArrayList<>();

            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.strip();
                if (ligne.startsWith("Titre;")) {
                    titre = ligne.split(";", 2)[1];
                } else if (ligne.startsWith("Unité;")) {
                    unite = ligne.split(";", 2)[1];
                } else if (!ligne.isEmpty() && !ligne.startsWith("Libelle;")) {
                    String[] elements = ligne.split(";", -1);
                    if (elements.length < 3) {
                        throw new IllegalArgumentException("Ligne de tâche" +
                                                    " mal formatée : " + ligne);
                    }

                    String libelle = elements[0];
                    String description = elements[1];
                    double duree = Double.parseDouble(elements[2]);

                    Tache nouvelleTache = new Tache(libelle, description,duree);
                    taches.add(nouvelleTache);

                    dependancesEnAttente.add(new String[] { 
                        libelle, elements.length > 3 ? elements[3] : "" 
                    });
                }
            }

            // Associer les dépendances après la création de toutes les tâches
            for (String[] infos : dependancesEnAttente) {
                String libelleTache = infos[0];
                String dependancesStr = infos[1];
                Tache tache = taches.stream()
                        .filter(t -> t.getLibelle().equals(libelleTache))
                        .findFirst()
                        .orElseThrow();

                if (!dependancesStr.isEmpty()) {
                    String[] nomsDependances = dependancesStr.split(" ");
                    for (String nomDep : nomsDependances) {
                        for (Tache possible : taches) {
                            if (possible.getLibelle().equals(nomDep)) {
                                tache.ajouterTacheRequise(possible);
                            }
                        }
                    }
                }
            }

            if (titre == null || unite == null) {
                throw new IllegalArgumentException("Le fichier CSV est " +
                                                   "incomplet.");
            }

            return new Graphe(titre, unite, taches);
        }
    }
    
    /**
     * Point d'entrée du programme pour tester le chargement et les calculs
     * d'un graphe. Permet de choisir entre la saisie via console ou
     * le chargement direct depuis un fichier CSV.
     * @param args non utilisé
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graphe graphe = null;

        System.out.print("Voulez-vous charger un 'csv' "
                        + " ou saisir via la 'console' ? ");
        String choix = scanner.nextLine().strip().toLowerCase();

        try {
            if ("csv".equals(choix)) {
                System.out.print("Entrez le chemin du fichier CSV à charger : ");
                String cheminFichier = scanner.nextLine().strip();
                graphe = ChargeurCSV.chargerGrapheDepuisCSV(cheminFichier);
                System.out.println("Chargement depuis " + cheminFichier + " réussi !");

            } else if ("console".equals(choix)) {
                graphe = ChargeurConsole.chargerDepuisConsole();
                
                System.out.print("Entrez le chemin du fichier CSV pour la sauvegarde : ");
                String cheminFichier = scanner.nextLine().strip();

                ChargeurCSV.exporterGrapheCSV(graphe, cheminFichier);
                System.out.println("Export vers " + cheminFichier + " terminé !");
                
            } else {
                System.out.println("Choix non valide. Fin du programme.");
            }

            // Si un graphe a bien été chargé ou créé, on lance les calculs
            if (graphe != null) {
                System.out.println("\n--- Lancement des calculs sur le graphe ---");
                System.out.println("Titre : " + graphe.getTitre());
                System.out.println("Unité : " + graphe.getUnite());

                // Trier les tâches pour respecter les dépendances
                graphe.trierTaches();
                System.out.println("\n--- Tâches triées ---");
                for (Tache tache : graphe.getTaches()) {
                    System.out.println("- " + tache.getLibelle());
                }

                /** Créer les événements à partir des tâches
                graphe.creerEvenements();
                System.out.println("\n--- Événements créés ---");
                System.out.println(graphe.getEvenements().size() + " événements ont été générés.");

                // Dates au plus tôt
                Outils.calculerDatesAuPlusTot(graphe);

                // Dates au plus tard
                Outils.calculerDatesAuPlusTard(graphe); */

                // Afficher les résultats
                System.out.println("\n--- Résultats des Calculs ---");
                for (Evenement evenement : graphe.getEvenements()) {
                    System.out.println("Événement " + evenement.getId() + " :");
                    // System.out.println("  Date au plus tôt : " + evenement.getDateAuPlusTot());
                    // System.out.println("  Date au plus tard : " + evenement.getDateAuPlusTard());
                    // System.out.println("  Est critique ? : " + evenement.estCritique());
                }

                // Date de fin du projet
                System.out.println("\nFin du projet (calculée) : " + Outils.calculerFinProjet(graphe) + " " + graphe.getUnite());
            }

        } catch (IOException | IllegalStateException erreurEcriture) {
            System.err.println("ERREUR : " + erreurEcriture.getMessage());
        }
        scanner.close();
    }
}