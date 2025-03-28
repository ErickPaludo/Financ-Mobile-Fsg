package com.project.financ.Models;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class Geral extends Credito{

    public Geral(int id, String titulo, String descricao, double valor, LocalDateTime dthrReg, String status, int userId, double valorIntegral, LocalDateTime dataVencimento, int totalParcelas) {
        super(id, titulo, descricao, valor, dthrReg, status, userId, valorIntegral, dataVencimento, totalParcelas);
    }
    public Geral Retorna(){
        return null;
    }
    @NonNull
    @Override
    public String toString() {
        return "Geral:";
    }

    @Override
    public Geral Retorno() {
        return null;
    }
}
