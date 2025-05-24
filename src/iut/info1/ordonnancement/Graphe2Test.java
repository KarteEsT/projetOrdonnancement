/*
 * Graphe.java                                           14 mai 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;

/**
 * Représenter et gère un graphe PERT 
 * (Program Evaluation Review Technique).
 * Elle permet d'ajouter des tâches, de définir leurs dépendances
 * et de calculer le chemin critique du projet.
 *
 * @author Le Goff Gabriel
 * @author Robache Gabriel
 * @author Roveri Esteban
 * @author Massicard MAel
 * @author Sauvaire Leo
 * @version 1.0
 */
public class Graphe2Test {
    
    /** Titre du graphe */
    private String titre;
    
    /** Unité de temps du graphe */
    private String unite;
    
    /** Ensembles des différentes tâches du graphe */
    private ArrayList<Tache> taches;
    
    /** Ensemble des différents événement du graphe */
    private static ArrayList<Evenement> evenement;
    
    /**
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param evenements composant le graphe
     * @throws NullPointerException si le titre ou l'unité est null
     */
    public Graphe2Test(String titre, String unite, ArrayList<Tache> taches, 
            ArrayList<Evenement> evenements) {
        if (titre == null || titre.isEmpty() || titre.isBlank()) {
                throw new NullPointerException("Le titre ne peut pas être null.");
        }
        
        if (unite == null) {
                throw new NullPointerException("L'unité ne peut pas être null.");
        }
        
        if (!verifierTachesRequisesExistantes()) {
            throw new IllegalArgumentException("Les tâches requises ne sont pas toutes présentes.");
        }
        
        //Peuvent lever des exceptions si les taches ou évènements sont null
        for (Tache tache : taches) {
            ajouterTache(tache);
        }
        
        for (Evenement evenement : evenements) {
            ajouterEvenement(evenement);
        }
        
        this.titre = titre;
        this.unite = unite;
        this.taches = taches;
        this.evenement = evenements;
    }


    /**
     * @return nouvelle valeur de titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return nouvelle valeur de unite
     */
    public String getUnite() {
        return unite;
    }

    /**
     * @return nouvelle valeur de taches
     */
    public ArrayList<Tache> getTaches() {
        return taches;
    }

    /**
     * @return nouvelle valeur de évènement
     */
    public static ArrayList<Evenement> getEvenement() {
        return evenement;
    }
    
    /**
     * Permet de vérifier si toutes les tâches requises existent dans le graphe.
     * @return true si toutes les tâches requises existent
     *         false sinon.
     */
    public boolean verifierTachesRequisesExistantes() {
        if (getTaches() == null) {
            return true; 
        }

        for (Tache tache : getTaches()) {
            for (Tache requise : tache.getTachesRequises()) {
                boolean trouvee = false;
                for (Tache t : getTaches()) {
                    if (t.equals(requise)) {
                        trouvee = true;
                        break;
                    }
                }
                if (!trouvee) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ajoute une tâche au graphe et vérifie la validité 
     * des tâches requises.
     *
     * @param tache la tâche à ajouter
     * @throws NullPointerException si la tâche est null
     * @throws IllegalArgumentException si la tâche existe déjà dans le graphe
     */
    public void ajouterTache(Tache tache) {
        if (tache == null) {
            throw new NullPointerException("Une tâche ne peut pas être null.");
        }
        for (Tache t : taches) {
            if (tache.equals(t)) {
                throw new IllegalArgumentException("La tâche " + tache.getLibelle() +
                                                   " existe déjà dans le graphe.");
            }
        }
        
        getTaches().add(tache);
        if (!verifierTachesRequisesExistantes()) {
            throw new IllegalArgumentException("Les tâches requises ne " +
                                               "sont pas toutes présentes.");
        }
    }
    

    /**
     * Ajoute un évènement au graphe.
     * Cette méthode vérifie que l'évènement n'existe pas déjà
     * @param evenement l'évènement à ajouter
     * @throws NullPointerException si la tâche est null
     * @throws IllegalArgumentException si l'évènement existe déjà dans le graphe
     */
    public void ajouterEvenement(Evenement evenement) {
        if (evenement == null) {
            throw new NullPointerException("Un évènement ne peut pas être null.");
        }
        for (Evenement event : getEvenement()) {
            if (evenement.equals(event)) {
                throw new IllegalArgumentException("L'évènement " + event.getId() +
                                                   " existe déjà dans le graphe.");
            }
        }
        
        getEvenement().add(evenement);
    }
    
    /**
     * @return le nombre de tâches du graphe
     */
    public int getNombreTaches() {
        return getTaches().size();
    }
    
    /**
     * Méthode permettant de vérifier si il existe un 
     * circuit dans le graphe des tâches.
     * @return true si un circuit existe,
     *         false sinon.
     */
    public boolean existeCircuit() {
        // Modéliser le graphe par une matrice d'adjacence
        // M* = M
        /*
         * Algorithme de détection de circuit :
         * On recherche si il existe une ligne ou une colonne de 0 dans M*
         * ^  -si non, alors il existe un circuit
         * |  -si oui :
         * |    -on supprime la ligne et la colonne correspondantes
         * |    -M* devient la matrice résultante de cette opération
         * |    -on recommence jusqu'à ce que M* ne contienne que des 0 
         * |     (ou soit vide,). Alors il n'existe pas de circuit
         * |     |
         *  ------
         */
        
        /* Modélisation du graphe par une matrice d'adjacence */
        /* On compte le nombre de taches */
        int nombreTaches = getNombreTaches();
        
        /* On crée la matrice d'adjacence */
        boolean[][] matriceAdjacence = new boolean[nombreTaches][nombreTaches];
        
        /* On remplit la matrice d'adjacence */
        for (int i = 0; i < nombreTaches; i++) {
            Tache tache = getTaches().get(i);
            for (Tache requise : tache.getTachesRequises()) {
                int j = getTaches().indexOf(requise);
                if (j != -1) {
                    matriceAdjacence[i][j] = true;
                }
            }
        }
        
        /* On vérifie s'il existe un circuit */
        for (int i = 0; i < nombreTaches; i++) {
            boolean ligneVide = true;
            boolean colonneVide = true;
            for (int j = 0; j < nombreTaches; j++) {
                ligneVide = ligneVide && matriceAdjacence[i][j];
                colonneVide = colonneVide && matriceAdjacence[j][i];
            }
            if (ligneVide || colonneVide) {
                // On supprime la ligne et la colonne correspondantes
                for (int j = 0; j < nombreTaches; j++) {
                    matriceAdjacence[i][j] = false;
                    matriceAdjacence[j][i] = false;
                }
            } else {
                return true; // Il existe un circuit
            }
        }
        
        
        return false; //STUB 
    }
}