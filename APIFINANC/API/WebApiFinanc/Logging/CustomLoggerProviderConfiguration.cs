namespace WebApiFinanc.Logging
{
    public class CustomLoggerProviderConfiguration
    {
        public string LogPath { get; set; }
        public LogLevel LogLevel { get; set; } = LogLevel.Warning;
        public int EventId { get; set; } = 0;
    }
}
