using Microsoft.EntityFrameworkCore;
using WebApiFinanc.Context;
using WebApiFinanc.Models;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc.Repositories.UsuarioRepository_
{
    public class UsuarioRepository : RepositoryDefault<Usuarios>, IUsuarioRepository
    {
        public UsuarioRepository(AppDbContext context) : base(context)
        {
        }
    }
}
