/*
 * TestEvenement.java                                                       16 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement.tests;

import iut.info1.ordonnancement.Evenement;
import iut.info1.ordonnancement.Tache;
import iut.info1.ordonnancement.Graphe;

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

        Evenement evenementInitial = new Evenement();

        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(evenementInitial, evenement1.getEvenementPredecesseurList().get(0));
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

        Evenement evenementInitial = new Evenement();
        evenementsPredecesseurs.add(evenementInitial);
        // Je n'ajoute pas de tache prédécesseur pour créer une erreur.

        assertThrows(IllegalArgumentException.class, () -> {
            new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        });
    }

    @Test
    void testConstructeurEvenementTacheIncorrect() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        Tache tache2 = new Tache("Tâche 2", "Description de la tâche 2", 3.0);

        evenementsPredecesseurs.add(evenementInitial);
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

        Evenement evenementInitial = new Evenement();
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(0, evenementInitial.getId());
        assertEquals(1, evenement1.getId());
    }

    @Test
    void testGetDateAuPlusTot() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(0.0, evenement1.getDateAuPlusTot()); // ici 0.0 car l'ordonnancement n'est pas encore fait
        assertEquals(0.0, evenementInitial.getDateAuPlusTot());
    }

    @Test
    void testGetDateAuPlusTard() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(0.0, evenement1.getDateAuPlusTard()); //ici 0.0 car l'ordonnancement n'est pas encore fait
        assertEquals(0.0, evenementInitial.getDateAuPlusTard());
    }

    @Test
    void testGetEvenementPredecesseurList() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);

        tachesPredecesseurs.add(tache1);
        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(1, evenement1.getEvenementPredecesseurList().size());
        assertTrue(evenement1.getEvenementPredecesseurList().contains(evenementInitial));
    }

    //------------ Test des méthodes -------------//

    @Test
    void testAddEvenementPredecesseur() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        Evenement evenement2 = new Evenement(2, evenementsPredecesseurs, tachesPredecesseurs);

        evenement1.addEvenementPredecesseur(evenement2);

        assertTrue(evenement1.getEvenementPredecesseurList().contains(evenement2),
                "L'événement prédécesseur doit être ajouté à la liste.");
    }

    @Test
    void testAddEvenementSuccesseur() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        Evenement evenement2 = new Evenement(2, evenementsPredecesseurs, tachesPredecesseurs);

        evenement1.addEvenementSuccesseur(evenement2);

        assertTrue(evenement1.getEvenementSuccesseurList().contains(evenement2),
                "L'événement successeur doit être ajouté à la liste.");
    }

    @Test
    void testAddTachePredecesseur() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        Tache tache2 = new Tache("Tâche 2", "Description de la tâche 2", 3.0);
        evenement1.addTachePredecesseur(tache2);

        assertTrue(evenement1.getTachePredecesseurList().contains(tache2),
                "La tâche prédécesseur doit être ajoutée à la liste.");
    }

    @Test
    void testAddTacheSuccesseur() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        Tache tache2 = new Tache("Tâche 2", "Description de la tâche 2", 3.0);
        evenement1.addTacheSuccesseur(tache2);

        assertTrue(evenement1.getTacheSuccesseurList().contains(tache2),
                "La tâche successeur doit être ajoutée à la liste.");
    }

    @Test
    void testSetDatePlusTot() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        evenement1.setDatePlusTot(10.0);
        assertEquals(10.0, evenement1.getDateAuPlusTot(), "La date au plus tôt doit être 10.0 après le set.");
    }

    @Test
    void testSetDatePlusTard() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        evenement1.setDatePlusTard(10.0);
        assertEquals(10.0, evenement1.getDateAuPlusTard(), "La date au plus tôt doit être 10.0 après le set.");
    }

    @Test
    void testSetEvenementSuccesseurList() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);
        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        ArrayList<Evenement> evenementsSuccesseurs = new ArrayList<>();
        evenementsSuccesseurs.add(evenement1);

        evenement1.setEvenementSuccesseurList(evenementsSuccesseurs);

        assertEquals(1, evenement1.getEvenementSuccesseurList().size(),
                "La liste des événements successeurs doit contenir un élément.");
        assertTrue(evenement1.getEvenementSuccesseurList().contains(evenement1),
                "L'événement successeur doit être présent dans la liste.");
    }

    @Test
    void testSetTacheSuccesseurList() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);
        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        ArrayList<Tache> tachesSuccesseurs = new ArrayList<>();
        tachesSuccesseurs.add(tache1);

        evenement1.setTacheSuccesseurList(tachesSuccesseurs);

        assertEquals(1, evenement1.getTacheSuccesseurList().size(),
                "La liste des tâches successeurs doit contenir un élément.");
        assertTrue(evenement1.getTacheSuccesseurList().contains(tache1),
                "La tâche successeur doit être présente dans la liste.");
    }

    @Test
    void testEstCritique() {
        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);

        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        evenement1.setDatePlusTot(5.0);
        evenement1.setDatePlusTard(5.0);

        assertTrue(evenement1.estCritique(), "L'événement doit être critique car les dates au plus tôt et au plus tard sont égales.");

    }

    @Test
    void testEquals() {

        ArrayList<Evenement> evenementsPredecesseurs = new ArrayList<>();
        ArrayList<Tache> tachesPredecesseurs = new ArrayList<>();

        Evenement evenementInitial = new Evenement();
        Tache tache1 = new Tache("Tâche 1", "Description de la tâche 1", 5.0);
        evenementsPredecesseurs.add(evenementInitial);
        tachesPredecesseurs.add(tache1);

        Evenement evenement1 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);
        Evenement evenement2 = new Evenement(1, evenementsPredecesseurs, tachesPredecesseurs);

        assertEquals(evenement1.equals(evenement2), evenement2.equals(evenement1));
    }
}