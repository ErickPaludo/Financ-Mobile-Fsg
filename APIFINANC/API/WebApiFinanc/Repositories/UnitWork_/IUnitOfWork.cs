
using WebApiFinanc.Repositories.CreditoRepository_;
using WebApiFinanc.Repositories.DebitoRepository_;
using WebApiFinanc.Repositories.GastoRepository_;
using WebApiFinanc.Repositories.GastoStatusRepository_;
using WebApiFinanc.Repositories.SaldoRepository_;
using WebApiFinanc.Repositories.UsuarioRepository_;

namespace WebApiFinanc.Repositories.UnitWork
{
    public interface IUnitOfWork
    {
        IUsuarioRepository UsuarioRepository { get; }
        IGastosRepository GastosRepository { get; }
        IGastoStatusRepository GastoStatusRepository { get; }
        IDebitoRepository DebitoRepository { get; }
        ICreditoRepository CreditoRepository { get; }
        ISaldoRepository SaldoRepository { get; }
        void Commit();
    }
}
