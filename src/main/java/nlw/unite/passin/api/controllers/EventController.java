package nlw.unite.passin.api.controllers;

import lombok.RequiredArgsConstructor;
import nlw.unite.passin.api.dto.event.EventIdDTO;
import nlw.unite.passin.api.dto.event.EventRequestDTO;
import nlw.unite.passin.api.dto.event.EventResponseDTO;
import nlw.unite.passin.domain.model.event.Event;
import nlw.unite.passin.domain.repositories.EventRepository;
import nlw.unite.passin.domain.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final CompositeUriComponentsContributor mvcUriComponentsContributor;
    private EventRepository eventRepository;
    private final EventService eventService;


    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetails(eventId);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuider){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuider.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

}
