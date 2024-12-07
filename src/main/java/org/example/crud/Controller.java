package org.example.crud;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    // ...und das ist gut, `final` (bzw alle modifiers) sind in erster Linie als Hilfe für den Dev gedacht.
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
		for (Jacke jacke : jacken) { // wir bevorzugen den for-each loop wo möglich
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
        // IllegalArgumentException wird von spring zu einem HTTP/500 übersetzt. Das ist unüblich.
        // Normalerweise schicken wir für so ein Fall ein HTTP/400 oder HTTP/404.
        throw new JackeNotFoundException("Jacke mit ID " + id + " nicht gefunden");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static final class JackeNotFoundException extends RuntimeException {
        JackeNotFoundException(String message) {
            super(message);
        }
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
