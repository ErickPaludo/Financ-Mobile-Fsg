package com.project.financ.Models;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Debito extends  Gastos<Debito> {
    public Debito(int id, String titulo, String descricao, double valor, LocalDateTime dthrReg, String status, int userId) {
        super(id, titulo, descricao, valor, dthrReg, status, userId);
    }
    @NonNull
    @Override
    public String toString() {
        return "Debito:";
    }

    @Override
    public void Cadastro() {

    }

    @Override
    public Debito Retorno() {
        return null;
    }
}
