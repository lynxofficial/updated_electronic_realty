--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: agencies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agencies (
    agency_id integer NOT NULL,
    address character varying(255),
    agency_name character varying(255)
);


ALTER TABLE public.agencies OWNER TO postgres;

--
-- Name: agencies_agency_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agencies_agency_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.agencies_agency_id_seq OWNER TO postgres;

--
-- Name: agencies_agency_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.agencies_agency_id_seq OWNED BY public.agencies.agency_id;


--
-- Name: passwordresettoken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passwordresettoken (
    id integer NOT NULL,
    expiry_date_time timestamp(6) without time zone,
    token character varying(255),
    user_id integer NOT NULL
);


ALTER TABLE public.passwordresettoken OWNER TO postgres;

--
-- Name: passwordresettoken_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passwordresettoken_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.passwordresettoken_id_seq OWNER TO postgres;

--
-- Name: passwordresettoken_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passwordresettoken_id_seq OWNED BY public.passwordresettoken.id;


--
-- Name: realty_objects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.realty_objects (
    realty_object_id integer NOT NULL,
    realty_object_name character varying NOT NULL,
    description character varying,
    image_url character varying,
    user_id integer,
    price numeric(38,2),
    address character varying(255),
    square double precision
);


ALTER TABLE public.realty_objects OWNER TO postgres;

--
-- Name: realty_objects_realty_object_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.realty_objects_realty_object_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.realty_objects_realty_object_id_seq OWNER TO postgres;

--
-- Name: realty_objects_realty_object_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.realty_objects_realty_object_id_seq OWNED BY public.realty_objects.realty_object_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    email character varying(255),
    enable boolean,
    full_name character varying(255),
    password character varying(255),
    role character varying(255),
    verification_code character varying(255),
    balance numeric(38,2),
    digital_signature character varying(255),
    password_for_digital_signature character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- Name: agencies agency_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agencies ALTER COLUMN agency_id SET DEFAULT nextval('public.agencies_agency_id_seq'::regclass);


--
-- Name: passwordresettoken id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwordresettoken ALTER COLUMN id SET DEFAULT nextval('public.passwordresettoken_id_seq'::regclass);


--
-- Name: realty_objects realty_object_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realty_objects ALTER COLUMN realty_object_id SET DEFAULT nextval('public.realty_objects_realty_object_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- Data for Name: agencies; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.agencies (agency_id, address, agency_name) VALUES (1, 'Test district, 12', 'TestAgency');
INSERT INTO public.agencies (agency_id, address, agency_name) VALUES (2, 'Test district, 13', 'TestSecondAgency');


--
-- Data for Name: passwordresettoken; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.passwordresettoken (id, expiry_date_time, token, user_id) VALUES (6, '2024-05-19 21:23:37.101488', '4a52d191-8a6a-4ce8-a76d-502a0ef89396', 94);
INSERT INTO public.passwordresettoken (id, expiry_date_time, token, user_id) VALUES (8, '2024-05-22 18:23:41.51693', '737ab9a4-9f8a-4592-9228-575c602e925b', 99);
INSERT INTO public.passwordresettoken (id, expiry_date_time, token, user_id) VALUES (9, '2024-06-23 22:52:13.70398', 'e4190173-1510-4c43-92eb-f62ec25d2806', 103);


--
-- Data for Name: realty_objects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.realty_objects (realty_object_id, realty_object_name, description, image_url, user_id, price, address, square) VALUES (82, 'МойОбъектНедвижимости', 'Тестовое описание объекта недвижимости', 'c6b2fbbc86319eb4cce95e711548f82aae35ca9d.png', 103, 10000000.00, 'Пушкина, 10', 39.4);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (94, 'example@example.com', true, 'Горшенин Егор Романович', '$2a$10$CRa3qtPcSX.J/SPoMIh7WeRfrt1WmlmF8qv9YRK/bNDkEB.C.YoKK', 'ROLE_USER', NULL, 10000000.00, NULL, NULL);
INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (103, 'test@test.com', true, 'test123', '$2a$10$vHniGjyX7pl0Vh2uPCrefe4hyovF0qJZXPfW97Tjq.q4RRJ2BsUVW', 'ROLE_USER', NULL, 10000000.00, '〼Ȝ㘑淃躳ぴ瓐죀楗䶬칁嶖ᗼ﬏Ȝ惎ᾄ䣮텆ꬩ溚刷䖼莘㐜걕酼�', '123');
INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (106, '1', false, '1', '$2a$10$p2QHfk9xIJ2jcHDj45CvIeIfEF.MwB8JhJTBlGPyUMym74ZUHQvgK', 'ROLE_USER', '43fff6ac-0054-410e-8ede-baba0659979a', 0.00, NULL, NULL);
INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (99, 'test@example.com', true, 'Горшенин Егор Романович', '$2a$10$QoNMuNQlvzkc1tn0C64g8egGEKFg0fCNBGH.OCeErRdY619s5fbd2', 'ROLE_ADMIN', NULL, 9000000.00, '〽Ȝ糐䐯䱼㩦쳢灷牀깝퐤⨋䥠䌻皩ȝº緜⻠찺咆둞㋆怓槺⍔�厄윾�', '123');
INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (90, 'test1', true, '123', '$2a$10$k/n1piQ6B9shAUHQ0A.nj.D4Y7Es0PYlCnsYnZURATRQECw1ym0pC', 'ROLE_ADMIN', NULL, 500.00, NULL, NULL);
INSERT INTO public.users (user_id, email, enable, full_name, password, role, verification_code, balance, digital_signature, password_for_digital_signature) VALUES (102, '12345', true, 'Egor', '$2a$10$8aX8iM.kIOukH2neaJqRIeBh8ejz9xkuuzG3XpVEU3jp5.TjjSUM.', 'ROLE_USER', NULL, 0.00, NULL, NULL);


--
-- Name: agencies_agency_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agencies_agency_id_seq', 2, true);


--
-- Name: passwordresettoken_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.passwordresettoken_id_seq', 9, true);


--
-- Name: realty_objects_realty_object_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.realty_objects_realty_object_id_seq', 82, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 106, true);


--
-- Name: agencies agencies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agencies
    ADD CONSTRAINT agencies_pkey PRIMARY KEY (agency_id);


--
-- Name: passwordresettoken passwordresettoken_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwordresettoken
    ADD CONSTRAINT passwordresettoken_pkey PRIMARY KEY (id);


--
-- Name: realty_objects realty_objects_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realty_objects
    ADD CONSTRAINT realty_objects_pk PRIMARY KEY (realty_object_id);


--
-- Name: passwordresettoken uk_9dbcvg1tus9vsxrly63i6l16n; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwordresettoken
    ADD CONSTRAINT uk_9dbcvg1tus9vsxrly63i6l16n UNIQUE (user_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: passwordresettoken fkky4yy1iru766swgx0f2v0iv11; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwordresettoken
    ADD CONSTRAINT fkky4yy1iru766swgx0f2v0iv11 FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: realty_objects realty_objects_users_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.realty_objects
    ADD CONSTRAINT realty_objects_users_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

