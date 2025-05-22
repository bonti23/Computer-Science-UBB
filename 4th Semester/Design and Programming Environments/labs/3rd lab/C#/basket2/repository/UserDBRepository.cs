using basket2.domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using log4net;
using System.Linq;

namespace basket2.repository
{
    public class UserDBRepository : DBUtils<long, User>, UserRepository
    {
        private static readonly ILog log = LogManager.GetLogger("UserDBRepository");

        public UserDBRepository(IDictionary<string, string> props) : base(props)
        {
        }

        // Adaugă un utilizator în baza de date
        public void Save(User user)
        {
            int result = ExecuteNonQuery("insert into \"User\" (\"name\", \"username\", \"password\")" +
                                         "values(@name, @username, @password)", new Dictionary<string, object>
            {
                { "@name", user.get_name() },
                { "@username", user.get_username() },
                { "@password", user.get_password() }
            });
        }

        // Actualizează un utilizator
        public void Update(User user)
        {
            int result = ExecuteNonQuery("update \"User\" set \"name\" = @name, \"username\" = @username, \"password\" = @password where \"id\" = @id", 
                new Dictionary<string, object>
            {
                { "@name", user.get_name() },
                { "@username", user.get_username() },
                { "@password", user.get_password() },
                { "@id", user.get_id() } // Asigură-te că `user.Id` este disponibil
            });
        }

        // Șterge un utilizator
        public void Delete(long id)
        {
            int result = ExecuteNonQuery("delete from \"User\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }

        // Obține un utilizator după ID
        public User findOne(long id)
        {
            log.Info($"Getting user by id: {id}");
            return SelectFirst("select * from \"User\" where \"id\" = @id", new Dictionary<string, object>
            {
                { "@id", id }
            });
        }

        // Obține toți utilizatorii
        public IEnumerable<User> findAll()
        {
            return Select("select * from \"User\"");
        }

        // Obține utilizatorii ordonați alfabetic după numele de utilizator
        public List<User> findByUsernameAlphabetically(string username)
        {
            return Select("select * from \"User\" where \"username\" = @username order by \"username\" asc", 
                new Dictionary<string, object> { { "@username", username } }).ToList();
        }

        // Metodă protejată pentru decodarea unui reader
        protected override User DecodeReader(IDataReader reader)
        {
            log.Info("Decoding user");

            var id = Convert.ToInt64(reader["id"]);
            var name = reader["name"].ToString();
            var username = reader["username"].ToString();
            var password = reader["password"].ToString();

            var user = new User(name, username, password);
            user.identitykey = id;
            return user;
        }
    }
}
