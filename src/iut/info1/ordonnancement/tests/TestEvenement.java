/*
 * TestEvenement.java                                                       16 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement.tests;

import iut.info1.ordonnancement.Evenement;
import iut.info1.ordonnancement.Tache;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Evenement.
 * Elle vérifie le bon fonctionnement des méthodes et des validations
 * dans la classe Evenement.
 */
public class TestEvenement {
	
	//------------ Test Constructeurs -------------//
	
	@Test
	void testConstructeurEvenementCorrect() {
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();
		
		Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
		
		evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
		tachesPredecesseurs.add(tache1);
		
		Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
		
		assertEquals(Evenement.EVENEMENT_INITIAL, evenement1.getEvenementPredecesseurList().get(0));
	}
	
	@Test
	void testConstructeurEvenementIncorrect() {
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

		Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

		tachesPredecesseurs.add(tache1);
		// Je n'ajoute pas l'événement initial dans la liste des événements prédécesseurs pour créer une erreur
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
		});
	}
	
	@Test
	void testConstructeurTacheIncorrect() {
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

		evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
		// Je n'ajoute pas de tache prédécesseur pour créer une erreur.
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
		});
	}
	
	@Test
	void testConstructeurEvenementTacheIncorrect() {
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

		Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
		Tache tache2 = new Tache("Tâche 2", "Description de la tâche 2", 3.0);

		evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
		tachesPredecesseurs.add(tache1);
		tachesPredecesseurs.add(tache2);

		assertThrows(IllegalArgumentException.class, () -> {
			new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
		});
	}
	
	//------------ Test des getters -------------//
	
	@Test
	void testGetId() {
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

		Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

		evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
		tachesPredecesseurs.add(tache1);

		Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

		assertEquals(0, Evenement.EVENEMENT_INITIAL.getId());
		assertEquals(1, evenement1.getId());
	}
	
	@Test
	void testGetDateAuPlusTot() {
		fail();
	}
	
	@Test
	void testGetDateAuPlusTard() {
		fail();
	}
	
	@Test
    void testGetEvenementPredecesseurList() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();
        
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
        
        tachesPredecesseurs.add(tache1);
        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        
        assertEquals(1, evenement1.getEvenementPredecesseurList().size());
        assertTrue(evenement1.getEvenementPredecesseurList().contains(Evenement.EVENEMENT_INITIAL));
    }
	
	//------------ Test des méthodes -------------//
	
    @Test
    void testEstCritique() {
    	fail();
    }
    
    @Test
    void testCalculerDatePlusTot() {
    	fail();
    }
    
    @Test
    void testCalculerDatePlusTard() {
    	fail();
    }
    
    @Test
	void testCalculerFinProjet() {
		fail();
	}
    
    @Test
    void testEquals() { //TODO
    	
		ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
		ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

		Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
		evenementsPredecesseurs.add(Evenement.EVENEMENT_INITIAL);
		tachesPredecesseurs.add(tache1);

		Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
		Evenement evenement2 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

		assertEquals(evenement1.equals(evenement2), evenement2.equals(evenement1));
    }
}