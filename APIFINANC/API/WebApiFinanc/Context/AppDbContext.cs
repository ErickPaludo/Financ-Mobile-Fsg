using Microsoft.EntityFrameworkCore;
using WebApiFinanc.Models;
using WebApiFinanc.Models.DTOs.Credito;

namespace WebApiFinanc.Context
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }
        public DbSet<Usuarios>? Usuarios { get; set; }
        public DbSet<Gastos>? Gastos { get; set; }
        public DbSet<Credito>? Credito { get; set; }
        public DbSet<Debito>? Debito { get; set; }
        public DbSet<GastosStatus>? GastosStatus { get; set; }
        public DbSet<Saldo>? Saldo { get; set; }
    }
}
