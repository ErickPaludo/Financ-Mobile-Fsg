using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace WebApiFinanc.Models.DTOs.Credito
{
    public class CreditoDTO
    {
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descricao { get; set; }
        public decimal Valor { get; set; }
        public decimal ValorIntegral { get; set; }
        public DateTime DthrReg { get; set; }
        public int Parcela { get; set; }
        public int TotalParcelas { get; set; }
        public bool Pago { get; set; }
        public string Status { get; set; }
        public int UserId { get; set; }
    }
}
