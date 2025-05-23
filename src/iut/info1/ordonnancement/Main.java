/*
 * Main.java                                   23 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

/**
 * Permet de lancer l'application
 * @author Esteban
 */
public class Main {
    
    /**
     * Méthode principale
     * @param args non utilisé
     */
    public static void main(String[] args) {
        
        ChargeurConsole chargeur = new ChargeurConsole();

        Graphe graphe = new Graphe("Graphe Test");

        String nom = chargeur.demandeNomTache();
        String description = chargeur.demandeDescriptionTache();
        double duree = chargeur.demandeDureeTache();
        String[] nomsPredecesseurs = chargeur.demandePredecesseursTache();

        Tache nouvelleTache = new Tache(nom, description, duree);
        
        graphe.ajouterTache(nouvelleTache);

        System.out.println("Tâche ajoutée au graphe : " + nouvelleTache.getLibelle());
        System.out.println("Nombre total de tâches dans le graphe : " + graphe.getTaches());
        
        

    }
}