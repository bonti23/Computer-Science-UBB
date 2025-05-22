using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Text;
using System.Text.Json;
using BasketModel;
using BasketNetworking.DTO;
using BasketServices;
using log4net;
using System.Text.RegularExpressions;
namespace BasketNetworking.JsonProtocol;

public class Proxy : IService
{
    private string host;
    private int port;

    private IObserver client;
    private NetworkStream stream;
    private TcpClient connection;
    private Queue<Response> responses;
    private volatile bool finished;
    private EventWaitHandle _waitHandle;
    private static readonly ILog log = LogManager.GetLogger(typeof(Proxy));
    public Proxy(string host, int port)
    {
        this.host = host;
        this.port = port;
        responses=new Queue<Response>();
        initializeConnection();
    }
    
    private void startReader()
    {
        Thread tw =new Thread(run);
        tw.Start();
    }
    private void closeConnection()
    {
        finished=true;
        try
        {
            stream.Close();
            connection.Close();
            _waitHandle.Close();
            client=null;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }

    private void sendRequest(Request request)
    {
        try
        {
            // Ensure the connection is open
            if (connection == null || !connection.Connected || stream == null || stream.CanWrite == false)
            {
                // Re-initialize the connection if it's closed or disposed
                Console.WriteLine("Reinitializing connection...");
                initializeConnection();
            }

            string jsonRequest = JsonSerializer.Serialize(request);
            Console.WriteLine("Made json request: " + jsonRequest); // Log pentru a verifica ce trimitem
            log.DebugFormat("Sending request {0}", jsonRequest);

            byte[] data = Encoding.UTF8.GetBytes(jsonRequest + "\n");
            stream.Write(data, 0, data.Length);
            stream.Flush();
        }
        catch (Exception e)
        {
            throw new Exception("Error sending object: " + e);
        }
    }

    private void initializeConnection()
    {
        try
        {
            // Close existing connection if it exists
            if (connection != null && connection.Connected)
            {
                stream.Close();
                connection.Close();
            }

            // Initialize a new connection
            connection = new TcpClient(host, port);
            stream = connection.GetStream();
            finished = false;
            _waitHandle = new AutoResetEvent(false);
            startReader();
        }
        catch (Exception e)
        {
            Console.WriteLine("Error initializing connection: " + e.StackTrace);
        }
    }


    private Response readResponse()
    {
        Response response =null;
        try
        {
            _waitHandle.WaitOne();
            lock (responses)
            {
                response = responses.Dequeue();
                
            }
        }catch (Exception e) {
            Console.WriteLine(e.StackTrace);
        }
        return response;
    }
    
    
    public virtual void run()
    {
        using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
        while (!finished)
        {
            try
            {
                string responseJson = reader.ReadLine();
                if (string.IsNullOrEmpty(responseJson))
                    continue;

                Response response = JsonSerializer.Deserialize<Response>(responseJson);
                log.Debug("Response received " + response);

                if (response.Type == ResponseType.UPDATE)
                {
                    GameDTO gameDto = JsonSerializer.Deserialize<GameDTO>(response.Data.ToString());
                    Game game = gameDto.ToModel();
                    client.NotifyBoughtSeats(game);
                }
                else
                {
                    lock (responses)
                    {
                        responses.Enqueue(response);
                    }
                    _waitHandle.Set();
                }
            }
            catch (Exception e)
            {
                log.Error("Reading error " + e);
            }
        }
    }

    public void Login(string username, string password, IObserver client)
    {
        log.InfoFormat("Proxy: login attempt for {0}", username);
        initializeConnection();

        string encodedPassword = password; // Use real encoding if needed
        this.client = client;

        Request request = new Request(RequestType.LOGIN, new UserDTO("", username, encodedPassword));
        sendRequest(request);

        Response response = readResponse();

        if (response == null)
        {
            closeConnection();
            throw new Exception("No response received from server.");
        }
        if (response.Type == ResponseType.LOGIN_FAILED)
        {
            closeConnection();
            throw new Exception(response.Data.ToString());
        }

        log.InfoFormat("Login successful for {0}", username);
    }

    public void Logout(string username, IObserver client)
    {
        Request request = new Request(RequestType.LOGOUT, new UserDTO("", username, null));
        sendRequest(request);
        Response response = readResponse();
        closeConnection();

        if (response.Type == ResponseType.LOGIN_FAILED)
        {
            throw new Exception(response.Data.ToString());
        }
    }
    /*
     * public void Signup(string name, string username, string password)
    {
        if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
        {
            throw new ArgumentException("Name, Username, and Password must be provided.");
        }

        UserDTO userDto = new UserDTO(name, username, password); 
        Request request = new Request(RequestType.SIGNUP, userDto);
        sendRequest(request); // Trimite request-ul
    }
     */
    /*public void Signup(string name, string username, string password)
        {
            initializeConnection();
            
            Request request = new Request(RequestType.SIGNUP, new UserDTO(name, username, password));
            sendRequest(request);
            Response response = readResponse();
            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }
            if (response.Type == ResponseType.SIGNUP_FAILED)
            {
                closeConnection();
                throw new Exception(response.Data.ToString());
            }
            log.InfoFormat("Signup successful for {0}", username);
        }*/
    public void Signup(string name, string username, string password)
    {
        UserDTO userDto = new UserDTO(name, username, password);

        // Log pentru a vedea ce trimite clientul
        Console.WriteLine("Sending signup request: " + JsonSerializer.Serialize(userDto));

        Request request = new Request(RequestType.SIGNUP, userDto);
        sendRequest(request);
    }




        public List<Purchase> FindByClient(string client)
        {
            Request request = new Request(RequestType.FIND_BY_CLIENT, client);
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            if (response.Type == ResponseType.PURCHASES_FOUND)
            {
                List<PurchaseDTO> purchaseDtos = JsonSerializer.Deserialize<List<PurchaseDTO>>(response.Data.ToString());
                List<Purchase> purchases = new List<Purchase>();
                foreach (var dto in purchaseDtos)
                {
                    purchases.Add(dto.ToModel());
                }
                return purchases;
            }

            closeConnection();
            throw new Exception("Error finding purchases for client.");
        }

        public List<Game> ShowcaseGamesByType(string type)
        {
            Request request = new Request(RequestType.FILTER_GAMES, type);
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            if (response.Type == ResponseType.FILTERED_GAMES)
            {
                List<GameDTO> gameDtos = JsonSerializer.Deserialize<List<GameDTO>>(response.Data.ToString());
                List<Game> games = new List<Game>();
                foreach (var dto in gameDtos)
                {
                    games.Add(dto.ToModel());
                }
                return games;
            }

            closeConnection();
            throw new Exception("Error retrieving filtered games.");
        }


        public void AddPurchase(Purchase purchase)
        {
            Request request = new Request(RequestType.ADD_PURCHASE, new PurchaseDTO(purchase));
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            if (response.Type == ResponseType.PURCHASE_FAILED)
            {
                closeConnection();
                throw new Exception(response.Data.ToString());
            }

            log.Info("Purchase added successfully.");
        }

        public Game UpdateSeats(Game game, int seats)
        {
            Request request = new Request(RequestType.UPDATE_SEATS, new { game, seats });
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            if (response.Type == ResponseType.UPDATE_SEATS_FAILED)
            {
                closeConnection();
                throw new Exception(response.Data.ToString());
            }

            GameDTO gameDto = JsonSerializer.Deserialize<GameDTO>(response.Data.ToString());
            return gameDto.ToModel();
        }

        public void ValidatePassword(string password)
        {
            List<string> errors = new List<string>();
            
            if (password.Length < 8)
                errors.Add("Password must be at least 8 characters.");
            
            if (!Regex.IsMatch(password, "[!@#$%^&]"))
                errors.Add("Password must contain at least one special character (!@#$%^&).");

            if (!Regex.IsMatch(password, "[a-z]"))
                errors.Add("Password must contain at least one lowercase letter.");

            if (!Regex.IsMatch(password, "[A-Z]"))
                errors.Add("Password must contain at least one uppercase letter.");

            if (!Regex.IsMatch(password, "[0-9]"))
                errors.Add("Password must contain at least one digit.");

            if (errors.Any())
                throw new ArgumentException(string.Join(" ", errors));
        }

        public List<Game> ShowGames()
        {
            // Dacă serverul așteaptă un filtru specific, trimite-l în request
            Request request = new Request(RequestType.FILTER_GAMES, new { type = "all" }); // Exemplu: trimitem tipul "all"
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            if (response.Type == ResponseType.FILTERED_GAMES)
            {
                List<GameDTO> gameDtos = JsonSerializer.Deserialize<List<GameDTO>>(response.Data.ToString());
                List<Game> games = new List<Game>();
                foreach (var dto in gameDtos)
                {
                    games.Add(dto.ToModel());
                }
                return games;
            }

            closeConnection();
            throw new Exception("Error retrieving all games.");
        }


        public bool FindByUsername(string username)
        {
            Request request = new Request(RequestType.CHECK_USERNAME, username);
            sendRequest(request);
            Response response = readResponse();

            if (response == null)
            {
                closeConnection();
                throw new Exception("No response received from server.");
            }

            return response.Type == ResponseType.USERNAME_EXISTS;
        }
    }
