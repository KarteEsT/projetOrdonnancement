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
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param evenements composant le graphe
     */
    public Graphe(String titre, String unite, Taches[] taches, 
                  Evenement[] evenements) {
        
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
     * @return nouvelle valeur de evenement
     */
    public Evenement[] getEvenement() {
        return evenement;
    }
    
    /**
     * Calcule la date au plus tot de chaque événement
     * @return date au plus tot
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
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return le(s) chemin(s) critique(s)
     */
    public double calculerCheminCritique() {
        return 0.0;
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la marge libre
     */
    public double calculerMargeLibre() {
        return 0.0;
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     * @return la marge totale
     */
    public double calculerMargeTotale() {
        return 0.0;
    }
    //FIN TEST HISTORIQUE GIT 
}
