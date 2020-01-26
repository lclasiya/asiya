INSERT INTO user (id, username,avatar,sex,tend, age, email, password ) VALUES (1, 'lcl','', '男','長身','28', '510854315@qq.com', '$2a$10$6v6jGmBCG3qGaWrWLHguEeiDWuRhL0i6o.96g7I6nu6/Il73VPE2q');
INSERT INTO user (id, username,avatar,sex,tend, age, email, password)  VALUES (2, 'asiya','', '女','欧米','39', '734824855@qq.com', '$2a$10$NJr2UUPHoPLxgRfCVj3b5OQGaChNh35J6UsL4RuLv23zFO4oa9ydm');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
