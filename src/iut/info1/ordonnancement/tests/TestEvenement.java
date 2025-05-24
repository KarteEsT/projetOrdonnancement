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
	void testConstructeurEvenementInitialCorrecte() {
		Evenement evenement = new Evenement(1);
		assertEquals(1, evenement.getId(), "L'ID de l'événement doit être 1.");
		assertEquals(0.0, evenement.getDateAuPlusTot(), "La tâche au plus tôt doit être 0.0.");
		assertEquals(0.0, evenement.getDateAuPlusTard(), "La tâche au plus tard doit être 0.0.");
	}
	
	@Test
	void testConstructeurEvenementCorrecte() {
		fail();
	}
	
	@Test
	void testConstructeurEvenementIncorrecte() {
		fail();
	}
	
	@Test
	void testConstructeurTacheIncorrecte() {
		fail();
	}
	
	@Test
	void testConstructeurEvenementTacheIncorrecte() {
		fail();
	}
	
	//------------ Test des getters -------------//
	
	@Test
	void testGetId() {
		fail();
	}
	
	@Test
	void testGetTacheAuPlusTot() {
		fail();
	}
	
	@Test
	void testGetTacheAuPlusTard() {
		fail();
	}
	
	@Test
    void testGetEvenementPredecesseurList() {
        fail();
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
    void testEquals() {
    	fail();
    }
    
    @Test
    void testhashCode() {
		fail();	
    }
}