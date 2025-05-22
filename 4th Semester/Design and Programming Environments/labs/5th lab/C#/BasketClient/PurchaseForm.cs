using System;
using System.Windows.Forms;
using BasketModel;
using BasketServices;
using BasketNetworking.DTO; // Asigură-te că ai importat namespace-ul pentru DTO

namespace BasketClient
{
    public partial class PurchaseForm : Form
    {
        private IService service;
        private Game selectedGame;

        public PurchaseForm(IService service, Game game)
        {
            InitializeComponent();
            this.service = service;
            this.selectedGame = game;
            if (this.selectedGame == null || this.selectedGame.id == 0)
            {
                MessageBox.Show("Game is not valid. Please select a valid game.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Close();
            }
            UpdateGameDetails();        }

        private void UpdateGameDetails()
        {
            gameDetailsLabel.Text = $"{selectedGame.teamA} vs {selectedGame.teamB}";
            seatsAvailableLabel.Text = $"Seats Available: {selectedGame.seats}";
        }

        private async void PurchaseButton_Click(object sender, EventArgs e)
        {
            string clientName = clientNameTextBox.Text;
            string address = addressTextBox.Text;
            string seatsText = seatsTextBox.Text;

            if (string.IsNullOrEmpty(clientName) || string.IsNullOrEmpty(address) || string.IsNullOrEmpty(seatsText))
            {
                errorMessageLabel.Text = "All fields are required!";
                return;
            }

            try
            {
                int seats = int.Parse(seatsText);

                if (seats <= 0)
                {
                    errorMessageLabel.Text = "Invalid number of seats!";
                    return;
                }

                if (seats > selectedGame.seats)
                {
                    errorMessageLabel.Text = "Not enough seats available!";
                    return;
                }

                PurchaseDTO purchaseDTO = new PurchaseDTO(new Purchase(clientName, selectedGame.id, seats, address));
                Purchase purchase = purchaseDTO.ToModel();
                await Task.Run(() => service.AddPurchase(purchase));
                MessageBox.Show("Purchase added successfully!", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.Hide();
            }
            catch (FormatException)
            {
                errorMessageLabel.Text = "Number of seats must be a valid number!";
            }
            catch (Exception ex)
            {
                errorMessageLabel.Text = ex.Message;
            }
        }


        private void CancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        // Event when form loads
        private void PurchaseForm_Load(object sender, EventArgs e)
        {
            // You can add any initializations or configurations if needed
        }
    }
}
