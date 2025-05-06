using System;
using System.Windows.Forms;
using BasketModel;
using BasketServices;
using log4net; // Asigură-te că ai adăugat referința la modelul tău (BasketModel)

namespace BasketClient
{
    public partial class LoginForm : Form
    {
        private IService service;
        private MainMenuForm mainMenu;
        private string currentUsername;
        private static readonly ILog logger = LogManager.GetLogger(typeof(LoginForm));
        public LoginForm(IService service)
        {
            this.service = service;
            InitializeComponent();
            logger.Debug("Login form initialized");
            
        }

        private void Login_Load(object sender, EventArgs e)
        {
            // Optional: any load-time logic
        }
        private void btnLogin_Click(object sender, EventArgs e)
        {
            string username = txtUsername.Text;
            string password = txtPassword.Text;
            try
            {
                mainMenu = new MainMenuForm(service, this);
                service.Login(username, password, mainMenu);

                currentUsername = username;
                OpenMainWindow();
            }
            catch (Exception ex)
            {
                logger.Error("Login failed", ex);
                MessageBox.Show("Login failed: " + ex.Message, "Login Error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);

                if (mainMenu != null)
                {
                    mainMenu.Dispose();
                    mainMenu = null;
                }
            }
        }
        private void OpenMainWindow()
        {
            try
            {
                mainMenu.Text = $"Main Window for {currentUsername}";

                mainMenu.FormClosed += (s, args) =>
                {
                    this.Show();
                    txtUsername.Clear();
                    txtPassword.Clear();
                };

                mainMenu.FormClosing += (sender, e) =>
                {
                    LogoutUser();
                    txtUsername.Clear();
                    txtPassword.Clear();
                    this.Show();
                };

                mainMenu.Show();
                mainMenu.setUsername(currentUsername);
                mainMenu.LoadData();
                this.Hide();
                mainMenu.FormClosed += (s, args) => this.Show();


            }
            catch (Exception ex)
            {
                logger.Error($"Error opening main window: {ex.Message}", ex);
                MessageBox.Show($"Error opening main window: {ex.Message}", "Error",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        private void LogoutUser()
        {
            try
            {
                if (!string.IsNullOrEmpty(currentUsername) && mainMenu != null)
                {
                    service.Logout(currentUsername, mainMenu);
                }
            }
            catch (Exception ex)
            {
                logger.Error($"Error during logout: {ex.Message}", ex);
            }
        }
        private void lblSignUp_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            // Deschidem formularul de înregistrare și transmitem serviciul
            SignUpForm signUpForm = new SignUpForm(service);
            signUpForm.Show();
        }
    }
}
