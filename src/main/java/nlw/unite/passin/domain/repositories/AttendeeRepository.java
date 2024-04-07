package nlw.unite.passin.domain.repositories;

import nlw.unite.passin.domain.model.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
