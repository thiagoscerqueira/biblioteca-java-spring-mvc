package br.edu.fib.bibliotecajavamvc.model;

public enum Grupo {

    ADMINISTRADOR("Administrador"),
    USUARIO_BIBLIOTECA("Usuário da Biblioteca");

    private String descricaoGrupo;

    Grupo(String descricaoGrupo) {
        this.descricaoGrupo = descricaoGrupo;
    }

    public String getDescricaoGrupo() {
        return descricaoGrupo;
    }
}
