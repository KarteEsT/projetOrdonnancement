/*
 * Evenement.java                                                   14 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un événement dans un système d'ordonnancement.
 * Un événement est défini par un identifiant,
 * des dates au plus tôt et au plus tard,
 * des prédécesseurs, des successeurs, et des tâches associées.
 * Elle permet de calculer les dates au plus tôt et au plus tard, 
 * ainsi que de vérifier
 * si un événement est critique.
 * 
 * @author Gabriel Robache
 * @author Gabriel Le Goff
 * @author Mael Massicard
 * @author Esteban Roveri
 * @author Léo Sauvaire
 * @version 2.0
 */
public class Evenement {

    /** Identifiant de l'événement */
    private int id;

    /** Tâche au plus tôt d'un événement */
    private double dateAuPlusTot;

    /** Tâche au plus tard d'un événement */
    private double dateAuPlusTard;

    /** Ensemble des tâches prédécesseurs d'un événement */
    private ArrayList<Tache> tachePredecesseurList = new ArrayList<>();
    
    /** Ensemble des tâches successeurs d'un événement */
    private ArrayList<Tache> tacheSuccesseurList = new ArrayList<>();
    
    /** Ensemble des événement prédécesseurs d'un événement */
    private ArrayList<Evenement> evenementPredecesseurList = new ArrayList<>();
    
    /** Ensemble des événements successeurs d'un événement */
    private ArrayList<Evenement> evenementSuccesseurList = new ArrayList<>();
    
    /**
     * Constructeur pour un événement initial.
     * Les valeurs de tâche au plus tôt et au plus tard 
     * sont initialisées à 0.0.
     * @param id Identifiant de l'événement
     */
    public Evenement() {
        this.id = 0;
        this.dateAuPlusTot = 0.0;
        this.dateAuPlusTard = 0.0;
    }
    
    /**
     * Constructeur pour un événement.
     * @param id Identifiant de l'événement
     * @param evenementPredecesseurList Liste des événements 
     *        prédécesseurs
     * @param tachePredecesseurList Liste des tâches prédécesseurs
     * @throws IllegalArgumentException si une des listes est 
     *         vide ou contient des éléments invalides
     */
    public Evenement(int id, ArrayList<Evenement> evenementPredecesseurList, 
                     ArrayList<Tache> tachePredecesseurList) {
    	
        if (evenementPredecesseurList.isEmpty()) {
            throw new IllegalArgumentException("Un événement doit avoir au " + 
                                          "moins un événement prédécesseur.");
        }
        if (tachePredecesseurList.isEmpty()) {
            throw new IllegalArgumentException("Un événement doit avoir au " + 
                                               "moins une tâche prédécesseur.");
        }
        
        this.id = id;
        this.dateAuPlusTot = 0.0; 
        this.dateAuPlusTard = 0.0; 
        this.evenementPredecesseurList = evenementPredecesseurList;
        this.tachePredecesseurList = tachePredecesseurList;
    }   

    /**
     * @return l'identifiant de l'événement
     */
    public int getId() {
        return id;
    }

    /**
     * @return la tâche au plus tôt de l'événement
     */
    public double getDateAuPlusTot() {
        return dateAuPlusTot;
    }

    /**
     * @return la tâche au plus tard de l'événement
     */
    public double getDateAuPlusTard() {
        return dateAuPlusTard;
    }
    
    /**
     * @return une liste des événements prédécesseurs
     */
    public ArrayList<Evenement> getEvenementPredecesseurList() {
        return evenementPredecesseurList;
    }
    
	/**
	 * @return une liste des événements successeurs
	 */
    public ArrayList<Evenement> getEvenementSuccesseurList() {
        return evenementSuccesseurList;
    }

    /**
     * @return une liste des tâches prédécesseurs
     */
    public ArrayList<Tache> getTachePredecesseurList() {
        return tachePredecesseurList;
    }

    /**
     * @return une liste des tâches successeurs
     */
    public ArrayList<Tache> getTacheSuccesseurList() {
        return tacheSuccesseurList;
    }

    /**
     * Setter de la liste des événements successeurs.
     * @param evenementSuccesseurList Liste des événements successeurs
     */
    public void setEvenementSuccesseurList(
                                ArrayList<Evenement> evenementSuccesseurList) {
        if (    evenementSuccesseurList == null ||
                evenementSuccesseurList.isEmpty()) {
            throw new IllegalArgumentException("La liste des événements " +
                                          "successeurs ne peut pas être vide.");
        }
        this.evenementSuccesseurList = evenementSuccesseurList;
    }
	
    /**
     * Setter de la liste des taches successeurs.
     * @param tacheSuccesseurList Liste des tâches successeurs
     */
    public void setTacheSuccesseurList(ArrayList<Tache> tacheSuccesseurList) {
        if (tacheSuccesseurList == null || tacheSuccesseurList.isEmpty()) {
            throw new IllegalArgumentException("La liste des tâches" + 
                                         " successeurs ne peut pas être vide.");
        }
        this.tacheSuccesseurList = tacheSuccesseurList;
    }

    /**
     * Setter de la date au plus tôt.
     * @param dateAuPlusTot Date au plus tôt de l'événement
     */
    public void setDatePlusTot(double dateAuPlusTot) {
        this.dateAuPlusTot = dateAuPlusTot;
    }

    /**
     * Setter de la date au plus tard.
     * @param dateAuPlusTard Date au plus tard de l'événement 
     */
    public void setDatePlusTard(double dateAuPlusTard) {
        this.dateAuPlusTard = dateAuPlusTard;
    }

    /**
     * Ajout d'une tache dans la liste des tache successeur.
     * @param tache Tâche à ajouter en tant que successeur
     */
    public void addTacheSuccesseur(Tache tache) {
        tacheSuccesseurList.add(tache);
    }

    /**
     * Ajout d'une tache dans la liste des tache successeur.
     * @param tache Tâche à ajouter en tant que prédécesseur
     */
    public void addTachePredecesseur(Tache tache) {
        tachePredecesseurList.add(tache);
    }

    /**
     * Ajout d'une tache dans la liste des tache successeur.
     * @param evenement Événement à ajouter en tant que successeur
     */
    public void addEvenementSuccesseur(Evenement evenement) {
        evenementSuccesseurList.add(evenement);
    }

    /**
     * Ajout d'une tache dans la liste des tache successeur.
     * @param evenement Événement à ajouter en tant que prédécesseur
     */
    public void addEvenementPredecesseur(Evenement evenement) {
        evenementPredecesseurList.add(evenement);
    }

    /**
     * Vérifie si cet événement est critique.
     * Un événement est critique si la tâche au plus tôt
     * et la tâche au plus tard sont égales.
     * @return true si l'événement est critique, false sinon
     */
    public boolean estCritique() {
        return dateAuPlusTot == dateAuPlusTard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false; 
        Evenement evenement = (Evenement) o;
        return id == evenement.id
               && evenement.getDateAuPlusTot() == getDateAuPlusTot()
               && evenement.getDateAuPlusTard() == getDateAuPlusTard()
               && evenementPredecesseurList.equals(
                                           evenement.evenementPredecesseurList)
               && tachePredecesseurList.equals(evenement.tachePredecesseurList);
    }
    
    @Override
    public String toString() {
        String evenement = "\n\nÉvénement " + id + " :\n";
        evenement += "  Date au plus tôt : " + dateAuPlusTot + "\n";
        evenement += "  Date au plus tard : " + dateAuPlusTard + "\n";
        evenement += "  Prédécesseurs : ";
        for (Evenement predecesseur : evenementPredecesseurList) {
            evenement += predecesseur.getId() + ", ";
        }
        evenement += "\n  Successeurs : ";
        for (Evenement successeur : evenementSuccesseurList) {
            evenement += successeur.getId() + ", ";
        }
        evenement += "\n  Tâches prédécesseurs : ";
        for (Tache tache : tachePredecesseurList) {
            evenement += tache.getLibelle() + ", ";
        }
        evenement += "\n  Tâches successeurs : ";
        for (Tache tache : tacheSuccesseurList) {
            evenement += tache.getLibelle() + ", ";
        }
        return evenement;
    }
}