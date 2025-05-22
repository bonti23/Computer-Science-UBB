using System.Data;
using basket2.domain;

namespace basket2.repository;

public abstract class DBUtils<ID, E> where E : Entity<ID>
{
    private IDictionary<string, string> Properties;

    protected DBUtils(IDictionary<string, string> props)
    {
        Properties = props;
    }
    protected abstract E DecodeReader(IDataReader reader);
    private IDbCommand CreateCommand(IDbConnection con, string sql, Dictionary<string, object> parameters = null)
    {
        var cmd = con.CreateCommand();
        cmd.CommandText = sql;
        if (parameters != null)
        {
            foreach (var arg in parameters)
            {
                var param = cmd.CreateParameter();
                param.ParameterName = arg.Key;
                param.Value = arg.Value;
                cmd.Parameters.Add(param);
            }
        }
        return cmd;
    }
            
    protected int ExecuteNonQuery(string sql, Dictionary<string, object> parameters = null)
    {
        var con = DBConnectionUtils.getConnection(Properties);
        using (var cmd = CreateCommand(con, sql, parameters))             
            return cmd.ExecuteNonQuery();            
    }

    protected IEnumerable<E> Select(string sql, Dictionary<string, object> parameters = null)
    {
        var con = DBConnectionUtils.getConnection(Properties);
        using (var cmd = CreateCommand(con, sql, parameters))
        {
            using(var reader = cmd.ExecuteReader())
            {
                while(reader.Read())
                {
                    yield return DecodeReader(reader);
                }
            }
        }
        yield break;
    }

    protected E SelectFirst(string sql, Dictionary<string, object> parameters = null)
    {
        var con = DBConnectionUtils.getConnection(Properties);
        using (var cmd = CreateCommand(con, sql, parameters))
        {
            using (var reader = cmd.ExecuteReader())
            {
                if (!reader.Read())
                    return default;
                return DecodeReader(reader);                 
            }
        }            
    }
}