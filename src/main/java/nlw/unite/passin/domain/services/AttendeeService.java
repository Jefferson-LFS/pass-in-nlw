package nlw.unite.passin.domain.services;

import lombok.RequiredArgsConstructor;
import nlw.unite.passin.api.dto.attendee.AttendeeBadgeResponseDTO;
import nlw.unite.passin.api.dto.attendee.AttendeeDetailDTO;
import nlw.unite.passin.api.dto.attendee.AttendeesListResponseDTO;
import nlw.unite.passin.api.dto.attendee.AttendeeBadgeDTO;
import nlw.unite.passin.domain.model.attendee.Attendee;
import nlw.unite.passin.domain.model.attendee.exceptions.AttendeeAlreadyExistException;
import nlw.unite.passin.domain.model.attendee.exceptions.AttendeeNotFoundException;
import nlw.unite.passin.domain.model.checkin.CheckIn;
import nlw.unite.passin.domain.repositories.AttendeeRepository;
import nlw.unite.passin.domain.repositories.CheckInRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendees(String eventId) {
        List<Attendee> attendeesList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailsList = attendeesList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetailDTO(attendee.getId(), attendee.getName(), attendee.getEmail(),
                    attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
       Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
       if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeeRepository.findById(attendeeId).
                orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(),uri,attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }
}
