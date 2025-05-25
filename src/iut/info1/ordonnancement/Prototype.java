/*
 * Prototype.java                                   23 mai 2025
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
     * TODO commenter le rôle de cette méthode (SRP)
     * @param args
     */
    public static void main(String[] args) {
        // Création des tâches
        Tache tache1 = new Tache("T1", "Description T1", 3);
        Tache tache2 = new Tache("T2", "Description T2", 2);
        Tache tache3 = new Tache("T3", "Description T3", 1);
        Tache tache4 = new Tache("T4", "Description T4", 4);

        // Définition des dépendances (T2 dépend de T1, T3 dépend de T2)
        tache2.getTachesRequises().add(tache1);
        tache3.getTachesRequises().add(tache2);

        // Ajout d'une dépendance circulaire pour tester (T1 dépend de T3)
        tache1.getTachesRequises().add(tache2);

        // Création de la liste des tâches
        ArrayList<Tache> taches = new ArrayList<>();
        taches.add(tache1);
        taches.add(tache2);
        taches.add(tache3);
        taches.add(tache4);

        // Création du graphe
        Graphe2Test graphe = new Graphe2Test("Graphe Test", "jours", taches, new ArrayList<>());

        // Test de la méthode existeCircuit
        boolean circuitExiste = graphe.existeCircuit();
        System.out.println("Un circuit existe : " + circuitExiste);
    }
    
    
    
    
    /**
     * Méthode principale
     * @param args non utilisé
     */
    /*
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
    */
}