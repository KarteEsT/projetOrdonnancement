/*
 * Outils.java                                           1 juin 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

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
}
