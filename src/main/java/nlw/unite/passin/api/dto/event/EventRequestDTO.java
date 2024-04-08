package nlw.unite.passin.api.dto.event;


public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees
) {

}
