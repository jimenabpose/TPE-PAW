CREATE TABLE issues_relations
(
  issues_id integer NOT NULL,
  related_issue_fk integer NOT NULL,
  relationtype character varying(255) NOT NULL,
  CONSTRAINT issues_relations_pkey PRIMARY KEY (issues_id, related_issue_fk, relationtype),
  CONSTRAINT fked8712b293008f5 FOREIGN KEY (related_issue_fk)
      REFERENCES issues (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fked8712b2ae10d236 FOREIGN KEY (issues_id)
      REFERENCES issues (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE issues_relations OWNER TO paw;



CREATE TABLE issues_followers
(
  issue_fk integer NOT NULL,
  user_fk integer NOT NULL,
  CONSTRAINT issues_followers_pkey PRIMARY KEY (issue_fk, user_fk),
  CONSTRAINT fk1ba2b0d0b7e19c79 FOREIGN KEY (user_fk)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk1ba2b0d0cca4f361 FOREIGN KEY (issue_fk)
      REFERENCES issues (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE issues_followers OWNER TO paw;


insert into issues_relations (issues_id, related_issue_fk, relationtype)
select id, dependesonid, 'DEPENDS'
from issues
where dependesonid is not null;

insert into issues_relations (issues_id, related_issue_fk, relationtype)
select id, duplicatedwithid, 'DUPLICATED'
from issues
where duplicatedwithid is not null;

insert into issues_relations (issues_id, related_issue_fk, relationtype)
select id, necessaryforid, 'NECESSARY'
from issues
where necessaryforid is not null;

insert into issues_relations (issues_id, related_issue_fk, relationtype)
select id, relatedwithid, 'RELATED'
from issues
where relatedwithid is not null;


ALTER TABLE issues DROP COLUMN dependesonid;
ALTER TABLE issues DROP COLUMN duplicatedwithid;
ALTER TABLE issues DROP COLUMN necessaryforid;
ALTER TABLE issues DROP COLUMN relatedwithid;

ALTER TABLE issues ALTER COLUMN title SET NOT NULL;
ALTER TABLE users ALTER COLUMN firstname SET NOT NULL;
ALTER TABLE users ALTER COLUMN lastname SET NOT NULL;
ALTER TABLE users ALTER COLUMN pass SET NOT NULL;
ALTER TABLE projects ALTER COLUMN name SET NOT NULL;
ALTER TABLE projects ALTER COLUMN code SET NOT NULL;
ALTER TABLE version ALTER COLUMN name SET NOT NULL;
ALTER TABLE version ALTER COLUMN releasedate SET NOT NULL;
ALTER TABLE version ALTER COLUMN state SET NOT NULL;
ALTER TABLE jobs ALTER COLUMN duration SET NOT NULL;
ALTER TABLE jobs ALTER COLUMN description SET NOT NULL;
ALTER TABLE issues_comments ALTER COLUMN comment SET NOT NULL;
ALTER TABLE usersolicitation ALTER COLUMN username SET NOT NULL;
ALTER TABLE usersolicitation ALTER COLUMN firstname SET NOT NULL;
ALTER TABLE usersolicitation ALTER COLUMN lastname SET NOT NULL;
ALTER TABLE usersolicitation ALTER COLUMN pass SET NOT NULL;


