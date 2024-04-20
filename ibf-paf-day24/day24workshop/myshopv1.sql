drop database if exists myshop;

create database myshop;

use myshop;

create table purchase_orders (
   po_id char(8) not null,
   name varchar(32) not null,
   email varchar(128) not null,
   delivery_date datetime,
   rush boolean default false, 
   comments text,
   last_update datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

   primary key(po_id)
);

create table line_items (
   li_id int AUTO_INCREMENT,
   item varchar(32) not null,
   quantity int default 1,
   po_id char(8) not null,

   primary key(li_id),
   foreign key(po_id)
      references purchase_orders(po_id)
);

insert into purchase_orders(po_id, name, email, delivery_date, rush, comments) 
values
   ('10000', 'fred', 'fred@gmail.com', CURRENT_TIMESTAMP, false, 'fragile item'),
   ('10001', 'barney', 'barney@gmail.com', CURRENT_TIMESTAMP, false, 'add customization');

grant all privileges on orders.* to 'abcde'@'%';

flush privileges;
flush privileges;