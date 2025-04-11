using System.Collections.ObjectModel;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace WebApiFinanc.Models
{
    [Table("Usuarios")]
    public class Usuarios
    {
        public Usuarios()
        {
            Gastos = new Collection<Gastos>();
        }

        [Key]
        public int UserId { get; set; }
        [DefaultValue(true)]
        public bool Active { get; set; }
        [DefaultValue(1)]
        public int Status { get; set; }

        [StringLength(80)]
        [Required(ErrorMessage = "Nome não pode ser nulo")]
        public string FirstName { get; set; }

        [StringLength(80)]
        [Required(ErrorMessage = "Sobrenome não pode ser nulo")]
        public string LastName { get; set; }

        [StringLength(15, MinimumLength = 2, ErrorMessage = "Usuário deve possuir entre 3 e 15 caracteres")]
        [Required(ErrorMessage = "Nome de usuário não pode ser nulo")]
        public string UserName { get; set; }

        [StringLength(15, MinimumLength = 3, ErrorMessage = "Senha deve possuir entre 3 e 15 caracteres")]
        [Required(ErrorMessage = "Senha não pode ser nulo")]
        public string UserPass { get; set; }

        [StringLength(100)]
        [EmailAddress(ErrorMessage = "Dado inserido não é um email!")]
        [Required(ErrorMessage = "Email não pode ser nulo")]
        public string Email { get; set; }

        [JsonIgnore]
        public ICollection<Gastos> Gastos { get; set; }
    }

}
