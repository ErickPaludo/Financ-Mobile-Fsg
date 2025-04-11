using WebApiFinanc.Context;
using WebApiFinanc.Models;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc.Repositories.GastoStatusRepository_
{
    public class GastoStatusRepository : RepositoryDefault<GastosStatus>, IGastoStatusRepository
    {
        public GastoStatusRepository(AppDbContext context) : base(context)
        {
        }
    }
}
