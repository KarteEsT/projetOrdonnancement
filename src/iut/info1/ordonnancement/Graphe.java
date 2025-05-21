/*
 * Graphe.java                                           14 mai 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

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
    private Tache[] taches;
    
    /** Ensemble des différents événement du graphe */
    private Evenement[] evenement;
    
    
	/**
	 * Constructeur par défaut
	 * @param titre du graphe
	 * @throws NullPointerException si le titre est null
	 */
    public Graphe(String titre) {
		if (titre == null) {
			throw new NullPointerException("Le titre ne peut pas être null.");
		}
    	this.titre = titre;
    }
    
    /**
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param evenements composant le graphe
     * @throws NullPointerException si le titre ou l'unité est null
     */
    public Graphe(String titre, String unite, Tache[] taches, 
                  Evenement[] evenements) {
		if (titre == null) {
			throw new NullPointerException("Le titre ne peut pas être null.");
		}
		if (unite == null) {
			throw new NullPointerException("L'unité ne peut pas être null.");
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
    public Tache[] getTaches() {
        return taches;
    }

    /**
     * @return nouvelle valeur de évènement
     */
    public Evenement[] getEvenement() {
        return evenement;
    }
    
    /**
     * Calcule la date au plus tôt de chaque événement
     * @return date au plus tôt
     */
    public double calculerDatePlusTot() {
        return 0.0;
    }
    
    /**
     * Calcule la date au plus tard de chaque événement
     * @return date au plus tard
     */
    public double calculerDatePlusTard() {
        return 0.0;
    }
    
    /**
     * Calcule la date de fin de projet
     * @return date de fin de projet
     */
    public double calculerFinProjet() {
        return 0.0;
    }
    
}