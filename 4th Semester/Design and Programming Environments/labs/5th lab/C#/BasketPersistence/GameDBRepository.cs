using BasketModel;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using BasketType = BasketModel.GameType;

namespace BasketPersistence
{
    public class GameDBRepository(IDictionary<string, string> dbConnection) : GameRepository
    {
        private static readonly ILog log = LogManager.GetLogger("GameDBRepository");

        public Game FindOne(long id)
        {
            log.InfoFormat("Entering FindOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(dbConnection);
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Games\" where \"id\" = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        long gid = Convert.ToInt64(reader["id"]);
                        string teamA = reader.GetString(1);
                        string teamB = reader.GetString(2);
                        string date = reader.GetString(3);
                        float price = reader.GetFloat(4);
                        string typeStr = reader.GetString(5);
                        BasketType type = Enum.Parse<BasketType>(typeStr);
                        int seats = reader.GetInt32(6);

                        Game game = new Game(gid, teamA, teamB, date, price, type, seats);
                        return game;
                    }
                }
            }

            log.InfoFormat("Exiting FindOne with null");
            return null;
        }

        public List<Game> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(dbConnection);
            List<Game> games = new List<Game>();
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Games\"";
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        var game = new Game(
                            Convert.ToInt64(reader["id"]),
                            reader["teamA"].ToString(),
                            reader["teamB"].ToString(),
                            reader["date"].ToString(),
                            Convert.ToSingle(reader["price"]),
                            Enum.Parse<BasketType>(reader["type"].ToString()),
                            Convert.ToInt32(reader["seats"])
                        );
                        games.Add(game);
                    }
                }
            }

            return games;
        }

        public Game? Save(Game game)
        {
            try
            {
                IDbConnection con = DBUtils.getConnection(dbConnection);
                using (var command = con.CreateCommand())
                {
                    command.CommandText =
                        "insert into \"Games\" (\"teamA\", \"teamB\", \"date\", \"price\", \"type\", \"seats\") " +
                        "values (@teamA, @teamB, @date, @price, @type, @seats)";

                    var paramTeamA = command.CreateParameter();
                    paramTeamA.ParameterName = "@teamA";
                    paramTeamA.Value = game.teamA;
                    command.Parameters.Add(paramTeamA);

                    var paramTeamB = command.CreateParameter();
                    paramTeamB.ParameterName = "@teamB";
                    paramTeamB.Value = game.teamB;
                    command.Parameters.Add(paramTeamB);

                    var paramDate = command.CreateParameter();
                    paramDate.ParameterName = "@date";
                    paramDate.Value = game.date;
                    command.Parameters.Add(paramDate);

                    var paramPrice = command.CreateParameter();
                    paramPrice.ParameterName = "@price";
                    paramPrice.Value = game.price;
                    command.Parameters.Add(paramPrice);

                    var paramType = command.CreateParameter();
                    paramType.ParameterName = "@type";
                    paramType.Value = game.type.ToString();
                    command.Parameters.Add(paramType);

                    var paramSeats = command.CreateParameter();
                    paramSeats.ParameterName = "@seats";
                    paramSeats.Value = game.seats;
                    command.ExecuteNonQuery();
                }

                return game;
            }
            catch (Exception ex)
            {
                log.Error("Error while saving purchase", ex);
                return null;
            }
        }

        public Game? Delete(long id)
        {
            return null;
        }

        public Game? Update(Game game)
        {
            log.Info("Updating game");
            IDbConnection con = DBUtils.getConnection(dbConnection);
            using (var command = con.CreateCommand())
            {
                command.CommandText = "update \"Games\" set \"teamA\" = @teamA, \"teamB\" = @teamB, \"date\" = @date, " +
                                      "\"price\" = @price, \"type\" = @type, \"seats\" = @seats where \"id\" = @id";

                var paramTeamA = command.CreateParameter();
                paramTeamA.ParameterName = "@teamA";
                paramTeamA.Value = game.teamA;
                command.Parameters.Add(paramTeamA);

                var paramTeamB = command.CreateParameter();
                paramTeamB.ParameterName = "@teamB";
                paramTeamB.Value = game.teamB;
                command.Parameters.Add(paramTeamB);

                var paramDate = command.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = game.date;
                command.Parameters.Add(paramDate);

                var paramPrice = command.CreateParameter();
                paramPrice.ParameterName = "@price";
                paramPrice.Value = game.price;
                command.Parameters.Add(paramPrice);

                var paramType = command.CreateParameter();
                paramType.ParameterName = "@type";
                paramType.Value = game.type.ToString();
                command.Parameters.Add(paramType);

                var paramSeats = command.CreateParameter();
                paramSeats.ParameterName = "@seats";
                paramSeats.Value = game.seats;
                command.Parameters.Add(paramSeats);

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = game.id;
                command.Parameters.Add(paramId);

                command.ExecuteNonQuery();

                log.Info($"Game with id {game.id} updated successfully.");
            }

            return game;
        }

        public List<Game> findByTypeOrderedByDate(string type)
        {
            log.Info($"Finding games by type {type}, ordered by date");
            IDbConnection con = DBUtils.getConnection(dbConnection);
            List<Game> games = new List<Game>();

            using (var command = con.CreateCommand())
            {
                command.CommandText = "SELECT * FROM \"Games\" WHERE \"type\" = @type ORDER BY \"date\" ASC";

                var paramType = command.CreateParameter();
                paramType.ParameterName = "@type";
                paramType.Value = type;
                command.Parameters.Add(paramType);

                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        long gameId = Convert.ToInt64(reader["id"]);
                        string teamA = reader["teamA"].ToString();
                        string teamB = reader["teamB"].ToString();
                        string date = reader["date"].ToString();
                        float price = Convert.ToSingle(reader["price"]);
                        string typeStr = reader["type"].ToString();
                        BasketType basketType = Enum.Parse<BasketType>(typeStr);
                        int seats = Convert.ToInt32(reader["seats"]);

                        Game game = new Game(gameId, teamA, teamB, date, price, basketType, seats);
                        game.id=gameId;
                        games.Add(game);
                    }
                }
            }
            return games;
        }
    }
}
