package com.evgen.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationDao;
import com.evgen.mapper.GuestRowMapper;
import com.evgen.mapper.ReservationRowMapper;

@Component
@PropertySource(value = "classpath:sql.properties")
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

  @Value("${ReservationDaoSql.getGuestById}")
  private String getGuestSql;

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

    namedParameterJdbcTemplate.update(createReservationSql, parameterSource);
    return namedParameterJdbcTemplate.queryForObject(getGuestSql, parameterSource, new GuestRowMapper());
  }

  @Override
  public Guest updateReservation(Reservation reservation, String guestId) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue(GUEST_ID, Integer.parseInt(guestId));
    parameterSource.addValue(RESERVATION_ID, Integer.parseInt(reservation.getReservationId()));
    parameterSource.addValue(APARTMENT_ID, Integer.parseInt(reservation.getApartmentNumber()));
    parameterSource.addValue(RES_DAYS, reservation.getReservationDay().stream().mapToInt(Math::toIntExact).toArray());
    parameterSource.addValue(START_RES_DAY, reservation.getStartReservationDay());
    parameterSource.addValue(END_RES_DAY, reservation.getEndReservationDay());

    namedParameterJdbcTemplate.update(updateReservationSql, parameterSource);
    return namedParameterJdbcTemplate.queryForObject(getGuestSql, parameterSource, new GuestRowMapper());
  }

  @Override
  public Guest deleteReservation(String reservationId, String guestId) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue(RESERVATION_ID, Integer.parseInt(reservationId));
    parameterSource.addValue(GUEST_ID, Integer.parseInt(guestId));

    namedParameterJdbcTemplate.update(deleteReservationSql, parameterSource);

    return namedParameterJdbcTemplate.queryForObject(getGuestSql, parameterSource, new GuestRowMapper());
  }
}