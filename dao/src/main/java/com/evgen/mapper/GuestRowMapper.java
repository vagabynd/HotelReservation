package com.evgen.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.evgen.Guest;
import com.evgen.builder.ReservationBuilder;

public class GuestRowMapper implements RowMapper<Guest> {

  public Guest mapRow(ResultSet resultSet, int i) throws SQLException {
    Guest guest = new Guest(
        resultSet.getString("guest_id"),
        resultSet.getString("name"),
        new ArrayList<>()
    );

    if (resultSet.getObject("reservation_id", Integer.class) == null) {
      return guest;
    }

    do {
      guest.getReservations().add(
          ReservationBuilder.buildReservation(resultSet)
      );
    } while (resultSet.next());

    return guest;
  }
}

