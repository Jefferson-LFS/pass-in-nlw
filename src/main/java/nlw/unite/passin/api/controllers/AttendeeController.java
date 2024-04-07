package nlw.unite.passin.api.controllers;

import nlw.unite.passin.domain.repositories.AttendeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {
    private AttendeeRepository attendeeRepository;

    @GetMapping
    public ResponseEntity<String> getTeste(){
        return ResponseEntity.ok("sucesso");
    }

}
