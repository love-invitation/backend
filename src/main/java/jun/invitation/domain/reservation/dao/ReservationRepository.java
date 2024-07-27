package jun.invitation.domain.reservation.dao;

import jun.invitation.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
