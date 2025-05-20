/*
 * ChargeurCSV.java                               29 avril 2025
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
     * Enregistre des données dans un fichier CSV avec des valeurs
     * séparées par des points-virgules.
     * @param cheminFichier le chemin vers le fichier à écrire
     * @param donnees une liste de lignes, où chaque ligne est un
     * tableau de chaînes.
     */
    public static void ecrireCSV(String cheminFichier, 
                                 ArrayList<String[]> donnees) {
        try {
            BufferedWriter ecriture =
                    new BufferedWriter(new FileWriter(cheminFichier));

            for (String[] ligne : donnees) {
                for (int i = 0; i < ligne.length; i++) {
                    ecriture.write(ligne[i]);
                    if (i < ligne.length - 1) {
                        ecriture.write(";");
                    }
                }
                ecriture.newLine();
            }

            ecriture.close();
            System.out.println("Écriture réussie !");
        } catch (IOException erreurEcriture) {
            System.out.println("Erreur d'écriture : "
                              + erreurEcriture.getMessage());
        }
    }

    /**
     * Enregistre des données dans un fichier CSV avec des
     * valeurs séparées par des points-virgules.
     * @param cheminFichier le chemin vers le fichier à lire
     * @param donnees une liste de lignes, où chaque ligne
     * est un tableau de chaînes de caractères
     */
    public static void lireCSV(String cheminFichier,
                               ArrayList<String[]> donnees) {
        try {
            BufferedReader lecture =
                        new BufferedReader(new FileReader(cheminFichier));
            String ligne;

            /* Lecture du fichier ligne par ligne */
            while ((ligne = lecture.readLine()) != null) {
                String[] valeurs = ligne.split(";");
                donnees.add(valeurs);
            }

            lecture.close();
        } catch (IOException erreurLecture) {
            System.out.println("Erreur de lecture : "
                              + erreurLecture.getMessage());
        }
    }

    /**
     * Demande à l'utilisateur l'emplacement d'écriture du fichier CSV.
     * 
     * @param args non utilisé
     */
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);

         // Exemple de données qui vont être écrites dans le fichier CSV
         ArrayList<String[]> nouvellesDonnees = new ArrayList<>();
         nouvellesDonnees.add(new String[] { "EtapePrecedente", "Tache",
                                             "EtapeSuivante" });
         nouvellesDonnees.add(new String[] { "0", "a", "2" });
         nouvellesDonnees.add(new String[] { "1", "c", "3" });
         nouvellesDonnees.add(new String[] { "2", "j", "3" });

         /* Saisie du chemin d’écriture du fichier CSV */
         System.out.print("Entrez le chemin du fichier CSV dans lequel "
                         + " écrire les données (les données présentes dans "
                         + " ce fichier seront écrasées) : ");
        String cheminEcriture = scanner.nextLine();

        ecrireCSV(cheminEcriture, nouvellesDonnees);

        // Saisie du chemin de lecture du fichier CSV
        System.out.print("\nEntrez le chemin du fichier CSV à lire : ");
        String cheminLecture = scanner.nextLine();

        // Lecture du fichier CSV
        ArrayList<String[]> donneesLues = new ArrayList<>();
        lireCSV(cheminLecture, donneesLues);
        System.out.println("Données lues :\n");

        // Affichage données lues
        for (int nbValeurs = 0; nbValeurs < donneesLues.size(); nbValeurs++) {
            String[] ligne = donneesLues.get(nbValeurs);
            for (int i = 0; i < ligne.length; i++) {
                System.out.print(ligne[i]);
                if (i < ligne.length - 1) {
                    System.out.print(";");
                }
            }
            System.out.println();
        }
        scanner.close();
    }
}