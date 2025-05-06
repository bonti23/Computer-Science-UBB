using System;
using System.IO;
using System.Reflection;
using BasketNetworking;
using BasketPersistence;
using BasketServices;
using log4net;
using log4net.Config;
using System.Reflection;
using log4net;
using log4net.Config;
using System.Configuration;
using System.Net.Sockets;
using BasketModel;
using BasketNetworking;
using BasketNetworking.JsonProtocol;

namespace BasketServer
{
    public class Program
    {
        private const int DefaultPort = 55555;
        private static String Default_IP="127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));

        public static void Main(string[] args)
        {
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
            log.Info("Starting  server");
            log.Info("Reading properties from App.config ...");
            int port = DefaultPort;
            String ip = Default_IP;
            String portS= ConfigurationManager.AppSettings["port"];
            if (portS == null)
            {
                log.Debug("Port property not set. Using default value "+DefaultPort);
            }
            else
            {
                bool result = Int32.TryParse(portS, out port);
                if (!result)
                {
                    log.Debug("Port property not a number. Using default value "+ DefaultPort);
                    port = DefaultPort;
                    log.Debug("Portul "+port);
                }
            }
            Console.WriteLine("Port property set to "+port);
            String ipS=ConfigurationManager.AppSettings["ip"];
           
            if (ipS == null)
            {
                log.Info("Port property not set. Using default value " + Default_IP);
            }
           
            else
            {
                ip = ipS;
                log.Debug("IP property set to " + ip);
            }
           
            Console.WriteLine("IP property set to " + ip);

            Console.WriteLine("Configuration Settings for concursDB {0}", GetConnectionStringByName("BasketServer"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("BasketServer"));
            GameRepository game_repo = new GameDBRepository(props);
            UserRepository user_repo = new UserDBRepository(props);
            PurchaseRepository purchase_repo = new PurchaseDBRepository(props);
            IService service=new Service(game_repo, user_repo, purchase_repo);
            
            log.DebugFormat("Starting server on IP {0} and port {1}", ip, port);
            JsonServer server = new JsonServer(ip,port, service);
            Console.WriteLine("Server created");
            server.Start();
            log.Debug("Server started ...");
            Console.ReadLine();        
        }
        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;
            ConnectionStringSettings connectionStringSettings = ConfigurationManager.ConnectionStrings[name];
            if (connectionStringSettings != null)
            {
                returnValue = connectionStringSettings.ConnectionString;
            }
            return returnValue;
        }
    }
    public class JsonServer: ConcurrentServer 
    {
        private IService server;
        private ClientWorker worker;
        private static readonly ILog log = LogManager.GetLogger(typeof(JsonServer));
        public JsonServer(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
            log.Debug("Creating JsonServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            if (client == null)
            {
                Console.WriteLine("TcpClient is null.");
                throw new ArgumentNullException(nameof(client), "TcpClient cannot be null.");
            }

            worker = new ClientWorker(server, client);
            return new Thread(worker.run);
        }
    }
}
