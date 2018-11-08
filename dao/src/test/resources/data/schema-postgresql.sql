DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS apartment;
DROP TABLE IF EXISTS guest;
DROP TABLE IF EXISTS hotel;

CREATE TABLE guest (
  guest_id        SERIAL         PRIMARY KEY,
  name            VARCHAR(255)   NOT NULL UNIQUE,
  password        VARCHAR(255)   NOT NULL,
);

CREATE TABLE hotel (
  hotel_id        SERIAL         PRIMARY KEY,
  hotel_name      VARCHAR(255)   NOT NULL,
);

CREATE TABLE apartment (
  apartment_id    SERIAL         PRIMARY KEY,
  room_count      INT            NOT NULL,
  hotel_id        INT            NOT NULL,
  FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id) ON DELETE CASCADE
);

CREATE TABLE reservation (
  reservation_id  SERIAL         PRIMARY KEY,
  guest_id        INT            NOT NULL,
  apartment_id    INT            NOT NULL,
  res_days        Array          NOT NULL,
  start_res_day   VARCHAR(255)   NOT NULL,
  end_res_day     VARCHAR(255)   NOT NULL,
  FOREIGN KEY (guest_id) REFERENCES guest(guest_id) ON DELETE CASCADE ,
  FOREIGN KEY (apartment_id) REFERENCES apartment(apartment_id) ON DELETE CASCADE
);