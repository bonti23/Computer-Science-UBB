namespace BasketServices
{
    using BasketModel;
    using System;
    using System.Collections.Generic;

    public interface IService
    {
        void Login(string username, string password, IObserver observer);
        void Logout(string username, IObserver client);
        void Signup(string name, string username, string password);
        List<Purchase> FindByClient(string client);
        List<Game> ShowcaseGamesByType(string type);
        void AddPurchase(Purchase purchase);
        Game UpdateSeats(Game game, int seats);
        void ValidatePassword(string password);
        List<Game> ShowGames();
        bool FindByUsername(string username);
    }
}
