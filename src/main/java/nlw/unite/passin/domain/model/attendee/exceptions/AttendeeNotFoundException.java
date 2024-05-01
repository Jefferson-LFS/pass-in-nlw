package nlw.unite.passin.domain.model.attendee.exceptions;

public class AttendeeNotFoundException extends RuntimeException {

    public AttendeeNotFoundException(String message) {
        super(message);
    }
}
