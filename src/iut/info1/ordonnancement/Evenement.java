/*
 * Evenement.java                                                   14 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;

/**
 * Cette classe représente un événement dans un système d'ordonnancement.
 * Un événement est défini par un identifiant, des dates au plus tôt et au plus tard,
 * des prédécesseurs, des successeurs, et des tâches associées.
 * Elle permet de calculer les dates au plus tôt et au plus tard, ainsi que de vérifier
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

    /** Événement initial, utilisé pour représenter le début du projet */
    public final static Evenement EVENEMENT_INITIAL = new Evenement();
    
    /**
     * Constructeur pour un événement initial.
     * Les valeurs de tâche au plus tôt et au plus tard sont initialisées à 0.0.
     * @param id Identifiant de l'événement
     */
    private Evenement() {
        this.id = 0;
        this.dateAuPlusTot = 0.0;
        this.dateAuPlusTard = 0.0;
    }
    
    /**
     * Constructeur pour un événement.
     * @param id Identifiant de l'événement
     * @param evenementPredecesseurList Liste des événements prédécesseurs
     * @param tachePredecesseurList Liste des tâches prédécesseurs
     * @throws IllegalArgumentException si une des listes est vide ou contient des éléments invalides
     */
    public Evenement(int id, ArrayList<Evenement> evenementPredecesseurList, ArrayList<Tache> tachePredecesseurList) {
    	
        if (evenementPredecesseurList.isEmpty()) {
            throw new IllegalArgumentException("Un événement doit avoir au moins un événement prédécesseur.");
        }
        if (tachePredecesseurList.isEmpty()) {
            throw new IllegalArgumentException("Un événement doit avoir au moins une tâche prédécesseur.");
        }
        if (evenementPredecesseurList.size() != tachePredecesseurList.size()) {
            throw new IllegalArgumentException("Le nombre de prédécesseurs doit correspondre au nombre de tâches.");
        }
        
        this.id = id;
        this.dateAuPlusTot = 0.0; // Initialisation de la date au plus tôt qui sera calculée lors de l'ordonnancement
        this.dateAuPlusTard = 0.0; // Initialisation de la date au plus tard qui sera calculée lors de l'ordonnancement
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
     * Vérifie si cet événement est critique.
     * Un événement est critique si la tâche au plus tôt et la tâche au plus tard sont égales.
     * @return true si l'événement est critique, false sinon
     */
    public boolean estCritique() {
        return dateAuPlusTot == dateAuPlusTard;
    }

    /**
     * Calcule la date au plus tôt de cet événement.
     * La date au plus tôt est déterminée en fonction des dates au plus tôt
     * des événements prédécesseurs et des durées des tâches associées.
     *
     * @return la date au plus tôt calculée pour cet événement
     */
    public double calculerDatePlusTot() {
        double maxDate = 0.0;

        // Parcourt les prédécesseurs et leurs tâches associées
        for (int i = 0; i < evenementPredecesseurList.size(); i++) {
            Evenement predecesseur = evenementPredecesseurList.get(i);
            Tache tachePredecesseur = tachePredecesseurList.get(i);

            // Calcule la date au plus tôt pour ce prédécesseur
            double datePrecedente = predecesseur.getDateAuPlusTot() + tachePredecesseur.getDuree();

            // Met à jour la date maximale
            if (datePrecedente > maxDate) {
                maxDate = datePrecedente;
            }
        }

        // Met à jour la date au plus tôt de cet événement
        this.dateAuPlusTot = maxDate;

        return dateAuPlusTot;
    }

    /**
     * Calcule la date au plus tard de cet événement.
     * La date au plus tard est déterminée en fonction des dates au plus tard
     * des événements successeurs et des durées des tâches associées.
     *
     * @param dateFinProjet la date de fin du projet
     * @return la date au plus tard calculée pour cet événement
     */
    public double calculerDatePlusTard(double dateFinProjet) {
        if (evenementSuccesseurList.isEmpty()) {
            // Si aucun successeur, la date au plus tard est la date de fin du projet
            this.dateAuPlusTard = dateFinProjet;
        } else {
            double minDate = Double.MAX_VALUE;

            // Parcourt les successeurs et leurs tâches associées
            for (int i = 0; i < evenementSuccesseurList.size(); i++) {
                Evenement successeur = evenementSuccesseurList.get(i);
                Tache tacheSuccesseur = tacheSuccesseurList.get(i);

                // Calcule la date au plus tard pour ce successeur
                double dateSuivante = successeur.getDateAuPlusTard() - tacheSuccesseur.getDuree();

                // Met à jour la date minimale
                if (dateSuivante < minDate) {
                    minDate = dateSuivante;
                }
            }

            // Met à jour la date au plus tard de cet événement
            this.dateAuPlusTard = minDate;
        }

        return dateAuPlusTard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Vérifie si les deux références pointent vers le même objet
        if (o == null || getClass() != o.getClass()) return false; // Vérifie la classe et si l'objet est null
        Evenement evenement = (Evenement) o;
        return id == evenement.id
                && evenement.getDateAuPlusTot() == getDateAuPlusTot()
                && evenement.getDateAuPlusTard() == getDateAuPlusTard()
                && evenementPredecesseurList.equals(evenement.evenementPredecesseurList)
                && tachePredecesseurList.equals(evenement.tachePredecesseurList);
    }
}