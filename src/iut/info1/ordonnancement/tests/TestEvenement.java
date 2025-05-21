/*
 * TestEvenement.java                                                       16 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import iut.info1.ordonnancement.Evenement;

import iut.info1.ordonnancement.Tache;

/**
 * Cette classe est responsable de tester la classe `Evenement`.
 * Elle vérifie que les méthodes et les constructeurs de la classe `Evenement`
 * se comportent comme attendu.
 * Les tests couvrent la génération de hashCode, les constructeurs,
 * les accesseurs (getters) ainsi que la méthode `equals`.
 * 
 * @author Gabriel Le Goff
 * @author Gabriel Robache
 * @author Roveri Esteban
 * @author Sauvaire Léo
 * @author Massicard Maël
 */

class TestEvenement {
	
    /**
     * Test method for {@link iut.info1.ordonnancement.Evenement#hashCode()}.
     */
    @Test
    final void testHashCode() {
    	Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
    	Evenement Evenement2 = new Evenement(2, 1.0, 4.0, new Evenement[] { Evenement1 });
    	Evenement Evenement3 = new Evenement(3, 2.0, 3.0, new Evenement[] { Evenement1, Evenement2 });       
        
        assertNotEquals(Evenement1.hashCode(), Evenement2.hashCode());
        assertNotEquals(Evenement1.hashCode(), Evenement3.hashCode());
        assertNotEquals(Evenement2.hashCode(), Evenement3.hashCode());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Evenement#Evenement( int, double, double, Evenement[] )}.}
     */
    @Test
    final void testConstructeur() {
    	Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
    	Evenement Evenement2 = new Evenement(2, 1.0, 4.0, new Evenement[] { Evenement1 });
    	Evenement Evenement3 = new Evenement(3, 2.0, 3.0, new Evenement[] { Evenement1, Evenement2 });
    	
    	assertEquals(1, Evenement1.getId());
    	assertEquals(0.0, Evenement1.getTacheAuPlusTot());
    	assertEquals(5.0, Evenement1.getTacheAusPlusTard());
    	assertArrayEquals(new Evenement[] { null }, Evenement1.getEvenementPredecesseur());
    	
    	assertEquals(2, Evenement2.getId());
    	assertEquals(1.0, Evenement2.getTacheAuPlusTot());
    	assertEquals(4.0, Evenement2.getTacheAusPlusTard());
    	assertArrayEquals(new Evenement[] { Evenement1 }, Evenement2.getEvenementPredecesseur());
    	
    	assertEquals(3, Evenement3.getId());
    	assertEquals(2.0, Evenement3.getTacheAuPlusTot());
    	assertEquals(3.0, Evenement3.getTacheAusPlusTard());
    	assertArrayEquals(new Evenement[] { Evenement1, Evenement2 }, Evenement3.getEvenementPredecesseur());
    }
    
    /**
     * Test method for {@link iut.info1.ordonnancement.Evenement#Evenement( Evenement event_initial )}.
     */
    @Test
    final void testConstructeurInitial() {
		Evenement Evenement1 = new Evenement(new Evenement(0, 0.0, 0.0, new Evenement[] { null }));

		assertEquals(new Evenement(0, 0.0, 0.0, new Evenement[] { null }), Evenement1.getEvenementInitial());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Evenement#getId()}.
     */
    @Test
    final void testGetId() {
	Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
		
	assertEquals(1, Evenement1.getId());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Evenement#getTacheAuPlusTot()}.
     */
	@Test
	void testGetTacheAuPlusTot() {
		Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
		
		assertEquals(0.0, Evenement1.getTacheAuPlusTot());
	}

	/**
	 * Test method for {@link iut.info1.ordonnancement.Evenement#getTacheAusPlusTard()}.
	 */
	@Test
	void testGetTacheAusPlusTard() {
		Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
		
		assertEquals(5.0, Evenement1.getTacheAusPlusTard());
	}

	/**
	 * Test method for {@link iut.info1.ordonnancement.Evenement#getEvenementPredecesseur()}.
	 */
	@Test
	void testGetEvenementPredecesseur() {
		Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
		Evenement Evenement2 = new Evenement(2, 1.0, 4.0, new Evenement[] { Evenement1 });
		
		assertArrayEquals(new Evenement[] { null }, Evenement1.getEvenementPredecesseur());
		assertArrayEquals(new Evenement[] { Evenement1 }, Evenement2.getEvenementPredecesseur());
	}

	/**
	 * Test method for {@link iut.info1.ordonnancement.Evenement#getEvenementInitial()}.
	 */
	@Test
	void testGetEvenementInitial() {
	    Evenement evenementInitial = new Evenement(0, 0.0, 0.0, new Evenement[] { null });
	    Evenement evenement = new Evenement(evenementInitial);

	    assertEquals(evenementInitial, evenement.getEvenementInitial());
	}

	/**
	 * Test method for {@link iut.info1.ordonnancement.Evenement#estCritique()}.
	 */
	@Test
	void testEstCritique() {
		Evenement Evenement1 = new Evenement(1, 5.0, 5.0, new Evenement[] { null });
		Evenement Evenement2 = new Evenement(2, 1.0, 1.0, new Evenement[] { Evenement1 });
		Evenement Evenement3 = new Evenement(3, 2.0, 3.0, new Evenement[] { Evenement1, Evenement2 });

		assertTrue(Evenement1.estCritique());
		assertTrue(Evenement2.estCritique());
		assertFalse(Evenement3.estCritique());
	}

	/**
     * Test method for {@link iut.info1.ordonnancement.Evenement#equals(java.lang.Object)}.
     */
    @Test
    final void testEqualsObject() {
		Evenement Evenement1 = new Evenement(1, 0.0, 5.0, new Evenement[] { null });
		Evenement Evenement2 = new Evenement(2, 1.0, 4.0, new Evenement[] { Evenement1 });
		Evenement Evenement3 = new Evenement(3, 2.0, 3.0, new Evenement[] { Evenement1, Evenement2 });
		Evenement Evenement4 = new Evenement(3, 2.0, 3.0, new Evenement[] { Evenement1, Evenement2 });

		assertNotEquals(Evenement1, Evenement2);
		assertNotEquals(Evenement1, Evenement3);
		assertNotEquals(Evenement2, Evenement3);
		assertEquals(Evenement3, Evenement4);
    }
}
