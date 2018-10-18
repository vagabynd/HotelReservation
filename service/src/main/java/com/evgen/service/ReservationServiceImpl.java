package com.evgen.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final GuestRepository guestRepository;
  private final HotelRepository hotelRepository;

  @Autowired
  public ReservationServiceImpl(GuestRepository guestRepository, HotelRepository hotelRepository) {
    this.guestRepository = guestRepository;
    this.hotelRepository = hotelRepository;
  }

  @Override
  public Guest createReservation(ReservationRequest reservationRequest) {
    Guest guest = guestRepository.findByGuestId(reservationRequest.getGuestIdAsObjectId());
    Hotel hotel = hotelRepository.findByHotelName(reservationRequest.getHotelName());
    Reservation reservation = new Reservation(hotel, reservationRequest.getApartmentNumber(), new ObjectId());
    guest.getReservations().add(reservation);
    guestRepository.save(guest);
    return guest;
  }

  @Override
  public Reservation retrieveReservation(ObjectId id) {
    return guestRepository.findAll()
        .stream()
        .map(Guest::getReservations)
        .flatMap(Collection::stream)
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
    List<Reservation> updateReservations = guest.getReservations()
        .stream()
        .filter(o -> !o.getReservationId().equals(reservationId))
        .collect(Collectors.toList());
    updateReservations.add(reservation);
    guest.setReservations(updateReservations);
    guestRepository.save(guest);
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
    return guest;
  }
}
