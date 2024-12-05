package org.example.crud;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/*
****Funktionsbeschreibung****
* Ein RestController  bearbeitet HTTP-Anfragen (wie GET, POST, PUT, DELETE).
* Der RestController empfängt HTTP-Anfragen und gibt HTTP-Antworten zurück
* Er ist dafür verantwortlich, die Daten von der Service-Schicht anzufordern und als Antwort zurückzugeben.
*
* ****disclaimer****
* Ich habe zum Teil Komponenten nicht nach Sonar standart gemacht,
* weil ich es sonst unleserlich gefunden habe (zB for (Jacke jacke : jacken), mit so einem Code will man doch nur angeben)
*
* Ich bin auf die Schönheit dieses Codes stolz. Ich glaube Sie können mir zustimmen, dass ich mich im Vergleich zum letzten Projekt, in diesem Aspekt massiv gesteigert habe😂
*/

@RestController
@RequestMapping("/jacken")
public class Controller {
    //ich habe probiert final zu benutzen. ich dachte hier macht es sinn, obwohl eigentlich niemand diesen code anfassen wird
    final List<Jacke> jacken = new ArrayList<>();
    int nextId = 1;

    //Jacke erstellen👍
    @PostMapping
    public Jacke createJacke(@RequestBody Jacke neueJacke) {
        neueJacke.setId(nextId);
        nextId += 1;
        jacken.add(neueJacke);
        return neueJacke;
    }

    //Jacke aktuellieren
    @PutMapping("/{id}")
    public Jacke updateJacke(@PathVariable int id, @RequestBody Jacke updatedJacke) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                jacke.setName(updatedJacke.getName());
                jacke.setBrand(updatedJacke.getBrand());
                jacke.setSize(updatedJacke.getSize());
                jacke.setColor(updatedJacke.getColor());
                jacke.setSeason(updatedJacke.getSeason());
                jacke.setReleaseYear(updatedJacke.getReleaseYear());
                return jacke;
            }
        }
        throw new IllegalArgumentException("Jacke mit ID " + id + " nicht gefunden");
    }
    //löschen👍
    @DeleteMapping("/{id}")
    public String deleteJacke(@PathVariable int id) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                jacken.remove(i);
                return "Jacke mit ID " + id + " gelöscht";
            }
        }
        throw new IllegalArgumentException("Jacke mit ID: " + id + " nicht gefunden");
    }

    //alle Jacken zurückgeben
    @GetMapping
    public List<Jacke> getAllJacken() {
        return jacken;
    }

    //nach ID suchen
    @GetMapping("/{id}")
    public Jacke getJackeById(@PathVariable int id) {
        for (int i = 0; i < jacken.size(); i++) {
            Jacke jacke = jacken.get(i);
            if (jacke.getId() == id) {
                return jacke;
            }
        }
        throw new IllegalArgumentException("Jacke mit ID: " + id + " nicht gefunden");
    }

    //Nach Farbe suchen
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
