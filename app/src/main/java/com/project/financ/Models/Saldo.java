package com.project.financ.Models;

import androidx.annotation.NonNull;

import com.project.financ.Models.API.HttpRequest;

import java.time.LocalDateTime;

public class Saldo extends Gastos<Saldo> {
    public Saldo(int id, String titulo, String descricao, double valor, LocalDateTime dthrReg, String status, int userId) {
        super(id, titulo, descricao, valor, dthrReg, status, userId);
    }

    public Saldo() {
        super();
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


    public static void Cadastro(Saldo obj) {
        HttpRequest.Post(obj,"Saldo");
    }


    public Saldo Retorno() {
        return null;
    }
}
