package com.project.financ.Models;

import java.time.LocalDateTime;

abstract public class Gastos<T>{
    public  int id;
    public  String titulo;
    public  String descricao;
    public  double valor;
    public  LocalDateTime dthrReg;
    public  String status;
    public  String userId;

    public Gastos(int id, String titulo, String descricao, double valor, LocalDateTime dthrReg, String status, String userId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.dthrReg = dthrReg;
        this.status = status;
        this.userId = userId;
    }

    public Gastos() {

    }

    public int getId() {
 return id;
    }

    public void setId(int id) {
        this.id = id;
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
        descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDthrReg() {
        return dthrReg;
    }

    public void setDthrReg(LocalDateTime dthrReg) {
        this.dthrReg = dthrReg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
