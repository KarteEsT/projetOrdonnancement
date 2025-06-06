/*
 * Outils.java                                           1 juin 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe contient des méthodes utilitaires pour l'ordonnancement.
 */
public class Outils {
    
    /**
     * Calcule les dates au plus tôt pour tous les événements dans un graphe
     * d'ordonnancement. La date au plus tôt d'un événement est déterminée par
     * la date au plus tôt de ses prédécesseurs et la durée des tâches associées
     *
     * @param graphe le graphe contenant les événements
     */
    public static void calculerDatesAuPlusTot(Graphe graphe) {
        // Parcourt tous les événements du graphe
        for (Evenement evenement : graphe.getEvenements()) {
            double maxDate = 0.0;

            // Calcule la date au plus tôt en fonction des prédécesseurs
            for (int i = 0; i < evenement.getEvenementPredecesseurList().size();
                                                                          i++) {
                Evenement predecesseur = evenement
                                        .getEvenementPredecesseurList().get(i);
                Tache tache = evenement.getTachePredecesseurList().get(i);

                double datePrecedente = predecesseur.getDateAuPlusTot() 
                                        + tache.getDuree();
                if (datePrecedente > maxDate) {
                    maxDate = datePrecedente;
                }
            }

            // Met à jour la date au plus tôt de l'événement courant
            evenement.setDatePlusTot(maxDate);
        }
    }

    
    /**
     * Calcule la date de fin de projet.
     * La date de fin de projet correspond à la date au plus tôt
     * de l'unique événement sans successeur.
     * @param graphe 
     *
     * @return la date de fin du projet
     */
    public static double calculerFinProjet(Graphe graphe) {
        double dateFinProjet = 0.0;
        for (Evenement evenement : graphe.getEvenements()) {
            if (evenement.getDateAuPlusTot() > dateFinProjet) {
                dateFinProjet = evenement.getDateAuPlusTot();
            }
        }

        return dateFinProjet;
    }    

    /**
     * Calcule les dates au plus tard pour tous les événements dans un graphe
     * d'ordonnancement. La date au plus tard d'un événement est déterminée par
     * la date au plus tard de ses successeurs et la durée des tâches associées.
     *
     * @param graphe le graphe contenant les événements
     */
    public static void calculerDatesAuPlusTard(Graphe graphe) {
        
        double dateFinProjet = graphe.calculerFinProjet();
        
        // Étape 1 : parcourir les événements en ordre inverse
        List<Evenement> evenements = graphe.getEvenements();
        for (int i = evenements.size() - 1; i >= 0; i--) {
            Evenement evenement = evenements.get(i);

            if (evenement.getEvenementSuccesseurList().isEmpty()) {
                // Aucun successeur => date au plus tard = fin du projet
                evenement.setDatePlusTard(dateFinProjet);
            } else {
                double minDate = Double.MAX_VALUE;

                // Parcourt les successeurs et leurs tâches associées
                for (int j = 0; j < evenement.getEvenementSuccesseurList()
                                                                 .size(); j++) {
                    Evenement successeur = evenement
                                           .getEvenementSuccesseurList().get(j);
                    Tache tache = evenement.getTacheSuccesseurList().get(j);

                    double dateSuivante = successeur.getDateAuPlusTard() 
                                            - tache.getDuree();
                    if (dateSuivante < minDate) {
                        minDate = dateSuivante;
                    }
                }

                // Met à jour la date au plus tard
                evenement.setDatePlusTard(minDate);
            }
        }
    }

    
    /**
     * Parcourt récursivement les événements pour trouver
     * les chemins critiques.
     * @param evenement l'événement actuel à explorer
     * @param cheminActuel 
     * @param cheminsCritiques 
     * @param visites 
     */
    public static void parcourirCheminCritique(Evenement evenement, 
            List<Evenement> cheminActuel,List<List<Evenement>> cheminsCritiques,
            List<Evenement> visites) {
        // Ajouter l'événement actuel au chemin
        cheminActuel.add(evenement);
        visites.add(evenement);

        // Vérifier si l'événement n'a pas de successeurs critiques
        boolean aucunSuccesseurCritique = true;
        for (Evenement successeur : evenement.getEvenementSuccesseurList()) {
            if (successeur.estCritique() && !visites.contains(successeur)) {
                aucunSuccesseurCritique = false;
                parcourirCheminCritique(successeur, cheminActuel, 
                                        cheminsCritiques, visites);
            }
        }

        //Aucun successeur critique => ajout chemin actuel aux chemins critiques
        if (aucunSuccesseurCritique) {
            cheminsCritiques.add(new ArrayList<>(cheminActuel));
        }

        // Retirer l'événement actuel pour revenir en arrière
        cheminActuel.remove(cheminActuel.size() - 1);
        visites.remove(visites.size() - 1);
    }
    
    /**
     * Trouve les chemins critiques dans un graphe d'ordonnancement.
     * @param graphe
     * @return la liste des chemins critiques
     */
    public static List<List<Evenement>> trouverCheminsCritiques(Graphe graphe) {
        List<List<Evenement>> cheminsCritiques = new ArrayList<>();

        for (Evenement evenement : graphe.getEvenements()) {
            // On commence uniquement depuis l'event initial
            if (evenement.getEvenementPredecesseurList().isEmpty() 
                                                   && evenement.estCritique()) {
                parcourirCheminCritique(evenement, new ArrayList<>(),
                                           cheminsCritiques, new ArrayList<>());
            }
        }

        return cheminsCritiques;
    }

}
