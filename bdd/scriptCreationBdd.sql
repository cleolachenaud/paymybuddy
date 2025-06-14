CREATE TABLE users(
	id_user INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(250) NOT NULL UNIQUE, 
	mdp VARCHAR(250) NOT null,
	user_role VARCHAR(20) not NULL
);

CREATE TABLE users_link(
	id_user_link INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_user_sender INTEGER NOT NULL,
	id_user_reciever INTEGER NOT NULL
);

ALTER TABLE users_link 
ADD FOREIGN KEY (id_user_sender) REFERENCES users(id_user)
;
ALTER TABLE users_link 
ADD FOREIGN KEY (id_user_reciever) REFERENCES users(id_user)
;


CREATE TABLE transactions(
	id_transaction INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_user_sender INTEGER NOT NULL,
	id_user_reciever INTEGER NOT NULL,
	description VARCHAR(250),
	montant DECIMAL(15,2) NOT NULL
);

ALTER TABLE transactions 
ADD FOREIGN KEY (id_user_sender) REFERENCES usersh3(id_user)
;
ALTER TABLE transactions 
ADD FOREIGN KEY (id_user_reciever) REFERENCES users(id_user)
;

CREATE TABLE compte(
	id_compte INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_user_compte INTEGER NOT NULL,
	solde_compte DECIMAL (15,2) NOT NULL
);
ALTER TABLE compte 
ADD FOREIGN KEY (id_user_compte) REFERENCES users(id_user)
;

INSERT INTO users (username, email, mdp, user_role) VALUES ('Appa', 'appa@email.com', '123rizPaddy', 'USER');
INSERT INTO users (username, email, mdp, user_role) VALUES ('Moja', 'moja@email.com', '123paddaVis', 'USER');
INSERT INTO users (username, email, mdp, user_role) VALUES ('Marley', 'marley@email.com', 'chenilleForever', 'USER');
INSERT INTO users (username, email, mdp, user_role) VALUES ('Cookie', 'cookie@email.com', 'baronDeLaKet3000', 'USER');
INSERT INTO users (username, email, mdp, user_role) VALUES ('root', 'root@jetest.com', 'root', 'USER');

insert into users_link (id_user_sender, id_user_reciever) values (1, 2);
insert into users_link (id_user_sender, id_user_reciever) values (3, 4);
insert into users_link (id_user_sender, id_user_reciever) values (2, 1);
insert into users_link (id_user_sender, id_user_reciever) values (4, 3);

insert into compte (id_user_compte, solde_compte) values (1, 234.34);
insert into compte (id_user_compte, solde_compte) values (2, 1234.99);
insert into compte (id_user_compte, solde_compte) values (3, 4.10);
insert into compte (id_user_compte, solde_compte) values (4, 99234.78);

select * from transactions;
select * from users;
select * from users_link;
select * from compte;
show tables;
show columns from users;
show columns from user_link;
