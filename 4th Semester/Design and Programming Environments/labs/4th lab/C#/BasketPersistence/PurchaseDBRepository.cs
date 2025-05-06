using BasketModel;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;

namespace BasketPersistence
{
    public class PurchaseDBRepository(IDictionary<string, string> dbConnection) : PurchaseRepository
    {
        private static readonly ILog log = LogManager.GetLogger("PurchaseDBRepository");

        public Purchase? Save(Purchase purchase)
        {
            try
            {
                IDbConnection con = DBUtils.getConnection(dbConnection);
                using (var command = con.CreateCommand())
                {
                    command.CommandText = "insert into \"Purchase\" (\"client\", \"game\", \"seats\", \"address\") " +
                                          "values (@client, @game, @seats, @address); SELECT last_insert_rowid();";
                    var paramClient = command.CreateParameter();
                    paramClient.ParameterName = "@client";
                    paramClient.Value = purchase.getClient();
                    command.Parameters.Add(paramClient);

                    var paramGame = command.CreateParameter();
                    paramGame.ParameterName = "@game";
                    paramGame.Value = purchase.getGame();
                    command.Parameters.Add(paramGame);

                    var paramSeats = command.CreateParameter();
                    paramSeats.ParameterName = "@seats";
                    paramSeats.Value = purchase.getSeats();
                    command.Parameters.Add(paramSeats);

                    var paramAddress = command.CreateParameter();
                    paramAddress.ParameterName = "@address";
                    paramAddress.Value = purchase.getAddress();
                    command.Parameters.Add(paramAddress);

                    command.ExecuteNonQuery();
                }

                return purchase;
            }
            catch (Exception ex)
            {
                log.Error("Error while saving purchase", ex);
                return null;
            }
        }

        public List<Purchase> findByClientOrderedBySeats(String client) {
            IDbConnection con = DBUtils.getConnection(dbConnection);
            List<Purchase> purchases = new List<Purchase>();

            using (var command = con.CreateCommand())
            {
                command.CommandText = "SELECT * FROM \"Purchase\" WHERE \"client\" = @client ORDER BY \"seats\" DESC";

                var paramClient = command.CreateParameter();
                paramClient.ParameterName = "@client";
                paramClient.Value = client;
                command.Parameters.Add(paramClient);

                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        long id = Convert.ToInt64(reader["id"]);
                        string readClient = reader["client"].ToString();
                        long game = Convert.ToInt64(reader["game"]);
                        int seats = Convert.ToInt32(reader["seats"]);
                        string address = reader["address"].ToString();

                        Purchase purchase = new Purchase(readClient, game, seats, address);
                        purchase.set_identitykey(id);
                        purchases.Add(purchase);
                    }
                }
            }

            return purchases;
        }
        public Purchase? Update(Purchase purchase)
        {
            return null;
        }

        public Purchase? Delete(long id)
        {
            return null;
        }

        public Purchase FindOne(long id)
        {
            log.InfoFormat("Entering FindOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(dbConnection);
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Purchase\" where \"id\" = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        long pid = Convert.ToInt64(reader["id"]);
                        string client = reader["client"].ToString();
                        long game = Convert.ToInt64(reader["game"]);
                        int seats = Convert.ToInt32(reader["seats"]);
                        string address = reader["address"].ToString();

                        Purchase purchase = new Purchase(client, game, seats, address);
                        purchase.set_identitykey(pid);
                        return purchase;
                    }
                }
            }
            return null;
        }

        public List<Purchase> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(dbConnection);
            List<Purchase> purchases = new List<Purchase>();
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Purchase\"";
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        var purchase = new Purchase(reader["client"].ToString(), Convert.ToInt64(reader["game"]),
                            Convert.ToInt32(reader["seats"]), reader["address"].ToString());
                        purchases.Add(purchase);
                    }
                }
            }
            return purchases;
        }
    }
}
