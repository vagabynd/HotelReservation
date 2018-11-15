package com.evgen.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.ReservationService;

@Component
public class MessageReceiver {

  private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

  private final ReservationService reservationService;
  private final MessageSender messageSender;

  @Autowired
  public MessageReceiver(ReservationService reservationService, MessageSender messageSender) {
    this.reservationService = reservationService;
    this.messageSender = messageSender;
  }

  @JmsListener(destination = "queue-reservation")
  public void receiveMessage(final Message<com.evgen.Message> message) {

    MessageHeaders headers = message.getHeaders();
    com.evgen.Message response = message.getPayload();

    switch (response.getEndPoint()) {
      case "createReservation":
        Guest guest = reservationService.createReservation((ReservationRequest) response.getRequestObject());

        com.evgen.Message createReservationResponse = new com.evgen.Message(response.getId(), response.getEndPoint());
        createReservationResponse.getRequestObject().add(guest);

        messageSender.sendMessage(createReservationResponse);
        break;
      case "deleteReservation":
        Guest guest1 = reservationService.deleteReservation(response.getRequestObject().get(0).toString(),
            response.getRequestObject().get(1).toString());

        com.evgen.Message deleteReservationResponse = new com.evgen.Message(response.getId(), response.getEndPoint());
        deleteReservationResponse.getRequestObject().add(guest1);

        messageSender.sendMessage(deleteReservationResponse);
        break;
      case "retrieveReservation":
        Reservation reservation = reservationService.retrieveReservation(response.getRequestObject().toString());

        com.evgen.Message retrieveReservationResponse = new com.evgen.Message(response.getId(), response.getEndPoint());
        retrieveReservationResponse.getRequestObject().add(reservation);

        messageSender.sendMessage(retrieveReservationResponse);
        break;
      case "updateReservation":
        Guest guest2 = reservationService.updateReservation(response.getRequestObject().get(0).toString(),
            (ReservationRequest) response.getRequestObject().get(1));

        com.evgen.Message updateReservationResponse = new com.evgen.Message(response.getId(), response.getEndPoint());
        updateReservationResponse.getRequestObject().add(guest2);

        messageSender.sendMessage(updateReservationResponse);
        break;
    }

  }
}
