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
    private int currentId = 1; // ID-Zähler

    // CREATE
    @PostMapping
    public Jacke createJacke(@RequestBody Jacke neueJacke) {
        neueJacke.setId(currentId++);
        jacken.add(neueJacke);
        return neueJacke;
    }

    // READ - Alle Jacken
    @GetMapping
    public List<Jacke> getAllJacken() {
        return jacken;
    }

    // READ - Einzelne Jacke nach ID
    @GetMapping("/{id}")
    public Jacke getJackeById(@PathVariable int id) {
        return jacken.stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Jacke mit ID " + id + " nicht gefunden"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Jacke updateJacke(@PathVariable int id, @RequestBody Jacke updatedJacke) {
        Jacke existingJacke = getJackeById(id);
        existingJacke.setName(updatedJacke.getName());
        existingJacke.setBrand(updatedJacke.getBrand());
        existingJacke.setSize(updatedJacke.getSize());
        existingJacke.setColor(updatedJacke.getColor());
        existingJacke.setSeason(updatedJacke.getSeason());
        existingJacke.setReleaseYear(updatedJacke.getReleaseYear());
        return existingJacke;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteJacke(@PathVariable int id) {
        Jacke jacke = getJackeById(id);
        jacken.remove(jacke);
        return "Jacke mit ID " + id + " wurde gelöscht.";
    }

    // SUCHE - Nach Farbe
    @GetMapping("/suche")
    public List<Jacke> searchByColor(@RequestParam String color) {
        List<Jacke> result = new ArrayList<>();
        for (Jacke jacke : jacken) {
            if (jacke.getColor().equalsIgnoreCase(color)) {
                result.add(jacke);
            }
        }
        return result;
    }
}
