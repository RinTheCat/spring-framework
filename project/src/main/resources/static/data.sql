insert into statuses (name, DTYPE) values ('Assembly', 'Assembly');
insert into statuses (name, DTYPE) values ('Cancelled', 'Cancelled');
insert into statuses (name, DTYPE) values ('Closed', 'Closed');
insert into statuses (name, DTYPE) values ('Created', 'Created');
insert into statuses (name, DTYPE) values ('Inspection', 'Inspection');
insert into statuses (name, DTYPE) values ('Sent', 'Sent');

insert into orders(status_id) values (1);
insert into orders(status_id) values (2);
insert into orders(status_id) values (3);
insert into orders(status_id) values (4);