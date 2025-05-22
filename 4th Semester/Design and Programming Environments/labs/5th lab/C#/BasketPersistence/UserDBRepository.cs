using System;
using System.Collections.Generic;
using System.Data;
using BasketModel;
using log4net;
using Microsoft.VisualBasic.Logging;

namespace BasketPersistence
{
    public class UserDBRepository(IDictionary<string, string> dbConnection) : UserRepository
    {
        private static readonly ILog log = LogManager.GetLogger("UserDBRepository");
        public User? Save(User user)
        {
            try
            {
                IDbConnection con = DBUtils.getConnection(dbConnection);
                using (var command = con.CreateCommand())
                {
                    command.CommandText = "insert into \"Users\" (\"name\", \"username\", \"password\") values (@name, @username, @password)";

                    var paramName = command.CreateParameter();
                    paramName.ParameterName = "@name";
                    paramName.Value = user.getName();
                    command.Parameters.Add(paramName);

                    var paramUsername = command.CreateParameter();
                    paramUsername.ParameterName = "@username";
                    paramUsername.Value = user.getUsername();
                    command.Parameters.Add(paramUsername);

                    var paramPassword = command.CreateParameter();
                    paramPassword.ParameterName = "@password";
                    paramPassword.Value = user.getPassword();
                    command.Parameters.Add(paramPassword);
                    command.ExecuteNonQuery();
                }
                return user;
            }
            catch (Exception ex)
            {
                log.Error("Error while saving user", ex);
                return null;
            }
        }
        public User? Update(User user)
        {
            return null;
        }

        public User? Delete(long id)
        {
            return null;
        }

        public User? FindOne(long id)
        {
            log.InfoFormat("Entering FindOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(dbConnection);
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Users\" where \"id\" = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        return new User(
                            reader["name"].ToString(),
                            reader["username"].ToString(),
                            reader["password"].ToString()
                        );
                    }
                }
            }
            return null;
        }
        public User? FindOneByUsername(string username)
        {
            log.InfoFormat("Entering FindOneByUsername with value {0}", username);
            IDbConnection con = DBUtils.getConnection(dbConnection);
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Users\" where \"username\" = @username";
                IDbDataParameter paramUsername = command.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                command.Parameters.Add(paramUsername);

                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        return new User(
                            reader["name"].ToString(),
                            reader["username"].ToString(),
                            reader["password"].ToString()
                        );
                    }
                }
            }
            return null;
        }
        public List<User> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(dbConnection);
            List<User> users = new List<User>();
            using (var command = con.CreateCommand())
            {
                command.CommandText = "select * from \"Users\"";
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        var user = new User(
                            reader["name"].ToString(),
                            reader["username"].ToString(),
                            reader["password"].ToString()
                        );
                        users.Add(user);
                    }
                }
            }
            return users;
        }
    }
}
