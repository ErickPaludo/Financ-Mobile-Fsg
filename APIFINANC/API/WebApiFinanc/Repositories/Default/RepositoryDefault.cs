using Microsoft.EntityFrameworkCore;
using System.Linq;
using System.Linq.Expressions;
using WebApiFinanc.Context;
using WebApiFinanc.Filters.FiltersControllers;
using WebApiFinanc.Models;
using WebApiFinanc.Pagination;

namespace WebApiFinanc.Repositories.Default
{
    public class RepositoryDefault<T> : IRepositoryDefault<T> where T : class
    {
        private readonly AppDbContext _context;

        public RepositoryDefault(AppDbContext context)
        {
            _context = context;
        }

        public IEnumerable<T>? GetObjects(Expression<Func<T, bool>> predicate)
        {
            return _context.Set<T>().AsNoTracking().Where(predicate).ToList();
        }
        public IQueryable<T> Get()
        {
            return _context.Set<T>().AsNoTracking();
        }
        public bool ObjectAny(Expression<Func<T, bool>> predicate)
        {
            return _context.Set<T>().AsNoTracking().Any(predicate);
        }
        public T? Create(T userobject)
        {
            _context.Set<T>().Add(userobject);
            return userobject;

        }
        public T? Update(T userobject)
        {
            _context.Set<T>().Update(userobject);
            return userobject;
        }

        public bool Delete(Expression<Func<T, bool>> predicate)
        {
            try
            {
                var entity = _context.Set<T>().AsNoTracking().Where(predicate).ToList();
                _context.Set<T>().RemoveRange(entity);
                return true;
            }
            catch
            {
                return false;
            }
        }

        public PagedList<T> GetWithParameters(IQueryable<T> objeto, QueryStringParameters produtoParameters, Expression<Func<T, int>> ordenation)
        {
            var gastos = _context.Set<T>()
            .AsNoTracking()
            .OrderBy(ordenation)
            .AsQueryable();
            var debitosOrdenados = PagedList<T>.TotalPagedList(gastos, produtoParameters.PageNumber, produtoParameters.PageSize);
            return debitosOrdenados;
        }
    }
}
