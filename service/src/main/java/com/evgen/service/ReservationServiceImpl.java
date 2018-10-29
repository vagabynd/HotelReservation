package com.evgen.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.ReservationService;
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.dao.ReservationDaoImpl;
import com.evgen.dao.ReservationRepository;
import com.evgen.validator.ApartmentsValidator;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final GuestRepository guestRepository;
  private final HotelRepository hotelRepository;
  private final ReservationRepository reservationRepository;
  private final ReservationDaoImpl reservationDao;

  @Autowired
  public ReservationServiceImpl(GuestRepository guestRepository, HotelRepository hotelRepository,
      ReservationRepository reservationRepository, ReservationDaoImpl reservationDao) {
    this.guestRepository = guestRepository;
    this.hotelRepository = hotelRepository;
    this.reservationRepository = reservationRepository;
    this.reservationDao = reservationDao;
  }

  @Override
  public Guest createReservation(ReservationRequest reservationRequest) {
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    ApartmentsValidator.validationApartment(reservationRequest, hotel);

    Reservation reservation = buildCreateReservation(reservationRequest, hotel);
    Reservation reservationWithId = reservationRepository.save(reservation);

    reservationDao.addReservationToGuest(reservationWithId, reservationRequest.getGuestId());

    return guestRepository.findByGuestId(reservationRequest.getGuestId());
  }

  @Override
  public Reservation retrieveReservation(String id) {
    return reservationRepository.findByReservationId(id);
  }

  @Override
  public Guest updateReservation(String reservationId, ReservationRequest reservationRequest) {
    reservationRepository.save(buildUpdateReservation(reservationId, reservationRequest));

    return guestRepository.findByGuestId(reservationRequest.getGuestId());
  }

  @Override
  public Guest deleteReservation(String id, String guestId) {
    Guest guest = deleteReservationFromGuestReservations(id, guestId);
    reservationRepository.deleteByReservationId(id);

    return guest;
  }

  private List<Long> getReservationDay(String startReservationDate, String endReservationDate) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    try {
      Date startDate = format.parse(startReservationDate);
      Date endDate = format.parse(endReservationDate);
      List<Long> reservationDay = new ArrayList<>();
      for (long i = TimeUnit.MILLISECONDS.toDays(startDate.getTime());
          i < TimeUnit.MILLISECONDS.toDays(endDate.getTime()); i++) {
        reservationDay.add(i);
      }
      return reservationDay;
    } catch (ParseException e) {
      throw new RuntimeException("Bad date format");
    }
  }

  private Reservation buildCreateReservation(ReservationRequest reservationRequest, Hotel hotel) {
    return Reservation.builder()
        .hotel(hotel)
        .apartmentNumber(reservationRequest.getApartmentNumber())
        .startReservationDay(reservationRequest.getStartReservationData())
        .endReservationDay(reservationRequest.getEndReservationData())
        .reservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData())
        )
        .build();
  }

  private Reservation buildUpdateReservation(String reservationId, ReservationRequest reservationRequest) {
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    return retrieveReservation(reservationId).updater()
        .hotel(hotel)
        .apartmentNumber(reservationRequest.getApartmentNumber())
        .startReservationDay(reservationRequest.getStartReservationData())
        .endReservationDay(reservationRequest.getEndReservationData())
        .reservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData()))
        .build();
  }

  private Guest deleteReservationFromGuestReservations(String id, String guestId) {
    Guest guest = guestRepository.findByGuestId(guestId);
    List<Reservation> reservations = getReservations(id, guest);

    guest.setReservations(reservations);
    guestRepository.save(guest);

    return guest;
  }

  private List<Reservation> getReservations(String id, Guest guest) {
    return Optional.ofNullable(guest)
        .map(Guest::getReservations)
        .map(List::stream)
        .orElse(Stream.empty())
        .filter(reservation -> !StringUtils.equals(reservation.getReservationId(), id))
        .collect(Collectors.toList());
  }
}