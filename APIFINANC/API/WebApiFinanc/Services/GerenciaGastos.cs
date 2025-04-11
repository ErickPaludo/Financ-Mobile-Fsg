using AutoMapper;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Query.SqlExpressions;
using WebApiFinanc.Controllers;
using WebApiFinanc.Models;
using WebApiFinanc.Models.DTOs;
using WebApiFinanc.Models.DTOs.Credito;
using WebApiFinanc.Models.DTOs.Debito;
using WebApiFinanc.Models.DTOs.Saldo_;
using WebApiFinanc.Repositories.UnitWork;

namespace WebApiFinanc.Services
{
    public class GerenciaGastos : IGerenciaGastos
    {
        private readonly IUnitOfWork _unit;
        private readonly IMapper _mapper;
        public GerenciaGastos(IUnitOfWork unit, IMapper mapper)
        {
            _unit = unit;
            _mapper = mapper;
        }

        public void Excluir(int id, string tipo = "O")
        {
            if (tipo.Equals("D"))
            {
                if (_unit.DebitoRepository.ObjectAny(x => x.Id == id))
                {
                    _unit.DebitoRepository.Delete(x => x.Id == id);
                }
                else
                {
                    throw new KeyNotFoundException($"Id solicitado = ({id})");
                }
            }
            else if (tipo.Equals("C"))
            {
                if (_unit.CreditoRepository.ObjectAny(x => x.Id == id) && _unit.GastoStatusRepository.ObjectAny(x => x.GPaiId == id))
                {
                    _unit.CreditoRepository.Delete(x => x.Id == id);
                    _unit.GastoStatusRepository.Delete(x => x.Id == id);
                }
                else
                {
                    throw new KeyNotFoundException($"Id solicitado = ({id})");
                }
            }
            else if (tipo.Equals("S"))
            {
                if (_unit.SaldoRepository.ObjectAny(x => x.Id == id))
                {
                    _unit.SaldoRepository.Delete(x => x.Id == id);
                }
                else
                {
                    throw new KeyNotFoundException($"Id solicitado = ({id})");
                }
            }
            _unit.Commit();
        }

        public Gastos Update(Gastos gastoModify)
        {
            DateTime dataVencimento = DateTime.Now;
            if (gastoModify.Categoria.Equals("C"))
            {
                bool somaMeses = false;
                bool atualizadata = false;

                DateTime novaData = gastoModify.DthrReg;

                var listobjoriginal = _unit.GastosRepository.GetObjects(x => x.GastoPaiId == gastoModify.Id);
                var objoriginal = listobjoriginal.FirstOrDefault();

                var diferenca = objoriginal.TotalParcelas - gastoModify.TotalParcelas;
                dataVencimento = objoriginal.DataVencimento.AddMonths(1);
                DateTime dataRegistro = objoriginal.DataVencimento.AddMonths(1);
                if (gastoModify.DthrReg != objoriginal.DthrReg)
                {
                    atualizadata = true;
                    dataVencimento = dataVencimento.AddMonths(objoriginal.TotalParcelas + (diferenca * -1) - 1);
                    dataRegistro = dataVencimento.AddMonths(-((diferenca * -1) - 1));
                }
                gastoModify.Valor = gastoModify.ValorIntegral / gastoModify.TotalParcelas;
                if (objoriginal.TotalParcelas != gastoModify.TotalParcelas)
                {

                    if (diferenca < 0)//Adiciona parcelas
                    {
                        somaMeses = true;
                        diferenca = diferenca * -1;//inverte o valor

                        dataVencimento = objoriginal.DataVencimento.AddMonths(diferenca);

                        for (int i = 1; i <= diferenca; i++)
                        {
                            var novocredito = new Gastos
                            {
                                Id = 0,
                                Titulo = gastoModify.Titulo,
                                Descricao = gastoModify.Descricao,
                                Valor = gastoModify.Valor,
                                ValorIntegral = gastoModify.ValorIntegral,
                                DthrReg = dataRegistro,
                                DataVencimento = dataVencimento,
                                TotalParcelas = gastoModify.TotalParcelas,
                                Pago = false,
                                Categoria = gastoModify.Categoria,
                                UserId = gastoModify.UserId,
                                GastoPaiId = gastoModify.Id,
                                Parcela = objoriginal.TotalParcelas + i
                            };
                            _unit.GastosRepository.Create(novocredito);
                            dataRegistro = dataRegistro.AddMonths(1);
                        }
                        _unit.Commit();
                    }
                    else if (diferenca > 0)//diminui parcelas
                    {
                        for (int i = 0; i < diferenca; i++)
                        {
                            _unit.GastosRepository.Delete(x => x.GastoPaiId.Equals(gastoModify.Id) && x.Parcela.Equals(objoriginal.TotalParcelas - i));
                        }
                        _unit.Commit();
                        listobjoriginal = _unit.GastosRepository.GetObjects(x => x.GastoPaiId == gastoModify.Id);
                    }
                }
                foreach (var obj in listobjoriginal)
                {
                    obj.Titulo = gastoModify.Titulo;
                    obj.Descricao = gastoModify.Descricao;
                    obj.Valor = gastoModify.Valor;
                    obj.ValorIntegral = gastoModify.ValorIntegral;
                    if (atualizadata)
                    {
                        novaData = gastoModify.DthrReg.AddMonths(obj.Parcela);
                        obj.DthrReg = novaData;
                    }
                    else
                    {
                        obj.DthrReg = somaMeses ? obj.DthrReg.AddMonths(obj.Parcela) : obj.DthrReg;
                    }

                    obj.DataVencimento = dataVencimento;
                    obj.TotalParcelas = gastoModify.TotalParcelas;
                    obj.Pago = gastoModify.Pago;

                    _unit.GastosRepository.Update(obj);
                    _unit.Commit();
                }
                return gastoModify;
            }
            else if (gastoModify.Categoria.Equals("D"))
            {
                _unit.GastosRepository.Update(gastoModify);
                return gastoModify;
            }
            else
            {
                return gastoModify;
            }
        }

        public Credito RegistraCredito(Credito credito)
        {
            int parcelaTotal = credito.TotalParcelas;

            if (credito.Valor > (decimal)0.01 && credito.ValorIntegral == (decimal)0.01) // Se for inserido somente o valor das parcelas
            {
                credito.ValorIntegral = credito.Valor * credito.TotalParcelas; //vai montar o valor total
            }
            else //se for inserido o valor total
            {
                credito.Valor = credito.ValorIntegral / credito.TotalParcelas; //quebrará o valo em valores de parcela
            }

            credito.DthrReg = credito.DthrReg.AddMonths(1);
            credito.DataVencimento = credito.DthrReg.AddMonths(credito.TotalParcelas - 1);
            var idcreddito = _unit.CreditoRepository.Create(credito);


            for (int i = 1; i <= parcelaTotal; i++)
            {
                var creditoSequencial = new GastosStatus
                {
                    FkGasto = idcreddito,
                    Parcela = i,
                    Status = "N"
                };
                _unit.GastoStatusRepository.Create(creditoSequencial);
            }
            _unit.Commit();
            return idcreddito;
        }

        public Debito RegistraDebito(Debito debito)
        {
            _unit.DebitoRepository.Create(debito);
            _unit.Commit();
            return debito;
        }

        public Debito UpdateDebito(int id, JsonPatchDocument<DebitoEditDTO> debito)
        {

            var gasto = _unit.DebitoRepository.GetObjects(x => x.Id == id).FirstOrDefault();

            if (gasto is null)
                throw new KeyNotFoundException($"Id solicitado = ({id})");

            var gastoDTO = _mapper.Map<DebitoEditDTO>(gasto);
            debito.ApplyTo(gastoDTO);


            _mapper.Map(gastoDTO, gasto);
            _unit.DebitoRepository.Update(gasto);
            _unit.Commit();

            return gasto;
        }

        public Credito UpdateCredito(int id, JsonPatchDocument<CreditoEditDTO> credito)
        {
            var gasto = _unit.CreditoRepository.GetObjects(x => x.Id == id).FirstOrDefault();

            bool alteraParcelas = false;
            bool alteraValorParcela = false;
            bool alteraValorIntegral = false;
            int dif = 0;
            if (gasto is null)
                throw new KeyNotFoundException($"Id solicitado = ({id})");

            var gastoDTO = _mapper.Map<CreditoEditDTO>(gasto);
            credito.ApplyTo(gastoDTO);

            if (gastoDTO.TotalParcelas != gasto.TotalParcelas)
            {
                alteraParcelas = true;
                alteraValorParcela = true;
                dif = gastoDTO.TotalParcelas - gasto.TotalParcelas;
                gastoDTO.Valor = gastoDTO.ValorIntegral / gastoDTO.TotalParcelas;
                gasto.DataVencimento = gasto.DataVencimento.AddMonths(dif);
                if (dif < 0)
                {
                    dif = dif * -1;

                    for (int i = 0; i < dif; i++)
                    {
                        _unit.GastoStatusRepository.Delete(x => x.GPaiId == id && x.Parcela == (gasto.TotalParcelas - i));
                    }
                }
                else
                {
                    var gastoStatus = _mapper.Map<Credito>(gastoDTO);
                    gastoStatus.Id = id;
                    for (int i = 1; i <= dif; i++)
                    {
                        var creditoSequencial = new GastosStatus
                        {
                            //  FkGasto = gastoStatus,
                            GPaiId = id,
                            Parcela = gasto.TotalParcelas + i,
                            Status = "N"
                        };

                        _unit.GastoStatusRepository.Create(creditoSequencial);
                    }
                }
            }

            if (gastoDTO.DthrReg != gasto.DthrReg)
            {
                gasto.DataVencimento = gastoDTO.DthrReg.AddMonths(gastoDTO.TotalParcelas + 1);
            }

            _mapper.Map(gastoDTO, gasto);
            _unit.CreditoRepository.Update(gasto);

            _unit.Commit();

            return gasto;
        }

        public Saldo RegistraSaldo(Saldo saldo)
        {
            _unit.SaldoRepository.Create(saldo);
            _unit.Commit();
            return saldo;
        }

        public Saldo UpdateSaldo(int id, JsonPatchDocument<SaldoEditDTO> saldo)
        {
            var saldoOrg = _unit.SaldoRepository.GetObjects(x => x.Id == id).FirstOrDefault();

            if (saldoOrg is null)
                throw new KeyNotFoundException($"Id solicitado = ({id})");

            var saldoDTO = _mapper.Map<SaldoEditDTO>(saldoOrg);
            saldo.ApplyTo(saldoDTO);

            _mapper.Map(saldoDTO, saldoOrg);
            _unit.SaldoRepository.Update(saldoOrg);
            _unit.Commit();

            return saldoOrg;
        }

        public string DeParaStatus(string status)
        {
            switch (status)
            {
                case "N":
                    return "Pendente";
                    break;
                case "S":
                    return "Pago";
                    break;
                case "A":
                    return "Atraso";
                    break;
                case "PA":
                    return "Pago com Atraso";
                    break;
                case "C":
                    return "Cancelado";
                    break;
                default:
                    return "Não definido";

            }
        }
    }
}
