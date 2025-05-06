using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using BasketModel;
using BasketNetworking.DTO;
using BasketServices;

namespace BasketNetworking.JsonProtocol;

public class ClientWorker : IObserver
{
    private IService service;
    private TcpClient connection;

    private NetworkStream stream;
    private volatile bool connected;
    private static readonly log4net.ILog log = log4net.LogManager.GetLogger("ClientWorker");
    public ClientWorker(IService service, TcpClient connection)
    {
        this.service = service;
        this.connection = connection;
        try
        {
            stream=connection.GetStream();
            connected=true;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    public virtual void run()
    {
        using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
        while(connected)
        {
            try
            {
                string requestJson = reader.ReadLine();
                if (string.IsNullOrEmpty(requestJson)) continue;
                log.DebugFormat("Received json request {0}",requestJson);
                Console.WriteLine("Received json request {0}",requestJson);
                Request request = JsonSerializer.Deserialize<Request>(requestJson);
                log.DebugFormat("Deserializaed Request {0} ",request);
                Console.WriteLine("Deserialized Request {0} ",request);
                Response response =handleRequest(request);
                if (response!=null)
                {
                    sendResponse(response);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                if (e.InnerException!=null)
                    log.ErrorFormat("run inner error {0}",e.InnerException.Message);
                log.Error(e.StackTrace);
            }
				
            try
            {
                Thread.Sleep(1000);
            }
            catch (Exception e)
            {
                log.Error(e.StackTrace);
            }
        }
        try
        {
            stream.Close();
            connection.Close();
        }
        catch (Exception e)
        {
            log.Error("Error "+e);
        }
    }
    private Response handleRequest(Request request)
    {
        try
        {
            switch (request.Type)
            {
                case RequestType.LOGIN:
                    return HandleLogin(request);
                case RequestType.LOGOUT:
                    return HandleLogout(request);
                case RequestType.SIGNUP:
                    return HandleSignup(request);
                case RequestType.FILTER_GAMES:
                    return HandleFilterGames(request);
                case RequestType.ADD_PURCHASE:
                    return HandleAddPurchase(request);
                case RequestType.FIND_BY_CLIENT:
                    return HandleFindByClient(request);
                case RequestType.UPDATE_SEATS:
                    return HandleUpdateSeats(request);
                case RequestType.CHECK_USERNAME:
                    return HandleCheckUsername(request);
                default:
                    return new Response(ResponseType.ERROR, "Unknown request type");
            }
        }
        catch (Exception e)
        {
            return new Response(ResponseType.ERROR, e.Message);
        }
    }
    private void sendResponse(Response response)
    {
        String jsonString=JsonSerializer.Serialize(response);
        log.DebugFormat("sending response {0}",jsonString);
        lock (stream)
        {
            byte[] data = Encoding.UTF8.GetBytes(jsonString + "\n"); 
            stream.Write(data, 0, data.Length);
            stream.Flush();
        }

    }
    private Response HandleLogin(Request request)
    {Console.WriteLine("handle login");
        try
        {
            UserDTO dto = JsonSerializer.Deserialize<UserDTO>(request.Data.ToString());
            service.Login(dto.username, dto.password, this);
            return new Response(ResponseType.LOGIN_SUCCESS, null);
        }
        catch (Exception e)
        {
            return new Response(ResponseType.LOGIN_FAILED, e.Message);
        }
    }
    private Response HandleLogout(Request request)
    {
        try
        {
            UserDTO dto = JsonSerializer.Deserialize<UserDTO>(request.Data.ToString());
            var username = dto.username;
            Console.WriteLine(username + "Logout request");
            service.Logout(username, this);
            connected = false;
            return new Response(ResponseType.LOGOUT_SUCCESS, null);
        }
        catch (Exception e)
        {
            return new Response(ResponseType.LOGOUT_FAILED, e.Message);
        }
    }

    private Response HandleSignup(Request request)
    {
        try
        {
            // Verifică dacă Data nu este null sau gol
            if (request.Data == null || string.IsNullOrEmpty(request.Data.ToString()))
            {
                log.Error("Signup failed: Data is empty or null");
                return new Response(ResponseType.SIGNUP_FAILED, "Data is empty.");
            }

            // Deserializare
            UserDTO dto = JsonSerializer.Deserialize<UserDTO>(request.Data.ToString());
            if (dto == null || string.IsNullOrEmpty(dto.name) || string.IsNullOrEmpty(dto.username) || string.IsNullOrEmpty(dto.password))
            {
                log.Error("Signup failed: Missing required fields.");
                return new Response(ResponseType.SIGNUP_FAILED, "Name, Username, or Password is missing.");
            }

            // Dacă datele sunt valide, apelează serviciul de signup
            service.Signup(dto.name, dto.username, dto.password);
            return new Response(ResponseType.SIGNUP_SUCCESS, null);
        }
        catch (Exception e)
        {
            log.Error("Signup failed: " + e.Message);
            return new Response(ResponseType.SIGNUP_FAILED, e.Message);
        }
    }



    private Response HandleFilterGames(Request request)
    {
        string type = request.Data.ToString();
        var games = service.ShowcaseGamesByType(type);

        log.DebugFormat("Found {0} games of type: {1}", games.Count, type);
    
        var dtoList = games.ConvertAll(g => new GameDTO(g));
        return new Response(ResponseType.FILTERED_GAMES, dtoList);
    }


        private Response HandleAddPurchase(Request request)
        {
            try
            {
                PurchaseDTO dto = JsonSerializer.Deserialize<PurchaseDTO>(request.Data.ToString());
                service.AddPurchase(dto.ToModel());
                return new Response(ResponseType.PURCHASE_SUCCESS, null);
            }
            catch (Exception e)
            {
                return new Response(ResponseType.PURCHASE_FAILED, e.Message);
            }
        }

        private Response HandleFindByClient(Request request)
        {
            string client = request.Data.ToString();
            var purchases = service.FindByClient(client);
            var dtoList = purchases.ConvertAll(p => new PurchaseDTO(p));
            return new Response(ResponseType.PURCHASES_FOUND, dtoList);
        }

        private Response HandleUpdateSeats(Request request)
        {
            try
            {
                using JsonDocument doc = JsonDocument.Parse(request.Data.ToString());
                JsonElement root = doc.RootElement;

                // Extrage GameDTO din JSON
                GameDTO gameDto = JsonSerializer.Deserialize<GameDTO>(root.GetProperty("game").ToString());
                int seats = root.GetProperty("seats").GetInt32();

                Game updatedGame = service.UpdateSeats(gameDto.ToModel(), seats);
                return new Response(ResponseType.UPDATE_SEATS_SUCCESS, new GameDTO(updatedGame));
            }
            catch (Exception e)
            {
                return new Response(ResponseType.UPDATE_SEATS_FAILED, e.Message);
            }
        }

        private Response HandleCheckUsername(Request request)
        {
            string username = request.Data.ToString();
            bool exists = service.FindByUsername(username);
            return new Response(
                exists ? ResponseType.USERNAME_EXISTS : ResponseType.USERNAME_NOT_FOUND,
                null
            );
        }

        public void NotifyBoughtSeats(Game game)
        {
            GameDTO dto = new GameDTO(game);
            sendResponse(new Response(ResponseType.UPDATE, dto));
        }
    }
    
