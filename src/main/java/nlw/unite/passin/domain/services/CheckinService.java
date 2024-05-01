package nlw.unite.passin.domain.services;

import lombok.RequiredArgsConstructor;
import nlw.unite.passin.domain.model.attendee.Attendee;
import nlw.unite.passin.domain.model.checkin.CheckIn;
import nlw.unite.passin.domain.model.checkin.exceptions.CheckInAlreadyExistException;
import nlw.unite.passin.domain.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckinService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
       this.verifyCheckInExists(attendee.getId());
        CheckIn newCheckIn = new CheckIn();
       newCheckIn.setAttendee(attendee);
       newCheckIn.setCreatedAt(LocalDateTime.now());
       this.checkInRepository.save(newCheckIn);
   }

    public void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistException("Attendee is already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }

}

