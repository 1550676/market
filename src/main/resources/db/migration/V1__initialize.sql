drop table if exists products cascade;
create table products (id bigserial PRIMARY KEY, title varchar(255), description varchar(5000), price numeric(8, 2));
insert into products
(title, description, price) values
('Cheese', 'Fresh cheese', 320.0),
('Milk', 'Fresh milk', 80.0),
('Apples', 'Fresh apples', 80.0),
('Bread', 'Fresh bread', 30.0),
('A1', '', 1.0),
('A2', '', 2.0),
('A3', '', 3.0),
('A4', '', 4.0),
('A5', '', 5.0),
('A6', '', 6.0),
('A7', '', 7.0),
('A8', '', 8.0),
('A9', '', 9.0),
('A10', '', 10.0),
('A11', '', 11.0),
('A12', '', 12.0),
('A13', '', 13.0),
('A14', '', 14.0),
('A15', '', 15.0),
('A16', '', 16.0),
('A17', '', 17.0),
('A18', '', 18.0),
('A19', '', 19.0),
('A20', '', 20.0);

drop table if exists categories cascade;
create table categories (id bigserial PRIMARY KEY, title varchar(255), discount int);
insert into categories
(title, discount) values
('toys', 0),
('food', 80),
('cars', 5);

DROP TABLE IF EXISTS products_categories CASCADE;
CREATE TABLE products_categories (products_id bigint, categories_id bigint, FOREIGN KEY (products_id) REFERENCES products (id), FOREIGN KEY (categories_id) REFERENCES categories (id));
INSERT INTO products_categories (products_id, categories_id) VALUES
(1, 2),
(1, 1),
(2, 3),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 3),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 2),
(22, 2),
(23, 2),
(24, 2);

drop table if exists users;
create table users (
  id                    bigserial,
  phone                 VARCHAR(30) not null UNIQUE,
  password              VARCHAR(80) not null,
  enable                boolean,
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
  id                    serial,
  name                  VARCHAR(50) not null,
  primary key (id)
);

drop table if exists users_roles;
create table users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  primary key (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

insert into roles (name)
values
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

insert into users (phone, password, enable, first_name, last_name, email)
values
('2','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'true', 'admin','admin','admin@gmail.com'),
('100','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'true', 'user','user','user@gmail.com');


insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3),
(2, 1);

drop table if exists orders cascade;
create table orders (id bigserial, user_id bigint not null, price numeric(8, 2) not null, address varchar (255) not null, phone_number varchar(30) not null, primary key(id), constraint fk_user_id foreign key (user_id) references users (id));

drop table if exists orders_items cascade;
create table orders_items (id bigserial, order_id bigint not null, product_id bigint not null, quantity int, price numeric(8, 2), primary key(id), constraint fk_prod_id foreign key (product_id) references products (id), constraint fk_order_id foreign key (order_id) references orders (id));
