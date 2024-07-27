package jun.invitation.domain.reservation.service;

import jun.invitation.domain.reservation.dao.ReservationRepository;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.reservation.dto.WeddingDateReqDto;
import jun.invitation.domain.reservation.dto.WeddingPlaceReqDto;
import jun.invitation.global.utils.PointUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void update(WeddingPlaceReqDto place, WeddingDateReqDto booking, Reservation reservation) {

        reservation.updatePlace(
                place.getName(),
                place.getAddress(),
                place.getDetail(),
                PointUtils.PointConvert(
                        place.getLongitude(),
                        place.getLongitude()
                )
        );

        reservation.updateBooking(
                booking.getDate(),
                booking.getDateType()
        );
    }

    public Reservation create(WeddingDateReqDto booking, WeddingPlaceReqDto place) {
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
