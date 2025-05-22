using System;
using System.Windows.Forms;
using BasketServices;

namespace BasketClient
{
    public partial class SignUpForm : Form
    {
        private IService service;

        public SignUpForm(IService service)
        {
            InitializeComponent();
            this.service = service;
        }

        private void SignUpButton_Click(object sender, EventArgs e)
        {
            string name = nameTextBox.Text;
            string username = usernameTextBox.Text;
            string password = passwordTextBox.Text;
            Console.WriteLine(name + " " + username + " " + password + "\n");

            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                errorMessageLabel.Text = "All fields are required!";
                return;
            }

            try
            {
                Console.WriteLine("Calling Signup method from server...");
                service.Signup(name, username, password);
                Console.WriteLine("Signup completed successfully.");
                MessageBox.Show("Account created successfully!", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.Close();
            }
            catch (Exception ex)
            {
                errorMessageLabel.Text = ex.Message;
            }
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            this.Close(); // Close the form if Cancel is clicked
        }

        private void Fields_TextChanged(object sender, EventArgs e)
        {
            // Enable the sign-up button only if all fields are filled
            bool isFormValid = !string.IsNullOrEmpty(nameTextBox.Text) &&
                               !string.IsNullOrEmpty(usernameTextBox.Text) &&
                               !string.IsNullOrEmpty(passwordTextBox.Text);

            signUpButton.Enabled = isFormValid;
        }
    }
}