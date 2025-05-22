using System;
using System.Configuration;
using System.IO;
using System.Reflection;
using System.Windows.Forms;
using Newtonsoft.Json;
using BasketServices;
using BasketNetworking;
using BasketNetworking.JsonProtocol;
using log4net;
using log4net.Config;

namespace BasketClient
{
    static class Program
    {
        private static readonly int DefaultPort = 55556;
        private static readonly string Default_IP = "127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));

        [STAThread]
        static void Main()
        {
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
            log.Debug("Reading properties from app.config...");

            int port = DefaultPort;
            string ip = Default_IP;
            string portS = ConfigurationManager.AppSettings["port"];
            if (portS == null)
            {
                log.DebugFormat("Port property not set. Using default value {0}", DefaultPort);
            }
            else if (!int.TryParse(portS, out port))
            {
                log.DebugFormat("Port property not a number. Using default value {0}", Default_IP);
                port = DefaultPort;
            }

            string ipS = ConfigurationManager.AppSettings["ip"];
            if (ipS != null)
            {
                ip = ipS;
            }
            else
            {
                log.DebugFormat("IP property not set. Using default value {0}", Default_IP);
            }

            log.InfoFormat("Using server on IP {0} and port {1}", ip, port);

            // Initialize the service proxy
            IService server = new Proxy(ip, port);

            // Start the application
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            try
            {
                LoginForm loginWindow = new LoginForm(server);
                Application.Run(loginWindow);
            }
            catch (Exception ex)
            {
                log.Error("Application failed to start", ex);
                MessageBox.Show($"Application failed to start: {ex.Message}", "Error", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }
        }
    }
}