using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json;
using WebApiFinanc.Context;
using WebApiFinanc.Filters;
using WebApiFinanc.Models;
using WebApiFinanc.Repositories.UnitWork;

namespace WebApiFinanc.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsuariosController : ControllerBase
    {
        private readonly IUnitOfWork _unit;
        private readonly ILogger<UsuariosController> _logger;

        public UsuariosController(IUnitOfWork unit, ILogger<UsuariosController> logger)
        {
            _unit = unit;
            _logger = logger;
        }

        [HttpGet("/retornausers")]
        public ActionResult<IEnumerable<Usuarios>> RetornaUsers()
        {
            return Ok(_unit.UsuarioRepository.Get());
        }

        [HttpGet("/selecionauser/{id:int}",Name ="Obter")]
        public ActionResult<IEnumerable<Usuarios>> SelecionaUsers(int id)
        {
            var user = _unit.UsuarioRepository.GetObjects(x => x.UserId == id);
            if (user is null)
            {
                return NoContent();
            }

            return Ok(user);
        }

        [HttpPost("/cadastrauser")]
        public IActionResult CadastraUser([FromBody] Usuarios usuario)
        {
         
                if (_unit.UsuarioRepository.ObjectAny(x => x.UserName == usuario.UserName || x.Email == usuario.Email))
                {
                    return Conflict($"Usuário {usuario.FirstName} {usuario.LastName} já está cadastrado!");
                }
                _unit.UsuarioRepository.Create(usuario);
            
            _unit.Commit();
            return new CreatedAtRouteResult("Obter", new { id = usuario.UserId },usuario);
        }
        [HttpPatch("/alteruser")]
        public IActionResult AlterarUser([FromBody] Usuarios usuarios)
        {
            if (!_unit.UsuarioRepository.ObjectAny(x => x.UserId == usuarios.UserId))
            {
                return NotFound();
            }

            _unit.UsuarioRepository.Update(usuarios);
            _unit.Commit();
            return Ok(usuarios);
        }
    }
}
