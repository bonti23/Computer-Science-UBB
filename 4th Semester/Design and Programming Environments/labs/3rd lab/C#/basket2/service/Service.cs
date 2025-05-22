using System.Reflection;
using basket2.domain;
using basket2.repository;
using log4net;

namespace basket2.service;
using System.Text.RegularExpressions;

public class Service
{
    private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

    private GameRepository game_repo;
    private PurchaseRepository purchase_repo;
    private UserRepository user_repo;

    public Service(GameRepository game_repo, PurchaseRepository purchase_repo, UserRepository user_repo)
    {
        this.game_repo = game_repo;
        this.purchase_repo = purchase_repo;
        this.user_repo = user_repo;
    }

    public User? Login(string username, string password)
    {
        
        return user_repo.findAll().FirstOrDefault(user=>user.username == username && user.password == password);
    }

    public List<Purchase> findByClientId(string client)
    {
        return purchase_repo.findByClientOrderedBySeats(client);
    }

    public void Signup(string name, string username, string password)
    {
        if (user_repo.findAll().Any(user => user.username == username))
        {
            throw new ArgumentException("Username already exists");
        }

        ValidatePassword(password);
        var new_user=new User(name, username, password);
        user_repo.Save(new_user);
    }

    private void ValidatePassword(string password)
    {
        if(string.IsNullOrEmpty(password))
            throw new ArgumentException("Password is required!");
        if(password.Length < 8)
            throw new ArgumentException("Password must be at least 8 characters long!");
        if(!Regex.IsMatch(password, "[!@#$%^&]"))
            throw new ArgumentException("Password must contain at least one special character (!@#$%^&)!");
        if(!Regex.IsMatch(password, "[a-z]"))
            throw new ArgumentException("Password must contain at least one lowercase letter");
        if(!Regex.IsMatch(password, "[A-Z]"))
            throw new ArgumentException("Password must contain at least one uppercase letter");
        if(!Regex.IsMatch(password, "[0-9]"))
            throw new ArgumentException("Password must contain at least one digit");
    }

    public List<Game> ShowcaseGamesByType(string gameType)
    {
        var games = game_repo.findByTypeOrderedByDate(gameType);
        return new List<Game>(games);  // Returnează o copie nouă
    }




    public void AddPurchase(Purchase purchase)
    {
        var game=game_repo.findOne(purchase.game) ?? throw new ArgumentException("Game not found");
        UpdateSeats(game, purchase.seats);
        purchase_repo.Save(purchase);
    }

    public void UpdateSeats(Game game, int seats)
    {
        if(seats<=0)
            throw new ArgumentException("Invalid number of seats!");
        if(game.Seats!<seats)
            throw new ArgumentException("Not enough number of seats!");
        game.Seats -= seats;
        game_repo.Update(game);
    }
}
