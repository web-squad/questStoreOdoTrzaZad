--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2018-10-11 12:01:05 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2223 (class 0 OID 16896)
-- Dependencies: 181
-- Data for Name: artifacts; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.artifacts (artifact_id, name, description, price) FROM stdin;
1	korona	królów	100
2	koro	kasza	50
\.


--
-- TOC entry 2246 (class 0 OID 0)
-- Dependencies: 182
-- Name: artifacts_artifact_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.artifacts_artifact_id_seq', 2, true);


--
-- TOC entry 2228 (class 0 OID 16915)
-- Dependencies: 186
-- Data for Name: experience_level; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.experience_level (id, level, threshold) FROM stdin;
1	1	0
\.


--
-- TOC entry 2230 (class 0 OID 16920)
-- Dependencies: 188
-- Data for Name: login_access; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.login_access (id, email, password, access_level) FROM stdin;
6	dsjk	sdjk	1
7	creepy@guy	pass	3
8	mentor@guy	pass	2
\.


--
-- TOC entry 2234 (class 0 OID 16933)
-- Dependencies: 192
-- Data for Name: quests; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.quests (quest_id, name, description, reward) FROM stdin;
1	znajdź	karola	1
2	sok	zrób sok	20
\.


--
-- TOC entry 2236 (class 0 OID 16941)
-- Dependencies: 194
-- Data for Name: room; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.room (room_id, room_name, room_description) FROM stdin;
1	python	first room in school
\.


--
-- TOC entry 2227 (class 0 OID 16909)
-- Dependencies: 185
-- Data for Name: codecoolers; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.codecoolers (codecooler_id, coolcoins, exp_level, actual_room, coolcoins_ever_earned, quest_in_progress, first_name, last_name, nickname) FROM stdin;
6	0	1	1	0	1	jkdf	dfjk	dsjk
7	0	1	1	0	1	Creepy	Guy	crep
8	0	1	1	0	1	Mentor	Guy	ment
\.


--
-- TOC entry 2225 (class 0 OID 16904)
-- Dependencies: 183
-- Data for Name: artifacts_in_possess; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.artifacts_in_possess (id, artifact_id, codecooler_id) FROM stdin;
\.


--
-- TOC entry 2247 (class 0 OID 0)
-- Dependencies: 184
-- Name: artifacts_in_possess_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.artifacts_in_possess_id_seq', 1, false);


--
-- TOC entry 2248 (class 0 OID 0)
-- Dependencies: 198
-- Name: codecooler_id_seq; Type: SEQUENCE SET; Schema: public; Owner: kodoj
--

SELECT pg_catalog.setval('public.codecooler_id_seq', 8, true);


--
-- TOC entry 2249 (class 0 OID 0)
-- Dependencies: 187
-- Name: experience_level_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.experience_level_id_seq', 1, true);


--
-- TOC entry 2250 (class 0 OID 0)
-- Dependencies: 189
-- Name: login_access_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.login_access_id_seq', 8, true);


--
-- TOC entry 2232 (class 0 OID 16928)
-- Dependencies: 190
-- Data for Name: quest_completed; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.quest_completed (id, quest_id, codecooler_id) FROM stdin;
\.


--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 191
-- Name: quest_completed_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.quest_completed_id_seq', 1, false);


--
-- TOC entry 2252 (class 0 OID 0)
-- Dependencies: 193
-- Name: quests_quest_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.quests_quest_id_seq', 2, true);


--
-- TOC entry 2253 (class 0 OID 0)
-- Dependencies: 195
-- Name: room_room_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.room_room_id_seq', 1, true);


--
-- TOC entry 2238 (class 0 OID 16949)
-- Dependencies: 196
-- Data for Name: teams; Type: TABLE DATA; Schema: public; Owner: queststore
--

COPY public.teams (id, codecooler_id, team_name) FROM stdin;
\.


--
-- TOC entry 2254 (class 0 OID 0)
-- Dependencies: 197
-- Name: teams_id_seq; Type: SEQUENCE SET; Schema: public; Owner: queststore
--

SELECT pg_catalog.setval('public.teams_id_seq', 1, true);


-- Completed on 2018-10-11 12:01:06 CEST

--
-- PostgreSQL database dump complete
--

