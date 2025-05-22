using basket.domain;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using System.Linq;

namespace basket.repository
{
    public class GameDBRepository : DBUtils<long, Game>, GameRepository
    {
        private static readonly ILog log = LogManager.GetLogger("GameDBRepository");

        public GameDBRepository(IDictionary<string, string> props) : base(props)
        {
        }

        public void Save(Game game)
        {
            int result = ExecuteNonQuery("insert into \"Game\" (\"teamA\", \"teamB\", \"date\", \"price\", \"type\", \"seats\")" +
                                         "values(@teamA, @teamB, @date, @price, @type, @seats)", new Dictionary<string, object>
            {
                { "@teamA", game.get_teamA() },
                { "@teamB", game.get_teamB() },
                { "@date", game.get_date() },
                { "@price", game.get_price() },
                { "@type", game.get_type().ToString() },  // Asumăm că Type are un toString() valid
                { "@seats", game.get_seat() }
            });
        }

        public void Update(Game game)
        {
            int result = ExecuteNonQuery("update \"Game\" set \"teamA\" = @teamA, \"teamB\" = @teamB, \"date\" = @date, " +
                                         "\"price\" = @price, \"type\" = @type, \"seats\" = @seats where \"id\" = @id", 
                new Dictionary<string, object>
            {
                { "@teamA", game.get_teamA() },
                { "@teamB", game.get_teamB() },
                { "@date", game.get_date() },
                { "@price", game.get_price() },
                { "@type", game.get_type().ToString() },
                { "@seats", game.get_seat() },
                { "@id", game.get_id() } // Asumăm că `game.Id` este disponibil
            });
        }

        public void Delete(long id)
        {
            int result = ExecuteNonQuery("delete from \"Game\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }

        // Obține un joc după ID
        public Game findOne(long id)
        {
            log.Info($"Getting game by id: {id}");
            return SelectFirst("select * from \"Game\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }
        
        public IEnumerable<Game> findAll()
        {
            return Select("select * from \"Game\"");
        }

        public List<Game> findByTypeOrderedByDate(string type)
        {
            return Select("select * from \"Game\" where \"type\" = @type order by \"date\" asc", 
                new Dictionary<string, object> { { "@type", type } }).ToList();
        }

        protected override Game DecodeReader(IDataReader reader)
        {
            log.Info("Decoding game");

            var id = Convert.ToInt32(reader["id"]);
            var teamA = reader["teamA"].ToString();
            var teamB = reader["teamB"].ToString();
            var date = reader["date"].ToString();
            var price = Convert.ToSingle(reader["price"]);
            var type = (basket.domain.Type)Enum.Parse(typeof(basket.domain.Type), reader["type"].ToString());
            var seats = Convert.ToInt32(reader["seats"]);

            var game = new Game(teamA, teamB, date, price, type, seats);
            game.identitykey = id;
            return game;
        }
        public Game FindByDetails(string teamA, string teamB, string date)
        {
            log.Info($"Searching for game: {teamA} vs {teamB} on {date}");
    
            return SelectFirst("select * from \"Game\" where \"teamA\" = @teamA and \"teamB\" = @teamB and \"date\" = @date",
                new Dictionary<string, object>
                {
                    { "@teamA", teamA },
                    { "@teamB", teamB },
                    { "@date", date }
                });
        }
    }
}
