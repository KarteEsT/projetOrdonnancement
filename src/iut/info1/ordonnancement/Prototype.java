/*
 * Prototype.java                                   23 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;

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
	 * Méthode principale pour exécuter le prototype.
	 * @param args
	 */
	public static void main(String[] args) {
		// Création des tâches
		Tache tacheA = new Tache("tacheA", "Description tacheA", 3.0);
		Tache tacheB = new Tache("tacheB", "Description tacheB", 2.0);
		Tache tacheC = new Tache("tacheC", "Description tacheC", 4.0);
		Tache tacheD = new Tache("tacheD", "Description tacheD", 8.0);

		// Création des événements
		Evenement evenementInitial = new Evenement();
		Evenement evenement1 = new Evenement(1, new ArrayList<>(List.of(evenementInitial)), new ArrayList<>(List.of(tacheA)));
		Evenement evenement2 = new Evenement(2, new ArrayList<>(List.of(evenementInitial)), new ArrayList<>(List.of(tacheB)));
		Evenement evenement3 = new Evenement(3, new ArrayList<>(List.of(evenement1, evenement2)), new ArrayList<>(List.of(tacheC, tacheD)));

		// Définition des successeurs
		evenementInitial.addEvenementSuccesseur(evenement1);
		evenementInitial.addEvenementSuccesseur(evenement2);
		evenement1.addEvenementSuccesseur(evenement3);
		evenement2.addEvenementSuccesseur(evenement3);

		evenementInitial.addTacheSuccesseur(tacheA);
		evenementInitial.addTacheSuccesseur(tacheB);
		evenement1.addTacheSuccesseur(tacheC);
		evenement2.addTacheSuccesseur(tacheD);

		// Création du graphe
		ArrayList<Tache> taches = new ArrayList<>(List.of(tacheA, tacheB, tacheC, tacheD));
		ArrayList<Evenement> evenements = new ArrayList<>(List.of(evenementInitial, evenement1, evenement2, evenement3));
		Graphe graphe = new Graphe("Graphe Test", "jours", taches, evenements);

		// Calcul des dates au plus tôt
		for (Evenement evenement : evenements) {
			evenement.calculerDatePlusTot();
		}

		// Calcul de la date de fin du projet
		double dateFinProjet = graphe.calculerFinProjet();
		System.out.println("Date de fin du projet : " + dateFinProjet);

		// Calcul des dates au plus tard
		for (int i = evenements.size() - 1; i >= 0; i--) {
			evenements.get(i).calculerDatePlusTard(dateFinProjet);
		}
		
		for (Evenement evenement : evenements) {
			evenement.calculerMargeTotale();
			evenement.calculerMargeLibre();
		}

		// Affichage des dates au plus tôt et au plus tard
		System.out.println("Dates au plus tôt et au plus tard / Marge Libre et Totale:");
		for (Evenement evenement : evenements) {
			System.out.println("Événement " + evenement.getId() + " :");
			System.out.println("  Date au plus tôt : " + evenement.getDateAuPlusTot());
			System.out.println("  Date au plus tard : " + evenement.getDateAuPlusTard());
			System.out.println("  Marge libre : " + evenement.getMargeLibre());
			System.out.println("  Marge totale : " + evenement.getMargeTotale());
		}

		// Recherche du chemin critique
		System.out.println("Chemin critique :");
		for (Evenement evenement : evenements) {
			if (evenement.estCritique()) {
				System.out.println("Événement " + evenement.getId() + " est critique.");
			}
		}

		// Test de la méthode existeCircuit
		boolean circuitExiste = graphe.existeCircuit();
		System.out.println("\nCircuit existant : " + circuitExiste);

		graphe.trierTaches();
		System.out.println("Tâches triées dans le graphe : " + graphe.toString());

		//------------------------------------------------------------------------//

		//Essai tache requise pas dans graphe
		//Graphe graphe2 = new Graphe("Graphe Test 2", "jours", new ArrayList<>(), new ArrayList<>());
		//Tache t1 = new Tache("T1", "Description T1", 3);
		//Tache t2 = new Tache("T2", "Description T2", 2);

		//t1.ajouterTacheRequise(t2); // t2 n'est pas dans le graphe)

		//graphe2.ajouterPlusieursTaches(t2,t1);

	}
}