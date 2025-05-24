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
    private ArrayList<Evenement> evenement;
    
    /**
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param evenements composant le graphe
     * @throws NullPointerException si le titre ou l'unité est null
     */
    public Graphe(String titre, String unite, ArrayList<Tache> taches, 
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
        
        for (Tache tache : taches) {
            if (tache == null) {
                throw new NullPointerException("Une tâche ne peut pas être null.");
            }
            for (Tache t : taches) {
                if (tache.equals(t)) {
                    throw new IllegalArgumentException("La tâche " + tache.getLibelle() +
                                                       " existe déjà dans le graphe.");
                }
            }
        }
        
        for (Evenement evenement : evenements) {
            if (evenement == null) {
                throw new NullPointerException("Une tâche ne peut pas être null.");
            }
            for (Evenement event : evenements) {
                if (evenement.equals(event)) {
                    throw new IllegalArgumentException("L'évènement " + event.getId() +
                                                       " existe déjà dans le graphe.");
                }
            }
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
    public ArrayList<Evenement> getEvenement() {
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
     * Ajoute une tâche au graphe.
     *
     * @param tache la tâche à ajouter
     * @throws NullPointerException si la tâche est null
     */
    public void ajouterTache(Tache tache) {
        if (tache == null) {
            throw new NullPointerException("La tâche ne peut pas être null.");
        }
    
        // Vérifie si la tâche existe déjà
        for (Tache t : getTaches()) {
            if (t.equals(tache)) {
                throw new IllegalArgumentException("La tâche existe déjà dans le graphe.");
            }
        }
        
        taches.add(tache);

    }
}