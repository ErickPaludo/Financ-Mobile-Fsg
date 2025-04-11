using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using WebApiFinanc.Models;
using WebApiFinanc.Models.DTOs.Credito;
using WebApiFinanc.Models.DTOs.Debito;
using WebApiFinanc.Models.DTOs.Saldo_;

namespace WebApiFinanc.Services
{
    public interface IGerenciaGastos
    {
        Credito RegistraCredito(Credito credito);
        Debito RegistraDebito(Debito debito); 
        Saldo RegistraSaldo(Saldo saldo);
        void Excluir(int id,string tipo = "O");
        Gastos Update(Gastos gastoModify);
        Debito UpdateDebito(int id, JsonPatchDocument<DebitoEditDTO> debito);
        Credito UpdateCredito(int id, JsonPatchDocument<CreditoEditDTO> credito);
        Saldo UpdateSaldo(int id, JsonPatchDocument<SaldoEditDTO> saldo);
        string DeParaStatus(string status);
    }
}
