using Microsoft.EntityFrameworkCore;
using System.Linq.Expressions;
using WebApiFinanc.Context;
using WebApiFinanc.Models.DTOs.Credito;
using WebApiFinanc.Models.DTOs.Debito;
using WebApiFinanc.Pagination;
using WebApiFinanc.Repositories.DebitoRepository_;
using WebApiFinanc.Repositories.Default;

namespace WebApiFinanc.Repositories.CreditoRepository_
{
    public class CreditoRepository : RepositoryDefault<Credito>, ICreditoRepository 
    {
        public CreditoRepository(AppDbContext context) : base(context)
        {
        }

        public PagedList<CreditoDTO> GetPagination(QueryStringParameters pagination, Expression<Func<CreditoDTO,int>> ordenation, IQueryable<CreditoDTO> credito)
        {
            var gastos = credito
           .AsNoTracking()
           .OrderBy(ordenation)
           .AsQueryable();
            var debitosOrdenados = PagedList<CreditoDTO>.TotalPagedList(gastos, pagination.PageNumber, pagination.PageSize);
            return debitosOrdenados;
        }
    }
}
