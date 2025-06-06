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
    
    /** Le libellé de la tache */
    private String libelle;
    
    /** La description de la tâche */
    private String description;
    
    /** La durée estimée de la tâche */
    private double duree;
    
    /** La marge libre de la tâche */
    private double margeLibre;
    
    /** La marge totale de la tâche */
    private double margeTotale;
    
    /** Les tâches qui doivent être terminées avant celle-ci */
    private ArrayList<Tache> tachesRequises;

    /**
     * Construit une tâche avec un libellé, une description, 
     * une durée, et une liste de tâches requises.
     * Initialise tous les attributs de la tâche.
     *
     * @param libelle le nom de la tâche
     * @param description une brève explication de la tâche
     * @param duree le temps estimé pour réaliser la tâche
     * @param tachesRequises les tâches devant être terminées avant celle-ci
     */
    public Tache(String libelle, String description, double duree, 
                 ArrayList<Tache> tachesRequises) {
        if (libelle == null || libelle.isEmpty() || libelle.strip().isEmpty()) {
            throw new IllegalArgumentException("Le libellé de la tâche ne " +
                                               "peut pas être vide.");
        }
        if (description == null || description.isEmpty() || 
                                   description.strip().isEmpty()) {
            throw new IllegalArgumentException("La description de la tâche ne" +
                                               " peut pas être vide.");
        }
        if (duree < 0) {
            throw new IllegalArgumentException("La durée de la tâche doit " + 
                                               "être positive ou nulle.");
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
     * @return la marge libre de la tâche
     */
    public double getMargeLibre() {
        return margeLibre;
    }

    /**
     * @return la marge totale de la tâche
     */
    public double getMargeTotale() {
        return margeTotale;
    }

    /**
     * @return un ArrayList<Tache> des tâches à réaliser avant celle-ci
     */
    public ArrayList<Tache> getTachesRequises() {
        return tachesRequises;
    }
    
    //ajouterTache
    
    /**
     * ajoute une tache aux taches requises de la tache courante.
     * @param tache à ajouter aux tâches requises
     */
    public void ajouterTacheRequise(Tache tache) {
        if (getTachesRequises() == null) {
            tachesRequises = new ArrayList<>();
        }
        if (getTachesRequises().contains(tache)) {
            throw new IllegalArgumentException("La tâche " + libelle + 
                                " est déjà dans la liste des tâches requises.");
        }
        getTachesRequises().add(tache);
    }
    
    //TODO réfléchir ci-dessous
    // Peut-être renvoyer un boolean pour indiquer si la tâche a été supprimée ou non 
    
    /**
     * Calcule la marge libre d'une tâche. La marge libre est la
     * différence entre la date au plus tôt de 
     * l'événement successeur et la date au
     * plus tôt de l'événement prédécesseur, moins la durée de la tâche.
     * @param evenementPredecesseur 
     * @param evenementSuccesseur 
     * @return la marge totale
     */
    public double calculerMargeLibre(Evenement evenementPredecesseur, 
                                     Evenement evenementSuccesseur) {
        this.margeLibre = evenementSuccesseur.getDateAuPlusTot() - 
                          evenementPredecesseur.getDateAuPlusTot() - this.duree;
        return margeLibre;
    }
    
	/**
	 * Calcule la marge totale d'une tâche. La marge totale est la
	 * différence entre la date au plus tard de 
	 * l'événement successeur et la date au
	 * plus tôt de l'événement prédécesseur, moins la durée de la tâche.
	 * @param evenementPredecesseur 
	 * @param evenementSuccesseur 
	 * @return la marge totale
	 */
    public double calculerMargeTotale(Evenement evenementPredecesseur, 
                                      Evenement evenementSuccesseur) {
        this.margeTotale = evenementSuccesseur.getDateAuPlusTard() - 
                          evenementPredecesseur.getDateAuPlusTot() - this.duree;
        return margeTotale;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Tache))
            return false;
        Tache other = (Tache) obj;
        return libelle.equals(other.libelle);
    }
    
    @Override
    public String toString() {
        String tache = getLibelle();
        
        return tache;
    }   
}
