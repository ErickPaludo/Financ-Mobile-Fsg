using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using WebApiFinanc;
using WebApiFinanc.Context;
using WebApiFinanc.Filters;
using WebApiFinanc.Logging;
using WebApiFinanc.Models.Dominio;
using WebApiFinanc.Repositories.CreditoRepository_;
using WebApiFinanc.Repositories.DebitoRepository_;
using WebApiFinanc.Repositories.Default;
using WebApiFinanc.Repositories.GastoRepository_;
using WebApiFinanc.Repositories.GastoStatusRepository_;
using WebApiFinanc.Repositories.SaldoRepository_;
using WebApiFinanc.Repositories.UnitWork;
using WebApiFinanc.Repositories.UsuarioRepository_;
using WebApiFinanc.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAllOrigins",
        builder =>
        {
            builder.AllowAnyOrigin()
                   .AllowAnyMethod()
                   .AllowAnyHeader();
        });
});


builder.Services.AddControllers(options =>
{
    options.Filters.Add(typeof(ApiExceptionFilter));
})
.AddJsonOptions(options =>
{
    options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles;
}).AddNewtonsoftJson();

builder.Logging.AddProvider(new CustomLoggerProvider(new CustomLoggerProviderConfiguration
{
    LogPath = builder.Configuration["DirectoryLog"],
    LogLevel = LogLevel.Information
}));

builder.Services.AddScoped<ApiLoggingFilter>(); //Não ultilizada
builder.Services.AddScoped<IUsuarioRepository, UsuarioRepository>();
builder.Services.AddScoped<IGastosRepository, GastosRepository>();
builder.Services.AddScoped<IGastoStatusRepository, GastoStatusRepository>();
builder.Services.AddScoped<ICreditoRepository, CreditoRepository>();
builder.Services.AddScoped<IDebitoRepository, DebitoRepository>();
builder.Services.AddScoped<ISaldoRepository, SaldoRepository>();
builder.Services.AddScoped(typeof(IRepositoryDefault<>), typeof(RepositoryDefault<>));
builder.Services.AddScoped<IUnitOfWork, UnitOfWork>();
builder.Services.AddScoped<IGerenciaGastos, GerenciaGastos>();
builder.Services.AddAutoMapper(typeof(DominioDTO));
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

string mySqlConnection = builder.Configuration.GetConnectionString("DefaultConnection");


builder.Services.AddDbContext<AppDbContext>(options =>
                    options.UseMySql(mySqlConnection,
                    ServerVersion.AutoDetect(mySqlConnection)));


var app = builder.Build();
app.UseCors("AllowAllOrigins");
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment() || app.Environment.IsProduction())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseAuthorization();
app.MapControllers();
app.Run();
