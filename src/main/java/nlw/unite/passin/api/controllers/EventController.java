package nlw.unite.passin.api.controllers;

import lombok.RequiredArgsConstructor;
import nlw.unite.passin.api.dto.attendee.AttendeeIdDTO;
import nlw.unite.passin.api.dto.attendee.AttendeeRequestDTO;
import nlw.unite.passin.api.dto.attendee.AttendeesListResponseDTO;
import nlw.unite.passin.api.dto.event.EventIdDTO;
import nlw.unite.passin.api.dto.event.EventRequestDTO;
import nlw.unite.passin.api.dto.event.EventResponseDTO;
import nlw.unite.passin.domain.services.AttendeeService;
import nlw.unite.passin.domain.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final CompositeUriComponentsContributor mvcUriComponentsContributor;
    private final EventService eventService;
    private final AttendeeService attendeeService;


    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetails(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendees(eventId);
        return ResponseEntity.ok(attendeesListResponse);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuider){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuider.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuider){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuider.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }


}
