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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evgen.Reservation;
import com.evgen.dao.ReservationDao;

@CrossOrigin
@RestController
public class ReservationController {

  private final ReservationDao service;

  @Autowired
  public ReservationController(ReservationDao service) {
    this.service = service;
  }

  @PostMapping("/reservations")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public void CreateReservation(@RequestBody Reservation reservation) {
    service.createReservation(reservation);
  }

  @GetMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void RetrieveReservation(@RequestBody Reservation reservation,  @PathVariable("id") Integer id) {

  }

  @PutMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ResponseBody
  public void UpdateReservation(@RequestBody Reservation reservation, @PathVariable("id") Integer id) {

  }

  @DeleteMapping("/reservations/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void DeleteReservation(@RequestBody Reservation reservation, @PathVariable("id") Integer id) {

  }
}
