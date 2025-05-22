using System;
using System.Data.SqlClient;
using System.Threading;

namespace _4thSGDB
{
    internal class Program
    {
        static string connectionString = @"Server=ALEXANDRA\SQLEXPRESS;Database=CabinetStomatologic;Integrated Security=true;TrustServerCertificate=true;";

        static void Main(string[] args)
        {
            Thread thread1 = new Thread(new ThreadStart(runThread1));
            Thread thread2 = new Thread(new ThreadStart(runThread2));

            thread1.Start();
            thread2.Start();

            thread1.Join();
            thread2.Join();

            Console.WriteLine("Ambele fire s-au terminat.");
            Console.ReadLine(); // Pentru a păstra consola deschisă
        }

        private static void runThread1()
        {
            int nrIncercari = 0;
            while (!thr1_run())
            {
                nrIncercari++;
                if (nrIncercari >= 3)
                {
                    Console.WriteLine("First transaction aborted");
                    return;
                }
                Thread.Sleep(1000); // Pauză între retry-uri
            }
        }

        private static bool thr1_run()
        {
            bool succes = false;

            Console.WriteLine("First transaction started");
            using (SqlConnection con = new SqlConnection(connectionString))
            {
                SqlCommand command = con.CreateCommand();
                command.CommandTimeout = 30;

                try
                {
                    con.Open();
                    command.CommandText = "EXECUTE run_thread1";
                    command.ExecuteNonQuery();
                    succes = true;
                    Console.WriteLine("First transaction completed");
                }
                catch (SqlException ex)
                {
                    Console.WriteLine(ex.Message);
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Deadlock in thread1");
                    }
                    else
                    {
                        Console.WriteLine("Error in database (thread1)");
                    }
                }
            }
            return succes;
        }

        private static void runThread2()
        {
            int nrIncercari = 0;
            while (!thr2_run())
            {
                nrIncercari++;
                if (nrIncercari >= 3)
                {
                    Console.WriteLine("Second transaction aborted");
                    return;
                }
                Thread.Sleep(1000); // Pauză între retry-uri
            }
        }

        private static bool thr2_run()
        {
            bool succes = false;

            Console.WriteLine("Second transaction started");
            using (SqlConnection con = new SqlConnection(connectionString))
            {
                SqlCommand command = con.CreateCommand();
                command.CommandTimeout = 30;

                try
                {
                    con.Open();
                    command.CommandText = "EXECUTE run_thread2";
                    command.ExecuteNonQuery();
                    succes = true;
                    Console.WriteLine("Second transaction completed");
                }
                catch (SqlException ex)
                {
                    Console.WriteLine(ex.Message);
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Deadlock in thread2");
                    }
                    else
                    {
                        Console.WriteLine("Error in database (thread2)");
                    }
                }
            }
            return succes;
        }
    }
}
