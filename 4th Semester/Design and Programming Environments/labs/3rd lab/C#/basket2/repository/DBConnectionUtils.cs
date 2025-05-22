using System.Data;
using System.Data;
using System.Data.SQLite;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
namespace basket2.repository;

internal class DBConnectionUtils
{
    private static IDbConnection instance = null;

    public static IDbConnection getConnection(IDictionary<string, string> props)
    {
        if (instance == null || instance.State == ConnectionState.Closed)
        {
            instance = getNewConnection(props);
            instance.Open();
        }
        return instance;
    }

    private static Type GetType(string name)
    {
        foreach (var assembly in AppDomain.CurrentDomain.GetAssemblies())
        {                
            var type = assembly.GetType(name);
            if (type != null)
                return type;

        }
        return null;
    }

    private static IDbConnection getNewConnection(IDictionary<string, string> props)
    {                        
        Type connectionType = GetType(props["ConnectionType"]);                                                

        if (connectionType == null) 
        {
            throw new ArgumentException("Invalid connection type");
        }

        string connectionString = props["ConnectionString"];
        Console.WriteLine($"Se deschide o conexiune {connectionType} la  ... {connectionString}");
        return Activator.CreateInstance(connectionType, new object[] { connectionString })
            as IDbConnection;
    }

    static DBConnectionUtils()
    {
        MakeAssembliesVisible();
    }
    private static void MakeAssembliesVisible()
    {
        IDbConnection con = new SQLiteConnection();
        Console.WriteLine(con.ToString().Substring(0, 0));
    }
}