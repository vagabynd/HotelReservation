package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.config.ControllerMockTestConf;
import com.evgen.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerMockTestConf.class)
@WebAppConfiguration
public class ControllerMockTest {

  private static final String RESERVATION = "/Reservation.json";
  private static final String GUEST = "/Guest-with-reservations.json";
  private static final String RESERVATION_REQUEST = "/Reservation-request.json";
  private static final String UPDATE_RESERVATION_REQUEST = "/Update-reservation-request.json";

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ObjectMapper objectMapper;

  @After
  public void tearDown() {
    verify(reservationService);
    reset(reservationService);
  }

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void retrieveReservationTest() throws Exception {
    Reservation reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION), Reservation.class);

    expect(reservationService.retrieveReservation("5bc7340b677aa44e986d19db"))
        .andReturn(reservationRequest);
    replay(reservationService);

    mockMvc.perform(
        get("/reservations/5bc7340b677aa44e986d19db")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void deleteReservationTest() throws Exception {
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(reservationService
        .deleteReservation("5bc7340b677aa44e986d19db", "5bc449c09ddbcd660ac58f07"))
        .andReturn(guest);
    replay(reservationService);

    mockMvc.perform(
        delete("/reservations/5bc7340b677aa44e986d19db")
            .header("guestId", "5bc449c09ddbcd660ac58f07")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void createReservationTest() throws Exception {
    ReservationRequest reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(reservationService
        .createReservation(anyObject()))
        .andReturn(guest);
    replay(reservationService);

    mockMvc.perform(
        post("/reservations")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reservationRequest))
    ).andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  public void updateReservationTest() throws Exception {
    ReservationRequest updateReservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(UPDATE_RESERVATION_REQUEST), ReservationRequest.class);
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(reservationService
        .updateReservation(anyObject(), anyObject()))
        .andReturn(guest);
    replay(reservationService);

    mockMvc.perform(
        put("/reservations/5bc7340b677aa44e986d19db")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateReservationRequest))
    ).andDo(print())
        .andExpect(status().isAccepted());
  }
}