SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

drop owned by queststore;

CREATE TABLE public.artifacts (
    artifact_id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    price integer NOT NULL
);


ALTER TABLE public.artifacts OWNER TO queststore;

CREATE SEQUENCE public.artifacts_artifact_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_artifact_id_seq OWNER TO queststore;


ALTER SEQUENCE public.artifacts_artifact_id_seq OWNER TO queststore;


CREATE TABLE public.artifacts_in_possess (
    id integer NOT NULL,
    artifact_id integer,
    codecooler_id integer,
    used boolean DEFAULT FALSE
);


ALTER TABLE public.artifacts_in_possess OWNER TO queststore;


CREATE SEQUENCE public.artifacts_in_possess_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_in_possess_id_seq OWNER TO queststore;


ALTER SEQUENCE public.artifacts_in_possess_id_seq OWNER TO queststore;


CREATE TABLE public.codecoolers (
    codecooler_id integer NOT NULL,
    coolcoins integer,
    exp_level integer,
    actual_room integer,
    coolcoins_ever_earned integer,
    quest_in_progress integer,
    first_name character varying,
    last_name character varying,
    nickname character varying
);

CREATE SEQUENCE public.codecooler_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.codecooler_id_seq OWNED BY public.queststore;

ALTER TABLE public.codecoolers OWNER TO queststore;


CREATE TABLE public.experience_level (
    id integer NOT NULL,
    level_name character varying NOT NULL,
    threshold integer
);


ALTER TABLE public.experience_level OWNER TO queststore;


CREATE SEQUENCE public.experience_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.experience_level_id_seq OWNER TO queststore;


ALTER SEQUENCE public.experience_level_id_seq OWNED BY public.queststore;


CREATE TABLE public.login_access (
    id integer NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    access_level integer NOT NULL
);

ALTER TABLE public.login_access OWNER TO queststore;

CREATE SEQUENCE public.login_access_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.login_access_id_seq OWNER TO queststore;

ALTER SEQUENCE public.login_access_id_seq OWNED BY queststore;


CREATE TABLE public.quest_completed (
    id integer NOT NULL,
    quest_id integer,
    codecooler_id integer
);


ALTER TABLE public.quest_completed OWNER TO queststore;


CREATE SEQUENCE public.quest_completed_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quest_completed_id_seq OWNER TO queststore;


ALTER SEQUENCE public.quest_completed_id_seq OWNED BY queststore;

CREATE TABLE public.quests (
    quest_id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    reward integer NOT NULL
);


ALTER TABLE public.quests OWNER TO queststore;

CREATE SEQUENCE public.quests_quest_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quests_quest_id_seq OWNER TO queststore;


ALTER SEQUENCE public.quests_quest_id_seq OWNED BY queststore;

CREATE TABLE public.room (
    room_id integer NOT NULL,
    room_name character varying,
    room_description character varying
);


ALTER TABLE public.room OWNER TO queststore;


CREATE SEQUENCE public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.room_room_id_seq OWNER TO queststore;


ALTER SEQUENCE public.room_room_id_seq OWNED BY queststore;


CREATE TABLE public.teams (
    id integer NOT NULL,
    codecooler_id integer,
    team_name character varying
);


ALTER TABLE public.teams OWNER TO queststore;


CREATE SEQUENCE public.teams_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teams_id_seq OWNER TO queststore;

--
ALTER SEQUENCE public.teams_id_seq OWNED BY queststore;


ALTER TABLE ONLY public.artifacts ALTER COLUMN artifact_id SET DEFAULT nextval('public.artifacts_artifact_id_seq'::regclass);

ALTER TABLE public.codecoolers ALTER COLUMN codecooler_id SET DEFAULT nextval('codecooler_id_seq'::regclass);

ALTER TABLE ONLY public.artifacts_in_possess ALTER COLUMN id SET DEFAULT nextval('public.artifacts_in_possess_id_seq'::regclass);

ALTER TABLE ONLY public.experience_level ALTER COLUMN id SET DEFAULT nextval('public.experience_level_id_seq'::regclass);

ALTER TABLE ONLY public.login_access ALTER COLUMN id SET DEFAULT nextval('public.login_access_id_seq'::regclass);

ALTER TABLE ONLY public.quest_completed ALTER COLUMN id SET DEFAULT nextval('public.quest_completed_id_seq'::regclass);

ALTER TABLE ONLY public.quests ALTER COLUMN quest_id SET DEFAULT nextval('public.quests_quest_id_seq'::regclass);

ALTER TABLE ONLY public.room ALTER COLUMN room_id SET DEFAULT nextval('public.room_room_id_seq'::regclass);

ALTER TABLE ONLY public.teams ALTER COLUMN id SET DEFAULT nextval('public.teams_id_seq'::regclass);

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.artifacts
    ADD CONSTRAINT artifacts_pkey PRIMARY KEY (artifact_id);

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_pkey PRIMARY KEY (codecooler_id);

ALTER TABLE ONLY public.experience_level
    ADD CONSTRAINT experience_level_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.login_access
    ADD CONSTRAINT login_access_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.quests
    ADD CONSTRAINT quests_pkey PRIMARY KEY (quest_id);

ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_artifact_id_fkey FOREIGN KEY (artifact_id) REFERENCES public.artifacts(artifact_id);

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_actual_room_fkey FOREIGN KEY (actual_room) REFERENCES public.room(room_id);

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.login_access(id);

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_exp_level_fkey FOREIGN KEY (exp_level) REFERENCES public.experience_level(id);

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_quest_in_progress_fkey FOREIGN KEY (quest_in_progress) REFERENCES public.quests(quest_id);

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_quest_id_fkey FOREIGN KEY (quest_id) REFERENCES public.quests(quest_id);

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);
