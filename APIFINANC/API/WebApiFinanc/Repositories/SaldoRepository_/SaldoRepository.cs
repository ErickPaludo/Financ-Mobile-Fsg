using WebApiFinanc.Context;
using WebApiFinanc.Models;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc.Repositories.SaldoRepository_
{
    public class SaldoRepository : RepositoryDefault<Saldo>, ISaldoRepository
    {
        public SaldoRepository(AppDbContext context) : base(context)
        {
        }
    }
}
