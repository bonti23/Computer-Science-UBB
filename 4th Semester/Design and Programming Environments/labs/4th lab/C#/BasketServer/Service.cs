using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using BasketModel;
using BasketPersistence;
using BasketServices;
using log4net;

namespace BasketServer
{
    public class Service : IService
    {
        private readonly GameRepository gameRepo;
        private readonly UserRepository userRepo;
        private readonly PurchaseRepository purchaseRepo;
        private readonly Dictionary<string, IObserver> loggedClients;
        private readonly int defaultThreadsNo = 4;

        private static readonly ILog log = LogManager.GetLogger(typeof(Service));

        public Service(GameRepository gameRepo, UserRepository userRepo, PurchaseRepository purchaseRepo)
        {
            this.gameRepo = gameRepo;
            this.userRepo = userRepo;
            this.purchaseRepo = purchaseRepo;
            this.loggedClients = new Dictionary<string, IObserver>();
        }

        public void Login(string username, string password, IObserver client)
        {
            var user = userRepo.FindOneByUsername(username);
            if (user == null || user.getPassword() != password)
                throw new ServiceException("Invalid username or password");

            loggedClients[username] = client;
        }
        public void Logout(string username, IObserver client)
        {
            if (!loggedClients.ContainsKey(username))
            {
                throw new ServiceException("User isn't logged in");
            }
            loggedClients.Remove(username);
        }
        public void Signup(string name, string username, string password)
        {
            try
            {
                if (userRepo.FindAll().Any(u => u.getUsername() == username))
                    throw new ArgumentException("Username already exists.");

                ValidatePassword(password);
                var newUser = new User(name, username, password);
                userRepo.Save(newUser);
            }
            catch (ArgumentException ex)
            {
                log.Error($"Signup error: {ex.Message}");
            }
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
        public List<Purchase> FindByClient(string client)
        {
            return purchaseRepo.findByClientOrderedBySeats(client);
        }

        public List<Game> ShowcaseGamesByType(string type)
        {
            return gameRepo.findByTypeOrderedByDate(type);
        }
        public void AddPurchase(Purchase purchase)
        {
            var game = gameRepo.FindOne(purchase.getGame());
            if (game == null)
                throw new ArgumentException("Game not found");

            var updatedGame = UpdateSeats(game, purchase.getSeats());
            purchase.setGame(updatedGame.get_identitykey());
            purchaseRepo.Save(purchase);
            NotifyBoughtSeats(updatedGame);
        }
        public Game UpdateSeats(Game game, int seats)
        {
            if (seats <= 0)
                throw new ArgumentException("Invalid number of seats");

            if (game.seats < seats)
                throw new ArgumentException("Not enough seats available");

            game.seats=game.seats - seats;
            gameRepo.Update(game);
            return game;
        }
        public bool FindByUsername(string username)
        {
            return userRepo.FindAll().Any(u => u.getUsername() == username);
        }
        public List<Game> ShowGames()
        {
            return gameRepo.FindAll()
                .OrderBy(g => g.date)
                .ToList();
        }

        private void NotifyBoughtSeats(Game game)
        {
            var tasks = loggedClients.Values.Select(client => Task.Run(() =>
            {
                try
                {
                    client.NotifyBoughtSeats(game);
                }
                catch (Exception e)
                {
                    log.Error($"Error notifying client: {e.Message}", e);
                }
            })).ToList();
            Task.WhenAll(tasks).Wait();
        }
    }
}
