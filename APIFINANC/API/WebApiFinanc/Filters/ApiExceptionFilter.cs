using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.AspNetCore.Mvc;

namespace WebApiFinanc.Filters
{
    public class ApiExceptionFilter : IExceptionFilter
    {
        private readonly ILogger<ApiExceptionFilter> _logger;
        public ApiExceptionFilter(ILogger<ApiExceptionFilter> logger)
        {
            _logger = logger;
        }
        public void OnException(ExceptionContext context)
        {
            string mensagemErro;
            int statusCode;

            switch (context.Exception)
            {
                case KeyNotFoundException ex:
                    statusCode = 404;
                    mensagemErro = $"O recurso solicitado não foi encontrado. {ex.Message}";
                    break;

                case ArgumentException ex:
                    statusCode = 400;
                    mensagemErro = $"Erro nos parâmetros da requisição. {ex.Message}";
                    break;

                case UnauthorizedAccessException:
                    statusCode = 401;
                    mensagemErro = "Você não tem permissão para acessar este recurso.";
                    break;

                default:
                    statusCode = 500;
                    mensagemErro = "Ocorreu um erro interno no servidor.";
                    break;
            }

            _logger.LogError(context.Exception,
                $"[{DateTime.Now}] - Path: {context.HttpContext.Request.Path} \n Ocorreu um erro ({statusCode}): {context.Exception.Message}");

            context.Result = new ObjectResult(new
            {
                StatusCode = statusCode,
                Message = mensagemErro
            })
            {
                StatusCode = statusCode
            };

            context.ExceptionHandled = true;
        }
    }
}
