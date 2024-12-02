package org.example.crud;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/*
* Ein RestController  bearbeitet HTTP-Anfragen (wie GET, POST, PUT, DELETE).
* Der RestController empfängt HTTP-Anfragen und gibt HTTP-Antworten zurück
* Er ist dafür verantwortlich, die Daten von der Service-Schicht anzufordern und als Antwort zurückzugeben.
*/

@RestController
@RequestMapping("/jacken")
public class Controller {

    private final List<Jacke> jacken = new ArrayList<>();
    private int nextId = 1;

    // Jacke erstellen
    @PostMapping
    public Jacke createJacke(@RequestBody Jacke neueJacke) {
        neueJacke.setId(nextId);
        nextId = nextId + 1;
        jacken.add(neueJacke);
        return neueJacke;
    }

    // Jacke updaten

    @PutMapping("/{id}")
    public Jacke updateJacke(@PathVariable int id, @RequestBody Jacke neueDaten) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                jacke.setName(neueDaten.getName());
                jacke.setBrand(neueDaten.getBrand());
                jacke.setSize(neueDaten.getSize());
                jacke.setColor(neueDaten.getColor());
                jacke.setSeason(neueDaten.getSeason());
                jacke.setReleaseYear(neueDaten.getReleaseYear());
                return jacke;
            }
        }
        throw new IllegalArgumentException("Jacke mit ID " + id + " nicht gefunden");
    }
    // delete

    @DeleteMapping("/{id}")
    public String deleteJacke(@PathVariable int id) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                jacken.remove(i);
                return "Jacke mit ID " + id + " gelöscht";
            }
        }
        throw new IllegalArgumentException("Jacke mit ID " + id + " nicht gefunden");
    }

    // Alle Jacken anzeigen
    @GetMapping
    public List<Jacke> getAllJacken() {
        return jacken;
    }

    // Jacke nach ID suchen
    @GetMapping("/{id}")
    public Jacke getJackeById(@PathVariable int id) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                return jacke;
            }
        }
        throw new IllegalArgumentException("Jacke mit ID " + id + " nicht gefunden");
    }

    // Nach Farbe suchen
    @GetMapping("/search")
    public List<Jacke> searchByColor(@RequestParam String color) {
        List<Jacke> ergebnis = new ArrayList<>();
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getColor().equalsIgnoreCase(color)) {
                ergebnis.add(jacke);
            }
        }
        return ergebnis;
    }
}
