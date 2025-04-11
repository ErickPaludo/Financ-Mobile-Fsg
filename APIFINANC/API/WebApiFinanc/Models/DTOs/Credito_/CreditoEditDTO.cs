namespace WebApiFinanc.Models.DTOs.Credito
{
    public class CreditoEditDTO
    {
        public string Titulo { get; set; }
        public string Descricao { get; set; }
        public decimal Valor { get; set; }
        public decimal ValorIntegral { get; set; }
        public DateTime DthrReg { get; set; }
        public int TotalParcelas { get; set; }
        public bool Pago { get; set; }
    }
}
