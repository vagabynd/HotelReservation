package com.evgen.validator;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ServerWebInputException;

import com.evgen.Hotel;
import com.evgen.ReservationRequest;

public class ApartmentsValidator {

  public static void validationApartment(ReservationRequest reservationRequest, Hotel hotel) {
    Optional.ofNullable(hotel)
        .map(Hotel::getApartments)
        .orElse(new ArrayList<>())
        .stream()
        .filter(
            apartment -> StringUtils.equals(apartment.getApartmentNumber(), reservationRequest.getApartmentNumber()))
        .findFirst()
        .orElseThrow(() -> new ServerWebInputException("Apartment should not be null"));
  }
}
