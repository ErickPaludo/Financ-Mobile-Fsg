namespace WebApiFinanc.Filters.FiltersControllers
{
    public class FilterDataParameter
    {
        public DateTime DataIni { get; set; } = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 5);
        public DateTime DataFim { get; set; } = new DateTime(DateTime.Today.Year, DateTime.Today.Month - 1, 5);
    }
}
