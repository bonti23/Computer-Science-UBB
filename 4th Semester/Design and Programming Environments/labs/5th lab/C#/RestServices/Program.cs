using System;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;
using BasketModel;

namespace RestService
{
    public class Program
    {
        private static readonly HttpClient client = new HttpClient();
        private const string baseUrl = "http://localhost:8080/basket/games";

        public static async Task Main(string[] args)
        {
            try
            {
                // 1. print games
                var games = await GetAllGames();
                Console.WriteLine("\nAll games:");
                PrintGames(games);

                if (games.Length > 0)
                {
                    var lastGame = games[^1];  // ultimul joc

                    // 2. jocul dupa id
                    var gameById = await GetGameById(lastGame.id);
                    Console.WriteLine($"\nGame by ID: {gameById.teamA} vs {gameById.teamB}");

                    // 3. update
                    lastGame.teamA += " (Updated)";
                    await UpdateGame(lastGame);
                    Console.WriteLine("\nGame updated");

                    // 4. print games
                    games = await GetAllGames();
                    Console.WriteLine("\nAll games after update:");
                    PrintGames(games);

                    // 5. delete
                    await DeleteGame(lastGame.id);
                    Console.WriteLine("\nGame deleted");

                    // 6. print games
                    games = await GetAllGames();
                    Console.WriteLine("\nAll games after delete:");
                    PrintGames(games);
                }
                else
                {
                    Console.WriteLine("No games found to test GET, UPDATE and DELETE.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error: {ex.Message}");
            }
        }

        static void PrintGames(Game[] games)
        {
            foreach (var game in games)
            {
                Console.WriteLine($"- {game.id}: {game.teamA} vs {game.teamB}");
            }
        }

        static async Task<Game[]> GetAllGames()
        {
            return await client.GetFromJsonAsync<Game[]>(baseUrl);
        }

        static async Task<Game> GetGameById(long id)
        {
            return await client.GetFromJsonAsync<Game>($"{baseUrl}/{id}");
        }

        static async Task UpdateGame(Game game)
        {
            var response = await client.PutAsJsonAsync($"{baseUrl}/{game.id}", game);
            response.EnsureSuccessStatusCode();
        }

        static async Task DeleteGame(long id)
        {
            var response = await client.DeleteAsync($"{baseUrl}/{id}");
            response.EnsureSuccessStatusCode();
        }
    }
}
