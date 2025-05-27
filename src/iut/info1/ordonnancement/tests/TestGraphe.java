/*
 * GrapheTest.java                                   16 mai 2025
 * IUT de Rodez, Info1 2024-2025, pas de copyright
 */
package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import iut.info1.ordonnancement.Evenement;
import iut.info1.ordonnancement.Graphe;
import iut.info1.ordonnancement.Tache;

/**
 * Cette classe teste la classe Graphe.java.
 * @author Esteban Roveri, Gabriel Le Goff
 */
public class TestGraphe {
    
    Tache[] taches = { new Tache("Tache1", "Description1", 1.0),
                       new Tache("Tache2", "Description2", 2.0),
                       new Tache("Tache3", "Description3", 3.0),
                       new Tache("Tache4", "Description4", 4.0),
                       new Tache("Tache5", "Description5", 5.0)                    
                     };
    
    Evenement[] evenements = { new Evenement(1, 0.0, 0.0, new Evenement[0]),
                               new Evenement(2, 1.0, 1.0, new Evenement[0]),
                               new Evenement(3, 2.0, 2.0, new Evenement[0]),
                               new Evenement(4, 3.0, 3.0, new Evenement[0]),
                               new Evenement(5, 4.0, 4.0, new Evenement[0])
                             };

    /**
     * Test method for {@link iut.info1.ordonnancement.Graphe#Graphe(String,
     *                                          String, Taches[], Evenement[])}.
     */
    @ParameterizedTest
    @CsvSource({
            "Graphe1, jours, 3, 2",
            "Graphe2, heures, 5, 4",
            "Graphe3, minutes, 10, 8"
    })
    void testGraphe(String titre, String unite, Tache[] taches, Evenement[] evenements) {
        // Graphe graphe = new Graphe(titre, unite, taches, evenements);
        // assertEquals(titre, graphe.getTitre());
        // assertEquals(unite, graphe.getUnite());
        // assertEquals(taches, graphe.getTaches().size());
        // assertEquals(evenements, graphe.getEvenement().size());
        
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Graphe#getTitre()}.
     */
    @Test
    void testGetTitre() {
        Graphe graphe = new Graphe("Projet Test", "jours", taches, evenements);
        Graphe graphe2 = new Graphe("Projet Test2", "jours2", taches, evenements);
        Graphe graphe3 = new Graphe("Projet Test3", "jours3", taches, evenements);
        Graphe graphe4 = new Graphe("Projet Test4", "jours4", taches, evenements);
        Graphe graphe5 = new Graphe("Projet Test5", "jours5", taches, evenements);

        assertEquals("Projet Test", graphe.getTitre());
        assertEquals("Projet Test2", graphe2.getTitre());
        assertEquals("Projet Test3", graphe3.getTitre());
        assertEquals("Projet Test4", graphe4.getTitre());
        assertEquals("Projet Test5", graphe5.getTitre());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Graphe#getUnite()}.
     */
    @Test
    void testGetUnite() {
        Graphe graphe = new Graphe("Projet Test", "jours", taches, evenements);
        Graphe graphe2 = new Graphe("Projet Test2", "jours2", taches, evenements);
        Graphe graphe3 = new Graphe("Projet Test3", "jours3", taches, evenements);
        Graphe graphe4 = new Graphe("Projet Test4", "jours4", taches, evenements);
        Graphe graphe5 = new Graphe("Projet Test5", "jours5", taches, evenements);

        assertEquals("jours", graphe.getUnite());
        assertEquals("jours2", graphe2.getUnite());
        assertEquals("jours3", graphe3.getUnite());
        assertEquals("jours4", graphe4.getUnite());
        assertEquals("jours5", graphe5.getUnite());
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Graphe#getTaches()}.
     */
    @Test
    void testGetTaches() {
        Graphe graphe = new Graphe("Projet Test", "jours", taches, evenements);
        Graphe graphe2 = new Graphe("Projet Test2", "jours2", taches, evenements);
        Graphe graphe3 = new Graphe("Projet Test3", "jours3", taches, evenements);
        Graphe graphe4 = new Graphe("Projet Test4", "jours4", taches, evenements);
        Graphe graphe5 = new Graphe("Projet Test5", "jours5", taches, evenements);

        assertEquals(taches.length, graphe.getTaches().length);
        assertEquals(taches.length, graphe2.getTaches().length);
        assertEquals(taches.length, graphe3.getTaches().length);
        assertEquals(taches.length, graphe4.getTaches().length);
        assertEquals(taches.length, graphe5.getTaches().length);
    }

    /**
     * Test method for {@link iut.info1.ordonnancement.Graphe#getEvenement()}.
     */
    @Test
    void testGetEvenement() {
        Graphe graphe = new Graphe("Projet Test", "jours", taches, evenements);
        Graphe graphe2 = new Graphe("Projet Test2", "jours2", taches, evenements);
        Graphe graphe3 = new Graphe("Projet Test3", "jours3", taches, evenements);
        Graphe graphe4 = new Graphe("Projet Test4", "jours4", taches, evenements);
        Graphe graphe5 = new Graphe("Projet Test5", "jours5", taches, evenements);

        assertEquals(evenements.length, graphe.getEvenement().length);
        assertEquals(evenements.length, graphe2.getEvenement().length);
        assertEquals(evenements.length, graphe3.getEvenement().length);
        assertEquals(evenements.length, graphe4.getEvenement().length);
        assertEquals(evenements.length, graphe5.getEvenement().length);
    }
    
    @Test
    void testDateFinProjet() {
    	fail();
    }
}