package com.evgen.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.dao.ReservationDao;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationDao reservationDao;

  @Autowired
  public ReservationServiceImpl(ReservationDao reservationDao) {
    this.reservationDao = reservationDao;
  }

  @Override
  public Integer createReservation(ReservationRequest reservationRequest) {
    Reservation reservation = buildCreateReservation(reservationRequest);

    return reservationDao.createReservation(reservationRequest.getGuestId(),reservation);
  }

  @Override
  public Reservation retrieveReservation(Integer id) {
    return reservationDao.retrieveReservation(id);
  }

  @Override
  public Integer updateReservation(Integer reservationId, ReservationRequest reservationRequest) {
    return reservationDao.updateReservation(buildUpdateReservation(reservationId, reservationRequest));
  }

  @Override
  public Integer deleteReservation(String id, String guestId) {
    return reservationDao.deleteReservation(id);
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

  private Reservation buildCreateReservation(ReservationRequest reservationRequest) {
    return Reservation.builder()
        .apartmentNumber(reservationRequest.getApartmentNumber())
        .startReservationDay(reservationRequest.getStartReservationData())
        .endReservationDay(reservationRequest.getEndReservationData())
        .reservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData())
        )
        .build();
  }

  private Reservation buildUpdateReservation(Integer reservationId, ReservationRequest reservationRequest) {
    return retrieveReservation(reservationId).updater()
        .apartmentNumber(reservationRequest.getApartmentNumber())
        .startReservationDay(reservationRequest.getStartReservationData())
        .endReservationDay(reservationRequest.getEndReservationData())
        .reservationDay(
            getReservationDay(reservationRequest.getStartReservationData(), reservationRequest.getEndReservationData()))
        .build();
  }
}