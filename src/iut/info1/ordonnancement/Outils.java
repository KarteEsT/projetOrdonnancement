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
     // S'assurer que les tâches sont triées topologiquement avant de créer les événements dans le main
        for (Evenement evenement : graphe.getEvenements()) {
            double maxDatePredecesseurs = 0.0;

            // Trouver la date maximale parmi tous les événements prédécesseurs
            for (Evenement predecesseur : evenement.getEvenementPredecesseurList()) {
                if (predecesseur.getDateAuPlusTot() > maxDatePredecesseurs) {
                    maxDatePredecesseurs = predecesseur.getDateAuPlusTot();
                }
            }

            // Obtenir la durée de la seule tâche qui se termine à cet événement
            double dureeTache = 0.0;
            if (!evenement.getTachePredecesseurList().isEmpty()) {
                // Un événement est le résultat de sa ou ses tâches prédécesseurs
                dureeTache = evenement.getTachePredecesseurList().get(0).getDuree();
            }

            evenement.setDatePlusTot(maxDatePredecesseurs + dureeTache);
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
        double dateFinProjet = calculerFinProjet(graphe);
        List<Evenement> evenements = graphe.getEvenements();

        // Initialiser toutes les dates à la date de fin du projet
        for (Evenement evenement : evenements) {
            evenement.setDatePlusTard(dateFinProjet);
        }

        // Itérer à l'envers sur la liste d'événements (supposée triée topologiquement)
        for (int i = evenements.size() - 1; i >= 0; i--) {
            Evenement evenement = evenements.get(i);

            // Pour chaque tâche qui COMMENCE à cet événement
            for (Tache tacheSuccesseur : evenement.getTacheSuccesseurList()) {

                // Trouver l'événement où cette tâche successeur se TERMINE
                Evenement evenementFinTache = null;
                for(Evenement e : graphe.getEvenements()) {
                    if(e.getTachePredecesseurList().contains(tacheSuccesseur)) {
                        evenementFinTache = e;
                        break;
                    }
                }

                if (evenementFinTache != null) {
                     // La date au plus tard de l'événement actuel est influencée par la date au plus tard de son successeur
                    double nouvelleDatePossible = evenementFinTache.getDateAuPlusTard() - tacheSuccesseur.getDuree();
                    if (nouvelleDatePossible < evenement.getDateAuPlusTard()) {
                        evenement.setDatePlusTard(nouvelleDatePossible);
                    }
                }
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
    
    /**
     * Calcule les marges libres pour toutes les tâches d'un graphe.
     * La marge libre d'une tâche est le délai maximum qu'elle peut prendre
     * sans retarder la date de début au plus tôt de ses successeurs.
     *
     * @param graphe Le graphe contenant les tâches et les événements.
     */
    public static void calculerMargesLibres(Graphe graphe) {
        double dateFinProjet = calculerFinProjet(graphe);

        for (Tache tache : graphe.getTaches()) {
            // 1. Trouver l'événement de fin de la tâche actuelle
            Evenement eventFinTache = null;
            for (Evenement e : graphe.getEvenements()) {
                if (e.getTachePredecesseurList().contains(tache)) {
                    eventFinTache = e;
                    break;
                }
            }

            if (eventFinTache == null) continue;

            double dateFinAuPlusTotTache = eventFinTache.getDateAuPlusTot();

            // 2. Trouver la date de début au plus tôt minimale de toutes les tâches successeurs
            double minDateDebutSuccesseurs = dateFinProjet; // Initialiser avec la fin du projet

            if (eventFinTache.getTacheSuccesseurList().isEmpty()) {
                // Si pas de successeur, la marge libre va jusqu'à la fin du projet
                minDateDebutSuccesseurs = dateFinProjet;
            } else {
                for (Tache tacheSuccesseur : eventFinTache.getTacheSuccesseurList()) {
                    // Trouver l'événement de fin de la tâche successeur
                    Evenement eventFinSuccesseur = null;
                    for (Evenement e : graphe.getEvenements()) {
                        if (e.getTachePredecesseurList().contains(tacheSuccesseur)) {
                            eventFinSuccesseur = e;
                            break;
                        }
                    }
                    if (eventFinSuccesseur == null) continue;

                    // Calculer la date de DEBUT au plus tôt du successeur
                    double dateDebutSuccesseur = eventFinSuccesseur.getDateAuPlusTot() 
                                               - tacheSuccesseur.getDuree();
                    
                    if (dateDebutSuccesseur < minDateDebutSuccesseurs) {
                        minDateDebutSuccesseurs = dateDebutSuccesseur;
                    }
                }
            }

            // 3. Calculer et stocker la marge libre
            double margeLibre = minDateDebutSuccesseurs - dateFinAuPlusTotTache;
            
            // Assigner la marge à la tâche (nécessite d'ajouter un setter dans Tache.java)
            // Par exemple : tache.setMargeLibre(margeLibre); 
            // Pour l'instant, nous allons l'afficher directement.
            // Vous devrez modifier la classe Tache pour stocker cette valeur.
        }
    }

}
