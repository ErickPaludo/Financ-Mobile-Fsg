using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using WebApiFinanc.Filters.FiltersControllers;
using WebApiFinanc.Models;
using WebApiFinanc.Models.DTOs;
using WebApiFinanc.Models.DTOs.Credito;
using WebApiFinanc.Models.DTOs.Debito;
using WebApiFinanc.Pagination;
using WebApiFinanc.Repositories.UnitWork;
using WebApiFinanc.Services;

namespace WebApiFinanc.Controllers
{
    [Route("geral")]
    [ApiController]
    public class GastosController : ControllerBase
    {
        private readonly IUnitOfWork _unit;
        private readonly ILogger<GastosController> _logger;
        private readonly IGerenciaGastos _gerenciamento;
        private readonly IMapper _mapper;
        public GastosController(IUnitOfWork unit, ILogger<GastosController> logger, IGerenciaGastos gerenciamento, IMapper mapper)
        {
            _unit = unit;
            _logger = logger;
            _gerenciamento = gerenciamento;
            _mapper = mapper;
        }

        #region Retorna todos os gastos
        [HttpGet("retorno")]
        public ActionResult<IEnumerable<Geral>> RetornoGeral([FromQuery] QueryStringParameters geralParameters, [FromQuery] FilterDataParameter dateParam)
        {

            IEnumerable<Geral> geral = (_unit.CreditoRepository.Get()
     .Join(_unit.GastoStatusRepository.Get(),
         gastos => gastos.Id,
         status => status.GPaiId,
         (gastos, status) => new Geral
         {
             Titulo = gastos.Titulo,
             Decricao = gastos.Descricao,
             Valor = gastos.Valor * -1,
             Dthr = gastos.DthrReg.AddMonths(status.Parcela - 1),
             Parcela = $"{status.Parcela}/{gastos.TotalParcelas}",
             Status = _gerenciamento.DeParaStatus(status.Status),
             Categoria = "Crédito"
         })
     .ToList()).Concat(_unit.DebitoRepository.Get()
                    .Select(gastos => new Geral
                    {
                        Titulo = gastos.Titulo,
                        Decricao = gastos.Descricao,
                        Valor = gastos.Valor * -1,
                        Dthr = gastos.DthrReg,
                        Parcela = string.Empty,
                        Categoria = "Débito",
                        Status = _gerenciamento.DeParaStatus(gastos.Status)
                    })
                    .ToList()) // 🚀 Força a execução antes do Concat()        
                .Concat(_unit.SaldoRepository.Get()
                    .Select(gastos => new Geral
                    {
                        Titulo = gastos.Titulo,
                        Decricao = gastos.Descricao,
                        Valor = gastos.Valor,
                        Dthr = gastos.DthrReg,
                        Parcela = string.Empty,
                        Categoria = "Saldo",
                        Status = _gerenciamento.DeParaStatus(gastos.Status)
                    })
                    .ToList()).Where(x => x.Dthr >= dateParam.DataIni && x.Dthr <= dateParam.DataFim);

            var debitosOrdenados = PagedList<Geral>.TotalPagedList(geral.AsQueryable(), geralParameters.PageNumber, geralParameters.PageSize);

            Response.Headers.Append("X-Pagination", JsonConvert.SerializeObject(new
            {
                debitosOrdenados.TotalCount,
                debitosOrdenados.PageSize,
                debitosOrdenados.CurrentPage,
                debitosOrdenados.TotalPages,
                debitosOrdenados.HasNext,
                debitosOrdenados.HasPrevious
            }));

            return Ok(debitosOrdenados);
        }

        [HttpGet("retornafiltrado")]
        public ActionResult<IEnumerable<Gastos>> RetornoGeralFiltro([FromQuery] int iduser, [FromQuery] DateTime dtinicial, [FromQuery] DateTime dtfinal)
        {
            IEnumerable<Geral> geral = (from gastos in _unit.CreditoRepository.Get()
                                        join status in _unit.GastoStatusRepository.Get()
                                            on gastos.Id equals status.GPaiId
                                        where gastos.UserId == iduser && gastos.DthrReg.AddMonths(status.Parcela - 1) >= dtinicial && gastos.DthrReg.AddMonths(status.Parcela - 1) <= dtfinal
                                        select new Geral
                                        {
                                            Titulo = gastos.Titulo,
                                            Decricao = gastos.Descricao,
                                            Valor = gastos.Valor * -1,
                                            Dthr = gastos.DthrReg.AddMonths(status.Parcela - 1),
                                            Parcela = $"{status.Parcela.ToString()}/{gastos.TotalParcelas.ToString()}",
                                            Status = _gerenciamento.DeParaStatus(status.Status),
                                            Categoria = "Crédito"
                                        })
                                        .Concat(from gastos in _unit.DebitoRepository.Get()
                                                where gastos.UserId == iduser && gastos.DthrReg >= dtinicial && gastos.DthrReg <= dtfinal
                                                select new Geral
                                                {
                                                    Titulo = gastos.Titulo,
                                                    Decricao = gastos.Descricao,
                                                    Valor = gastos.Valor * -1,
                                                    Dthr = gastos.DthrReg,
                                                    Parcela = string.Empty,
                                                    Categoria = "Débito",
                                                    Status = _gerenciamento.DeParaStatus(gastos.Status),
                                                })
                                        .Concat(from gastos in _unit.SaldoRepository.Get()
                                                where gastos.UserId == iduser && gastos.DthrReg >= dtinicial && gastos.DthrReg <= dtfinal
                                                select new Geral
                                                {
                                                    Titulo = gastos.Titulo,
                                                    Decricao = gastos.Descricao,
                                                    Valor = gastos.Valor,
                                                    Dthr = gastos.DthrReg,
                                                    Parcela = string.Empty,
                                                    Categoria = "Saldo",
                                                    Status = _gerenciamento.DeParaStatus(gastos.Status),
                                                });

            return Ok(geral.ToList());
        }
        #endregion  
    }
}
