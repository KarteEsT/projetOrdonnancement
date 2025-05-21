/*
 * TestChargeurCSV.java                                                       21 mai 2025
 * IUT de Rodez, Info 1 2024 - 2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import iut.info1.ordonnancement.ChargeurCSV;

import org.junit.jupiter.api.Test;

/**
 * Cette classe est responsable de tester la classe `ChargeurCSV.java`.
 * Elle vérifie que les méthodes et les constructeurs de la classe `ChargeurCSV`
 * se comportent comme attendu.
 * 
 * @author Gabriel Le Goff
 * @author Gabriel Robache
 * @author Roveri Esteban
 * @author Sauvaire Léo
 * @author Massicard Maël
 */
class TestChargeurCSV {

	@Test
    void testEcrireEtLireCSV() throws IOException {
        // Create a temporary file for testing
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();

        // Prepare test data
        ArrayList<String[]> donneesEcriture = new ArrayList<>();
        donneesEcriture.add(new String[]{"Colonne1", "Colonne2", "Colonne3"});
        donneesEcriture.add(new String[]{"Valeur1", "Valeur2", "Valeur3"});
        donneesEcriture.add(new String[]{"A", "B", "C"});

        // Write data to the temporary file
        ChargeurCSV.ecrireCSV(tempFile.getAbsolutePath(), donneesEcriture);

        // Read data back from the file
        ArrayList<String[]> donneesLecture = new ArrayList<>();
        ChargeurCSV.lireCSV(tempFile.getAbsolutePath(), donneesLecture);

        // Verify the data
        assertEquals(donneesEcriture.size(), donneesLecture.size(), "Le nombre de lignes doit correspondre.");
        for (int i = 0; i < donneesEcriture.size(); i++) {
            assertArrayEquals(donneesEcriture.get(i), donneesLecture.get(i), "Les lignes doivent correspondre.");
        }
    }

    @Test
    void testLireCSVFichierInexistant() {
        ArrayList<String[]> donneesLecture = new ArrayList<>();
        ChargeurCSV.lireCSV("fichier_inexistant.csv", donneesLecture);

        // Verify that no data is read
        assertTrue(donneesLecture.isEmpty(), "Aucune donnée ne doit être lue pour un fichier inexistant.");
    }

}
