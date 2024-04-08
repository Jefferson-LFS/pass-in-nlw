package nlw.unite.passin.api.dto.event;

import lombok.Getter;
import nlw.unite.passin.domain.model.event.Event;
@Getter
public class EventResponseDTO {
    EventDetailDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendess){
        this.event = new EventDetailDTO(
                event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendess);
    }
}
