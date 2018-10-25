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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.dao.ReservationDaoImpl;
import com.evgen.dao.ReservationRepository;

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
    validationReservationRequest(reservationRequest);
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    validApartment(reservationRequest, hotel);

    Reservation reservation = createReservationBuild(reservationRequest, hotel);
    Reservation reservationWithId = reservationRepository.save(reservation);

    reservationDao.addReservationToGuest(reservationWithId, reservationRequest.getGuestId());

    return guestRepository.findByGuestId(reservationRequest.getGuestId());
  }

  @Override
  public Reservation retrieveReservation(String id) {
    return reservationRepository.findAll()
        .stream()
        .filter(o -> o.getReservationId().equals(id))
        .findAny()
        .orElseThrow(() -> new ServerWebInputException("there is no reservation"));
  }

  @Override
  public Guest updateReservation(String reservationId, ReservationRequest reservationRequest) {
    validationReservationRequest(reservationRequest);
    reservationRepository.save(updateReservationBuild(reservationId, reservationRequest));

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

  private void validApartment(ReservationRequest reservationRequest, Hotel hotel) {
    Optional.ofNullable(hotel)
        .map(Hotel::getApartments)
        .orElse(new ArrayList<>())
        .stream()
        .filter(o -> o.getApartmentNumber().equals(reservationRequest.getApartmentNumber()))
        .findFirst()
        .orElseThrow(() -> new ServerWebInputException("Apartment should not be null"));
  }

  private Reservation createReservationBuild(ReservationRequest reservationRequest, Hotel hotel) {
    return Reservation.builder()
        .setHotel(hotel)
        .setApartmentNumber(reservationRequest.getApartmentNumber())
        .setStartReservationDay(reservationRequest.getStartReservationData())
        .setEndReservationDay(reservationRequest.getEndReservationData())
        .setReservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData())
        )
        .build();
  }

  private Reservation updateReservationBuild(String reservationId, ReservationRequest reservationRequest) {
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    return retrieveReservation(reservationId).updater()
        .setHotel(hotel)
        .setApartmentNumber(reservationRequest.getApartmentNumber())
        .setStartReservationDay(reservationRequest.getStartReservationData())
        .setEndReservationDay(reservationRequest.getEndReservationData())
        .setReservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData()))
        .build();
  }

  private Guest deleteReservationFromGuestReservations(String id, String guestId) {
    Guest guest = guestRepository.findByGuestId(guestId);
    List<Reservation> reservations = Optional.ofNullable(guest)
        .map(Guest::getReservations)
        .map(list -> list
            .stream()
            .filter(o -> !o.getReservationId().equals(id))
            .collect(Collectors.toList()))
        .orElseThrow(() -> new ServerWebInputException("there are no reservations"));
    guest.setReservations(reservations);
    guestRepository.save(guest);

    return guest;
  }

  private void validationReservationRequest(ReservationRequest reservationRequest) {
    if (StringUtils.isBlank(reservationRequest.getApartmentNumber())
        || StringUtils.isBlank(reservationRequest.getStartReservationData())
        || StringUtils.isBlank(reservationRequest.getEndReservationData())
        || StringUtils.isBlank(reservationRequest.getGuestId())
        || StringUtils.isBlank(reservationRequest.getHotelName())) {
      throw new ServerWebInputException("Bad reservation request");
    }
  }
}