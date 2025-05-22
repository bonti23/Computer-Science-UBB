using System;
using System.IO;
using System.Reflection;
using System.Collections.Generic;
using System.Threading.Tasks;
using BasketModel;
using BasketPersistence;
using BasketServices;
using log4net;
using log4net.Config;
using System.Configuration;
using Grpc.Core;
using Org.Example.ClientFx.Grpc;

namespace BasketServer
{
    public class Program
    {
        private const int DefaultPort = 55555;
        private static string Default_IP = "127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));

        public static async Task Main(string[] args)
        {
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
            log.Info("Starting server");

            int port = DefaultPort;
            string ip = Default_IP;
            string portS = ConfigurationManager.AppSettings["port"];
            if (portS != null)
            {
                if (!Int32.TryParse(portS, out port))
                    port = DefaultPort;
            }

            string ipS = ConfigurationManager.AppSettings["ip"];
            if (ipS != null)
            {
                ip = ipS;
            }

            Console.WriteLine("IP: " + ip);
            Console.WriteLine("Port: " + port);

            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString", GetConnectionStringByName("BasketServer"));

            GameRepository gameRepo = new GameDBRepository(props);
            UserRepository userRepo = new UserDBRepository(props);
            PurchaseRepository purchaseRepo = new PurchaseDBRepository(props);
            Service service = new Service(gameRepo, userRepo, purchaseRepo);

            NotificationServiceImplementation notificationServiceImplementation = new NotificationServiceImplementation();
            ProtoServiceImplementation protoServiceImplementation = new ProtoServiceImplementation(gameRepo, userRepo, purchaseRepo, service, notificationServiceImplementation);

            // Configurarea serverului gRPC
            var server = new Grpc.Core.Server
            {
                Services =
                {
                    BasketService.BindService(protoServiceImplementation),
                    NotificationService.BindService(notificationServiceImplementation)
                },
                Ports = { new ServerPort(ip, port, ServerCredentials.Insecure) }
            };

            server.Start();
            log.Info("Server started on " + ip + ":" + port);

            Console.ReadLine();
            await server.ShutdownAsync();
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
}
