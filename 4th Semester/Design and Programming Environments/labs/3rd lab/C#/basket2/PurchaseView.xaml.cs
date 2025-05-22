using System;
using System.Windows;
using basket2.service;
using basket2.domain;

namespace basket2
{
    public partial class PurchaseView : Window
    {
        private Service _service;
        private Game _selectedGame;

        public PurchaseView()
        {
            InitializeComponent();
        }

        public void SetService(Service service)
        {
            _service = service;
        }

        public void SetGame(Game game)
        {
            _selectedGame = game;
            UpdateGameDetails();
        }

        private void UpdateGameDetails()
        {
            gameDetailsLabel.Content = $"{_selectedGame.TeamA} vs {_selectedGame.TeamB}";
        }

        private void PurchaseButton_Click(object sender, RoutedEventArgs e)
        {
            HandlePurchase();
        }

        private void HandlePurchase()
        {
            string clientName = clientNameField.Text;
            string address = addressField.Text;
            string seatsText = seatsField.Text;

            // Validate fields
            if (string.IsNullOrEmpty(clientName) || string.IsNullOrEmpty(address) || string.IsNullOrEmpty(seatsText))
            {
                errorMessage.Content = "All fields are required!";
                return;
            }

            try
            {
                int seats = int.Parse(seatsText);

                if (seats <= 0)
                {
                    errorMessage.Content = "Invalid number of seats!";
                    return;
                }

                if (seats > _selectedGame.Seats)
                {
                    errorMessage.Content = "Not enough seats available!";
                    return;
                }

                // Create Purchase object from domain
                var purchase = new basket2.domain.Purchase(clientName, _selectedGame.get_id(), seats, address);
                _service.AddPurchase(purchase);

                ShowSuccessAlert("Purchase added successfully!");
                CloseWindow();
            }
            catch (FormatException)
            {
                errorMessage.Content = "Number of seats must be a valid number!";
            }
            catch (ArgumentException ex)
            {
                errorMessage.Content = ex.Message;
            }
        }

        private void ShowSuccessAlert(string message)
        {
            MessageBox.Show(message, "Success", MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void CloseWindow()
        {
            this.Close();
        }

        private void CancelButton_Click(object sender, RoutedEventArgs e)
        {
            CloseWindow();
        }

        // Handle GotFocus event for Address Field
        private void AddressField_GotFocus(object sender, RoutedEventArgs e)
        {
            if (addressField.Text == "Address")
            {
                addressField.Text = "";
                addressField.Foreground = System.Windows.Media.Brushes.Black;
            }
        }

        // Handle LostFocus event for Address Field
        private void AddressField_LostFocus(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(addressField.Text))
            {
                addressField.Text = "Address";
                addressField.Foreground = System.Windows.Media.Brushes.Gray;
            }
        }

        // Handle GotFocus event for Seats Field
        private void SeatsField_GotFocus(object sender, RoutedEventArgs e)
        {
            if (seatsField.Text == "Number of Seats")
            {
                seatsField.Text = "";
                seatsField.Foreground = System.Windows.Media.Brushes.Black;
            }
        }

        // Handle LostFocus event for Seats Field
        private void SeatsField_LostFocus(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(seatsField.Text))
            {
                seatsField.Text = "Number of Seats";
                seatsField.Foreground = System.Windows.Media.Brushes.Gray;
            }
        }
    }
}
