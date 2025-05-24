/*
 * Tache.java                                   14 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;


/**
 * Cette classe représente une tâche dans un système d'ordonnancement.
 * Elle contient des informations sur le libellé, la description, la durée,
 * ainsi que les tâches requises pour son exécution.
 * Elle respecte le principe SRP en encapsulant les données et en fournissant
 * des méthodes pour y accéder et les manipuler.
 * 
 * Les méthodes `equals` et `hashCode` sont redéfinies pour permettre
 * la comparaison et l'utilisation dans des collections basées sur le hachage.
 * 
 * @author Gabriel Le Goff
 * @author Gabriel Robache
 * @author Roveri Esteban
 * @author Sauvaire Léo
 * @author Massicard Maël
 */

public class Tache {
    
    //TODO : Vérifier que chaque tâche a un libellé différent
    
    /** Le libellé de la tache */
    private String libelle;
    
    /** La description de la tâche */
    private String description;
    
    /** La durée estimée de la tâche */
    private double duree;
    
    /** Les tâches qui doivent être terminées avant celle-ci */
    private ArrayList<Tache> tachesRequises;

    /**
     * Construit une tâche avec un libellé, une description, une durée, et une liste de tâches requises.
     * Initialise tous les attributs de la tâche.
     *
     * @param libelle le nom de la tâche
     * @param description une brève explication de la tâche
     * @param duree le temps estimé pour réaliser la tâche
     * @param tachesRequises les tâches devant être terminées avant celle-ci
     */
    public Tache(String libelle, String description, double duree, ArrayList<Tache> tachesRequises) {
        if (libelle == null || libelle.isEmpty() || libelle.strip().isEmpty()) {
            throw new IllegalArgumentException("Le libellé de la tâche ne peut pas être vide.");
        }
        if (description == null || description.isEmpty() || description.strip().isEmpty()) {
            throw new IllegalArgumentException("La description de la tâche ne peut pas être vide.");
        }
        if (duree < 0) {
            throw new IllegalArgumentException("La durée de la tâche doit être positive ou nulle.");
        }
        
        this.libelle = libelle;
        this.description = description;
        this.duree = duree;
        this.tachesRequises = tachesRequises;
    }

    /**
     * Construit une tâche sans tâches requises.
     * Initialise la tâche avec un libellé, une description et une durée,
     * et un ArrayList<Tache> vide pour les tâches requises.
     *
     * @param libelle le nom de la tâche
     * @param description une brève explication de la tâche
     * @param duree le temps estimé pour réaliser la tâche
     */
    public Tache(String libelle, String description, double duree) {
        this(libelle, description, duree, new ArrayList<>());
    }

    /**
     * @return le libellé de la tâche
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @return la description de la tâche
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return la durée estimée de la tâche
     */
    public double getDuree() {
        return duree;
    }

    /**
     * @return un ArrayList<Tache> des tâches à réaliser avant celle-ci
     */
    public ArrayList<Tache> getTachesRequises() {
        return tachesRequises;
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
        String[][] matriceAdjacence = new String[nombreTaches][nombreTaches];
        
        /* On remplit la matrice d'adjacence */
        
        
        return false; //STUB 
    }
    
    /**
     * Méthode permettant de compter le nombre de Taches à partir de la liste
     * des Taches requises.
     * Surcharge de la méthode getNombreTaches(ArrayList<Tache> tachesVisitees)
     * @return une ArrayList avec la tache courante dedans ainsi qu'un appel 
     *         à la méthode getNombreTaches(ArrayList<Tache>).
     */
    public int getNombreTaches() {
        ArrayList<Tache> tachesVisitees = new ArrayList<>();
        tachesVisitees.add(this);
        return getNombreTaches(tachesVisitees);
    }
    
    /**
     * Méthode permettant de compter le nombre de Taches à partir de la liste
     * des Taches requises.
     * @param tachesVisitees les tâches déjà visitées dans le graphe
     *        Si c'est la première fois qu'on appelle cette méthode,
     *        tachesVisitees doit contenir this.
     * @return le nombre de Taches du graphe à partir des tachesRequises.
     */
    public int getNombreTaches(ArrayList<Tache> tachesVisitees) {
        /*
         * On va vérifier dans tacheRequises de chaque Tache combien 
         * de Taches différentes de celles qu'on a déjà compté elle contient
         * On va utiliser la méthode contains de ArrayList<Tache>
         */
        
        /* Condition d'arrêt */
        boolean plusDeCheminPossible = true;
        
        for (Tache tache : getTachesRequises()) {
            plusDeCheminPossible = plusDeCheminPossible && tachesVisitees.contains(tache);
        }
        
        if (plusDeCheminPossible) {
            return tachesVisitees.size();
        }
        
        
        /* Remplissage de tachesVisitees*/
        
        for (Tache tache : tachesRequises) {
            if (!tachesVisitees.contains(tache)) {
                tachesVisitees.add(tache);
            }
        }
        
        return getNombreTaches(tachesVisitees);
    }
    
}
