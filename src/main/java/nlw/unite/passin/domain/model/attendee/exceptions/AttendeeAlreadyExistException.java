package nlw.unite.passin.domain.model.attendee.exceptions;

public class AttendeeAlreadyExistException extends RuntimeException {
    public AttendeeAlreadyExistException(String message) {
        super(message);
    }

}
