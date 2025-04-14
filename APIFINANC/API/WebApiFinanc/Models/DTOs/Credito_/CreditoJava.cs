using System.ComponentModel.DataAnnotations;
using System.ComponentModel;
using WebApiFinanc.Models.DTOs.Credito;

namespace WebApiFinanc.Models
{
    public class CreditoJava
    {
        public Credito credito { get; set; }
        public List<CreditoParcelasJava> parcelas { get; set; }
    }

    public class  CreditoParcelasJava
    {
        public int Parcela { get; set; }
        public string Status { get; set; }
    }
}
