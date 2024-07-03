CREATE TABLE IF NOT EXISTS agencies
(
    agency_id   integer NOT NULL PRIMARY KEY,
    address     character varying(255),
    agency_name character varying(255)
);

CREATE TABLE IF NOT EXISTS passwordresettoken
(
    id               integer NOT NULL PRIMARY KEY,
    expiry_date_time timestamp(6) without time zone,
    token            character varying(255),
    user_id          integer NOT NULL
);

CREATE TABLE IF NOT EXISTS realty_objects
(
    realty_object_id   integer           NOT NULL PRIMARY KEY,
    realty_object_name character varying NOT NULL,
    description        character varying,
    image_url          character varying,
    user_id            integer,
    price              numeric(38, 2),
    address            character varying(255),
    square             double precision
);

CREATE TABLE IF NOT EXISTS users
(
    user_id                        integer NOT NULL PRIMARY KEY,
    email                          character varying(255),
    enable                         boolean,
    full_name                      character varying(255),
    password                       character varying(255),
    role                           character varying(255),
    verification_code              character varying(255),
    balance                        numeric(38, 2),
    digital_signature              character varying(255),
    password_for_digital_signature character varying(255)
);

INSERT INTO agencies (agency_id, address, agency_name)
VALUES (1, 'Test district, 12', 'TestAgency');
INSERT INTO agencies (agency_id, address, agency_name)
VALUES (2, 'Test district, 13', 'TestSecondAgency');

INSERT INTO passwordresettoken (id, expiry_date_time, token, user_id)
VALUES (6, '2024-05-19 21:23:37.101488', '4a52d191-8a6a-4ce8-a76d-502a0ef89396', 94);
INSERT INTO passwordresettoken (id, expiry_date_time, token, user_id)
VALUES (8, '2024-05-22 18:23:41.51693', '737ab9a4-9f8a-4592-9228-575c602e925b', 99);
INSERT INTO passwordresettoken (id, expiry_date_time, token, user_id)
VALUES (9, '2024-06-23 22:52:13.70398', 'e4190173-1510-4c43-92eb-f62ec25d2806', 103);

INSERT INTO realty_objects (realty_object_id, realty_object_name, description, image_url, user_id, price,
                            address, square)
VALUES (82, 'МойОбъектНедвижимости', 'Тестовое описание объекта недвижимости',
        'c6b2fbbc86319eb4cce95e711548f82aae35ca9d.png', 103, 10000000.00, 'Пушкина, 10', 39.4);

INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (94, 'example@example.com', true, 'Горшенин Егор Романович',
        '$2a$10$CRa3qtPcSX.J/SPoMIh7WeRfrt1WmlmF8qv9YRK/bNDkEB.C.YoKK', 'ROLE_USER', NULL, 10000000.00, NULL, NULL);
INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (103, 'test@test.com', true, 'test123', '$2a$10$vHniGjyX7pl0Vh2uPCrefe4hyovF0qJZXPfW97Tjq.q4RRJ2BsUVW',
        'ROLE_USER', NULL, 10000000.00, '〼Ȝ㘑淃躳ぴ瓐죀楗䶬칁嶖ᗼ﬏Ȝ惎ᾄ䣮텆ꬩ溚刷䖼莘㐜걕酼�', '123');
INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (106, '1', false, '1', '$2a$10$p2QHfk9xIJ2jcHDj45CvIeIfEF.MwB8JhJTBlGPyUMym74ZUHQvgK', 'ROLE_USER',
        '43fff6ac-0054-410e-8ede-baba0659979a', 0.00, NULL, NULL);
INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (99, 'test@example.com', true, 'Горшенин Егор Романович',
        '$2a$10$QoNMuNQlvzkc1tn0C64g8egGEKFg0fCNBGH.OCeErRdY619s5fbd2', 'ROLE_ADMIN', NULL, 9000000.00,
        '〽Ȝ糐䐯䱼㩦쳢灷牀깝퐤⨋䥠䌻皩ȝº緜⻠찺咆둞㋆怓槺⍔�厄윾�', '123');
INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (90, 'test1', true, '123', '$2a$10$k/n1piQ6B9shAUHQ0A.nj.D4Y7Es0PYlCnsYnZURATRQECw1ym0pC', 'ROLE_ADMIN', NULL,
        500.00, NULL, NULL);
INSERT INTO users (user_id, email, enable, full_name, password, role, verification_code, balance,
                   digital_signature, password_for_digital_signature)
VALUES (102, '12345', true, 'Egor', '$2a$10$8aX8iM.kIOukH2neaJqRIeBh8ejz9xkuuzG3XpVEU3jp5.TjjSUM.', 'ROLE_USER', NULL,
        0.00, NULL, NULL);

ALTER TABLE ONLY passwordresettoken
    ADD CONSTRAINT fkky4yy1iru766swgx0f2v0iv11 FOREIGN KEY (user_id) REFERENCES public.users (user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY realty_objects
    ADD CONSTRAINT realty_objects_users_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (user_id) ON UPDATE CASCADE ON DELETE CASCADE;
