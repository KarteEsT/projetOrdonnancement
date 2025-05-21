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
}