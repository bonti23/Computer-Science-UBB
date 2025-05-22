using System;
using System.Data.SQLite;
using System.Windows;
using basket2.service;
using log4net;

namespace basket2
{
    public partial class SignUp : Window
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(SignUp));
        private Service _service;

        public SignUp()
        {
            InitializeComponent();
        }

        // Setează serviciul pentru fereastra de înregistrare
        public void SetService(Service service)
        {
            _service = service;  // Setează serviciul pentru a fi utilizat în fereastra de înregistrare
        }

        // Event handler pentru butonul de Sign Up
        private void SignUpButton_Click(object sender, RoutedEventArgs e)
        {
            HandleSignUp();
        }

        // Verifică conexiunea la baza de date înainte de înregistrare
        private bool IsDatabaseConnected()
        {
            try
            {
                using (var connection = new SQLiteConnection(Config.DatabaseProperties["ConnectionString"]))
                {
                    connection.Open();
                    return true;  // Conexiune reușită
                }
            }
            catch (Exception ex)
            {
                log.Error("Conexiune la baza de date eșuată: " + ex.Message);
                MessageBox.Show("Conexiune la baza de date eșuată. Te rugăm să încerci mai târziu.", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                return false;
            }
        }

        // Verifică validitatea câmpurilor
        private bool ValidateFields(string name, string username, string password)
        {
            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                errorMessage.Text = "Toate câmpurile sunt obligatorii!";
                return false;
            }
            return true;
        }

        // Handle the sign-up logic
        private void HandleSignUp()
        {
            // Verifică conexiunea la baza de date
            if (!IsDatabaseConnected())
            {
                return;  // Oprește procesul dacă baza de date nu poate fi accesată
            }

            string name = nameField.Text;
            string username = usernameField.Text;
            string password = passwordField.Password;

            // Verifică dacă câmpurile sunt completate
            if (!ValidateFields(name, username, password))
            {
                return;  // Oprește procesul dacă câmpurile nu sunt valide
            }

            try
            {
                // Apelăm serviciul pentru a înregistra utilizatorul
                _service.Signup(name, username, password);

                // Afișăm un mesaj de succes și închidem fereastra
                ShowSuccessAlert("Contul a fost creat cu succes!");
            }
            catch (ArgumentException ex)
            {
                log.Error($"Înregistrare eșuată: {ex.Message}");
                errorMessage.Text = $"Eroare: {ex.Message}. Te rugăm să verifici datele introduse.";
            }
            catch (Exception ex)
            {
                log.Error("Eroare neașteptată în timpul înregistrării: " + ex.ToString());
                errorMessage.Text = "A apărut o eroare neașteptată. Te rugăm să încerci din nou.";
            }
        }

        // Afișează un mesaj de succes
        private void ShowSuccessAlert(string message)
        {
            MessageBox.Show(message, "Succes", MessageBoxButton.OK, MessageBoxImage.Information);
            this.Close();  // Închide fereastra de înregistrare după succes
        }
    }
}
