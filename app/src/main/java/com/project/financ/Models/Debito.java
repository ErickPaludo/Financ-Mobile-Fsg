package com.project.financ.Models;

import androidx.annotation.NonNull;

import com.project.financ.Models.API.HttpRequest;

import java.time.LocalDateTime;

public class Debito extends  Gastos<Debito> {
    public Debito(int id, String titulo, String descricao, double valor, String dthrReg, String status, String userId) {
        super(id, titulo, descricao, valor, dthrReg, status, userId);
    }
    @NonNull
    @Override
    public String toString() {
        return "ID: " + id +
                "\nTitulo: " + titulo +
                "\nDescrição: " + descricao +
                "\nValor: " + valor +
                "\nData e Hora: " + dthrReg +
                "\nStatus: " + status +
                "\nUserId: " + userId;
    }
    public static void Cadastro(Debito obj) {
        HttpRequest.Post(obj,"debito/cadastro");
    }


    public Debito Retorno() {
        return null;
    }
}
