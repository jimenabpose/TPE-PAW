alter table users add column is_active boolean default true;
alter table projects add column is_public boolean default true;

create table rel_users_projects(
user_fk integer not null,
project_fk integer not null,
CONSTRAINT rel_users_projects_pkey PRIMARY KEY (user_fk, project_fk)
);
ALTER TABLE rel_users_projects OWNER TO paw;

alter table rel_users_projects add constraint user_fk_rel_users_projects foreign key(user_fk) references users(id);
alter table rel_users_projects add constraint project_fk_rel_users_projects foreign key(project_fk) references projects(id);

create table jobs(
id serial NOT NULL,
user_fk integer not null,
issue_fk integer not null,
duration integer default 0,
resolved_date timestamp with time zone,
description character varying(256),
primary key(id)
);
ALTER TABLE jobs OWNER TO paw;

alter table jobs add constraint user_fk_jobs foreign key(user_fk) references users(id);
alter table jobs add constraint issue_fk_jobs foreign key(issue_fk) references issues(id);

create table issues_comments(
id serial NOT NULL,
user_fk integer not null,
issue_fk integer not null,
creation_date date,
comment character varying(256),
primary key(id)
);
ALTER TABLE issues_comments OWNER TO paw;

alter table issues_comments add constraint user_fk_issues_comments foreign key(user_fk) references users(id);
alter table issues_comments add constraint issue_fk_issues_comments foreign key(issue_fk) references issues(id);
