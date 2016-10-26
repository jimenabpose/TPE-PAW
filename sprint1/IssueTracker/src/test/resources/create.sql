-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id serial NOT NULL,
  firstname character varying(50),
  lastname character varying(50),
  username character varying(50),
  pass character varying(10),
  usertype character varying(50),
  CONSTRAINT user_pk PRIMARY KEY (id),
  CONSTRAINT username_unique UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users OWNER TO paw;



-- Table: projects

-- DROP TABLE projects;

CREATE TABLE projects
(
  id serial NOT NULL,
  code character varying(20),
  description character varying(256),
  leaderid integer,
  "name" character varying(20),
  CONSTRAINT project_pkey PRIMARY KEY (id),
  CONSTRAINT projects_code_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE projects OWNER TO paw;



-- Table: issues

-- DROP TABLE issues;

CREATE TABLE issues
(
  id serial NOT NULL,
  title character varying(30),
  description character varying(256),
  creationdate date,
  reporterid integer,
  assigneeid integer,
  projectid integer,
  estimatedtime numeric,
  state character varying(10),
  priority character varying(8),
  resolution character varying(20),
  CONSTRAINT issue_pkey PRIMARY KEY (id),
  CONSTRAINT "assigneeId" FOREIGN KEY (assigneeid)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "issue_projectId_fkey" FOREIGN KEY (projectid)
      REFERENCES projects (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "reporterId" FOREIGN KEY (reporterid)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE issues OWNER TO paw;

