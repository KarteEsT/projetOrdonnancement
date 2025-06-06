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
            double datePrecedente = predecesseur.getDateAuPlusTot() 
                                    + tachePredecesseur.getDuree();

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
     * Calcule la date au plus tard d'un événement.
     * La date au plus tard est déterminée en fonction des dates au plus tard
     * des événements successeurs et des durées des tâches associées.
     *
     * @param dateFinProjet la date de fin du projet
     * @return la date au plus tard calculée pour cet événement
     */
    public double calculerDatePlusTard(double dateFinProjet) {
        if (   evenementSuccesseurList == null || 
               evenementSuccesseurList.isEmpty()) {
            // Si aucun successeur, date au plus tard = fin du projet
            this.dateAuPlusTard = dateFinProjet;
        } else {
            double minDate = Double.MAX_VALUE;

            // Parcourt les successeurs et leurs tâches associées
            for (int i = 0; i < evenementSuccesseurList.size(); i++) {
                Evenement successeur = evenementSuccesseurList.get(i);
                Tache tacheSuccesseur = tacheSuccesseurList.get(i);

                // Calcule la date au plus tard pour ce successeur
                double dateSuivante = successeur.getDateAuPlusTard() 
                                      - tacheSuccesseur.getDuree();

                // Met à jour la date minimale
                if (dateSuivante < minDate) {
                    minDate = dateSuivante;
                }
            }

            this.dateAuPlusTard = minDate;
        }

        return dateAuPlusTard;
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
     * Cette méthode trouve tous les chemins critiques 
     * à partir d'un événement initial. 
     * @param evenementInitial
     * @return une liste de chemins critiques
     */
    public static List<List<Evenement>> trouverCheminsCritiques(Evenement
                                                         evenementInitial) {
        List<List<Evenement>> cheminsCritiques = new ArrayList<>();
        List<Evenement> cheminActuel = new ArrayList<>();
        List<Evenement> visites = new ArrayList<>();

        parcourirCheminCritique(evenementInitial, cheminActuel, 
                                cheminsCritiques, visites);
        return cheminsCritiques;
    }
}
