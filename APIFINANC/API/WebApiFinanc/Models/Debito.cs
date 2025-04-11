using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace WebApiFinanc.Models
{
    [Table("tb_debito")]
    public class Debito
    {
        [Key]
        public int Id { get; set; }
        [Required]
        [StringLength(50)]
        public string Titulo { get; set; }

        [StringLength(400)]
        public string Descricao { get; set; }

        [DefaultValue(0.01)]
        [Required]
        [Range(minimum: 0.01, maximum: 9999999999999, ErrorMessage = "Valor mínimo de 0,01 e máximo de 9999999999999")]
        [Column(TypeName = "decimal(15,2)")]
        public decimal Valor { get; set; }

        [Required]
        public DateTime DthrReg { get; set; }

        [DefaultValue("N")]
        [StringLength(1)]
        public string Status { get; set; }

        [Required]
        public int UserId { get; set; }

        [ForeignKey("UserId")]
        [JsonIgnore]
        public Usuarios? Usuarios { get; set; }
    }
}
