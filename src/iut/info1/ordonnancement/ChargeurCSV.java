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
	
	/* Chemin du fichier CSV à écrire */
	private static String cheminFichierEcrit;
	
	/* Chemin du fichier CSV à lire */
	private static String cheminFichierLu;
	
    /**
     * Enregistre des données dans un fichier CSV avec des valeurs
     * séparées par des points-virgules.
     * @param cheminFichierEcriture 
     * @param cheminFichier le chemin vers le fichier à écrire
     * @param donnees une liste de lignes, où chaque ligne est un
     * tableau de chaînes.
     */
    public static void ecrireCSV(String cheminFichierEcriture, ArrayList<String[]> donnees) {
    	
    	cheminFichierEcrit = cheminFichierEcriture;    	
        try {
            BufferedWriter ecriture = new BufferedWriter(new FileWriter(cheminFichierEcriture));

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
    	
    	cheminFichierLu = cheminFichier;
    	
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
	 * Tests temporaires de la classe ChargeurCSV
	 * @param args non utilisé
	 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Exemple de données qui vont être écrites dans le fichier CSV
        ArrayList<String[]> nouvellesDonnees = new ArrayList<>();
        nouvellesDonnees.add(new String[] {"EtapePrecedente", "Tache", "EtapeSuivante"});
        nouvellesDonnees.add(new String[] {"0", "a", "2"});
        nouvellesDonnees.add(new String[] {"1", "c", "3"});
        nouvellesDonnees.add(new String[] {"2", "j", "3"});

        /* Saisie du chemin d’écriture du fichier CSV */
        System.out.print("Entrez le chemin du fichier CSV dans lequel écrire les données "
        		           + "(les données présentes dans ce fichier seront écrasées) : ");
        String cheminEcriture = scanner.nextLine();
        ecrireCSV(cheminEcriture, nouvellesDonnees);
        
        // Saisie du chemin de lecture du fichier CSV
        System.out.print("\nEntrez le chemin du fichier CSV à lire : ");
        String cheminLecture = scanner.nextLine();
        
        // Lecture du fichier CSV
        ArrayList<String[]> donneesLues = new ArrayList<>();
        lireCSV(cheminLecture, donneesLues);
        System.out.println("Données lues :\n");
		
        // Affichage des données lues
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
    
	
    /**
     * Renvoie le chemin du fichier CSV à écrire
     * @return le chemin du fichier CSV à écrire
     */
    public String getCheminFichier() {
        return cheminFichierEcrit;
    }
    
    /**
     * Renvoie le chemin du fichier CSV à lire
     * @return le chemin du fichier CSV à lire
     */
    public String getCheminFichierLecture() {
        return cheminFichierLu;
    }
	
	/**
	 * Modifie le chemin du fichier CSV à écrire
	 * @param nouveauCheminFichierEcrit le nouveau chemin du fichier CSV à écrire
	 */
	public void setCheminFichierEcrit(String nouveauCheminFichierEcrit) {
		cheminFichierEcrit = nouveauCheminFichierEcrit;
	}
	
	/**
	 * Modifie le chemin du fichier CSV à lire
	 * @param nouveauCheminFichierLu le nouveau chemin du fichier CSV à lire
	 */
	public void setCheminFichierLu(String nouveauCheminFichierLu) {
		cheminFichierLu = nouveauCheminFichierLu;
	}
}

