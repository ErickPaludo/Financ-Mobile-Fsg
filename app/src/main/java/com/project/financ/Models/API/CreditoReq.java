package com.project.financ.Models.API;

import com.project.financ.Models.Credito;

import java.util.ArrayList;
import java.util.List;

public class CreditoReq {
    private Credito credito;
    private ArrayList<CreditoParcelas> parcelas;

    public CreditoReq(Credito credito,ArrayList<CreditoParcelas> parcelas){
        this.credito = credito;
        this.parcelas = parcelas;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public List<CreditoParcelas> getParcelas() {
        return parcelas;
    }

    public void setParcelas(ArrayList<CreditoParcelas> parcelas) {
        this.parcelas = parcelas;
    }
}
