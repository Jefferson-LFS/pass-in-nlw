package nlw.unite.passin.domain.model.checkin.exceptions;

public class CheckInAlreadyExistException extends RuntimeException {
    public CheckInAlreadyExistException(String message) {
        super(message);
    }

}
