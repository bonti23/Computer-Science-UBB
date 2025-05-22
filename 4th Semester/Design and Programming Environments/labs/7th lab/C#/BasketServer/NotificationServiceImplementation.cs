using Google.Protobuf.WellKnownTypes;
using Grpc.Core;
using System.Collections.Concurrent;
using System.Threading.Tasks;
using BasketModel;
using log4net;
using Org.Example.ClientFx.Grpc;

namespace BasketServer
{
    public class NotificationServiceImplementation : NotificationService.NotificationServiceBase
    {
        private static readonly ConcurrentBag<IServerStreamWriter<Notification>> _subscribers = new ConcurrentBag<IServerStreamWriter<Notification>>();
        private static readonly ILog log = LogManager.GetLogger(typeof(NotificationServiceImplementation));

        public override async Task NewPurchaseNotification(
            Empty request,
            IServerStreamWriter<Notification> responseStream,
            ServerCallContext context)
        {
            _subscribers.Add(responseStream);
            log.Info($"Un nou client s-a abonat la notificări. Total abonați: {_subscribers.Count}");

            try
            {
                await Task.Delay(-1, context.CancellationToken);
            }
            catch (OperationCanceledException)
            {
                log.Info("Un client s-a deconectat de la notificări.");
            }
        }

        public async Task BroadcastAsync(GameDTO gameDto)
        {
            log.Info("Trimitem notificări către clienți...");

            var notification = new Notification
            {
                Message = $"Un nou bilet a fost cumpărat pentru jocul: {gameDto.TeamA} vs {gameDto.TeamB} la data: {gameDto.Date}."
            };

            foreach (var subscriber in _subscribers)
            {
                try
                {
                    await subscriber.WriteAsync(notification);
                    log.Info("Notificare trimisă cu succes.");
                }
                catch (Exception ex)
                {
                    log.Error($"Eroare la trimiterea notificării: {ex.Message}");
                }
            }
        }
    }
}
