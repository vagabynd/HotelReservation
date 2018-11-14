package com.evgen.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.ReservationService;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class ReservationController {

  private final ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @POST
  @Path("/reservations")
  public Response createReservation(ReservationRequest reservationRequest) {
    Guest guest = reservationService.createReservation(reservationRequest);
    return Response.status(Status.OK).entity(guest).build();
  }

  @GET
  @Path("/reservations/{id}")
  public Response retrieveReservation(@PathParam("id") String id) {
    Reservation reservation = reservationService.retrieveReservation(id);
    return Response.status(Status.OK).entity(reservation).build();
  }

  @PUT
  @Path("/reservations/{id}")
  public Response updateReservation(@PathParam("id") String reservationId,
      ReservationRequest reservationRequest) {
    Guest guest = reservationService.updateReservation(reservationId, reservationRequest);
    return Response.status(Status.ACCEPTED).entity(guest).build();
  }

  @DELETE
  @Path("/reservations/{id}")
  public Response deleteReservation(@PathParam("id") String id,
      @HeaderParam(value = "guestId") String guestId) {
    Guest guest = reservationService.deleteReservation(id, guestId);
    return Response.status(Status.OK).entity(guest).build();
  }
}