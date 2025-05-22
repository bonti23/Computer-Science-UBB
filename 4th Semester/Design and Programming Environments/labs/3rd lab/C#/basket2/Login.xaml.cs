using System.Data.SQLite;
using System.Reflection;
using System.Windows;
using basket2.repository;
using basket2.service;
using log4net;
using log4net.Config;

namespace basket2
{
    public partial class Login : Window
    {
        private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
        public Service _service;

        public void SetService(Service service)
        {
            if (_service == null)
            {
                _service = service;
            }
        }

        public Login()
        {
            InitializeComponent();
            log.Info("Login window constructor called.");
            log.Info("Fereastra Login a fost deschisă la " + DateTime.Now);
            InitializeApp(); // Inițializează aplicația direct din Login
        }

        private void InitializeApp()
        {
            try
            {
                var gameRepository = new GameDBRepository(Config.DatabaseProperties);
                var purchaseRepository = new PurchaseDBRepository(Config.DatabaseProperties);
                var userRepository = new UserDBRepository(Config.DatabaseProperties);

                _service = new Service(gameRepository, purchaseRepository, userRepository);

                log.Info("Service initialized successfully.");
            }
            catch (Exception ex)
            {
                log.Error("An error occurred while initializing the application: " + ex.Message);
                MessageBox.Show("Error initializing the application. Check the log for details.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void LoginButton_Click(object sender, RoutedEventArgs e)
        {
            if (!Config.DatabaseProperties.ContainsKey("ConnectionString") || string.IsNullOrEmpty(Config.DatabaseProperties["ConnectionString"]))
            {
                log.Error("ConnectionString nu a fost încărcat corect din Config.");
                MessageBox.Show("Eroare: ConnectionString nu a fost configurat corect!", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            string username = UsernameTextBox.Text;
            string password = PasswordBox.Password;

            if (_service == null)
            {
                log.Error("Serviciul nu a fost inițializat în Login.");
                MessageBox.Show("Eroare: serviciul nu este inițializat corect!", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            try
            {
                string connectionString = Config.DatabaseProperties["ConnectionString"];

                if (string.IsNullOrEmpty(connectionString))
                {
                    MessageBox.Show("Connection string nu este corect configurat!", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                using (var connection = new SQLiteConnection(connectionString))
                {
                    connection.Open();  // Verifică conexiunea la baza de date
                    MessageBox.Show("Conexiune la baza de date reușită!");
                }

                var user = _service.Login(username, password); 

                if (user != null)
                {
                    MessageBox.Show("Autentificare reușită!", "Succes", MessageBoxButton.OK, MessageBoxImage.Information);
                    MainMenu mainWindow = new MainMenu();
                    mainWindow.SetService(_service);
                    mainWindow.Show();
                    this.Close();
                }
                else
                {
                    errorMessage.Content = "Nume de utilizator sau parolă incorecte!";
                    errorMessage.Visibility = Visibility.Visible;
                }
            }
            catch (Exception ex)
            {
                log.Error($"Conexiune la baza de date eșuată: {ex.Message}");
                MessageBox.Show($"Conexiune la baza de date eșuată: {ex.Message}", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void SignUp_Click(object sender, RoutedEventArgs e)
        { 
            MessageBox.Show("Butonul de Sign Up a fost apăsat!");
            try
            {
                if (_service == null)
                {
                    log.Error("Serviciul nu a fost inițializat corect!");
                    MessageBox.Show("Eroare: serviciul nu a fost inițializat corect!", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }

                // Log pentru diagnosticare
                log.Info("Se încearcă deschiderea ferestrei de înregistrare...");

                // Creează și deschide fereastra de înregistrare
                SignUp signUpView = new SignUp();
                signUpView.SetService(_service);  // Setează serviciul pentru fereastra de înregistrare
                signUpView.Show();  // Deschide fereastra de înregistrare
            }
            catch (Exception ex)
            {
                log.Error("Eroare la deschiderea ferestrei de înregistrare: " + ex.Message);
                MessageBox.Show("Eroare la deschiderea ferestrei de înregistrare: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }



    }
}
