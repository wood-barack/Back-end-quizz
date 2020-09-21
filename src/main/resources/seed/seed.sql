INSERT INTO user (id, email, nom, password, prenom, username) VALUES (NULL, 'alphakaba5@gmail.com', 'kaba', '$2a$10$zxvEq8XzYEYtNjbkRsJEbukHeRx3XS6MDXHMu8cNuNsRfZJWwswDy', 'alpha', 'slice');
INSERT INTO user (id, email, nom, password, prenom, username) VALUES (NULL, 'Postman@gmail.com', 'Postman-modif', '$2a$10$cS/EhmOIr3O.vVlblIED5eG5ZPE20tl9r28ibqRmjBvFIwYZY.zgy', 'Postman', 'PostmanSlice');

/*
pour le compte postman mdp : Postman
pour le compte alpha mdp : nimda

 */

 /* Insertion quizz */

INSERT INTO quizz (id, date_creation, titre, description, user_id) VALUES (NULL, '2020-07-23 00:00:00', 'test', 'description', '1');
INSERT INTO quizz (id, date_creation, titre,description, user_id) VALUES (NULL, '2020-07-23 00:00:00', 'test2', 'description' ,'1');
INSERT INTO quizz (id, date_creation, titre,description, user_id) VALUES (NULL, '2020-07-23 00:00:00', 'test2', 'description' ,'2');
INSERT INTO quizz (id, date_creation, titre,description, user_id) VALUES (NULL, '2020-07-23 00:00:00', 'test23', 'description2' ,'2');


INSERT INTO detail_quizz (id, deuxieme_choix, point, premier_choix, quatrieme_choix, question, reponse, time, troisieme_choix, quizz_id) VALUES (NULL, 'test', '11', 'test', 'test', 'test', 'test', '11', 'test', '1');
INSERT INTO detail_quizz (id, deuxieme_choix, point, premier_choix, quatrieme_choix, question, reponse, time, troisieme_choix, quizz_id) VALUES (NULL, 'test2', '121', 'te2st', 'te2st', 'tes2t', 'tes2t', '121', 'te2st', '1');

INSERT INTO detail_quizz (id, deuxieme_choix, point, premier_choix, quatrieme_choix, question, reponse, time, troisieme_choix, quizz_id) VALUES (NULL, 'test2', '121', 'te2st', 'te2st', 'qui est le plus con du group', 'premierChoix', '121', 'te2st', '3');
INSERT INTO detail_quizz (id, deuxieme_choix, point, premier_choix, quatrieme_choix, question, reponse, time, troisieme_choix, quizz_id) VALUES (NULL, 'test2', '121', 'te2st', 'te2st', 'qui est le plus fort du groupe', 'premierChoix', '121', 'te2st', '3');
INSERT INTO detail_quizz (id, deuxieme_choix, point, premier_choix, quatrieme_choix, question, reponse, time, troisieme_choix, quizz_id) VALUES (NULL, 'test2', '121', 'te2st', 'te2st', 'qui est le plus beau du group', 'premierChoix', '121', 'te2st', '3');
