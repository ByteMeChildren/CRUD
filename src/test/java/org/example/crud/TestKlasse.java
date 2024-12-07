package org.example.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestKlasse {

    // A pure JUnit test. Nice, because it's fast but doesn't test too much.
    // We would like to test the actual REST calls instead, using a WebTestClient.

    @Test
    void testCreateJacke() {
        Controller controller = new Controller();

        Jacke neueJacke = new Jacke();
        neueJacke.setName("Winterjacke");
        neueJacke.setBrand("S-Oliver");
        neueJacke.setSize(Size.valueOf("S"));
        neueJacke.setColor("Grau");
        neueJacke.setSeason(Season.valueOf("Winter"));
        neueJacke.setReleaseYear(2043);
        Jacke erstellteJacke = controller.createJacke(neueJacke);

        assertNotNull(erstellteJacke);
        assertEquals(1, erstellteJacke.getId());
        assertEquals("Winterjacke", erstellteJacke.getName());
        assertEquals("S-Oliver", erstellteJacke.getBrand());
    }

    @Test
    void teestGetJackeById() {
        Controller controller = new Controller();
        Jacke jacke = new Jacke();
        jacke.setName("Winterjacke");
        controller.createJacke(jacke);

        Jacke gefundeneJacke = controller.getJackeById(1);
        assertNotNull(gefundeneJacke);
        assertEquals("Winterjacke", gefundeneJacke.getName());

        //ich habe diese lombok-Schreibweise nicht gemacht (wurde mir vorgeschlagen)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.getJackeById(99));
        assertEquals("Jacke mit ID: 99 nicht gefunden", exception.getMessage());
    }

    @Test
    void testDeleteJacke() {
        Controller controller = new Controller();
        Jacke jacke = new Jacke();
        jacke.setName("Jäckli");
        controller.createJacke(jacke);

        String meldung = controller.deleteJacke(1);
        assertEquals("Jacke mit ID 1 gelöscht", meldung);
    }

    @Test
    void testUpdateJacke() {
        Controller controller = new Controller();
        Jacke jacke = new Jacke();
        jacke.setName("Winterjacke");
        jacke.setBrand("H&M");
        controller.createJacke(jacke);

        //Jacke updaten
        Jacke updatedJacke = new Jacke();
        updatedJacke.setName("Sommerjacke");
        updatedJacke.setBrand("Nike");
        updatedJacke.setSize(Size.valueOf("M"));
        updatedJacke.setColor("Schwarz");
        Jacke neueJacke = controller.updateJacke(1, updatedJacke);

        assertNotNull(neueJacke);
        assertEquals("Sommerjacke", neueJacke.getName());
        assertEquals("Nike", neueJacke.getBrand());

        //Fehler testen
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.updateJacke(99, updatedJacke);
        });
        assertEquals("Jacke mit ID 99 nicht gefunden", exception.getMessage());
    }

    @Test
    void testSearchByColor() {
        Controller controller = new Controller();

        //2 neue Jacken
        Jacke j1 = new Jacke();
        j1.setColor("Magenta");
        controller.createJacke(j1);

        Jacke j2 = new Jacke();
        j2.setColor("Blau");
        controller.createJacke(j2);

        //nach Blau suchen
        List<Jacke> ergebnisse = controller.searchByColor("Blau");
        assertEquals(1, ergebnisse.size());
        assertEquals("Blau", ergebnisse.getFirst().getColor());

        //Farbe suchen, die es nicht gibt
        List<Jacke> keineErgebnisse = controller.searchByColor("Grün");
        assertTrue(keineErgebnisse.isEmpty());
    }
}
