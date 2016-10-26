update issues set estimatedtime = null where estimatedtime = -1;

ALTER TABLE rel_users_projects RENAME TO rel_users_projects_old;
ALTER TABLE issues_comments RENAME TO issues_comments_old;
ALTER TABLE jobs RENAME TO jobs_old;
ALTER TABLE issues RENAME TO issues_old;
ALTER TABLE projects RENAME TO projects_old;
ALTER TABLE users RENAME TO users_old;

ALTER SEQUENCE users_id_seq RENAME TO users_id_seq_old ;
ALTER SEQUENCE projects_id_seq RENAME TO projects_id_seq_old ;
ALTER SEQUENCE issues_id_seq RENAME TO issues_id_seq_old ;
ALTER SEQUENCE jobs_id_seq RENAME TO jobs_id_seq_old ;
ALTER SEQUENCE issues_comments_id_seq RENAME TO issues_comments_id_seq_old ;

ALTER INDEX issues_comments_pkey RENAME to issues_comments_pkey_old;
ALTER INDEX jobs_pkey RENAME to jobs_pkey_old;
ALTER INDEX rel_users_projects_pkey RENAME to rel_users_projects_pkey_old;
ALTER INDEX username_unique RENAME to username_unique_old;


--
-- PostgreSQL database dump
--

-- Started on 2011-06-01 11:01:21 ART

SET statement_timeout = 0;
SET client_encoding = 'LATIN1';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1521 (class 1259 OID 43055)
-- Dependencies: 3
-- Name: issuechanges; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issuechanges (
    id integer NOT NULL,
    changedate timestamp without time zone,
    changer character varying(255),
    field character varying(255),
    newvalue character varying(255),
    oldvalue character varying(255),
    issue_id integer
);


ALTER TABLE public.issuechanges OWNER TO paw;

--
-- TOC entry 1520 (class 1259 OID 43053)
-- Dependencies: 1521 3
-- Name: issuechanges_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE issuechanges_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.issuechanges_id_seq OWNER TO paw;

--
-- TOC entry 1877 (class 0 OID 0)
-- Dependencies: 1520
-- Name: issuechanges_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE issuechanges_id_seq OWNED BY issuechanges.id;


--
-- TOC entry 1525 (class 1259 OID 43071)
-- Dependencies: 3
-- Name: issues; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issues (
    id integer NOT NULL,
    creationdate timestamp without time zone,
    description character varying(255),
    estimatedtime real,
    issuetype character varying(255),
    priority character varying(255),
    resolution character varying(255),
    state character varying(255),
    title character varying(255),
    assigneeid integer,
    projectid integer,
    reporterid integer,
    relatedwithid integer,
    necessaryforid integer,
    duplicatedwithid integer,
    dependesonid integer
);


ALTER TABLE public.issues OWNER TO paw;

--
-- TOC entry 1527 (class 1259 OID 43082)
-- Dependencies: 3
-- Name: issues_affversion; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issues_affversion (
    issue_fk integer NOT NULL,
    version_fk integer NOT NULL
);


ALTER TABLE public.issues_affversion OWNER TO paw;

--
-- TOC entry 1529 (class 1259 OID 43089)
-- Dependencies: 3
-- Name: issues_comments; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issues_comments (
    id integer NOT NULL,
    creation_date timestamp without time zone,
    comment character varying(255),
    issue_fk integer,
    user_fk integer
);


ALTER TABLE public.issues_comments OWNER TO paw;

--
-- TOC entry 1528 (class 1259 OID 43087)
-- Dependencies: 3 1529
-- Name: issues_comments_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE issues_comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.issues_comments_id_seq OWNER TO paw;

--
-- TOC entry 1878 (class 0 OID 0)
-- Dependencies: 1528
-- Name: issues_comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE issues_comments_id_seq OWNED BY issues_comments.id;


--
-- TOC entry 1524 (class 1259 OID 43069)
-- Dependencies: 3 1525
-- Name: issues_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE issues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.issues_id_seq OWNER TO paw;

--
-- TOC entry 1879 (class 0 OID 0)
-- Dependencies: 1524
-- Name: issues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE issues_id_seq OWNED BY issues.id;


--
-- TOC entry 1526 (class 1259 OID 43077)
-- Dependencies: 3
-- Name: issues_issuechanges; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issues_issuechanges (
    issues_id integer NOT NULL,
    changes_id integer NOT NULL
);


ALTER TABLE public.issues_issuechanges OWNER TO paw;

--
-- TOC entry 1530 (class 1259 OID 43095)
-- Dependencies: 3
-- Name: issues_resversion; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE issues_resversion (
    issue_fk integer NOT NULL,
    version_fk integer NOT NULL
);


ALTER TABLE public.issues_resversion OWNER TO paw;

--
-- TOC entry 1532 (class 1259 OID 43102)
-- Dependencies: 3
-- Name: jobs; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE jobs (
    id integer NOT NULL,
    resolved_date timestamp without time zone,
    description character varying(255),
    duration integer,
    issue_fk integer,
    user_fk integer
);


ALTER TABLE public.jobs OWNER TO paw;

--
-- TOC entry 1531 (class 1259 OID 43100)
-- Dependencies: 3 1532
-- Name: jobs_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE jobs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.jobs_id_seq OWNER TO paw;

--
-- TOC entry 1880 (class 0 OID 0)
-- Dependencies: 1531
-- Name: jobs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE jobs_id_seq OWNED BY jobs.id;


--
-- TOC entry 1534 (class 1259 OID 43110)
-- Dependencies: 3
-- Name: projects; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE projects (
    id integer NOT NULL,
    code character varying(255),
    description character varying(255),
    ispublic boolean NOT NULL,
    name character varying(255),
    leaderid integer
);


ALTER TABLE public.projects OWNER TO paw;

--
-- TOC entry 1533 (class 1259 OID 43108)
-- Dependencies: 3 1534
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.projects_id_seq OWNER TO paw;

--
-- TOC entry 1881 (class 0 OID 0)
-- Dependencies: 1533
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE projects_id_seq OWNED BY projects.id;


--
-- TOC entry 1535 (class 1259 OID 43116)
-- Dependencies: 3
-- Name: rel_users_projects; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE rel_users_projects (
    project_fk integer NOT NULL,
    user_fk integer NOT NULL
);


ALTER TABLE public.rel_users_projects OWNER TO paw;

--
-- TOC entry 1537 (class 1259 OID 43123)
-- Dependencies: 3
-- Name: users; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    active boolean NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    pass character varying(255),
    usertype character varying(255),
    username character varying(255),
    voterid integer
);


ALTER TABLE public.users OWNER TO paw;
ALTER TABLE users ADD CONSTRAINT username_unique UNIQUE (username);
--
-- TOC entry 1536 (class 1259 OID 43121)
-- Dependencies: 3 1537
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO paw;

--
-- TOC entry 1882 (class 0 OID 0)
-- Dependencies: 1536
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 1523 (class 1259 OID 43063)
-- Dependencies: 3
-- Name: usersolicitation; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE usersolicitation (
    id integer NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    pass character varying(255),
    username character varying(255)
);


ALTER TABLE public.usersolicitation OWNER TO paw;

--
-- TOC entry 1522 (class 1259 OID 43061)
-- Dependencies: 1523 3
-- Name: usersolicitation_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE usersolicitation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usersolicitation_id_seq OWNER TO paw;

--
-- TOC entry 1883 (class 0 OID 0)
-- Dependencies: 1522
-- Name: usersolicitation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE usersolicitation_id_seq OWNED BY usersolicitation.id;


--
-- TOC entry 1539 (class 1259 OID 43131)
-- Dependencies: 3
-- Name: version; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE version (
    id integer NOT NULL,
    description character varying(255),
    name character varying(255),
    releasedate timestamp without time zone,
    state character varying(255),
    project_id integer
);


ALTER TABLE public.version OWNER TO paw;

--
-- TOC entry 1538 (class 1259 OID 43129)
-- Dependencies: 1539 3
-- Name: version_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE version_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.version_id_seq OWNER TO paw;

--
-- TOC entry 1884 (class 0 OID 0)
-- Dependencies: 1538
-- Name: version_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE version_id_seq OWNED BY version.id;


--
-- TOC entry 1817 (class 2604 OID 43058)
-- Dependencies: 1520 1521 1521
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE issuechanges ALTER COLUMN id SET DEFAULT nextval('issuechanges_id_seq'::regclass);


--
-- TOC entry 1819 (class 2604 OID 43074)
-- Dependencies: 1524 1525 1525
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE issues ALTER COLUMN id SET DEFAULT nextval('issues_id_seq'::regclass);


--
-- TOC entry 1820 (class 2604 OID 43092)
-- Dependencies: 1529 1528 1529
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE issues_comments ALTER COLUMN id SET DEFAULT nextval('issues_comments_id_seq'::regclass);


--
-- TOC entry 1821 (class 2604 OID 43105)
-- Dependencies: 1532 1531 1532
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE jobs ALTER COLUMN id SET DEFAULT nextval('jobs_id_seq'::regclass);


--
-- TOC entry 1822 (class 2604 OID 43113)
-- Dependencies: 1533 1534 1534
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE projects ALTER COLUMN id SET DEFAULT nextval('projects_id_seq'::regclass);


--
-- TOC entry 1823 (class 2604 OID 43126)
-- Dependencies: 1537 1536 1537
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1818 (class 2604 OID 43066)
-- Dependencies: 1523 1522 1523
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE usersolicitation ALTER COLUMN id SET DEFAULT nextval('usersolicitation_id_seq'::regclass);


--
-- TOC entry 1824 (class 2604 OID 43134)
-- Dependencies: 1538 1539 1539
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE version ALTER COLUMN id SET DEFAULT nextval('version_id_seq'::regclass);


--
-- TOC entry 1826 (class 2606 OID 43060)
-- Dependencies: 1521 1521
-- Name: issuechanges_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issuechanges
    ADD CONSTRAINT issuechanges_pkey PRIMARY KEY (id);


--
-- TOC entry 1834 (class 2606 OID 43086)
-- Dependencies: 1527 1527 1527
-- Name: issues_affversion_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issues_affversion
    ADD CONSTRAINT issues_affversion_pkey PRIMARY KEY (issue_fk, version_fk);


--
-- TOC entry 1836 (class 2606 OID 43094)
-- Dependencies: 1529 1529
-- Name: issues_comments_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issues_comments
    ADD CONSTRAINT issues_comments_pkey PRIMARY KEY (id);


--
-- TOC entry 1832 (class 2606 OID 43081)
-- Dependencies: 1526 1526
-- Name: issues_issuechanges_changes_id_key; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issues_issuechanges
    ADD CONSTRAINT issues_issuechanges_changes_id_key UNIQUE (changes_id);


--
-- TOC entry 1830 (class 2606 OID 43076)
-- Dependencies: 1525 1525
-- Name: issues_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT issues_pkey PRIMARY KEY (id);


--
-- TOC entry 1838 (class 2606 OID 43099)
-- Dependencies: 1530 1530 1530
-- Name: issues_resversion_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY issues_resversion
    ADD CONSTRAINT issues_resversion_pkey PRIMARY KEY (issue_fk, version_fk);


--
-- TOC entry 1840 (class 2606 OID 43107)
-- Dependencies: 1532 1532
-- Name: jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- TOC entry 1842 (class 2606 OID 43115)
-- Dependencies: 1534 1534
-- Name: projects_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- TOC entry 1844 (class 2606 OID 43120)
-- Dependencies: 1535 1535 1535
-- Name: rel_users_projects_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY rel_users_projects
    ADD CONSTRAINT rel_users_projects_pkey PRIMARY KEY (project_fk, user_fk);


--
-- TOC entry 1846 (class 2606 OID 43128)
-- Dependencies: 1537 1537
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 1828 (class 2606 OID 43068)
-- Dependencies: 1523 1523
-- Name: usersolicitation_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY usersolicitation
    ADD CONSTRAINT usersolicitation_pkey PRIMARY KEY (id);


--
-- TOC entry 1848 (class 2606 OID 43136)
-- Dependencies: 1539 1539
-- Name: version_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY version
    ADD CONSTRAINT version_pkey PRIMARY KEY (id);


--
-- TOC entry 1871 (class 2606 OID 43247)
-- Dependencies: 1841 1539 1534
-- Name: fk14f51cd8ac215e0c; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY version
    ADD CONSTRAINT fk14f51cd8ac215e0c FOREIGN KEY (project_id) REFERENCES projects(id);


--
-- TOC entry 1869 (class 2606 OID 43237)
-- Dependencies: 1535 1534 1841
-- Name: fk1af45537ac215db6; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY rel_users_projects
    ADD CONSTRAINT fk1af45537ac215db6 FOREIGN KEY (project_fk) REFERENCES projects(id);


--
-- TOC entry 1868 (class 2606 OID 43232)
-- Dependencies: 1535 1537 1845
-- Name: fk1af45537b8cb1fd2; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY rel_users_projects
    ADD CONSTRAINT fk1af45537b8cb1fd2 FOREIGN KEY (user_fk) REFERENCES users(id);


--
-- TOC entry 1849 (class 2606 OID 43137)
-- Dependencies: 1829 1525 1521
-- Name: fk1fc3afca99729a8c; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issuechanges
    ADD CONSTRAINT fk1fc3afca99729a8c FOREIGN KEY (issue_id) REFERENCES issues(id);


--
-- TOC entry 1866 (class 2606 OID 43222)
-- Dependencies: 1829 1525 1532
-- Name: fk31dc5699729a36; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY jobs
    ADD CONSTRAINT fk31dc5699729a36 FOREIGN KEY (issue_fk) REFERENCES issues(id);


--
-- TOC entry 1865 (class 2606 OID 43217)
-- Dependencies: 1845 1537 1532
-- Name: fk31dc56b8cb1fd2; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY jobs
    ADD CONSTRAINT fk31dc56b8cb1fd2 FOREIGN KEY (user_fk) REFERENCES users(id);


--
-- TOC entry 1862 (class 2606 OID 43202)
-- Dependencies: 1529 1829 1525
-- Name: fk57620dd999729a36; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_comments
    ADD CONSTRAINT fk57620dd999729a36 FOREIGN KEY (issue_fk) REFERENCES issues(id);


--
-- TOC entry 1861 (class 2606 OID 43197)
-- Dependencies: 1845 1537 1529
-- Name: fk57620dd9b8cb1fd2; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_comments
    ADD CONSTRAINT fk57620dd9b8cb1fd2 FOREIGN KEY (user_fk) REFERENCES users(id);


--
-- TOC entry 1870 (class 2606 OID 43242)
-- Dependencies: 1829 1525 1537
-- Name: fk6a68e0895e8b22e; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk6a68e0895e8b22e FOREIGN KEY (voterid) REFERENCES issues(id);


--
-- TOC entry 1858 (class 2606 OID 43182)
-- Dependencies: 1525 1829 1526
-- Name: fk88f9652f7ade790b; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_issuechanges
    ADD CONSTRAINT fk88f9652f7ade790b FOREIGN KEY (issues_id) REFERENCES issues(id);


--
-- TOC entry 1857 (class 2606 OID 43177)
-- Dependencies: 1521 1825 1526
-- Name: fk88f9652fd280e80f; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_issuechanges
    ADD CONSTRAINT fk88f9652fd280e80f FOREIGN KEY (changes_id) REFERENCES issuechanges(id);


--
-- TOC entry 1852 (class 2606 OID 43152)
-- Dependencies: 1845 1537 1525
-- Name: fkb9b772ba34b66fd5; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772ba34b66fd5 FOREIGN KEY (reporterid) REFERENCES users(id);


--
-- TOC entry 1853 (class 2606 OID 43157)
-- Dependencies: 1525 1829 1525
-- Name: fkb9b772ba56f6a5; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772ba56f6a5 FOREIGN KEY (duplicatedwithid) REFERENCES issues(id);


--
-- TOC entry 1855 (class 2606 OID 43167)
-- Dependencies: 1829 1525 1525
-- Name: fkb9b772ba6598da00; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772ba6598da00 FOREIGN KEY (necessaryforid) REFERENCES issues(id);


--
-- TOC entry 1854 (class 2606 OID 43162)
-- Dependencies: 1829 1525 1525
-- Name: fkb9b772ba8e20269f; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772ba8e20269f FOREIGN KEY (dependesonid) REFERENCES issues(id);


--
-- TOC entry 1850 (class 2606 OID 43142)
-- Dependencies: 1525 1525 1829
-- Name: fkb9b772bab5626097; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772bab5626097 FOREIGN KEY (relatedwithid) REFERENCES issues(id);


--
-- TOC entry 1851 (class 2606 OID 43147)
-- Dependencies: 1525 1841 1534
-- Name: fkb9b772baec36afff; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772baec36afff FOREIGN KEY (projectid) REFERENCES projects(id);


--
-- TOC entry 1856 (class 2606 OID 43172)
-- Dependencies: 1525 1537 1845
-- Name: fkb9b772bafeb81543; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues
    ADD CONSTRAINT fkb9b772bafeb81543 FOREIGN KEY (assigneeid) REFERENCES users(id);


--
-- TOC entry 1867 (class 2606 OID 43227)
-- Dependencies: 1845 1537 1534
-- Name: fkc479187a1e9b65fd; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT fkc479187a1e9b65fd FOREIGN KEY (leaderid) REFERENCES users(id);


--
-- TOC entry 1864 (class 2606 OID 43212)
-- Dependencies: 1530 1847 1539
-- Name: fkd84e3add20fa9156; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_resversion
    ADD CONSTRAINT fkd84e3add20fa9156 FOREIGN KEY (version_fk) REFERENCES version(id);


--
-- TOC entry 1863 (class 2606 OID 43207)
-- Dependencies: 1829 1525 1530
-- Name: fkd84e3add99729a36; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_resversion
    ADD CONSTRAINT fkd84e3add99729a36 FOREIGN KEY (issue_fk) REFERENCES issues(id);


--
-- TOC entry 1860 (class 2606 OID 43192)
-- Dependencies: 1847 1527 1539
-- Name: fked42df7c20fa9156; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_affversion
    ADD CONSTRAINT fked42df7c20fa9156 FOREIGN KEY (version_fk) REFERENCES version(id);


--
-- TOC entry 1859 (class 2606 OID 43187)
-- Dependencies: 1527 1525 1829
-- Name: fked42df7c99729a36; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY issues_affversion
    ADD CONSTRAINT fked42df7c99729a36 FOREIGN KEY (issue_fk) REFERENCES issues(id);


--
-- TOC entry 1876 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: jpose
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2011-06-01 11:01:21 ART

--
-- PostgreSQL database dump complete
--

insert into users ( id, active, firstname, lastname, username, pass, usertype)
select id, is_active, firstname, lastname, username, pass, usertype
from users_old;

insert into projects (id, ispublic, code, description, leaderid,  name)
select id, is_public, code, description, leaderid,  name
from projects_old;

insert into issues ( id, title, description, creationdate, reporterid,  assigneeid,  projectid,  estimatedtime, state, priority, resolution)
select id, title, description, creationdate, reporterid,  assigneeid,  projectid,  estimatedtime, state, priority, resolution
from issues_old;

insert into issues_comments (id, creation_date, comment, issue_fk, user_fk)
select id, creation_date, comment, issue_fk, user_fk
from issues_comments_old;

insert into jobs (id, resolved_date, description, duration, issue_fk, user_fk)
select id, resolved_date, description, duration, issue_fk, user_fk
from jobs_old;

insert into rel_users_projects (project_fk, user_fk)
select project_fk, user_fk
from rel_users_projects_old;


ALTER SEQUENCE users_id_seq RESTART with 7;
ALTER SEQUENCE projects_id_seq RESTART with 4;
ALTER SEQUENCE issues_id_seq RESTART with 23;


drop table rel_users_projects_old ;
drop table issues_comments_old ;
drop table jobs_old ;
drop table issues_old ;
drop table projects_old ;
drop table users_old ;
