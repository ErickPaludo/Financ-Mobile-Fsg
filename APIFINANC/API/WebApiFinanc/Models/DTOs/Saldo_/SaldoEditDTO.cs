using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace WebApiFinanc.Models.DTOs.Saldo_
{
    public class SaldoEditDTO
    {
        public string Titulo { get; set; }
        public string Descricao { get; set; }
        public decimal Valor { get; set; }
        public DateTime DthrReg { get; set; }
        public string Status { get;set; }
    }
}
