insert into usuario (id, password, username) values (1, '$2a$10$JkdiMx4FpktPdliubTRzt.OcrNTi3dPLcKZ0I2OgVyMRbVwecV6s.', 'admin');
insert  into usuario_grupo (id, grupo, user_id) values (1, 'ADMINISTRADOR', 1);
insert into usuario (id, password, username) values (2, '$2a$10$JkdiMx4FpktPdliubTRzt.OcrNTi3dPLcKZ0I2OgVyMRbVwecV6s.', 'usuario');
insert  into usuario_grupo (id, grupo, user_id) values (2, 'USUARIO_BIBLIOTECA', 2);

insert into autor(id, nome) values (1, 'Mauricio Aniche');
insert into livro(id, titulo, QUANTIDADE_PAGINAS, autor_id) values (1, 'OO para ninjas', 100, 1);
insert into livro(id, titulo, QUANTIDADE_PAGINAS, autor_id) values (2, 'TDD para mestres', 100, 1);