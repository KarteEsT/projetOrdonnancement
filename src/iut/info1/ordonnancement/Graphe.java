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
public class Graphe {
    
    /** Titre du graphe */
    private String titre;
    
    /** Unité de temps du graphe */
    private String unite;
    
    /** Ensembles des différentes tâches du graphe */
    private ArrayList<Tache> taches;
    
    /** Ensemble des différents événement du graphe */
    private ArrayList<Evenement> evenements;
    
    /**
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param listeEvenements composant le graphe
     * @throws NullPointerException si le titre ou l'unité est null
     */
    public Graphe(String titre, String unite, ArrayList<Tache> taches, ArrayList<Evenement> listeEvenements) {
        if (titre == null || titre.isEmpty() || titre.isBlank()) {
            throw new NullPointerException("Le titre ne peut pas être null.");
        }

        if (unite == null) {
            throw new NullPointerException("L'unité ne peut pas être null.");
        }

        this.taches = new ArrayList<>();
        if (taches != null && !taches.isEmpty()) {
            for (Tache tache : taches) {
                ajouterTache(tache);
            }
        }

        // Initialisation de la liste `evenements`
        this.evenements = new ArrayList<>();
        if (listeEvenements != null && !listeEvenements.isEmpty()) {
            for (Evenement evenement : listeEvenements) {
                ajouterEvenement(evenement);
            }
        }

        this.titre = titre;
        this.unite = unite;
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
     * Définit les tâches du graphe.
     * 
     * @param taches les tâches à définir
     */
    private void setTaches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    /**
     * @return nouvelle valeur de évènement
     */
    public ArrayList<Evenement> getEvenement() {
        return evenements;
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
        if (tache.getTachesRequises() != null ) {
            for (Tache requise : tache.getTachesRequises()) {
                if (!getTaches().contains(requise)) {
                    throw new IllegalArgumentException(
                            "La tâche requise " + requise.getLibelle() + " n'existe pas dans le graphe.");
                }
            }
        }
        
        if (getTaches().isEmpty() || getTaches() == null) {
            getTaches().add(tache);
        } else {
            if (getTaches().contains(tache)) {
                throw new IllegalArgumentException("La tâche " + tache.getLibelle() +
                        " existe déjà dans le graphe.");
            }

            getTaches().add(tache);
        }
        
    }
    
    /**
     * Ajoute plusieurs tâches au graphe. Cette méthode vérifie que les tâches
     * n'existent pas déjà
     * 
     * @param taches les tâches à ajouter
     * @throws NullPointerException     si une des tâches est null
     * @throws IllegalArgumentException si une des tâches existe déjà dans le graphe
     */
    public void ajouterPlusieursTaches(Tache... taches) {
        for (Tache tache : taches) {
            ajouterTache(tache);
        }
    }
    
    /**
     * Calcule la date de fin de projet.
     * La date de fin de projet correspond à la date au plus tôt
     * de l'unique événement sans successeur.
     *
     * @return la date de fin du projet
     */
    public double calculerFinProjet() {
        for (Evenement evenement : getEvenement()) {
            if (evenement.getEvenementSuccesseurList().isEmpty()) {
                return evenement.getDateAuPlusTot();
            }
        }
        throw new IllegalStateException("Aucun événement sans successeur trouvé.");
    }
    
    /**
     * Supprime une tâche du graphe.
     * @param tache la tâche à supprimer
     */
    public void supprimerTache(Tache tache) {
        if (tache == null) {
            throw new NullPointerException("La tâche à supprimer ne peut pas être null.");
        }

        if (!getTaches().contains(tache)) {
            throw new IllegalArgumentException("La tâche " + tache.getLibelle() + " n'existe pas dans le graphe.");
        }

        getTaches().remove(tache);
        
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
     * Ordonne les tâches du graphe en fonction de leurs dépendances.
     * Va créer des événements et les associer au graphe.
     * @throws  IllegalStateException si le graphe contient un circuit
     */
    public void ordonnerTaches() {
        
        trierTaches();
            
        /* Création des événements */
        int compteur;
        compteur = 1;
        ArrayList<Evenement> listeEvenements = new ArrayList<>();
        
        /* Association des tâches initiales à l'événement initial */
        
        Evenement evenementInitial = Evenement.EVENEMENT_INITIAL;
        for (Tache tache : getTaches()) {
            if (tache.getTachesRequises().isEmpty()) {
                evenementInitial.addTachePredecesseur(tache);
            }
        }
        
        //Evenement(int id, ArrayList<Evenement> evenementPredecesseurList, ArrayList<Tache> tachePredecesseurList) 
        /*
         * Créer un événement pour chaque tâche de la première couche
         * 
         * On créé un event à chaque fois, si l'évent existe déjà dans le graphe, 
         * on le "fusionne" on ajoute ses tâches prédécesseurs et evénements prédécesseurs
         * au nouvel événement créé et on supprime un des deux événements.
         */
        
        
    }
    
    /**
     * Trie la liste des tâches du graphe en fonction de leurs
     * taches requises (dépendances).
     * @throws IllegalStateException si le graphe contient un circuit
     */
    public void trierTaches() {
        if (existeCircuit()) {
            throw new IllegalStateException("Le graphe contient un circuit," 
                                            + " l'ordonnancement est impossible.");
        }
        /* Tri ArrayList */
        ArrayList<Tache> tachesTriees = new ArrayList<>();
        ArrayList<Tache> tachesNonTriees = new ArrayList<>(getTaches());
        
        while (!tachesNonTriees.isEmpty()) {
            for (Tache aTrier : tachesNonTriees) {
                System.out.println("Tache : " + aTrier.toString());
                if (aTrier.getTachesRequises().isEmpty() || tachesTriees.containsAll(aTrier.getTachesRequises())) {
                    tachesTriees.add(aTrier);
                    tachesNonTriees.remove(aTrier);
                }
            }
        }
        setTaches(tachesTriees);
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
        
        /* Algorithme de détection de circuit */
        boolean matriceEnCoursDeManipulation = true;
        boolean[] supprime = new boolean[nombreTaches];
        do {
            boolean modification = false;

            for (int i = 0; i < nombreTaches; i++) {
                if (supprime[i]) {
                    continue; // Ignorer les lignes/colonnes déjà supprimées
                }

                boolean ligneVide = true;
                boolean colonneVide = true;

                for (int j = 0; j < nombreTaches; j++) {
                    if (!supprime[j]) {
                        ligneVide &= !matriceAdjacence[i][j];
                        colonneVide &= !matriceAdjacence[j][i];
                    }
                }

                if (ligneVide || colonneVide) {
                    supprime[i] = true; // Marquer la ligne/colonne comme supprimée
                    modification = true;
                }
            }

            // Si aucune modification n'a été faite, vérifier s'il reste des éléments
            if (!modification) {
                for (int i = 0; i < nombreTaches; i++) {
                    if (!supprime[i]) {
                        for (int j = 0; j < nombreTaches; j++) {
                            if (!supprime[j] && matriceAdjacence[i][j]) {
                                return true; // Circuit détecté
                            }
                        }
                    }
                }
                matriceEnCoursDeManipulation = false; // Pas de circuit
            }
        } while (matriceEnCoursDeManipulation);

        return false;
    }
    
    
    @Override
    public String toString() {
        String graphe = getTitre();
        graphe += ", unite = " + getUnite();
        graphe += "\n" + getTaches();
        return graphe;
    }
}