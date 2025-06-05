package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import iut.info1.ordonnancement.Evenement;
import iut.info1.ordonnancement.Graphe;
import iut.info1.ordonnancement.Tache;

/**
 * TODO commenter la responsabilité de cette classe (SRP)
 */
public class TestGraphe {
    
    private Graphe graphe;

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @BeforeEach
    public void setUp() {
        // Création de tâches simples
        Tache t1 = new Tache("T1", "Début", 2.0);
        Tache t2 = new Tache("T2", "Étape 2", 3.0);
        Tache t3 = new Tache("T3", "Étape 3", 4.0);
        Tache t4 = new Tache("T4", "Fin", 1.0);

        // Dépendances : T2 et T3 dépendent de T1, T4 dépend de T2 et T3
        t2.ajouterTacheRequise(t1);
        t3.ajouterTacheRequise(t1);
        t4.ajouterTacheRequise(t2);
        t4.ajouterTacheRequise(t3);

        ArrayList<Tache> taches = new ArrayList<>();
        taches.add(t1);
        taches.add(t2);
        taches.add(t3);
        taches.add(t4);

        graphe = new Graphe("Test Graphe", "jours", taches);
    }

    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    @Test
    public void testCreerEvenementsNombre() {
        graphe.creerEvenements();
        
        // 1 événement initial + 1 événement par tâche = 5
        assertEquals(5, graphe.getEvenements().size(), "Le graphe doit contenir 5 événements.");
    }

    /**
     * Teste si l'événement de départ contient bien des tâches successeures.
     * Cela vérifie que les liens de dépendances sont correctement créés.
     */
    @Test
    public void testCreerEvenementsSuccesseurs() {
        // On génère les événements à partir des tâches
        graphe.creerEvenements();

        // On récupère le premier événement (normalement l'événement initial)
        Evenement evenementInitial = graphe.getEvenements().get(0);

        // Il doit avoir au moins une tâche successeure
        assertFalse(evenementInitial.getTacheSuccesseurList().isEmpty());

        // Vérifie que T2 et T3 sont bien dans les successeurs de l’événement initial
        boolean t2Trouve = false;
        boolean t3Trouve = false;

        for (Tache t : evenementInitial.getTacheSuccesseurList()) {
            if (t.getLibelle().equals("T2")) {
                t2Trouve = true;
            }
            if (t.getLibelle().equals("T3")) {
                t3Trouve = true;
            }
        }

        assertTrue(t2Trouve, "T2 devrait être successeur de l'événement initial.");
        assertTrue(t3Trouve, "T3 devrait être successeur de l'événement initial.");
    }


    /**
     * Teste si les dépendances entre les tâches sont bien prises en compte
     * lors de la création des événements.
     */
    @Test
    public void testCreerEvenementsDependancesRespectees() {
        graphe.creerEvenements();

        // On cherche la tâche T4 dans la liste des tâches
        Tache t4 = null;
        for (Tache t : graphe.getTaches()) {
            if (t.getLibelle().equals("T4")) {
                t4 = t;
                break;
            }
        }
        assertNotNull(t4);

        // On cherche l’événement qui a T4 comme tâche prédécesseur (donc T4 se termine à cet événement)
        Evenement eventFinT4 = null;
        for (Evenement e : graphe.getEvenements()) {
            if (e.getTachePredecesseurList().contains(t4)) {
                eventFinT4 = e;
                break;
            }
        }

        assertNotNull(eventFinT4, "Un événement devrait exister pour marquer la fin de T4.");

        // Vérifie qu’il n’y a pas de tâche successeure après T4 (car T4 est la dernière tâche)
        assertTrue(eventFinT4.getTacheSuccesseurList() == null 
                   || eventFinT4.getTacheSuccesseurList().isEmpty(),
                   "T4 ne devrait pas avoir de tâche successeure.");
    }

}
