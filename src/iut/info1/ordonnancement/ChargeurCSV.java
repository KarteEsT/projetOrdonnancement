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
            double dateFinProjet = 0.0;

            System.out.print("Voulez-vous charger un 'csv' "
                            + " ou saisir via la 'console' ? ");
            String choix = scanner.nextLine().strip().toLowerCase();

            try {
                if ("csv".equals(choix)) {
                    System.out.print("Entrez le chemin du fichier CSV à charger : ");
                    String cheminFichier = scanner.nextLine().strip();
                    graphe = chargerGrapheDepuisCSV(cheminFichier);
                    System.out.println("Chargement depuis " + cheminFichier + " réussi !");

                } else if ("console".equals(choix)) {
                    graphe = ChargeurConsole.chargerDepuisConsole();
                    
                    System.out.print("Entrez le chemin du fichier CSV pour la sauvegarde : ");
                    String cheminFichier = scanner.nextLine().strip();

                    exporterGrapheCSV(graphe, cheminFichier);
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

                    // Créer les événements à partir des tâches
                    graphe.creerEvenements();
                    System.out.println("\n--- Événements créés ---");
                    System.out.println(graphe.getEvenements().size() + " événements ont été générés.");

                    // Calculs des dates depuis la classe Outils
                    Outils.calculerDatesAuPlusTot(graphe);
                    Outils.calculerDatesAuPlusTard(graphe);
                    dateFinProjet = Outils.calculerFinProjet(graphe);


                    // Afficher les résultats des événements
                    System.out.println("\n--- Résultats des Calculs d'Événements ---");
                    for (Evenement evenement : graphe.getEvenements()) {
                        System.out.println("Événement " + evenement.getIdentifiant() + " :");
                        System.out.println("  Date au plus tôt : " + evenement.getDateAuPlusTot());
                        System.out.println("  Date au plus tard : " + evenement.getDateAuPlusTard());
                        System.out.println("  Est critique ? : " + evenement.estCritique());
                    }
                    
                    // ----- SECTION MISE À JOUR POUR LE CHEMIN CRITIQUE -----
                    System.out.println("\n--- Chemin(s) Critique(s) ---");
                    java.util.List<java.util.List<Evenement>> cheminsCritiques = Outils.trouverCheminsCritiques(graphe); //

                    if (cheminsCritiques.isEmpty()) {
                        System.out.println("Présence de chemin critique : Non");
                    } else {
                        System.out.println("Présence de chemin critique : Oui"); // Ligne ajoutée
                        int cheminIndex = 1;
                        for (java.util.List<Evenement> chemin : cheminsCritiques) {
                            System.out.print("  Chemin " + cheminIndex++ + " : ");
                            String cheminStr = chemin.stream()
                                                     .map(e -> e.getIdentifiant()) //
                                                     .collect(java.util.stream.Collectors.joining(" -> "));
                            System.out.println(cheminStr);
                        }
                    }
                    // ---------------------------------------------------------


                    // Calcul et affichage des marges pour chaque tâche
                    System.out.println("\n--- Marges des Tâches ---");
                    System.out.printf("%-10s | %-12s | %-12s\n", "Tâche", "Marge Totale", "Marge Libre");
                    System.out.println("-----------+--------------+--------------");

                    for (Tache tache : graphe.getTaches()) { //
                        Evenement eventFin = null;
                        for (Evenement e : graphe.getEvenements()) { //
                            if (e.getTachePredecesseurList().contains(tache)) { //
                                eventFin = e;
                                break;
                            }
                        }

                        if (eventFin != null) {
                            // Calcul Marge Totale
                            double dateDebutTot = eventFin.getDateAuPlusTot() - tache.getDuree();
                            double margeTotale = eventFin.getDateAuPlusTard() - dateDebutTot - tache.getDuree();
                            
                            // Calcul Marge Libre
                            double minDateDebutSuccesseurs = dateFinProjet;
                            if (eventFin.getTacheSuccesseurList().isEmpty()) { //
                                minDateDebutSuccesseurs = dateFinProjet;
                            } else {
                                for (Tache tacheSucc : eventFin.getTacheSuccesseurList()) {
                                     Evenement eventFinSucc = null;
                                     for(Evenement e : graphe.getEvenements()) {
                                         if(e.getTachePredecesseurList().contains(tacheSucc)) {
                                             eventFinSucc = e;
                                             break;
                                         }
                                     }
                                     if (eventFinSucc != null) {
                                        double dateDebutSucc = eventFinSucc.getDateAuPlusTot() - tacheSucc.getDuree();
                                        if (dateDebutSucc < minDateDebutSuccesseurs) {
                                            minDateDebutSuccesseurs = dateDebutSucc;
                                        }
                                     }
                                }
                            }
                            double margeLibre = minDateDebutSuccesseurs - eventFin.getDateAuPlusTot();
                            
                            System.out.printf("%-10s | %-12.1f | %-12.1f\n", tache.getLibelle(), margeTotale, margeLibre);
                        }
                    }

                    // Date de fin du projet
                    System.out.println("\nFin du projet (calculée) : " + dateFinProjet + " " + graphe.getUnite());
                }

            } catch (java.io.IOException | IllegalStateException erreur) {
                System.err.println("ERREUR : " + erreur.getMessage());
            }
            scanner.close();
        }
}