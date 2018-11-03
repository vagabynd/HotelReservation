package com.evgen.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;

@Component
public class ReservationDaoImpl implements ReservationDao {

  @Autowired
  public ReservationDaoImpl( ) {
  }

  @Override
  public Optional<Guest> findByGuestId(String guestId) {
    return Optional.empty();
  }

  @Override
  public Optional<Hotel> findByHotelName(String hotelName) {
    return Optional.empty();
  }

  @Override
  public List<Reservation> deleteByReservationId(String reservationId) {
    return null;
  }

  @Override
  public Optional<Reservation> findByReservationId(String reservationId) {
    return Optional.empty();
  }

  public void addReservationToGuest(Reservation reservation, String guestId) {

  }
}