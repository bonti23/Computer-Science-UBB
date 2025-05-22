using basket.domain;
using basket.repository;
using System.Collections.Generic;
using System;
using System.IO;
using System.Reflection;
using log4net;
using log4net.Config;

internal class Program
{
    private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
    static void Main(string[] args)
    {
        var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
        XmlConfigurator.Configure(logRepository);

        log.Info("App has started.");
        
        var GameRepository = new GameDBRepository(Config.DatabaseProperties);
        var PurchaseRepository = new PurchaseDBRepository(Config.DatabaseProperties);
        var UserRepository = new UserDBRepository(Config.DatabaseProperties);
        
        var user = new User("Alexandra Bontidean", "bonti", "parola123");
        UserRepository.Save(user);
        log.Info("Saved user");

        var gameType = basket.domain.Type.FINAL;
        var game = new Game("CFR", "U Cluj", "2025-03-20", 50.0f, gameType, 100);
        GameRepository.Save(game);
        log.Info("Saved game.");
        game = GameRepository.FindByDetails("CFR", "U Cluj", "2025-03-20");
        var purchase = new Purchase("Boros Patricia", game.get_id(), 2, "Street 1");
        PurchaseRepository.Save(purchase);
        log.Info("Saved purchase.");

        Console.WriteLine("\n\nShow games:");
        Show(GameRepository);

        Console.WriteLine("\n\nShow users:");
        Show(UserRepository);

        Console.WriteLine("\n\nShow purchases:");
        Show(PurchaseRepository);

        log.Info("DataBase");

        Console.WriteLine("Done");
        Console.ReadLine();
    }

    static void Show<ID, E>(Repository<ID, E> repo) where E : Entity<ID>
    {
        foreach (var item in repo.findAll())
        {
            Console.WriteLine(item);
            log.Debug($"Item: {item}");
        }
    }
}