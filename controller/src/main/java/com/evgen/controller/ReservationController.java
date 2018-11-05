package com.evgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.service.ReservationService;

@CrossOrigin
@RestController
public class ReservationController {

  private final ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping("/reservations")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Guest createReservation(@RequestBody ReservationRequest reservationRequest) {
    return reservationService.createReservation(reservationRequest);
  }

  @GetMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Reservation retrieveReservation(@PathVariable("id") Integer id) {
    return reservationService.retrieveReservation(id);
  }

  @PutMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ResponseBody
  public Guest updateReservation(@PathVariable("id") Integer reservationId,
      @RequestBody ReservationRequest reservationRequest) {
    return reservationService.updateReservation(reservationId, reservationRequest);
  }

  @DeleteMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Guest deleteReservation(@PathVariable("id") String id,
      @RequestHeader(value = "guestId") String guestId) {
    return reservationService.deleteReservation(id, guestId);
  }
}