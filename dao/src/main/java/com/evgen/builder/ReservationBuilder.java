package com.evgen.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.evgen.Hotel;
import com.evgen.Reservation;

public class ReservationBuilder {

  public static Reservation buildReservation(ResultSet resultSet) throws SQLException {
    return Reservation.builder()
        .reservationId(resultSet.getString("reservation_id"))
        .hotel(new Hotel(
            resultSet.getString("hotel_id"),
            resultSet.getString("hotel_name")
        ))
        .apartmentNumber(resultSet.getString("apartment_id"))
        .startReservationDay(resultSet.getString("start_res_day"))
        .endReservationDay(resultSet.getString("end_res_day"))
        .build();
  }
}
