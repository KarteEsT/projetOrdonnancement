/*
 * TestTache.java                                   14 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */

package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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

class TestTache {
    //TODO Créer nouveau test constructeur invalide !
    
    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#Tache(java.lang.String, java.lang.String, double, iut.info1.ordonnancement.Tache[])}.
     */
    @Test
    final void testConstructeur() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0, new ArrayList<>());
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0, new ArrayList<>());
        tacheB.getTachesRequises().add(tacheA);
        
        assertEquals("Tache A", tacheA.getLibelle());
        assertEquals("Fondation", tacheA.getDescription());
        
        assertTrue(tacheA.getTachesRequises().isEmpty()); // Aucune tâche requise initialement
        assertEquals("Tache A", tacheB.getTachesRequises().get(0).getLibelle()); 
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#Tache(java.lang.String, java.lang.String, double)}.
     */
    @Test
    final void testConstructeurInitial() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        assertEquals("Tache A", tacheA.getLibelle());
        assertEquals("Fondation", tacheA.getDescription());
        assertEquals(2.0, tacheA.getDuree());
        
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0);
        
        
        tacheB.getTachesRequises().add(tacheA);
        
        assertEquals("Tache A", tacheB.getTachesRequises().get(0).getLibelle());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getLibelle()}.
     */
    @Test
    final void testGetLibelle() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0);
        
        assertEquals("Tache A", tacheA.getLibelle());
        assertEquals("Tache B", tacheB.getLibelle());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getDescription()}.
     */
    @Test
    final void testGetDescription() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        
        assertEquals("Fondation", tacheA.getDescription());
        
        Tache tacheB = new Tache("Tache B", "Plomberie", 1.0);
        
        assertEquals("Plomberie", tacheB.getDescription());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getDuree()}.
     */
    @Test
    final void testGetDuree() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        assertEquals(2.0, tacheA.getDuree());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#getTachesRequises()}.
     */
    @Test
    final void testGetTachesRequises() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        
        Tache tacheB = new Tache("Tache B" , "Plomberie", 1.0);
        
        tacheB.getTachesRequises().add(tacheA);
        
        assertEquals("Tache A", tacheB.getTachesRequises().get(0).getLibelle());
        
        assertEquals(0, tacheA.getTachesRequises().size());
        
    }
        

    /**
     * Test method for {@link iut.info1.ordonnancement.Tache#equals(java.lang.Object)}.
     */
    @Test
    final void testEqualsObject() {
        Tache tacheA = new Tache("Tache A", "Fondation", 2.0);
        Tache tacheB = new Tache("Tache A", "Fondation", 2.0);
        Tache tacheC = new Tache("Tache C", "Électricité", 3.0);
        
        assertTrue(tacheA.equals(tacheB), "Les tâches A et B devraient être égales");
        assertFalse(tacheA.equals(tacheC), "Les tâches A et C ne devraient pas être égales");
        
    }

}
