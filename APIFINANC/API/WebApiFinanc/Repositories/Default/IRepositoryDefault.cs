using System.Linq.Expressions;
using WebApiFinanc.Models;
using WebApiFinanc.Pagination;

namespace WebApiFinanc.Repositories.Default
{
    public interface IRepositoryDefault<T> where T : class
    {
        IQueryable<T> Get();
        IEnumerable<T>? GetObjects(Expression<Func<T, bool>> predicate);
        T? Create(T userobject);
        T? Update(T userobject);
        bool Delete(Expression<Func<T, bool>> predicate);
        bool ObjectAny(Expression<Func<T, bool>> predicate);
        PagedList<T> GetWithParameters(IQueryable<T> objeto, QueryStringParameters produtoParameters, Expression<Func<T, int>> ordenation);
    }
}
