drop database if exists myshop;

create database myshop;

use myshop;

create table customers (
   email varchar(128) not null,
   name varchar(32) not null,

   constraint pk_email primary key(email)
);

create table purchase_order (
   po_id char(8) not null,
   email varchar(128) not null,
   delivery_date datetime,
   rush tinyint(1),
   comments text,
   last_update datetime default current_timestamp
      on update current_timestamp,

   constraint pk_po_id primary key(po_id),
   constraint fk_email foreign key(email)
      references customers(email)
);

create table line_items (
   li_id int auto_increment,
   item varchar(32) not null,
   quantity int default 1,
   po_id char(8) not null,

   constraint pk_li_id primary key(li_id),
   constraint fk_po_id foreign key(po_id)
      references purchase_order(po_id)
);

insert into customers(name, email) values
   ('acme', 'acme@gmail.com'),
   ('planetexpress', 'plantexpress@gmail.com');

grant all privileges on myshop.* to 'abcde'@'%';
flush privileges;