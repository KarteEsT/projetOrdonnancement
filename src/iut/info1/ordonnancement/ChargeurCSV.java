/*
 * ChargeurCSV.java                          29/04/2025
 * IUT de Rodez, info1, 2024-2025, pas de copyright
 */

package iut.info1.ordonnancement;

/**
 * Programme permettant la lecture et l'écriture de fichiers 
 * @author Léo Sauvaire
 * @author Gabriel Le Goff
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ChargeurCSV {

    /**
     * Enregistre des données dans un fichier CSV avec des valeurs séparées par des points-virgules
     * @param cheminFichier le chemin vers le fichier à écrire
     * @param donnees une liste de lignes, où chaque ligne est un tableau de chaînes
     */
    public static void ecrireCSV(String cheminFichier, ArrayList<String[]> donnees) {
        try {
            BufferedWriter ecriture = new BufferedWriter(new FileWriter(cheminFichier));

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
        } catch (IOException e) {
            System.out.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    /**
     * Demande à l'utilisateur l'emplacement d'écriture du fichier CSV
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exemple de données qui vont être écrites dans le fichier CSV
        ArrayList<String[]> nouvellesDonnees = new ArrayList<>();
        nouvellesDonnees.add(new String[] {"Nom", "Prénom", "Âge"});
        nouvellesDonnees.add(new String[] {"Durand", "Alice", "20"});
        nouvellesDonnees.add(new String[] {"Martin", "Bob", "22"});

        // Saisie du chemin d’écriture du fichier CSV
        System.out.print("Entrez le chemin du fichier CSV à enregistrer : ");
        String cheminEcriture = scanner.nextLine();

        ecrireCSV(cheminEcriture, nouvellesDonnees);

        scanner.close();
    }
}
