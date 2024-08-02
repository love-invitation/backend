package jun.invitation.domain.reservation.service;

import jun.invitation.domain.reservation.dao.ReservationRepository;
import jun.invitation.domain.reservation.domain.Booking;
import jun.invitation.domain.reservation.domain.Place;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.reservation.dto.DateReqDto;
import jun.invitation.domain.reservation.dto.PlaceReqDto;
import jun.invitation.global.utils.PointUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void update(PlaceReqDto place, DateReqDto booking, Reservation reservation) {
        updatePlace(place, reservation);
        updateBooking(booking, reservation);
    }

    private static void updateBooking(DateReqDto booking, Reservation reservation) {
        Optional.ofNullable(booking)
                .map(ReservationService::getBooking)
                .ifPresent(reservation::updateBooking);
    }

    private static void updatePlace(PlaceReqDto place, Reservation reservation) {
        Optional.ofNullable(place)
                .map(ReservationService::getPlace)
                .ifPresent(reservation::updatePlace);
    }

    public Reservation create(DateReqDto dateReqDto, PlaceReqDto placeDto) {

        Place place = Optional.ofNullable(placeDto)
                .map(ReservationService::getPlace)
                .orElse(null);

        Booking booking = Optional.ofNullable(dateReqDto)
                .map(ReservationService::getBooking)
                .orElse(null);

        return new Reservation(place, booking);
    }

    private static Booking getBooking(DateReqDto d) {
        return new Booking(d.getDate(), d.getDateType());
    }

    private static Place getPlace(PlaceReqDto p) {
        return new Place(
                p.getName(), p.getDetail(), p.getAddress(),
                PointUtils.PointConvert(
                        p.getLongitude(),
                        p.getLatitude()
                ));
    }
}
