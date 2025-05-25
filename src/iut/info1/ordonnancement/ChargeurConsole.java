/*
 * ChargeurConsole.java                                           23 mai 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.Scanner;

/**
 * Permet de charger des taches depuis la console
 */
public class ChargeurConsole {
    //nom de tâche ; description de la tâche ; durée ; prédécesseur 1 ; prédécesseur 2 ; ... ; -
    
    /**
     * Demande à l'utilisateur de saisir le nom d'une tâche
     * @return nom de la tâche
     */
    public String demandeNomTache() {
        Scanner entree = new Scanner(System.in);
        String nom;
        
        boolean nomInvalide = false;
        
        do {
            System.out.print("Renseignez le nom de la tâche : ");
            nom = entree.nextLine().strip();
            if (nom.equals("")) {
                System.out.println("Le nom de la tâche ne peut pas être vide.");
            } else {
                nomInvalide = false;
            }
        } while (nomInvalide);
        
        return nom;
    }
    
    /**
     * Demande à l'utilisateur de saisir la description d'une tâche
     * @return description de la tâche
     */
    public String demandeDescriptionTache() {
        Scanner entree = new Scanner(System.in);
        String description;
        
        boolean descriptionInvalide = false;
        
        do {
            System.out.print("Renseignez la description de la tâche : ");
            description = entree.nextLine().strip();
            if (description.equals("")) {
                System.out.println("La description de la tâche ne peut pas être vide.");
            } else {
                descriptionInvalide = false;
            }
        } while (descriptionInvalide);
        
        return description;
    }
    
    /**
     * Demande à l'utilisateur de saisir la durée d'une tâche
     * @return durée de la tâche
     */
    public double demandeDureeTache() {
        Scanner entree = new Scanner(System.in);
        double duree = 0.0;
        
        boolean dureeInvalide = false;
        
        do {
            System.out.print("Renseignez la durée de la tâche : ");
            try {
                duree = Double.parseDouble(entree.nextLine().strip());
                if (duree <= 0) {
                    System.out.println("La durée de la tâche doit être un flottant positif.");
                    dureeInvalide = true;
                } else {
                    dureeInvalide = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("La durée de la tâche doit être un entier positif.");
                dureeInvalide = true;
            }
        } while (dureeInvalide);
        
        return duree;
    }
    
    /**
     * Demande à l'utilisateur de saisir les prédécesseurs d'une tâche
     * @return nomsPredecesseurs de la tâche
     */
    public String[] demandePredecesseursTache() {
        Scanner entree = new Scanner(System.in);
        String input;
        String[] nomsPredecesseurs;

        System.out.print("Renseignez les prédécesseurs de la tâche (séparés par des virgules, laissez vide si aucun) : ");
        input = entree.nextLine().strip();
        if (input.equals("")) {
            nomsPredecesseurs = new String[0];
        } else {
            nomsPredecesseurs = input.split(",");
        }
        return nomsPredecesseurs;
    }
}