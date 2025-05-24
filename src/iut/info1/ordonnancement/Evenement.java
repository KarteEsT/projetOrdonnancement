/*
 * Evenement.java                                                   14 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import iut.info1.ordonnancement.Tache;

import java.util.ArrayList;

/**
 * Cette classe est utilisée pour créer des événements
 * Les événements sont ensuite utilisées dans la classe Graphe
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
	private double tacheAuPlusTot;
	
	/** Tâche au plus tard d'un événement */
	private double tacheAuPlusTard;
	
	/** Ensemble des événements prédécesseurs de l'événement */
	private ArrayList<Evenement> evenementPredecesseurList = new ArrayList<>();
	
	/** Ensemble des événements successeurs de l'événement */
	private ArrayList<Evenement> evenementSuccesseurList = new ArrayList<>();
	
	/** Liste des tâches associées à cet événement */
	private ArrayList<Tache> taches = new ArrayList<>();
	
	/** 
	 * Déclaration de l'événement initial pour le début
	 * du graphique d'ordonnancement
	 */
	private Evenement event_initial;
	
	/**
	 * Constructeur par défaut
	 * @param event_initial Evenement initial
	 */
	public Evenement(Evenement event_initial) {
		this.event_initial = event_initial;
	}
	
	/** Constructeur d'un événement
	 * @param id Identifiant de l'événement
	 * @param tacheAuPlusTot Tâche au plus tôt d'un événement
	 * @param tacheAuPlusTard Tâche au plus tard d'un événement
	 * @param evenementPredecesseur Ensemble des événements prédécesseurs
	 */
	public Evenement(int id, double tacheAuPlusTot, double tacheAuPlusTard,
			ArrayList<Evenement> evenementPredecesseurList) {
		this.id = id;
		this.tacheAuPlusTot = tacheAuPlusTot;
		this.tacheAuPlusTard = tacheAuPlusTard;
		this.evenementPredecesseurList = evenementPredecesseurList;
		
	}
	
	/** 
	 * Retourne l'identifiant d'un événement 
	 * @return id Identifiant de l'événement
	 */
	public int getId() {
		return id;
	}
	
	/** 
	 * Retourne la tache au plus tôt d'un événement 
	 * @return tacheAuPlusTot Tâche au plus tôt d'un événement
	 */
	public double getTacheAuPlusTot() {
		return tacheAuPlusTot;
	}
	
	/** 
	 * Retourne la tâche au plus tard d'un événement 
	 * @return tacheAuPlusTard Tâche au plus tard d'un événement
	 */
	public double getTacheAuPlusTard() {
		return tacheAuPlusTard;
	}
	
	/**
	 * Retourne les tâches associées à cet événement.
	 * @return une liste de tâches.
	 */
	public ArrayList<Tache> getTaches() {
	    return taches;
	}

	/**
	 * Ajoute une tâche à cet événement.
	 * @param tache la tâche à ajouter.
	 */
	public void ajouterTache(Tache tache) {
	    taches.add(tache);
	}
	
	/**
	 * Retourne les événements prédécesseurs d'un événement 
	 * @return evenementPredecesseur Ensemble des événements prédécesseurs
	 */
	public ArrayList<Evenement> getEvenementPredecesseurList() {
		return evenementPredecesseurList;
	}
	
	/**
	 * Retourne l'événement initial du graphe 
	 * @return event_initial Événement initial
	 */
	public Evenement getEvenementInitial() {
		return event_initial;
	}
	
	/**
	 * Cette méthode permet de savoir si un événement est critique.
	 * Un événement est critique si la tâche au plus tôt et la tâche au plus tard
	 * sont égales.
	 * @return true si l'événement est critique
	 *         false si l'événement n'est pas critique
	 */
	public boolean estCritique() {
		if (tacheAuPlusTot == tacheAuPlusTard) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
    public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Evenement))
			return false;
		Evenement evenement = (Evenement) o;
		return id == evenement.id && Double.compare(evenement.tacheAuPlusTot, tacheAuPlusTot) == 0
				&& Double.compare(evenement.tacheAuPlusTard, tacheAuPlusTard) == 0;
	}
	
	@Override
    public int hashCode() {
		int result = Integer.hashCode(id);
        result = 31 * result + Double.hashCode(tacheAuPlusTot);
        result = 31 * result + Double.hashCode(tacheAuPlusTard);
        return result;
	}
	
	/**
	 * Calcule la date au plus tôt de chaque événement.
	 * @return la date au plus tôt de cet événement.
	 */
	public double calculerDatePlusTot() {
	    if (evenementPredecesseurList.isEmpty()) {
	        // Si l'événement n'a pas de prédécesseurs, sa date au plus tôt est 0.0
	        tacheAuPlusTot = 0.0;
	    } else {
	        double maxDate = 0.0; // Initialisation à 0
	        for (Evenement predecesseur : evenementPredecesseurList) {
	            // Calcul de la date au plus tôt pour chaque prédécesseur
	            double datePlusTotPredecesseur = predecesseur.calculerDatePlusTot();
	            if (datePlusTotPredecesseur > maxDate) {
	                maxDate = datePlusTotPredecesseur;
	            }
	        }
	        tacheAuPlusTot = maxDate;
	    }
	    return tacheAuPlusTot;
	}
    
	/**
	 * Calcule la date au plus tard de chaque événement.
	 * @param dateFinProjet la date de fin du projet (nécessaire pour le dernier événement).
	 * @return la date au plus tard de cet événement.
	 */
	public double calculerDatePlusTard(double dateFinProjet) {
	    if (evenementSuccesseurList.isEmpty()) {
	        // Si l'événement est le dernier, sa date au plus tard est la date de fin du projet.
	        tacheAuPlusTard = dateFinProjet;
	    } else {
	        double minDate = Double.MAX_VALUE; // Initialisation à une valeur très grande
	        for (Evenement successeur : evenementSuccesseurList) {
	            // Calcul de la durée totale des tâches du successeur
	            double dureeTotale = 0;
	            for (Tache tache : successeur.getTaches()) {
	                dureeTotale += tache.getDuree();
	            }
	            // Calcul de la date au plus tard pour ce successeur
	            double datePlusTardSuccesseur = successeur.calculerDatePlusTard(dateFinProjet) - dureeTotale;
	            // Mise à jour du minimum
	            if (datePlusTardSuccesseur < minDate) {
	                minDate = datePlusTardSuccesseur;
	            }
	        }
	        tacheAuPlusTard = minDate;
	    }
	    return tacheAuPlusTard;
	}
    
	/**
	 * Calcule la date de fin du projet.
	 * @return la date de fin du projet.
	 */
	public double calculerFinProjet() {
	    // La date de fin du projet est la date au plus tôt du dernier événement.
	    return calculerDatePlusTot();
	}
}