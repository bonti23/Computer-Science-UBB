using basket2.domain;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using System.Linq;

namespace basket2.repository
{
    public class PurchaseDBRepository : DBUtils<long, Purchase>, PurchaseRepository
    {
        private static readonly ILog log = LogManager.GetLogger("PurchaseDBRepository");

        public PurchaseDBRepository(IDictionary<string, string> props) : base(props)
        {
        }

        // Adaugă o achiziție în baza de date
        public void Save(Purchase purchase)
        {
            int result = ExecuteNonQuery("insert into \"Purchase\" (\"client\", \"game\", \"seats\", \"address\")" +
                                         "values(@client, @game, @seats, @address)", new Dictionary<string, object>
            {
                { "@client", purchase.get_client() },  // "client" este un string
                { "@game", purchase.get_game() },
                { "@seats", purchase.get_seats() },
                { "@address", purchase.get_address() }
            });
        }

        // Actualizează o achiziție
        public void Update(Purchase purchase)
        {
            int result = ExecuteNonQuery("update \"Purchase\" set \"client\" = @client, \"game\" = @game, \"seats\" = @seats, " +
                                         "\"address\" = @address where \"id\" = @id", 
                new Dictionary<string, object>
            {
                { "@client", purchase.get_client() },  // "client" este un string
                { "@game", purchase.get_game() },
                { "@seats", purchase.get_seats() },
                { "@address", purchase.get_address() },
                { "@id", purchase.get_id() } // Asigură-te că `purchase.Id` este disponibil
            });
        }

        // Șterge o achiziție
        public void Delete(long id)
        {
            int result = ExecuteNonQuery("delete from \"Purchase\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }

        // Obține o achiziție după ID
        public Purchase findOne(long id)
        {
            log.Info($"Getting purchase by id: {id}");
            return SelectFirst("select * from \"Purchase\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }

        // Obține toate achizițiile
        public IEnumerable<Purchase> findAll()
        {
            return Select("select * from \"Purchase\"");
        }

        // Obține achizițiile unui client ordonate după numărul de locuri
        public List<Purchase> findByClientOrderedBySeats(string client)
        {
            return Select("select * from \"Purchase\" where \"client\" = @client order by \"seats\" asc", 
                new Dictionary<string, object> { { "@client", client } }).ToList();
        }

        // Metodă protejată pentru decodarea unui reader
        protected override Purchase DecodeReader(IDataReader reader)
        {
            log.Info("Decoding purchase");

            var id = Convert.ToInt64(reader["id"]);
            var client = reader["client"].ToString();  // "client" este un string
            var game = Convert.ToInt64(reader["game"]);
            var seats = Convert.ToInt32(reader["seats"]);
            var address = reader["address"].ToString();

            var purchase = new Purchase(client, game, seats, address);
            purchase.identitykey = id;
            return purchase;
        }
    }
}
