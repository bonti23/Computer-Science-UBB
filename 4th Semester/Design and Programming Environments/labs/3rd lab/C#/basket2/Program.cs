using System;
using System.Windows;
using log4net;

namespace basket2
{
    public class Program
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));

        [STAThread]
        public static void Main()
        {
            // Inițializează log4net
            log4net.Config.XmlConfigurator.Configure();

            try
            {
                // Deschide conexiunea la baza de date
                Config.OpenDatabaseConnection();
                
                // Crează instanța aplicației și rulează-o
                Application app = new Application();
                app.Run(new Login()); // Deschide fereastra de Login
            }
            catch (Exception ex)
            {
                // Loghează eroarea
                log.Error("Eroare la conectarea la baza de date", ex);
                MessageBox.Show($"Eroare la conectarea la baza de date: {ex.Message}", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}