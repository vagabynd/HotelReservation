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
       (2, 2, 2),
       (3, 3, 2),
       (4, 2, 1),
       (5, 3, 1),
       (6, 4, 3),
       (7, 2, 3),
       (8, 2, 3),
       (9, 5, 1),
       (10, 3, 4),
       (11, 2, 3),
       (12, 1, 4);

INSERT INTO reservation (guest_id, apartment_id, res_days, start_res_day, end_res_day)
VALUES (1, 1, (1,2,3), '2015-10-10', '2015-10-13');
