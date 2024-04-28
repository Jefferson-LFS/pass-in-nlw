package nlw.unite.passin.domain.services;

import lombok.RequiredArgsConstructor;
import nlw.unite.passin.api.dto.attendee.AttendeeIdDTO;
import nlw.unite.passin.api.dto.attendee.AttendeeRequestDTO;
import nlw.unite.passin.api.dto.event.EventIdDTO;
import nlw.unite.passin.api.dto.event.EventRequestDTO;
import nlw.unite.passin.api.dto.event.EventResponseDTO;
import nlw.unite.passin.domain.model.attendee.Attendee;
import nlw.unite.passin.domain.model.event.Event;
import nlw.unite.passin.domain.model.event.exceptions.EventFullException;
import nlw.unite.passin.domain.model.event.exceptions.EventNotFoundException;
import nlw.unite.passin.domain.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetails(String eventId) {

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();

        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());


    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }
    private String createSlug(String text){

        String normalizedText;

        normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+","-")
                .toLowerCase();

        return normalizedText;
    }

}
