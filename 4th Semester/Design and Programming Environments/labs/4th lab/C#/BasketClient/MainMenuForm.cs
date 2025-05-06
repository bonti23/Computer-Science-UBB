using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Windows.Forms;
using BasketModel;
using BasketServices;

namespace BasketClient
{
    public partial class MainMenuForm : Form, IObserver
    {
        private IService service;
        private string currentUsername; // added to store the username
        private LoginForm loginForm;

        public MainMenuForm(IService service, LoginForm loginForm)
        {
            try
            {
                InitializeComponent();
                this.service = service;
                this.loginForm = loginForm;
                LoadData();
                CustomizeDataGridView();
                gameDataGridView.CellFormatting += GameDataGridView_CellFormatting;

            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occurred while initializing the form: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                Application.Exit();  // Închide aplicația dacă există o eroare
            }

        }

        public void setUsername(string username)
        {
            this.currentUsername = username;
        }

        public void LoadData()
        {
            try
            {
                List<Game> allGames = new List<Game>();

                foreach (GameType type in Enum.GetValues(typeof(GameType)))
                {
                    List<Game> gamesByType = service.ShowcaseGamesByType(type.ToString());
                    allGames.AddRange(gamesByType);
                }

                Console.WriteLine($"Total games loaded: {allGames.Count}");
                gameDataGridView.DataSource = new BindingList<Game>(allGames);
                gameDataGridView.Refresh();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error loading games: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }



        private void GameDataGridView_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (gameDataGridView.Columns[e.ColumnIndex].HeaderText == "Available Seats")
            {
                DataGridViewRow row = gameDataGridView.Rows[e.RowIndex];
                Game game = row.DataBoundItem as Game;

                // Dacă locurile sunt 0, se face rândul roșu
                if (game != null && game.seats == 0)
                {
                    row.DefaultCellStyle.BackColor = Color.Red; 
                    row.DefaultCellStyle.ForeColor = Color.White;
                }
                else
                {
                    row.DefaultCellStyle.BackColor = Color.White;
                    row.DefaultCellStyle.ForeColor = Color.Black;
                }
            }
        }

        private void GameTypeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (gameTypeComboBox.SelectedItem != null)
            {
                string selectedString = gameTypeComboBox.SelectedItem.ToString();
                GameType selectedType = Enum.GetValues(typeof(GameType))
                    .Cast<GameType>()
                    .FirstOrDefault(x => x.ToFriendlyString() == selectedString);

                if (selectedType == GameType.None) // Assuming 'None' is an invalid type
                {
                    MessageBox.Show("Invalid Game Type selected.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }

                // Debugging: Verifică tipul de joc selectat

                UpdateGameTable(selectedType);
            }
        }


        private void CustomizeDataGridView()
{
    gameDataGridView.AutoGenerateColumns = true; // Dezactivează generarea automată a coloanelor
    gameDataGridView.Columns.Clear(); // Curăță orice coloană existentă

    // Setare stil pentru DataGridView
    gameDataGridView.BackgroundColor = Color.WhiteSmoke; // Setează fundalul tabelului
    gameDataGridView.BorderStyle = BorderStyle.FixedSingle; // Setează bordura tabelului
    gameDataGridView.GridColor = Color.LightGray; // Culoarea grilei
    gameDataGridView.DefaultCellStyle.Font = new Font("Arial", 10); // Setează fontul pentru celule
    gameDataGridView.DefaultCellStyle.SelectionBackColor = Color.LightBlue; // Culoarea de fundal pentru celula selectată
    gameDataGridView.DefaultCellStyle.SelectionForeColor = Color.Black; // Culoarea textului pentru celula selectată
    gameDataGridView.RowHeadersVisible = false; // Ascunde capul de tabel
    gameDataGridView.AllowUserToResizeColumns = true; // Permite redimensionarea coloanelor
    gameDataGridView.AllowUserToResizeRows = false; // Nu permite redimensionarea rândurilor
    gameDataGridView.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill; // Colonele se ajustează automat pentru a umple tabelul

    // Coloană pentru ID (identitykey)
    DataGridViewTextBoxColumn idColumn = new DataGridViewTextBoxColumn();
    idColumn.DataPropertyName = "id"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    idColumn.HeaderText = "ID";
    idColumn.Width = 100;
    idColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    idColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text în celulă
    idColumn.Visible = false;
    gameDataGridView.Columns.Add(idColumn);

    // Coloană pentru Echipa A
    DataGridViewTextBoxColumn teamAColumn = new DataGridViewTextBoxColumn();
    teamAColumn.DataPropertyName = "TeamA"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    teamAColumn.HeaderText = "Team A";
    teamAColumn.Width = 150;
    teamAColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    teamAColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleLeft; // Aliniere la stânga pentru echipe
    gameDataGridView.Columns.Add(teamAColumn);

    // Coloană pentru Echipa B
    DataGridViewTextBoxColumn teamBColumn = new DataGridViewTextBoxColumn();
    teamBColumn.DataPropertyName = "TeamB"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    teamBColumn.HeaderText = "Team B";
    teamBColumn.Width = 150;
    teamBColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    teamBColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleLeft; // Aliniere la stânga pentru echipe
    gameDataGridView.Columns.Add(teamBColumn);

    // Coloană pentru Data
    DataGridViewTextBoxColumn dateColumn = new DataGridViewTextBoxColumn();
    dateColumn.DataPropertyName = "Date"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    dateColumn.HeaderText = "Date";
    dateColumn.Width = 120;
    dateColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    dateColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text în celulă
    gameDataGridView.Columns.Add(dateColumn);

    // Coloană pentru Preț
    DataGridViewTextBoxColumn priceColumn = new DataGridViewTextBoxColumn();
    priceColumn.DataPropertyName = "Price"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    priceColumn.HeaderText = "Price";
    priceColumn.Width = 100;
    priceColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    priceColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight; // Aliniere la dreapta pentru preț
    priceColumn.DefaultCellStyle.Format = "C2"; // Format pentru preț (ex: 100,00 Lei)
    gameDataGridView.Columns.Add(priceColumn);

    // Coloană pentru Tipul jocului
    DataGridViewTextBoxColumn typeColumn = new DataGridViewTextBoxColumn();
    typeColumn.DataPropertyName = "Type"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    typeColumn.HeaderText = "Type";
    typeColumn.Width = 100;
    typeColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    typeColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text în celulă
    gameDataGridView.Columns.Add(typeColumn);

    // Coloană pentru Locuri disponibile
    DataGridViewTextBoxColumn seatsColumn = new DataGridViewTextBoxColumn();
    seatsColumn.DataPropertyName = "Seats"; // Asigură-te că acest nume se potrivește cu proprietatea din clasa Game
    seatsColumn.HeaderText = "Available Seats";
    seatsColumn.Width = 120;
    seatsColumn.HeaderCell.Style.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text header
    seatsColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleCenter; // Centrare text în celulă
    gameDataGridView.Columns.Add(seatsColumn);
}


        // Update the game table based on selected GameType
        private void UpdateGameTable(GameType gameType)
        {
            try
            {
                List<Game> games = service.ShowcaseGamesByType(gameType.ToString());
                gameDataGridView.DataSource = new BindingList<Game>(games);  // Folosește BindingList și aici
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error filtering games: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }




        // Handle Purchase Button click
        private void PurchaseButton_Click(object sender, EventArgs e)
        {
            if (gameDataGridView.SelectedRows.Count == 0)
            {
                MessageBox.Show("Please select a game to purchase tickets.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            Game selectedGame = (Game)gameDataGridView.SelectedRows[0].DataBoundItem;

            if (selectedGame.seats == 0)
            {
                MessageBox.Show("No more tickets left.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            PurchaseForm purchaseForm = new PurchaseForm(service, selectedGame);
            purchaseForm.Show();
        }

        // Handle Logout Button click
        private void LogoutButton_Click(object sender, EventArgs e)
        {
            this.Close();
            loginForm.Show();
        }

        // Notify the client when seats are bought
        public void NotifyBoughtSeats(Game updatedGame)
        {
            this.Invoke((MethodInvoker)delegate
            {
                BindingList<Game> games = (BindingList<Game>)gameDataGridView.DataSource;

                // Găsește și actualizează jocul din BindingList
                for (int i = 0; i < games.Count; i++)
                {
                    if (games[i].get_identitykey() == updatedGame.get_identitykey())
                    {
                        games[i] = updatedGame; // Înlocuiește jocul actualizat
                        break;
                    }
                }

            });
        }


        // Form Load event to populate GameType ComboBox
        private void MainMenuForm_Load(object sender, EventArgs e)
        {
            foreach (GameType type in Enum.GetValues(typeof(GameType)))
            {
                gameTypeComboBox.Items.Add(type.ToFriendlyString());
            }

            gameTypeComboBox.SelectedIndex = -1;
        }
    }
}
