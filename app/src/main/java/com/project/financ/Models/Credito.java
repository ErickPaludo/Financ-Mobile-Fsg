package com.project.financ.Models;

import android.annotation.SuppressLint;

import com.project.financ.Models.API.CreditoParcelas;
import com.project.financ.Models.API.CreditoReq;
import com.project.financ.Models.API.HttpRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Credito extends Gastos<Credito>{
    private double valorIntegral;
    private LocalDateTime dataVencimento;
    private int totalParcelas;

    public Credito(int id, String titulo, String descricao, double valor, String dthrReg, String status, String userId,
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


     @SuppressLint("NewApi")
     public static void Cadastro(Credito obj) {
        int parcelas = obj.totalParcelas;

        if(obj.valor > 0.01 && obj.valorIntegral == 0.01){
            obj.valorIntegral = obj.valor * obj.totalParcelas;
        }
        else{
            obj.valor = obj.valorIntegral / obj.totalParcelas;
        }


        int id = 0;

        ArrayList<CreditoParcelas> listCredito = new ArrayList<CreditoParcelas>();
        for(int i = 1;i <= parcelas;i++){
            CreditoParcelas creditoParcelas = new CreditoParcelas();
            creditoParcelas.parcela = i;
            creditoParcelas.status = "N";
            listCredito.add(creditoParcelas);
        };
        CreditoReq creditoReq = new CreditoReq(obj,listCredito);

         HttpRequest.Post(creditoReq,"api/FinancCredito/cadastro");
    }

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
