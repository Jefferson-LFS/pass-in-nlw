package nlw.unite.passin.config;

import nlw.unite.passin.api.dto.general.ErrorResponseDTO;
import nlw.unite.passin.domain.model.attendee.exceptions.AttendeeAlreadyExistException;
import nlw.unite.passin.domain.model.attendee.exceptions.AttendeeNotFoundException;
import nlw.unite.passin.domain.model.checkin.exceptions.CheckInAlreadyExistException;
import nlw.unite.passin.domain.model.event.exceptions.EventFullException;
import nlw.unite.passin.domain.model.event.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistException.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
