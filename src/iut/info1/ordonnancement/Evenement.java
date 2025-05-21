/*
 * Evenement.java                                                   14 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

/**
 * Cette classe est utilisée pour créer des événements
 * Les événements sont ensuite utilisées dans la classe Graphe
 * @author Gabriel Robache
 * @author Gabriel Le Goff
 * @author Mael Massicard
 * @author Esteban Roveri
 * @author Léo Sauvaire
 * @version 1.0
 */
public class Evenement {
	
	/** Identifiant de l'événement */
	private int id;
	
	/** Tâche au plus tôt d'un événement */
	private double tacheAuPlusTot;
	
	/** Tâche au plus tard d'un événement */
	private double tacheAuPlusTard;
	
	/** Ensemble des événements prédécesseurs de l'événement */
	private Evenement[] evenementPredecesseur;
	
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
				Evenement[] evenementPredecesseur) {
		this.id = id;
		this.tacheAuPlusTot = tacheAuPlusTot;
		this.tacheAuPlusTard = tacheAuPlusTard;
		this.evenementPredecesseur = evenementPredecesseur;
		
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
	public double getTacheAusPlusTard() {
		return tacheAuPlusTard;
	}
	
	/**
	 * Retourne les événements prédécesseurs d'un événement 
	 * @return evenementPredecesseur Ensemble des événements prédécesseurs
	 */
	public Evenement[] getEvenementPredecesseur() {
		return evenementPredecesseur;
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
}