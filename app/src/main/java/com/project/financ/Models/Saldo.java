package com.project.financ.Models;

import androidx.annotation.NonNull;

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

    @Override
    public void Cadastro() {

    }

    @Override
    public Saldo Retorno() {
        return null;
    }
}
