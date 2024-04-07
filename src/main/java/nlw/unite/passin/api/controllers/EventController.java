package nlw.unite.passin.api.controllers;

import nlw.unite.passin.domain.repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<String> getTeste(){
        return ResponseEntity.ok("sucesso");
    }
}
