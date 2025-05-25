/*
 * Main.java                                   23 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;

/**
 * Permet de lancer l'application
 * 
 * @author Gabriel Le Goff
 * @author Gabriel Robache
 * @author Roveri Esteban
 * @author Sauvaire Léo
 * @author Massicard Maël
 */
public class Prototype {
    
    /**
     * Méthode principale
     * @param args non utilisé
     */
    public static void main(String[] args) {
        
        ChargeurConsole chargeur = new ChargeurConsole();
        ArrayList<Tache> toutesLesTaches = new ArrayList<>();
        Graphe2Test graphe = new Graphe2Test(null, null, null, null); // TODO créer un constructeur par défaut pour graphe

        // 1. Saisie utilisateur sans nombre fixe, arrêt sur "fin" ou vide
        while (true) {
            System.out.println("Entrez le nom de la tâche (ou 'fin' pour terminer) :");
            String nom = chargeur.demandeNomTache();
            if (nom.equalsIgnoreCase("fin") || nom.isEmpty()) {
                break;
            }

            String description = chargeur.demandeDescriptionTache();
            double duree = chargeur.demandeDureeTache();
            String[] nomsPredecesseurs = chargeur.demandePredecesseursTache();

            // Cherche les tâches prédécesseurs dans la liste
            ArrayList<Tache> predecesseurs = new ArrayList<>();
            for (String nomPrecedent : nomsPredecesseurs) {
                for (Tache tache : toutesLesTaches) {
                    if (tache.getLibelle().equals(nomPrecedent)) {
                        predecesseurs.add(tache);
                    }
                }
            }

            // Créer la tâche et l’ajouter à la liste
            Tache nouvelleTache = new Tache(nom, description, duree, predecesseurs);
            toutesLesTaches.add(nouvelleTache);
            graphe.getTaches().add(nouvelleTache);
        }

        // 2. Créer les événements (début et fin pour chaque tâche)
        int idEvent = 0;
        ArrayList<Evenement> debuts = new ArrayList<>();
        ArrayList<Evenement> fins = new ArrayList<>();

        for (Tache tache : toutesLesTaches) {
            Evenement eventDebut = new Evenement(idEvent++);
            Evenement eventFin = new Evenement(idEvent++);

            eventDebut.ajouterSuccesseur(eventFin, tache); // tache entre début et fin

            debuts.add(eventDebut);
            fins.add(eventFin);

            graphe.getEvenement().add(eventDebut);
            graphe.getEvenement().add(eventFin);
        }

        // 3. Créer les liaisons entre tâches via événements
        for (int i = 0; i < toutesLesTaches.size(); i++) {
            Tache tacheCourante = toutesLesTaches.get(i);
            Evenement debutTache = debuts.get(i);

            for (Tache tachePrec : tacheCourante.getTachesRequises()) {
                int indexPrec = toutesLesTaches.indexOf(tachePrec);
                Evenement finPrec = fins.get(indexPrec);

                // On crée un arc direct entre la fin du prédécesseur
                // et le début de la tâche courante, sans créer de tâche liaison intermédiaire
                finPrec.ajouterSuccesseur(debutTache, null);
            }
        }

        // 4. Affichage des événements
        for (Evenement event : graphe.getEvenement()) {
            System.out.println("Événement " + event.getId() +
                " | Tôt : " + event.calculerDatePlusTot() +
                " | Tard : " + event.calculerDatePlusTard() +
                " | Critique : " + event.estCritique());
        }
    }
}