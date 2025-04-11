using WebApiFinanc.Context;
using WebApiFinanc.Models;
using WebApiFinanc.Pagination;
using WebApiFinanc.Repositories.DebitoRepository_;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc
{
    public class DebitoRepository : RepositoryDefault<Debito>, IDebitoRepository
    {
        public DebitoRepository(AppDbContext context) : base(context)
        {
        } 
    }
}
