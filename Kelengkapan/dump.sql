--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.3

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

--
-- Name: frequency; Type: TYPE; Schema: public; Owner: rafie.amandio
--

CREATE TYPE public.frequency AS ENUM (
    'Daily',
    'Weekly',
    'Monthly'
);


ALTER TYPE public.frequency OWNER TO "rafie.amandio";

--
-- Name: gender; Type: TYPE; Schema: public; Owner: rafie.amandio
--

CREATE TYPE public.gender AS ENUM (
    'Male',
    'Female'
);


ALTER TYPE public.gender OWNER TO "rafie.amandio";

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: checked_habits; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.checked_habits (
    id integer NOT NULL,
    habit_id uuid NOT NULL,
    checked boolean NOT NULL,
    checked_date date NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.checked_habits OWNER TO "rafie.amandio";

--
-- Name: checked_habits_id_seq; Type: SEQUENCE; Schema: public; Owner: rafie.amandio
--

CREATE SEQUENCE public.checked_habits_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checked_habits_id_seq OWNER TO "rafie.amandio";

--
-- Name: checked_habits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rafie.amandio
--

ALTER SEQUENCE public.checked_habits_id_seq OWNED BY public.checked_habits.id;


--
-- Name: goals; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.goals (
    goalid uuid NOT NULL,
    userid uuid NOT NULL,
    goalname character varying(255) NOT NULL,
    description text,
    targetdate date
);


ALTER TABLE public.goals OWNER TO "rafie.amandio";

--
-- Name: habit_points; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.habit_points (
    points_id integer NOT NULL,
    user_id uuid,
    habit_id uuid,
    points integer DEFAULT 0
);


ALTER TABLE public.habit_points OWNER TO "rafie.amandio";

--
-- Name: habit_points_points_id_seq; Type: SEQUENCE; Schema: public; Owner: rafie.amandio
--

CREATE SEQUENCE public.habit_points_points_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.habit_points_points_id_seq OWNER TO "rafie.amandio";

--
-- Name: habit_points_points_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rafie.amandio
--

ALTER SEQUENCE public.habit_points_points_id_seq OWNED BY public.habit_points.points_id;


--
-- Name: habitfrequency; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.habitfrequency (
    frequency_id integer NOT NULL,
    habit_id uuid,
    day_of_week integer
);


ALTER TABLE public.habitfrequency OWNER TO "rafie.amandio";

--
-- Name: habitfrequency_frequency_id_seq; Type: SEQUENCE; Schema: public; Owner: rafie.amandio
--

CREATE SEQUENCE public.habitfrequency_frequency_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.habitfrequency_frequency_id_seq OWNER TO "rafie.amandio";

--
-- Name: habitfrequency_frequency_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rafie.amandio
--

ALTER SEQUENCE public.habitfrequency_frequency_id_seq OWNED BY public.habitfrequency.frequency_id;


--
-- Name: habits; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.habits (
    habitid uuid NOT NULL,
    goalid uuid NOT NULL,
    habitname character varying(255) NOT NULL,
    description text,
    startdate date
);


ALTER TABLE public.habits OWNER TO "rafie.amandio";

--
-- Name: user_relationships; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.user_relationships (
    relationship_id integer NOT NULL,
    user_id1 uuid NOT NULL,
    user_id2 uuid NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.user_relationships OWNER TO "rafie.amandio";

--
-- Name: user_relationships_relationship_id_seq; Type: SEQUENCE; Schema: public; Owner: rafie.amandio
--

CREATE SEQUENCE public.user_relationships_relationship_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_relationships_relationship_id_seq OWNER TO "rafie.amandio";

--
-- Name: user_relationships_relationship_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: rafie.amandio
--

ALTER SEQUENCE public.user_relationships_relationship_id_seq OWNED BY public.user_relationships.relationship_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: rafie.amandio
--

CREATE TABLE public.users (
    userid uuid NOT NULL,
    username character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    gender public.gender NOT NULL
);


ALTER TABLE public.users OWNER TO "rafie.amandio";

--
-- Name: checked_habits id; Type: DEFAULT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.checked_habits ALTER COLUMN id SET DEFAULT nextval('public.checked_habits_id_seq'::regclass);


--
-- Name: habit_points points_id; Type: DEFAULT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habit_points ALTER COLUMN points_id SET DEFAULT nextval('public.habit_points_points_id_seq'::regclass);


--
-- Name: habitfrequency frequency_id; Type: DEFAULT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habitfrequency ALTER COLUMN frequency_id SET DEFAULT nextval('public.habitfrequency_frequency_id_seq'::regclass);


--
-- Name: user_relationships relationship_id; Type: DEFAULT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.user_relationships ALTER COLUMN relationship_id SET DEFAULT nextval('public.user_relationships_relationship_id_seq'::regclass);


--
-- Data for Name: checked_habits; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.checked_habits (id, habit_id, checked, checked_date, created_at, updated_at) FROM stdin;
3	5c027822-3f68-48e7-9b62-4d09673cda62	t	2023-06-18	2023-06-18 19:14:06.069582	2023-06-18 19:14:06.069582
4	83616343-e70b-4928-a972-8cb4d2a09add	f	2023-06-18	2023-06-18 23:55:00.331948	2023-06-18 23:55:00.331948
5	5c027822-3f68-48e7-9b62-4d09673cda62	t	2023-06-19	2023-06-19 00:54:01.278556	2023-06-19 00:54:01.278556
6	83616343-e70b-4928-a972-8cb4d2a09add	t	2023-06-19	2023-06-19 01:03:42.840915	2023-06-19 01:03:42.840915
\.


--
-- Data for Name: goals; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.goals (goalid, userid, goalname, description, targetdate) FROM stdin;
886ce816-b4a5-4e56-aaa9-18965f078c8e	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	Jadi Presiden	aku mau jadi presiden	2023-07-22
54e2f0d3-9e7a-4200-a784-dd496f54d0e8	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	Jadi Wapres	Jadi Wapres Juga	2023-07-22
b461e12a-f485-436e-9bfc-9bea21113c1f	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	goals new	bshsshaj	2023-07-04
f4402b52-60a0-43c9-8fa8-7399c7cb31a8	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	aTes	Tes baru	2023-07-20
\.


--
-- Data for Name: habit_points; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.habit_points (points_id, user_id, habit_id, points) FROM stdin;
1	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	83616343-e70b-4928-a972-8cb4d2a09add	1
2	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	83616343-e70b-4928-a972-8cb4d2a09add	-1
3	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	5c027822-3f68-48e7-9b62-4d09673cda62	1
4	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	5c027822-3f68-48e7-9b62-4d09673cda62	-1
5	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	5c027822-3f68-48e7-9b62-4d09673cda62	1
6	31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	83616343-e70b-4928-a972-8cb4d2a09add	1
\.


--
-- Data for Name: habitfrequency; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.habitfrequency (frequency_id, habit_id, day_of_week) FROM stdin;
4	5c027822-3f68-48e7-9b62-4d09673cda62	1
5	5c027822-3f68-48e7-9b62-4d09673cda62	2
6	5c027822-3f68-48e7-9b62-4d09673cda62	3
7	83616343-e70b-4928-a972-8cb4d2a09add	1
9	83616343-e70b-4928-a972-8cb4d2a09add	2
10	83616343-e70b-4928-a972-8cb4d2a09add	7
8	83616343-e70b-4928-a972-8cb4d2a09add	5
11	83616343-e70b-4928-a972-8cb4d2a09add	3
\.


--
-- Data for Name: habits; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.habits (habitid, goalid, habitname, description, startdate) FROM stdin;
5c027822-3f68-48e7-9b62-4d09673cda62	b461e12a-f485-436e-9bfc-9bea21113c1f	yahwhwhs\nshhshaah	Belajar 5 menit	2023-06-21
83616343-e70b-4928-a972-8cb4d2a09add	886ce816-b4a5-4e56-aaa9-18965f078c8e	cyctc	gxgc	2023-06-18
\.


--
-- Data for Name: user_relationships; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.user_relationships (relationship_id, user_id1, user_id2, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: rafie.amandio
--

COPY public.users (userid, username, email, password, gender) FROM stdin;
31119dab-e7f5-4d0b-ba6f-ad7dbf3fe88b	rafie	rafieamandio@gmail.com	$2b$10$GgF073lgGMdv7uIi4V8rl.yc0NlhBU94Tfk3XaTpqZWyC.HjaaHoy	Male
3220bf7f-d45e-4274-b862-092aa0cfda6d	handa	handaneswari@gmail.com	$2b$10$sAJshykyweVvCI2W/bME5.YDjAfgz649Zw4Io53ESH.DX.xifspv2	Female
d1cd64f3-97fa-4361-b1dc-4c53ac7f7a7c	bintang	bintang@gmail.com	$2b$10$xlwqtOvNDnQNP0EY8zT60ubUeEcDPPqv4v2l5lF/snViZ0V6Yy4uq	Male
\.


--
-- Name: checked_habits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rafie.amandio
--

SELECT pg_catalog.setval('public.checked_habits_id_seq', 6, true);


--
-- Name: habit_points_points_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rafie.amandio
--

SELECT pg_catalog.setval('public.habit_points_points_id_seq', 6, true);


--
-- Name: habitfrequency_frequency_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rafie.amandio
--

SELECT pg_catalog.setval('public.habitfrequency_frequency_id_seq', 11, true);


--
-- Name: user_relationships_relationship_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rafie.amandio
--

SELECT pg_catalog.setval('public.user_relationships_relationship_id_seq', 1, false);


--
-- Name: checked_habits checked_habits_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.checked_habits
    ADD CONSTRAINT checked_habits_pkey PRIMARY KEY (id);


--
-- Name: goals goals_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.goals
    ADD CONSTRAINT goals_pkey PRIMARY KEY (goalid);


--
-- Name: habit_points habit_points_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habit_points
    ADD CONSTRAINT habit_points_pkey PRIMARY KEY (points_id);


--
-- Name: habitfrequency habitfrequency_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habitfrequency
    ADD CONSTRAINT habitfrequency_pkey PRIMARY KEY (frequency_id);


--
-- Name: habits habits_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habits
    ADD CONSTRAINT habits_pkey PRIMARY KEY (habitid);


--
-- Name: user_relationships user_relationships_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.user_relationships
    ADD CONSTRAINT user_relationships_pkey PRIMARY KEY (relationship_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: checked_habits checked_habits_habit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.checked_habits
    ADD CONSTRAINT checked_habits_habit_id_fkey FOREIGN KEY (habit_id) REFERENCES public.habits(habitid);


--
-- Name: goals goals_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.goals
    ADD CONSTRAINT goals_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(userid);


--
-- Name: habit_points habit_points_habit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habit_points
    ADD CONSTRAINT habit_points_habit_id_fkey FOREIGN KEY (habit_id) REFERENCES public.habits(habitid);


--
-- Name: habit_points habit_points_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habit_points
    ADD CONSTRAINT habit_points_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(userid);


--
-- Name: habitfrequency habitfrequency_habit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habitfrequency
    ADD CONSTRAINT habitfrequency_habit_id_fkey FOREIGN KEY (habit_id) REFERENCES public.habits(habitid) ON DELETE CASCADE;


--
-- Name: habits habits_goalid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.habits
    ADD CONSTRAINT habits_goalid_fkey FOREIGN KEY (goalid) REFERENCES public.goals(goalid);


--
-- Name: user_relationships user_relationships_user_id1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.user_relationships
    ADD CONSTRAINT user_relationships_user_id1_fkey FOREIGN KEY (user_id1) REFERENCES public.users(userid);


--
-- Name: user_relationships user_relationships_user_id2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: rafie.amandio
--

ALTER TABLE ONLY public.user_relationships
    ADD CONSTRAINT user_relationships_user_id2_fkey FOREIGN KEY (user_id2) REFERENCES public.users(userid);


--
-- PostgreSQL database dump complete
--

