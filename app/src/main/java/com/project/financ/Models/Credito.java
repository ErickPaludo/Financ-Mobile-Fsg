package com.project.financ.Models;

import java.time.LocalDateTime;

public class Credito extends Gastos<Credito>{
    private double valorIntegral;
    private LocalDateTime dataVencimento;
    private int totalParcelas;

    public Credito(int id, String titulo, String descricao, double valor, LocalDateTime dthrReg, String status, int userId,
                   double valorIntegral, LocalDateTime dataVencimento, int totalParcelas) {
        super(id, titulo, descricao, valor, dthrReg, status, userId);
        this.valorIntegral = valorIntegral;
        this.dataVencimento = dataVencimento;
        this.totalParcelas = totalParcelas;
    }

    public double getValorItegral() {
        return valorIntegral;
    }

    public void setValorItegral(double valorItegral) {
        this.valorIntegral = valorItegral;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(int totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    @Override
    public void Cadastro() {

    }

    @Override
    public Credito Retorno() {
        return null;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                "\nTitulo: " + titulo +
                "\nDescrição: " + descricao +
                "\nValor: " + valor +
                "\nData e Hora: " + dthrReg +
                "\nStatus: " + status +
                "\nValor Integral: " + valorIntegral +
                "\nData Vencimento: " + dataVencimento +
                "\nTotal Parcelas: " + totalParcelas +
                "\nUserId: " + userId;
    }
}
