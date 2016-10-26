ALTER SEQUENCE version_id_seq RESTART WITH 1;

INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La primera', 'primera', '2011-04-20', 'RELEASED', 1);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La segunda', 'segunda', '2011-04-30', 'RELEASED', 1);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La tercera', 'tercera', '2011-06-10', 'ONCOURSE', 1);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La cuarta', 'cuarta', '2011-06-20', 'OPEN', 1);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La quinta', 'quinta', '2011-06-30', 'OPEN', 1);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La primera', 'primera', '2011-04-30', 'RELEASED', 2);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La segunda', 'segunda', '2011-06-10', 'ONCOURSE', 2);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La tercera', 'tercera', '2011-06-30', 'OPEN', 2);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La primera', 'primera', '2011-07-20', 'OPEN', 3);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La segunda', 'segunda', '2011-08-20', 'OPEN', 3);
INSERT INTO version ( description, name, releasedate, state, project_id) VALUES ( 'La tercera', 'tercera', '2011-09-20', 'OPEN', 3);

INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 11, 1);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 11, 2);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 11, 3);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 11, 4);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 11, 5);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 12, 1);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 12, 2);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 13, 1);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 13, 2);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 1, 6);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 1, 7);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 1, 8);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 16, 6);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 16, 7);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 17, 6);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 21, 9);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 21, 10);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 21, 11);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 22, 9);
INSERT INTO issues_resversion ( issue_fk, version_fk) VALUES ( 22, 10);

INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 11, 1);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 11, 2);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 11, 3);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 12, 1);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 1, 6);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 1, 7);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 16, 6);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 17, 6);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 21, 9);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 21, 10);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 21, 11);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 22, 9);
INSERT INTO issues_affversion ( issue_fk, version_fk) VALUES ( 22, 10);


INSERT INTO usersolicitation (firstname, lastname, pass, username) VALUES ('Jimena', 'Pose', '123456', 'jpose');
INSERT INTO usersolicitation (firstname, lastname, pass, username) VALUES ('Cristian', 'Prieto', '123456', 'cprieto');
INSERT INTO usersolicitation (firstname, lastname, pass, username) VALUES ('Santiago', 'Camisay', '123456', 'scamisay');
INSERT INTO usersolicitation (firstname, lastname, pass, username) VALUES ('Moria', 'Casan', '123456', 'mcasan');
INSERT INTO usersolicitation (firstname, lastname, pass, username) VALUES ('Jimena', 'Pose', '123456', 'jpose');


INSERT INTO issuechanges (changedate, changer, field, newvalue, oldvalue, issue_id) VALUES ('2011-01-05 00:39:33.71', 'Moria Casan', 'Usuario asignado', 'Moria Casan', '', 1);
INSERT INTO issuechanges (changedate, changer, field, newvalue, oldvalue, issue_id) VALUES ('2011-02-14 00:43:37.396', 'Moria Casan', 'Titulo', 'Modelo de datos', 'Modlo de dato', 1);
INSERT INTO issuechanges (changedate, changer, field, newvalue, oldvalue, issue_id) VALUES ('2011-03-23 00:43:37.399', 'Moria Casan', 'Usuario asignado', '', 'Moria Casan', 1);
INSERT INTO issuechanges (changedate, changer, field, newvalue, oldvalue, issue_id) VALUES ('2011-04-04 00:43:37.399', 'Moria Casan', 'Tipo', '', 'Mejora', 1);
INSERT INTO issuechanges (changedate, changer, field, newvalue, oldvalue, issue_id) VALUES ('2011-05-17 00:43:37.4', 'Moria Casan', 'Versiones de resolucion', 'primera, segunda, tercera', 'primera', 1);

INSERT INTO issues_issuechanges (issues_id, changes_id) VALUES (1, 1); 
INSERT INTO issues_issuechanges (issues_id, changes_id) VALUES (1, 2);
INSERT INTO issues_issuechanges (issues_id, changes_id) VALUES (1, 3);
INSERT INTO issues_issuechanges (issues_id, changes_id) VALUES (1, 4);
INSERT INTO issues_issuechanges (issues_id, changes_id) VALUES (1, 5);