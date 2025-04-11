using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using System.ComponentModel;

namespace WebApiFinanc.Models
{
    [Table("tb_financ")]
    public class Gastos : IValidatableObject
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [StringLength(50)]
        public string Titulo { get; set; }

        [StringLength(200)]
        public string Descricao { get; set; }

        [DefaultValue(0.01)]
        [Required]
        [Range(minimum: 0.01, maximum: 9999999999999, ErrorMessage = "Valor mínimo de 0,01 e máximo de 9999999999999")]
        [Column(TypeName = "decimal(15,2)")]
        public decimal Valor { get; set; }

        [DefaultValue(0.01)]
        [Required]
        [Range(minimum: 0.01, maximum: 9999999999999, ErrorMessage = "Valor mínimo de 0,01 e máximo de 9999999999999")]
        [Column(TypeName = "decimal(15,2)")]
        public decimal ValorIntegral { get; set; }

        [Required]
        public DateTime DthrReg { get; set; }

        [Required]
        public DateTime DataVencimento { get; set; }

        [Range(0, 99999)]
        public int Parcela { get; set; }

        [Range(0, 99999)]
        public int TotalParcelas { get; set; }

        [DefaultValue("N")]
        [StringLength(1)]
        public string Status { get; set; }  

        [DefaultValue(false)]
        public bool Pago { get; set; }

        [DefaultValue('D')]
        [Required]
        [StringLength(1)]
        public string Categoria { get; set; }

        [ForeignKey("credditoId")]
        public int GastoPaiId { get; set; }

        [Required]
        public int UserId { get; set; }

        [ForeignKey("UserId")]
        [JsonIgnore]
        public Usuarios? Usuarios { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (this.Categoria.Equals("C"))
            {
                if(this.TotalParcelas < 1 || this.Parcela < 1)
                {
                    yield return new ValidationResult("Categoria de crédito deve contem obrigatóriamente os campos Parcelas e Total parcelas maiores que zero!", new[] {nameof(this.TotalParcelas),nameof(this.Parcela)});
                }
                else if(this.Parcela > this.TotalParcelas)
                {
                    yield return new ValidationResult("Categoria de crédito não pode possuir o campo parcelas maior que total parcelas!",new[]{ nameof(this.TotalParcelas), nameof(this.Parcela) });
                }
            }
        }
    }
}
