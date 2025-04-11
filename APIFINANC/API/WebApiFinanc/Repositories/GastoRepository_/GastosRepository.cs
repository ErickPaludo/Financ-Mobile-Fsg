using WebApiFinanc.Context;
using WebApiFinanc.Models;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc.Repositories.GastoRepository_
{
    public class GastosRepository : RepositoryDefault<Gastos>, IGastosRepository
    {
        public GastosRepository(AppDbContext context) : base(context)
        {
        }
    }
}
