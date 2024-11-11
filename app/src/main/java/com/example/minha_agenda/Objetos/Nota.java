package com.example.minha_agenda.Objetos;

public class Nota {

    String id_nota, uid_usuario, email_usuario, data_hora_atual, titulo, descricao, data_nota, estado;

    public Nota() {
    }

    public Nota(String id_nota, String uid_usuario, String email_usuario, String data_hora_atual, String titulo, String descricao, String data_nota, String estado) {
        this.id_nota = id_nota;
        this.uid_usuario = uid_usuario;
        this.email_usuario = email_usuario;
        this.data_hora_atual = data_hora_atual;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_nota = data_nota;
        this.estado = estado;
    }

    public String getId_nota() {
        return id_nota;
    }

    public void setId_nota(String id_nota) {
        this.id_nota = id_nota;
    }

    public String getUid_usuario() {
        return uid_usuario;
    }

    public void setUid_usuario(String uid_usuario) {
        this.uid_usuario = uid_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getData_hora_atual() {
        return data_hora_atual;
    }

    public void setData_hora_atual(String data_hora_atual) {
        this.data_hora_atual = data_hora_atual;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_nota() {
        return data_nota;
    }

    public void setData_nota(String data_nota) {
        this.data_nota = data_nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
