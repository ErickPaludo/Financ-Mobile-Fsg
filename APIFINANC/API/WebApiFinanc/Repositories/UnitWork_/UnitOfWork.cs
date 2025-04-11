using WebApiFinanc.Context;
using WebApiFinanc.Repositories.CreditoRepository_;
using WebApiFinanc.Repositories.DebitoRepository_;
using WebApiFinanc.Repositories.GastoRepository_;
using WebApiFinanc.Repositories.GastoStatusRepository_;
using WebApiFinanc.Repositories.SaldoRepository_;
using WebApiFinanc.Repositories.UsuarioRepository_;

namespace WebApiFinanc.Repositories.UnitWork
{
    public class UnitOfWork : IUnitOfWork
    {
        private IUsuarioRepository? _usuRepo;
        private IGastosRepository? _gastosRepo;
        private IGastoStatusRepository? _gastosRepoStatus;
        private ICreditoRepository? _creditoRepo;
        private IDebitoRepository? _debitoRepo;
        private ISaldoRepository? _saldoRepo;


        public AppDbContext _context;

        public UnitOfWork(AppDbContext context)
        {
            _context = context;
        }

        public IUsuarioRepository UsuarioRepository { get { return _usuRepo = _usuRepo ?? new UsuarioRepository(_context); } }
        public IGastosRepository GastosRepository { get { return _gastosRepo = _gastosRepo ?? new GastosRepository(_context); } }
        public IGastoStatusRepository GastoStatusRepository { get { return _gastosRepoStatus = _gastosRepoStatus ?? new GastoStatusRepository(_context); } }
        public ICreditoRepository CreditoRepository { get { return _creditoRepo = _creditoRepo ?? new CreditoRepository(_context); } }
        public IDebitoRepository DebitoRepository { get { return _debitoRepo = _debitoRepo ?? new DebitoRepository(_context); } }
        public ISaldoRepository SaldoRepository { get { return _saldoRepo = _saldoRepo ?? new SaldoRepository(_context); } }

        public void Commit()
        {
            _context.SaveChanges();
        }
    }
}
