package com.project.financ.Models.API;

public class CreditoParcelas {
    public int parcela;
    public String status;

    public CreditoParcelas(int parcela,String status){
        this.parcela = parcela;
        this.status = status;
    }

    public CreditoParcelas() {

    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        parcela = parcela;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }
}
