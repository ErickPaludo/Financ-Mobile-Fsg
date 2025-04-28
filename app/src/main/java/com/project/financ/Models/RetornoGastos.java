package com.project.financ.Models;

import java.time.LocalDateTime;

public class RetornoGastos {
    public int id;
    public int gpId;
    public String titulo;
    public String descricao;
    public double valor;
    public String dthr;
    public String parcela;
    public String categoria;
    public String status;

    public RetornoGastos(int id, int gpId,String titulo, String descricao, double valor, String dthr, String parcela,String categoria,String status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.dthr = dthr;
        this.parcela = parcela;
        this.categoria = categoria;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGpId() {
        return gpId;
    }

    public void setGpId(int gpId) {
        this.gpId = gpId;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDthr() {
        return dthr;
    }

    public void setDthr(String dthr) {
        this.dthr = dthr;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
