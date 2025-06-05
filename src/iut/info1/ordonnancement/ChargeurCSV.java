/*
 * ChargeurCSV.java                                 29 avril 2025
 * IUT de Rodez, info1, 2024-2025, pas de copyright
 */

package iut.info1.ordonnancement;

import java.io.*;
import java.util.ArrayList;

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

            return new Graphe(titre, unite, taches, new ArrayList<>());
        }
    }
    
    /**
     * Point d'entrée du programme pour tester le chargement
     * d'un graphe depuis un fichier CSV.
     * @param args non utilisé
     */
    public static void main(String[] args) {
        ChargeurConsole chargeurConsole = new ChargeurConsole();
        Graphe grapheSaisi = chargeurConsole.chargerDepuisConsole();

        String cheminFichier = "C:\\Users\\esteb\\Desktop\\graphe.csv";

        try {
            ChargeurCSV.exporterGrapheCSV(grapheSaisi, cheminFichier);
            System.out.println("Export terminé !");

            Graphe grapheCharge = ChargeurCSV.chargerGrapheDepuisCSV(
                                                            cheminFichier);
            System.out.println("Chargement réussi !");
            System.out.println("Titre : " + grapheCharge.getTitre());
            System.out.println("Unité : " + grapheCharge.getUnite());
            System.out.println("Tâches :");

            for (Tache tache : grapheCharge.getTaches()) {
                System.out.print("- " + tache.getLibelle() + " (" + 
                                tache.getDuree() + " " + 
                                grapheCharge.getUnite() + ")");
                System.out.print(" | Dépendances : ");
                for (Tache dep : tache.getTachesRequises()) {
                    System.out.print(dep.getLibelle() + " ");
                }
                System.out.println();
            }

            // Appeler des calculs sur grapheCharge
            
            
            System.out.println("Fin du projet : " + 
                               grapheCharge.calculerFinProjet() + " " + 
                               grapheCharge.getUnite());

        } catch (IOException erreurImport) {
            System.err.println("Erreur lors de l'export ou du chargement : " + 
                               erreurImport.getMessage());
        }
    }
}