insert into PROJECT (id, created_at, modified_at, name, description, state)
values ('1', '2018-08-17 07:42:44.136', '2018-08-17 07:42:44.136', 'project 1', 'description 1', 'OPEN');
insert into PROJECT (id, created_at, modified_at, name, description, state)
values ('2', '2018-08-18 07:42:44.136', '2018-08-18 07:42:44.136', 'project 2', 'description 2', 'OPEN');

insert into ISSUE (id, created_at, modified_at, description, category, state, title, project_id)
values ('1', '2018-08-17 07:42:44.136', '2018-08-17 07:42:44.136', 'description 1', 'BACKLOG', 'OPEN', 'test issue 1',1);
insert into ISSUE (id, created_at, modified_at, description, category, state, title, project_id)
values ('2', '2018-08-19 09:12:44.136', '2018-08-19 09:12:44.136', 'description 2', 'REVIEW', 'OPEN', 'test issue 2',1);
insert into ISSUE (id, created_at, modified_at, description, category, state, title, project_id)
values ('3', '2018-09-21 07:42:44.136', '2018-09-21 07:42:44.136', 'description 3', 'FINISHED', 'OPEN', 'test issue 3',2);

insert into LABEL (id, created_at, modified_at, color, name)
values ('1', '2018-08-17 07:42:44.236', '2018-08-17 07:42:44.236', '47bd1c', 'story');
insert into LABEL (id, created_at, modified_at, color, name)
values ('2', '2018-08-17 07:42:44.536', '2018-08-17 07:42:44.536', '47bd1c', 'todo');
insert into LABEL (id, created_at, modified_at, color, name)
values ('3', '2018-08-19 09:12:44.236', '2018-08-19 09:12:44.236', '47bd1c', 'backend');
insert into LABEL (id, created_at, modified_at, color, name)
values ('4', '2018-09-21 07:42:44.236', '2018-09-21 07:42:44.236', '47bd1c', 'frontend');
insert into LABEL (id, created_at, modified_at, color, name)
values ('5', '2018-09-21 07:42:44.536', '2018-09-21 07:42:44.536', '47bd1c', 'bug');

insert into ISSUE_LABEL (issue_id, label_id)
values ('1', '1');
insert into ISSUE_LABEL (issue_id, label_id)
values ('1', '2');
insert into ISSUE_LABEL (issue_id, label_id)
values ('2', '3');
insert into ISSUE_LABEL (issue_id, label_id)
values ('3', '4');
insert into ISSUE_LABEL (issue_id, label_id)
values ('3', '5');