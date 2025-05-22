/*
 * Tache.java                                   14 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.Arrays;
import java.util.Objects;


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
    
    //TODO : Ajouter des commentaires pour chaque attribut
    private String libelle;
    private String description;
    private double duree;
    private Tache[] tachesRequises;

    /**
     * Construit une tâche avec un libellé, une description, une durée, et une liste de tâches requises.
     * Initialise tous les attributs de la tâche.
     *
     * @param libelle le nom de la tâche
     * @param description une brève explication de la tâche
     * @param duree le temps estimé pour réaliser la tâche
     * @param tachesRequises les tâches devant être terminées avant celle-ci
     */
    public Tache(String libelle, String description, double duree, Tache[] tachesRequises) {
        this.libelle = libelle;
        this.description = description;
        this.duree = duree;
        this.tachesRequises = tachesRequises;
    }

    /**
     * Construit une tâche sans tâches requises.
     * Initialise la tâche avec un libellé, une description et une durée,
     * et une liste vide pour les tâches requises.
     *
     * @param libelle le nom de la tâche
     * @param description une brève explication de la tâche
     * @param duree le temps estimé pour réaliser la tâche
     */
    public Tache(String libelle, String description, double duree) {
        this(libelle, description, duree, new Tache[0]);
    }

    /**
     * Fournit le libellé (nom) de la tâche.
     * Respecte le principe SRP en donnant un accès contrôlé à l'attribut `libelle`.
     *
     * @return le libellé de la tâche
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Fournit la description de la tâche.
     * Respecte le principe SRP en donnant un accès contrôlé à l'attribut `description`.
     *
     * @return la description de la tâche
     */
    public String getDescription() {
        return description;
    }

    /**
     * Fournit la durée de la tâche.
     * Respecte le principe SRP en donnant un accès contrôlé à l'attribut `duree`.
     *
     * @return la durée estimée de la tâche
     */
    public double getDuree() {
        return duree;
    }

    /**
     * Fournit la liste des tâches requises pour exécuter cette tâche.
     * Respecte le principe SRP en donnant un accès contrôlé à l'attribut `tachesRequises`.
     *
     * @return un tableau des tâches à réaliser avant celle-ci
     */
    public Tache[] getTachesRequises() {
        return tachesRequises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tache)) return false;
        Tache tache = (Tache) o;
        return Double.compare(tache.duree, duree) == 0 &&
               Objects.equals(libelle, tache.libelle) &&
               Objects.equals(description, tache.description) &&
               Arrays.equals(tachesRequises, tache.tachesRequises);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(libelle, description, duree);
        result = 31 * result + Arrays.hashCode(tachesRequises);
        return result;
    }
}
