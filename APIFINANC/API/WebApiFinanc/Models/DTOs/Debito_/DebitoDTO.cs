using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel;

namespace WebApiFinanc.Models.DTOs.Debito
{
    public class DebitoDTO
    {
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descricao { get; set; }
        public decimal Valor { get; set; }
        public DateTime DthrReg { get; set; }
        public bool Pago { get; set; }
        public int UserId { get; set; }
    }
}
