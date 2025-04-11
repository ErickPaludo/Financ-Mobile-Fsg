using AutoMapper;
using WebApiFinanc.Models.DTOs.Credito;
using WebApiFinanc.Models.DTOs.Debito;
using WebApiFinanc.Models.DTOs.Saldo_;

namespace WebApiFinanc.Models.Dominio
{
    public class DominioDTO : Profile
    {
        public DominioDTO()
        {
            CreateMap<Gastos, CreditoDTO>().ReverseMap();
            CreateMap<Gastos, DebitoDTO>().ReverseMap();
            CreateMap<Gastos, CreditoEditDTO>().ReverseMap();
            CreateMap<Gastos, DebitoEditDTO>().ReverseMap();
            CreateMap<Debito, DebitoEditDTO>().ReverseMap();
            CreateMap<Credito, CreditoEditDTO>().ReverseMap();
            CreateMap<Saldo, SaldoEditDTO>().ReverseMap();
            CreateMap<Gastos, Credito>().ReverseMap();
        }
    }
}

