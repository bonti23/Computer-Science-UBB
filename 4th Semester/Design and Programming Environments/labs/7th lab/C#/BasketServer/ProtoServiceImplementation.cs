using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using Org.Example.ClientFx.Grpc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BasketModel;
using BasketPersistence;
using BasketServices;

namespace BasketServer
{
    public class ProtoServiceImplementation : BasketService.BasketServiceBase
    {
        private readonly GameRepository gameRepo;
        private readonly UserRepository userRepo;
        private readonly PurchaseRepository purchaseRepo;
        private readonly Service service;
        private readonly NotificationServiceImplementation notificationService;

        public ProtoServiceImplementation(GameRepository gameRepo, UserRepository userRepo, PurchaseRepository purchaseRepo, Service service, NotificationServiceImplementation notificationService)
        {
            this.gameRepo = gameRepo;
            this.userRepo = userRepo;
            this.purchaseRepo = purchaseRepo;
            this.service = new Service(gameRepo, userRepo, purchaseRepo);
            this.notificationService = new NotificationServiceImplementation();
        }

        public override Task<DefaultResponse> Login(UserDTO request, ServerCallContext context)
        {
            var response = new DefaultResponse();
            try
            {
                service.Login(request.Username, request.Password, null);
                response.Success = true;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }

            return Task.FromResult(response);
        }

        private Org.Example.ClientFx.Grpc.GameType ConvertToProtoGameType(BasketModel.GameType type)
        {
            return (Org.Example.ClientFx.Grpc.GameType)System.Enum.Parse(
                typeof(Org.Example.ClientFx.Grpc.GameType),
                type.ToString(),
                true
            );
        }

        public override Task<DefaultResponse> Signup(UserDTO request, ServerCallContext context)
        {
            var response = new DefaultResponse();
            try
            {
                service.Signup(request.Name, request.Username, request.Password);
                response.Success = true;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return Task.FromResult(response);
        }

        public override Task<GameResponse> ShowGames(Empty request, ServerCallContext context)
        {
            var response = new GameResponse();
            try
            {
                var games = service.ShowGames();
                var gameList = new GameList();
                foreach (var game in games)
                {
                    gameList.Games.Add(new GameDTO
                    {
                        Identitykey = game.id,
                        TeamA = game.teamA,
                        TeamB = game.teamB,
                        Date = game.date,
                        Price = (float)game.price,
                        Type = ConvertToProtoGameType(game.type),
                        Seats = game.seats
                    });
                }
                response.Games = gameList;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return Task.FromResult(response);
        }

        public override Task<GameResponse> ShowcaseGamesByType(GameTypeRequest request, ServerCallContext context)
        {
            var response = new GameResponse();
            try
            {
                var games = service.ShowcaseGamesByType(request.Type.ToString().ToUpper());
                var gameList = new GameList();
                foreach (var game in games)
                {
                    gameList.Games.Add(new GameDTO
                    {
                        Identitykey = game.id,
                        TeamA = game.teamA,
                        TeamB = game.teamB,
                        Date = game.date,
                        Price = (float)game.price,
                        Type = ConvertToProtoGameType(game.type),
                        Seats = game.seats
                    });
                }
                response.Games = gameList;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return Task.FromResult(response);
        }

        public override Task<PurchaseResponse> FindAllPurchases(Empty request, ServerCallContext context)
        {
            var response = new PurchaseResponse();
            try
            {
                var purchases = service.FindAllPurchases();
                var purchaseList = new PurchaseList();

                foreach (var p in purchases)
                {
                    purchaseList.Purchases.Add(new PurchaseDTO
                    {
                        Identitykey = p.id,
                        Client = p.getClient(),
                        Game = p.getGame(),
                        Seats = p.getSeats(),
                        Address = p.getAddress()
                    });
                }

                response.Purchases = purchaseList;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return Task.FromResult(response);
        }

        public override async Task<DefaultResponse> AddPurchase(PurchaseDTO request, ServerCallContext context)
        {
            var response = new DefaultResponse();
            try
            {
                var purchase = new Purchase(request.Client, request.Game, request.Seats, request.Address);
                purchase.id=request.Identitykey;
                service.AddPurchase(purchase);

                var game = gameRepo.FindOne(purchase.getGame());
                var gameDto = new GameDTO
                {
                    Identitykey = game.id,
                    TeamA = game.teamA,
                    TeamB = game.teamB,
                    Date = game.date,
                    Price = (float)game.price,
                    Type = ConvertToProtoGameType(game.type),
                    Seats = game.seats
                };
                await notificationService.BroadcastAsync(gameDto);

                response.Success = true;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return response;
        }

        public override Task<PurchaseResponse> FindPurchasesByClient(UsernameRequest request, ServerCallContext context)
        {
            var response = new PurchaseResponse();
            try
            {
                var purchases = service.FindByClient(request.Username);
                var purchaseList = new PurchaseList();
                foreach (var p in purchases)
                {
                    purchaseList.Purchases.Add(new PurchaseDTO
                    {
                        Identitykey = p.id,
                        Client = p.getClient(),
                        Game = p.getGame(),
                        Seats = p.getSeats(),
                        Address = p.getAddress()
                    });
                }
                response.Purchases = purchaseList;
            }
            catch (Exception ex)
            {
                response.Error = ex.Message;
            }
            return Task.FromResult(response);
        }
    }
}
