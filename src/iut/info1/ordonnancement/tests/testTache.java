/*
 * testTache.java                                   14 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */

package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import iut.info1.ordonnancement.Tache;

/**
 * Cette classe est responsable de tester la classe `Tache`.
 * Elle vérifie que les méthodes et les constructeurs de la classe `Tache`
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

class testTache {

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#hashCode()}.
     */
    @Test
    final void testHashCode() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0, new Tache[] { tacheA });
        Tache tacheC = new Tache("Tache C", "Électricité", 3.0, new Tache []{ tacheA, tacheB });       
        
        assertNotEquals(tacheA.hashCode(), tacheB.hashCode());
        assertNotEquals(tacheA.hashCode(), tacheC.hashCode());
        assertNotEquals(tacheB.hashCode(), tacheC.hashCode());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#Tache(java.lang.String, java.lang.String, double, iut.info1.ordonnancement.Tache[])}.
     */
    @Test
    final void testConstructeur() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0, new Tache[] { tacheA });
        Tache tacheC = new Tache("Tache C", "Électricité", 3.0, new Tache[] { tacheA, tacheB });

        assertEquals("Tache A", tacheA.getLibelle());
        assertEquals("Fondation", tacheA.getDescription());
        assertEquals(2.0, tacheA.getDuree());
        assertArrayEquals(new Tache[] { null }, tacheA.getTachesRequises());

        assertEquals("Tache B", tacheB.getLibelle());
        assertEquals("Plomberie", tacheB.getDescription());
        assertEquals(1.0, tacheB.getDuree());
        assertArrayEquals(new Tache[] { tacheA }, tacheB.getTachesRequises());

        assertEquals("Tache C", tacheC.getLibelle());
        assertEquals("Électricité", tacheC.getDescription());
        assertEquals(3.0, tacheC.getDuree());
        assertArrayEquals(new Tache[] { tacheA, tacheB }, tacheC.getTachesRequises());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#Tache(java.lang.String, java.lang.String, double)}.
     */
    @Test
    final void testConstructeurInitial() {
        Tache tache = new Tache("Tache A", "Fondation", 2.0);
        assertEquals("Tache A", tache.getLibelle());
        assertEquals("Fondation", tache.getDescription());
        assertEquals(2.0, tache.getDuree());
        assertArrayEquals(new Tache[0], tache.getTachesRequises());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getLibelle()}.
     */
    @Test
    final void testGetLibelle() {
        Tache tache = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        assertEquals("Tache A", tache.getLibelle());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getDescription()}.
     */
    @Test
    final void testGetDescription() {
        Tache tache = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        assertEquals("Fondation", tache.getDescription());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getDuree()}.
     */
    @Test
    final void testGetDuree() {
        Tache tache = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        assertEquals(2.0, tache.getDuree());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getTachesRequises()}.
     */
    @Test
    final void testGetTachesRequises() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0, new Tache[] { tacheA });
        Tache tacheC = new Tache("Tache C", "Électricité", 3.0, new Tache[] { tacheA, tacheB });

        assertArrayEquals(new Tache[] { tacheA, tacheB }, tacheC.getTachesRequises());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#equals(java.lang.Object)}.
     */
    @Test
    final void testEqualsObject() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0, new Tache[] { null });
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0, new Tache[] { tacheA });
        Tache tacheC = new Tache("Tache C", "Électricité", 3.0, new Tache[] { tacheA, tacheB });
        Tache tacheD = new Tache("Tache C", "Électricité", 3.0, new Tache[] { tacheA, tacheB });

        assertNotEquals(tacheA, tacheB);
        assertNotEquals(tacheA, tacheC);
        assertNotEquals(tacheB, tacheC);
        assertEquals(tacheC, tacheD);
    }

}
