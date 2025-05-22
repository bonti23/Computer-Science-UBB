using System.ComponentModel;
using System.Windows.Forms;

namespace BasketClient
{
    partial class MainMenuForm
    {
        private System.ComponentModel.IContainer components = null;
        private ComboBox gameTypeComboBox;
        private DataGridView gameDataGridView;
        private Button purchaseButton;
        private Button logoutButton;
        private PictureBox background;

        // Form's Dispose method to clean up resources
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
                components.Dispose();
            base.Dispose(disposing);
        }

        // Initialize form components and layout
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.gameTypeComboBox = new ComboBox();
            this.gameDataGridView = new DataGridView();
            this.purchaseButton = new Button();
            this.logoutButton = new Button();
            this.background = new PictureBox();

            ((System.ComponentModel.ISupportInitialize)(this.gameDataGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.background)).BeginInit();
            this.SuspendLayout();

            // gameTypeComboBox
            this.gameTypeComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            this.gameTypeComboBox.Location = new System.Drawing.Point(20, 20);
            this.gameTypeComboBox.Size = new System.Drawing.Size(200, 25);
            this.gameTypeComboBox.SelectedIndexChanged += new EventHandler(this.GameTypeComboBox_SelectedIndexChanged);

            // gameDataGridView
            this.gameDataGridView.Location = new System.Drawing.Point(20, 60);
            this.gameDataGridView.Size = new System.Drawing.Size(600, 250);
            this.gameDataGridView.AutoGenerateColumns = true;
            this.gameDataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            // purchaseButton
            this.purchaseButton.Location = new System.Drawing.Point(20, 330);
            this.purchaseButton.Size = new System.Drawing.Size(100, 30);
            this.purchaseButton.Text = "Purchase";
            this.purchaseButton.Click += new EventHandler(this.PurchaseButton_Click);

            // logoutButton
            this.logoutButton.Location = new System.Drawing.Point(520, 330);
            this.logoutButton.Size = new System.Drawing.Size(100, 30);
            this.logoutButton.Text = "Logout";
            this.logoutButton.Click += new EventHandler(this.LogoutButton_Click);

            // background
            this.background.Location = new System.Drawing.Point(0, 0);
            this.background.Size = new System.Drawing.Size(650, 400);
            this.background.SizeMode = PictureBoxSizeMode.StretchImage;

            // MainMenuForm
            this.ClientSize = new System.Drawing.Size(650, 400);
            this.Controls.Add(this.gameTypeComboBox);
            this.Controls.Add(this.gameDataGridView);
            this.Controls.Add(this.purchaseButton);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.background);
            this.Text = "Main Menu";
            this.Load += new System.EventHandler(this.MainMenuForm_Load);

            ((System.ComponentModel.ISupportInitialize)(this.gameDataGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.background)).EndInit();
            this.ResumeLayout(false);
        }
    }
}
