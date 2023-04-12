insert into role(id,nome) values (1, 'ROLE_USER');
insert into role(id,nome) values (2, 'ROLE_ADMIN');

insert into user(id,nome,email,login,senha) values (1,'Admin','admin@gmail.com','admin','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');
insert into user(id,nome,email,login,senha) values (2,'User','user@gmail.com','user','$2a$10$HKveMsPlst41Ie2LQgpijO691lUtZ8cLfcliAO1DD9TtZxEpaEoJe');

insert into user_roles(user_id,role_id) values(1, 1);
insert into user_roles(user_id,role_id) values(2, 2);

INSERT INTO projeto (id, nome, descricao, valor, dias_restantes) VALUES
(1, "The Book", "Awesome book", 170.00, 41),
(2, "Silver's Heart", "RPG game", 240.00, 121),
(3, "Driver", "Street rap", 110.00, 26),
(4, "Callback", "Programing book", 80.00, 68),
(5, "Fable", "Painting", 54.00, 14);