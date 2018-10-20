package com.evgen.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;

import com.evgen.Apartment;
import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.dao.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final GuestRepository guestRepository;
  private final HotelRepository hotelRepository;
  private final ReservationRepository reservationRepository;

  @Autowired
  public ReservationServiceImpl(GuestRepository guestRepository, HotelRepository hotelRepository,
      ReservationRepository reservationRepository) {
    this.guestRepository = guestRepository;
    this.hotelRepository = hotelRepository;
    this.reservationRepository = reservationRepository;
  }

  @Override
  public Guest createReservation(ReservationRequest reservationRequest) {
    Guest guest = guestRepository.findByGuestId(reservationRequest.getGuestIdAsObjectId());
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    Apartment apartment = hotel.getApartments()
        .stream()
        .filter(o -> o.getApartmentNumber().equals(reservationRequest.getApartmentNumber()))
        .findFirst()
        .orElse(null);
    if (apartment == null) throw new ServerWebInputException("Apartment should not be null");

    Reservation reservation = new Reservation(hotel, reservationRequest.getApartmentNumber(), new ObjectId(),
        getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData()));
    guest.getReservations().add(reservation);

    guestRepository.save(guest);
    reservationRepository.save(reservation);

    return guest;
  }

  @Override
  public Reservation retrieveReservation(ObjectId id) {
    return reservationRepository.findAll()
        .stream()
        .filter(o -> o.getReservationId().equals(id))
        .findAny()
        .orElse(null);
  }

  @Override
  public Guest updateReservation(ObjectId reservationId, ReservationRequest reservationRequest) {
    Guest guest = guestRepository.findByGuestId(reservationRequest.getGuestIdAsObjectId());
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());

    Reservation reservation = retrieveReservation(reservationId);
    reservation.setHotel(hotel);
    reservation.setApartmentNumber(reservationRequest.getApartmentNumber());
    reservation.setReservationDay(
        getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData()));
    reservationRepository.save(reservation);

    return guest;
  }

  @Override
  public Guest deleteReservation(ObjectId id, ObjectId guestId) {
    Guest guest = guestRepository.findByGuestId(guestId);
    List<Reservation> reservations = guest.getReservations()
        .stream()
        .filter(o -> !o.getReservationId().equals(id))
        .collect(Collectors.toList());
    guest.setReservations(reservations);
    guestRepository.save(guest);
    reservationRepository.deleteByReservationId(id);
    return guest;
  }

  private List<Long> getReservationDay(String startReservationDate, String endReservationDate) {
    DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
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
      e.printStackTrace();
    }
    return null;
  }
}