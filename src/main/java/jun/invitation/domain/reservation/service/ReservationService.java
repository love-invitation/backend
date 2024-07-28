package jun.invitation.domain.reservation.service;

import jun.invitation.domain.reservation.dao.ReservationRepository;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.reservation.dto.DateReqDto;
import jun.invitation.domain.reservation.dto.PlaceReqDto;
import jun.invitation.global.utils.PointUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void update(PlaceReqDto place, DateReqDto booking, Reservation reservation) {

        reservation.updatePlace(
                place.getName(),
                place.getAddress(),
                place.getDetail(),
                PointUtils.PointConvert(
                        place.getLongitude(),
                        place.getLatitude()
                )
        );

        reservation.updateBooking(
                booking.getDate(),
                booking.getDateType()
        );
    }

    public Reservation create(DateReqDto booking, PlaceReqDto place) {
        return new Reservation(
                place.getName(),
                place.getDetail(),
                place.getAddress(),
                PointUtils.PointConvert(
                        place.getLongitude(),
                        place.getLatitude()
                ),
                booking.getDate(),
                booking.getDateType()
        );
    }
}
