INSERT INTO guest (guest_id, name, password)
VALUES (1, 'sergei', '$2a$11$Q7WZYUTPvjz9r4bqkwGhLuugf54lTO5qKgyE.3xT/I/6Ru6yz5POy'),
       (2, 'vadim',  '$2a$11$Q7WZYUTPvjz9r4bqkwGhLuugf54lTO5qKgyE.3xT/I/6Ru6yz5POy'),
       (3, 'den',    '$2a$11$Q7WZYUTPvjz9r4bqkwGhLuugf54lTO5qKgyE.3xT/I/6Ru6yz5POy');

INSERT INTO hotel (hotel_id, hotel_name)
VALUES (1, 'Abc'),
       (2, 'Ter'),
       (3, 'Mir'),
       (4, 'Belarus');

INSERT INTO apartment (apartment_id, room_count, hotel_id)
VALUES (1, 4, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 2, 1),
       (5, 3, 2),
       (6, 4, 2),
       (7, 2, 2),
       (8, 2, 2),
       (9, 5, 3),
       (10, 3, 3),
       (11, 2, 3),
       (12, 1, 4);

INSERT INTO reservation (reservation_id, guest_id, apartment_id, res_days)
VALUES (1, 1, 2, ARRAY[10, 11, 12]),
       (2, 1, 2, ARRAY[10, 11, 12, 13]),
       (3, 1, 3, ARRAY[16, 17, 18, 19]),
       (4, 2, 8, ARRAY[1, 2, 3]);