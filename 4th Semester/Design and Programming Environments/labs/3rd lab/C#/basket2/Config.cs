using System.Collections.Generic;
using System.Configuration;
using System.Data.SQLite;
using System.Reflection;
using log4net;

public static class Config
{
    private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

    // Metodă pentru obținerea connection string din App.config
    public static string GetConnectionStringByName(string name)
    {
        var connectionString = ConfigurationManager.ConnectionStrings[name]?.ConnectionString;

        if (string.IsNullOrEmpty(connectionString))
        {
            log.Error($"Connection string {name} nu a fost găsit sau este gol. Asigură-te că este definit în App.config.");
        }
        else
        {
            log.Info($"Connection string găsit: {connectionString}");
        }

        return connectionString;
    }

    // Populăm DatabaseProperties cu connection string din App.config
    public static readonly IDictionary<string, string> DatabaseProperties = new Dictionary<string, string>
    {
        // Înlocuiește "DefaultConnection" cu "InotDb"
        { "ConnectionString", GetConnectionStringByName("InotDb") }, // Obținem din App.config
        { "ConnectionType", "System.Data.SQLite.SQLiteConnection" } // Specificăm tipul de conexiune SQLite
    };

    // Metodă pentru a deschide conexiunea la baza de date
    public static void OpenDatabaseConnection()
    {
        string connectionString = DatabaseProperties["ConnectionString"];
        string connectionType = DatabaseProperties["ConnectionType"];

        if (connectionType == "System.Data.SQLite.SQLiteConnection")
        {
            try
            {
                using (var connection = new SQLiteConnection(connectionString))
                {
                    connection.Open();
                    log.Info("Conexiune la baza de date reușită!");
                }
            }
            catch (Exception ex)
            {
                log.Error($"Conexiune la baza de date eșuată: {ex.Message}");
                throw;
            }
        }
        else
        {
            log.Error("Tipul de conexiune nu este valid.");
        }
    }
}
