package com.evgen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;

@Component
public class ReservationDaoImpl implements ReservationDao {

  private static final String GUEST_ID = "guestId";
  private static final String RESERVATION_ID = "reservationId";
  private static final String APARTMENT_ID = "apartmentId";
  private static final String RES_DAYS = "resDays";
  private static final String START_RES_DAY = "startResDay";
  private static final String END_RES_DAY = "endResDay";

  @Value("${ReservationDaoSql.getReservationById}")
  private String getReservationByIdSql;

  @Value("${ReservationDaoSql.createReservation}")
  private String createReservationSql;

  @Value("${ReservationDaoSql.updateReservation}")
  private String updateReservationSql;

  @Value("${ReservationDaoSql.deleteReservation}")
  private String deleteReservationSql;

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public ReservationDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

  }

  @Override
  public Reservation retrieveReservation(Integer reservationId) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource(RESERVATION_ID, reservationId);

    return namedParameterJdbcTemplate
        .queryForObject(getReservationByIdSql, namedParameters, new ReservationRowMapper());
  }

  @Override
  public Guest createReservation(String guestId, Reservation reservation) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue(GUEST_ID, Integer.parseInt(guestId));
    parameterSource.addValue(APARTMENT_ID, Integer.parseInt(reservation.getApartmentNumber()));
    parameterSource.addValue(RES_DAYS, reservation.getReservationDay().stream().mapToInt(Math::toIntExact).toArray());
    parameterSource.addValue(START_RES_DAY, reservation.getStartReservationDay());
    parameterSource.addValue(END_RES_DAY, reservation.getEndReservationDay());

    return namedParameterJdbcTemplate.queryForObject(createReservationSql, parameterSource, new GuestRowMapper());
  }

  @Override
  public Guest updateReservation(Reservation reservation) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue(RESERVATION_ID, Integer.parseInt(reservation.getReservationId()));
    parameterSource.addValue(APARTMENT_ID, Integer.parseInt(reservation.getApartmentNumber()));
    parameterSource.addValue(RES_DAYS, reservation.getReservationDay().stream().mapToInt(Math::toIntExact).toArray());
    parameterSource.addValue(START_RES_DAY, reservation.getStartReservationDay());
    parameterSource.addValue(END_RES_DAY, reservation.getEndReservationDay());

    return namedParameterJdbcTemplate.queryForObject(updateReservationSql, parameterSource, new GuestRowMapper());
  }

  @Override
  public Guest deleteReservation(String reservationId) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource(RESERVATION_ID, Integer.parseInt(reservationId));

    return namedParameterJdbcTemplate.queryForObject(deleteReservationSql, namedParameters, new GuestRowMapper());
  }

  private class ReservationRowMapper implements RowMapper<Reservation> {

    public Reservation mapRow(ResultSet resultSet, int i) throws SQLException {
      return new Reservation(
          resultSet.getString("reservation_id"),
          new Hotel(
              resultSet.getString("hotel_id"),
              resultSet.getString("hotel_name")
          ),
          resultSet.getString("apartment_id"),
          resultSet.getString("start_res_day"),
          resultSet.getString("end_res_day")
      );
    }

  }

  private class GuestRowMapper implements RowMapper<Guest> {

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
        guest.getReservations().add(new Reservation(
            resultSet.getString("reservation_id"),
            new Hotel(
                resultSet.getString("hotel_id"),
                resultSet.getString("hotel_name")
            ),
            resultSet.getString("apartment_id"),
            resultSet.getString("start_res_day"),
            resultSet.getString("end_res_day")
        ));
      } while (resultSet.next());

      return guest;
    }
  }
}

