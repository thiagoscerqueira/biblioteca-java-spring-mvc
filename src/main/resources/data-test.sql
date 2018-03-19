insert into usuario (id, password, username) values (1, '$2a$10$JkdiMx4FpktPdliubTRzt.OcrNTi3dPLcKZ0I2OgVyMRbVwecV6s.', 'admin');
insert  into usuario_grupo (id, grupo, user_id) values (1, 'ADMINISTRADOR', 1);
insert into usuario (id, password, username) values (2, '$2a$10$JkdiMx4FpktPdliubTRzt.OcrNTi3dPLcKZ0I2OgVyMRbVwecV6s.', 'usuario');
insert  into usuario_grupo (id, grupo, user_id) values (2, 'USUARIO_BIBLIOTECA', 2);

insert into autor(id, nome) values (1, 'Mauricio Aniche');
insert into livro(id, titulo, QUANTIDADE_PAGINAS, autor_id) values (1, 'OO para ninjas', 100, 1);
insert into livro(id, titulo, QUANTIDADE_PAGINAS, autor_id) values (2, 'TDD para mestres', 100, 1);

insert into autor(id, nome) values (2, 'Outro autor');

insert into emprestimo(id, data_emprestimo, data_devolucao, livro_id, usuario_id) values (1, sysdate - 1, null, 2, 2);
insert into emprestimo(id, data_emprestimo, data_devolucao, livro_id, usuario_id) values (2, sysdate - 7, sysdate-2, 2, 2);

insert into review(id, avaliacao, comentario, livro_id, usuario_id) VALUES (1, 3, 'bom', 2, 2);