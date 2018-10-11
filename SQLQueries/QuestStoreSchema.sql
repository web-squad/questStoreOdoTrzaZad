drop owned by queststore;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2018-10-10 12:55:27 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12395)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 190 (class 1259 OID 16527)
-- Name: artifacts; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.artifacts (
    artifact_id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    price integer NOT NULL
);


ALTER TABLE public.artifacts OWNER TO queststore;

--
-- TOC entry 189 (class 1259 OID 16525)
-- Name: artifacts_artifact_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.artifacts_artifact_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_artifact_id_seq OWNER TO queststore;

--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 189
-- Name: artifacts_artifact_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.artifacts_artifact_id_seq OWNED BY public.artifacts.artifact_id;


--
-- TOC entry 197 (class 1259 OID 16613)
-- Name: artifacts_in_possess; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.artifacts_in_possess (
    id integer NOT NULL,
    artifact_id integer,
    codecooler_id integer,
    used boolean DEFAULT FALSE
);


ALTER TABLE public.artifacts_in_possess OWNER TO queststore;

--
-- TOC entry 196 (class 1259 OID 16611)
-- Name: artifacts_in_possess_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.artifacts_in_possess_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artifacts_in_possess_id_seq OWNER TO queststore;

--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 196
-- Name: artifacts_in_possess_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.artifacts_in_possess_id_seq OWNED BY public.artifacts_in_possess.id;


--
-- TOC entry 191 (class 1259 OID 16549)
-- Name: codecoolers; Type: TABLE; Schema: public; Owner: queststore
--

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

ALTER SEQUENCE public.codecooler_id_seq OWNED BY public.codecoolers.codecooler_id;


ALTER TABLE public.codecoolers OWNER TO queststore;

--
-- TOC entry 184 (class 1259 OID 16497)
-- Name: experience_level; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.experience_level (
    id integer NOT NULL,
    level_name character varying NOT NULL,
    threshold integer
);


ALTER TABLE public.experience_level OWNER TO queststore;

--
-- TOC entry 183 (class 1259 OID 16495)
-- Name: experience_level_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.experience_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.experience_level_id_seq OWNER TO queststore;

--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 183
-- Name: experience_level_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.experience_level_id_seq OWNED BY public.experience_level.id;


--
-- TOC entry 182 (class 1259 OID 16486)
-- Name: login_access; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.login_access (
    id integer NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    access_level integer NOT NULL
);


ALTER TABLE public.login_access OWNER TO queststore;

--
-- TOC entry 181 (class 1259 OID 16484)
-- Name: login_access_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.login_access_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.login_access_id_seq OWNER TO queststore;

--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 181
-- Name: login_access_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.login_access_id_seq OWNED BY public.login_access.id;


--
-- TOC entry 195 (class 1259 OID 16595)
-- Name: quest_completed; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.quest_completed (
    id integer NOT NULL,
    quest_id integer,
    codecooler_id integer
);


ALTER TABLE public.quest_completed OWNER TO queststore;

--
-- TOC entry 194 (class 1259 OID 16593)
-- Name: quest_completed_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.quest_completed_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quest_completed_id_seq OWNER TO queststore;

--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 194
-- Name: quest_completed_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.quest_completed_id_seq OWNED BY public.quest_completed.id;


--
-- TOC entry 188 (class 1259 OID 16516)
-- Name: quests; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.quests (
    quest_id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    reward integer NOT NULL
);


ALTER TABLE public.quests OWNER TO queststore;

--
-- TOC entry 187 (class 1259 OID 16514)
-- Name: quests_quest_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.quests_quest_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quests_quest_id_seq OWNER TO queststore;

--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 187
-- Name: quests_quest_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.quests_quest_id_seq OWNED BY public.quests.quest_id;


--
-- TOC entry 186 (class 1259 OID 16505)
-- Name: room; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.room (
    room_id integer NOT NULL,
    room_name character varying,
    room_description character varying,
    assigned_mentor character varying
);


ALTER TABLE public.room OWNER TO queststore;

--
-- TOC entry 185 (class 1259 OID 16503)
-- Name: room_room_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.room_room_id_seq OWNER TO queststore;

--
-- TOC entry 2235 (class 0 OID 0)
-- Dependencies: 185
-- Name: room_room_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.room_room_id_seq OWNED BY public.room.room_id;


--
-- TOC entry 193 (class 1259 OID 16579)
-- Name: teams; Type: TABLE; Schema: public; Owner: queststore
--

CREATE TABLE public.teams (
    id integer NOT NULL,
    codecooler_id integer,
    team_name character varying
);


ALTER TABLE public.teams OWNER TO queststore;

--
-- TOC entry 192 (class 1259 OID 16577)
-- Name: teams_id_seq; Type: SEQUENCE; Schema: public; Owner: queststore
--

CREATE SEQUENCE public.teams_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teams_id_seq OWNER TO queststore;

--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 192
-- Name: teams_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: queststore
--

ALTER SEQUENCE public.teams_id_seq OWNED BY public.teams.id;


--
-- TOC entry 2075 (class 2604 OID 16530)
-- Name: artifact_id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts ALTER COLUMN artifact_id SET DEFAULT nextval('public.artifacts_artifact_id_seq'::regclass);


--
-- TOC entry 2078 (class 2604 OID 16616)
-- Name: id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts_in_possess ALTER COLUMN id SET DEFAULT nextval('public.artifacts_in_possess_id_seq'::regclass);


--
-- TOC entry 2072 (class 2604 OID 16500)
-- Name: id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.experience_level ALTER COLUMN id SET DEFAULT nextval('public.experience_level_id_seq'::regclass);


--
-- TOC entry 2071 (class 2604 OID 16489)
-- Name: id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.login_access ALTER COLUMN id SET DEFAULT nextval('public.login_access_id_seq'::regclass);


--
-- TOC entry 2077 (class 2604 OID 16598)
-- Name: id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quest_completed ALTER COLUMN id SET DEFAULT nextval('public.quest_completed_id_seq'::regclass);


--
-- TOC entry 2074 (class 2604 OID 16519)
-- Name: quest_id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quests ALTER COLUMN quest_id SET DEFAULT nextval('public.quests_quest_id_seq'::regclass);


--
-- TOC entry 2073 (class 2604 OID 16508)
-- Name: room_id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.room ALTER COLUMN room_id SET DEFAULT nextval('public.room_room_id_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 16582)
-- Name: id; Type: DEFAULT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.teams ALTER COLUMN id SET DEFAULT nextval('public.teams_id_seq'::regclass);


--
-- TOC entry 2096 (class 2606 OID 16618)
-- Name: artifacts_in_possess_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_pkey PRIMARY KEY (id);


--
-- TOC entry 2088 (class 2606 OID 16535)
-- Name: artifacts_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts
    ADD CONSTRAINT artifacts_pkey PRIMARY KEY (artifact_id);


--
-- TOC entry 2090 (class 2606 OID 16556)
-- Name: codecoolers_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_pkey PRIMARY KEY (codecooler_id);


--
-- TOC entry 2082 (class 2606 OID 16502)
-- Name: experience_level_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.experience_level
    ADD CONSTRAINT experience_level_pkey PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 16494)
-- Name: login_access_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.login_access
    ADD CONSTRAINT login_access_pkey PRIMARY KEY (id);


--
-- TOC entry 2094 (class 2606 OID 16600)
-- Name: quest_completed_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_pkey PRIMARY KEY (id);


--
-- TOC entry 2086 (class 2606 OID 16524)
-- Name: quests_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quests
    ADD CONSTRAINT quests_pkey PRIMARY KEY (quest_id);


--
-- TOC entry 2084 (class 2606 OID 16513)
-- Name: room_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);


--
-- TOC entry 2092 (class 2606 OID 16587)
-- Name: teams_pkey; Type: CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_pkey PRIMARY KEY (id);


--
-- TOC entry 2104 (class 2606 OID 16619)
-- Name: artifacts_in_possess_artifact_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_artifact_id_fkey FOREIGN KEY (artifact_id) REFERENCES public.artifacts(artifact_id);


--
-- TOC entry 2105 (class 2606 OID 16624)
-- Name: artifacts_in_possess_codecooler_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.artifacts_in_possess
    ADD CONSTRAINT artifacts_in_possess_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);


--
-- TOC entry 2098 (class 2606 OID 16562)
-- Name: codecoolers_actual_room_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_actual_room_fkey FOREIGN KEY (actual_room) REFERENCES public.room(room_id);


--
-- TOC entry 2100 (class 2606 OID 16572)
-- Name: codecoolers_codecooler_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.login_access(id);


--
-- TOC entry 2097 (class 2606 OID 16557)
-- Name: codecoolers_exp_level_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_exp_level_fkey FOREIGN KEY (exp_level) REFERENCES public.experience_level(id);


--
-- TOC entry 2099 (class 2606 OID 16567)
-- Name: codecoolers_quest_in_progress_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.codecoolers
    ADD CONSTRAINT codecoolers_quest_in_progress_fkey FOREIGN KEY (quest_in_progress) REFERENCES public.quests(quest_id);


--
-- TOC entry 2103 (class 2606 OID 16606)
-- Name: quest_completed_codecooler_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);


--
-- TOC entry 2102 (class 2606 OID 16601)
-- Name: quest_completed_quest_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.quest_completed
    ADD CONSTRAINT quest_completed_quest_id_fkey FOREIGN KEY (quest_id) REFERENCES public.quests(quest_id);


--
-- TOC entry 2101 (class 2606 OID 16588)
-- Name: teams_codecooler_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: queststore
--

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_codecooler_id_fkey FOREIGN KEY (codecooler_id) REFERENCES public.codecoolers(codecooler_id);


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-10-10 12:55:27 CEST

--
-- PostgreSQL database dump complete
--

