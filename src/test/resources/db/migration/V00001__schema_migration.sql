CREATE TABLE IF NOT EXISTS agencies
(
    id      serial NOT NULL PRIMARY KEY,
    address character varying(255),
    name    character varying(255)
);

CREATE TABLE IF NOT EXISTS passwordresettoken
(
    id               serial  NOT NULL PRIMARY KEY,
    expiry_date_time timestamp(6) without time zone,
    token            character varying(255),
    user_id          integer NOT NULL
);

CREATE TABLE IF NOT EXISTS realty_objects
(
    id          serial            NOT NULL PRIMARY KEY,
    name        character varying NOT NULL,
    description character varying,
    image_url   character varying,
    user_id     integer,
    price       numeric(38, 2),
    address     character varying(255),
    square      double precision
);

CREATE TABLE IF NOT EXISTS users
(
    id                             serial NOT NULL PRIMARY KEY,
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

ALTER TABLE ONLY passwordresettoken
    ADD CONSTRAINT fkky4yy1iru766swgx0f2v0iv11 FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY realty_objects
    ADD CONSTRAINT realty_objects_users_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE ON DELETE CASCADE;
