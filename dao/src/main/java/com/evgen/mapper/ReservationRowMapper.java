package com.evgen.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.evgen.Reservation;
import com.evgen.builder.ReservationBuilder;

public class ReservationRowMapper implements RowMapper<Reservation> {

  public Reservation mapRow(ResultSet resultSet, int i) throws SQLException {
    return ReservationBuilder.buildReservation(resultSet);
  }

}