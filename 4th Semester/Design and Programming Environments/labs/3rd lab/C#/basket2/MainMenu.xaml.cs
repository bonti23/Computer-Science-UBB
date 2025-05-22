using System;
using System.Collections.Generic;
using System.Reflection;
using System.Windows;
using System.Windows.Controls;
using basket2.domain;
using basket2.service;
using log4net;
using Type = basket2.domain.Type;

namespace basket2
{
    public partial class MainMenu : Window
    {
        private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
        private Service _service;

        public MainMenu()
        {
            InitializeComponent();
            gameTypeComboBox.SelectionChanged += GameTypeComboBox_SelectionChanged;
            PopulateGameTypes();
        }

        public void SetService(Service service)
        {
            _service = service;
        }

        public void PopulateGameTypes()
        {
            var gameTypes = new List<Type>
            {
                Type.FINAL,
                Type.SEMIFINAL,
                Type.QUARTERFINAL,
                Type.REGULAR,
                Type.PLAYOFF
            };
            gameTypeComboBox.ItemsSource = gameTypes;
        }

        private void GameTypeComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (gameTypeComboBox.SelectedItem != null)
            {
                Type selectedType = (Type)gameTypeComboBox.SelectedItem;
                log.Info($"Selected game type: {selectedType}");

                UpdateGameTable(selectedType);
            }
            else
            {
                log.Warn("No game type selected");
                gameTable.ItemsSource = null;
            }
        }

        private void UpdateGameTable(Type gameType)
        {
            log.Info($"Updating game table with type: {gameType}");

            var games = _service.ShowcaseGamesByType(gameType.ToString());

            if (games == null || games.Count == 0)
            {
                log.Info($"No games found for the type: {gameType}");
                gameTable.ItemsSource = null;
                return;
            }

            log.Info($"Retrieved {games.Count} games for type: {gameType}");

            foreach (var game in games)
            {
                log.Info($"Game: {game.TeamA} vs {game.TeamB} on {game.Date}");
            }

            Dispatcher.Invoke(() =>
            {
                if (gameTable.Columns.Count == 0)
                {
                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Team A",
                        Binding = new System.Windows.Data.Binding("TeamA")
                    });

                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Team B",
                        Binding = new System.Windows.Data.Binding("TeamB")
                    });

                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Date",
                        Binding = new System.Windows.Data.Binding("Date")
                    });

                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Price",
                        Binding = new System.Windows.Data.Binding("Price")
                    });

                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Type",
                        Binding = new System.Windows.Data.Binding("Type")
                    });

                    gameTable.Columns.Add(new DataGridTextColumn
                    {
                        Header = "Seats",
                        Binding = new System.Windows.Data.Binding("Seats")
                    });
                }

                gameTable.ItemsSource = games;
                gameTable.Items.Refresh();
            });
        }

        private void HandlePurchase()
        {
            Game selectedGame = (Game)gameTable.SelectedItem;

            if (selectedGame == null)
            {
                ShowErrorMessage("please select a game to purchase tickets.");
                return;
            }

            if (selectedGame.Seats == 0)
            {
                ShowErrorMessage("there are no more tickets left.");
                return;
            }

            var purchaseWindow = new PurchaseView();
            purchaseWindow.SetService(_service);
            purchaseWindow.SetGame(selectedGame);
            purchaseWindow.Show();
        }

        private void PurchaseButton_Click(object sender, RoutedEventArgs e)
        {
            HandlePurchase();
        }

        private void LogoutButton_Click(object sender, RoutedEventArgs e)
        {
            var loginWindow = new Login();
            loginWindow.Show();
            this.Close();
        }

        private void ShowErrorMessage(string message)
        {
            MessageBox.Show(message, "selection error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
