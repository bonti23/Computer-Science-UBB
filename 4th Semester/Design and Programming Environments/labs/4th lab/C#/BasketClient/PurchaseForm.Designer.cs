using System.ComponentModel;
using System.Windows.Forms;

namespace BasketClient
{
    partial class PurchaseForm
    {
        private System.ComponentModel.IContainer components = null;
        private Label gameDetailsLabel;
        private Label seatsAvailableLabel;
        private TextBox clientNameTextBox;
        private TextBox seatsTextBox;
        private Button purchaseButton;
        private Button cancelButton;
        private Label nameLabel;
        private Label seatCountLabel;
        private Label addressLabel;
        private TextBox addressTextBox;
        private Label errorMessageLabel;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
                components.Dispose();
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.gameDetailsLabel = new Label();
            this.seatsAvailableLabel = new Label();
            this.clientNameTextBox = new TextBox();
            this.seatsTextBox = new TextBox();
            this.purchaseButton = new Button();
            this.cancelButton = new Button();
            this.nameLabel = new Label();
            this.seatCountLabel = new Label();
            this.addressLabel = new Label();
            this.addressTextBox = new TextBox();
            this.errorMessageLabel = new Label();

            this.SuspendLayout();

            // gameDetailsLabel
            this.gameDetailsLabel.AutoSize = true;
            this.gameDetailsLabel.Location = new System.Drawing.Point(30, 20);
            this.gameDetailsLabel.Size = new System.Drawing.Size(200, 20);
            this.gameDetailsLabel.Font = new System.Drawing.Font("Segoe UI", 10, System.Drawing.FontStyle.Bold);

            // seatsAvailableLabel
            this.seatsAvailableLabel.AutoSize = true;
            this.seatsAvailableLabel.Location = new System.Drawing.Point(30, 50);
            this.seatsAvailableLabel.Size = new System.Drawing.Size(140, 20);

            // nameLabel
            this.nameLabel.Text = "Your Name:";
            this.nameLabel.Location = new System.Drawing.Point(30, 90);
            this.nameLabel.Size = new System.Drawing.Size(100, 20);

            // clientNameTextBox
            this.clientNameTextBox.Location = new System.Drawing.Point(150, 90);
            this.clientNameTextBox.Size = new System.Drawing.Size(200, 23);

            // addressLabel
            this.addressLabel.Text = "Address:";
            this.addressLabel.Location = new System.Drawing.Point(30, 170);
            this.addressLabel.Size = new System.Drawing.Size(100, 20);

            // addressTextBox
            this.addressTextBox.Location = new System.Drawing.Point(150, 170);
            this.addressTextBox.Size = new System.Drawing.Size(200, 23);

            // seatCountLabel
            this.seatCountLabel.Text = "Number of Tickets:";
            this.seatCountLabel.Location = new System.Drawing.Point(30, 130);
            this.seatCountLabel.Size = new System.Drawing.Size(120, 20);

            // seatsTextBox
            this.seatsTextBox.Location = new System.Drawing.Point(150, 130);
            this.seatsTextBox.Size = new System.Drawing.Size(100, 23);

            // purchaseButton
            this.purchaseButton.Text = "Buy Tickets";
            this.purchaseButton.Location = new System.Drawing.Point(150, 210);
            this.purchaseButton.Size = new System.Drawing.Size(100, 30);
            this.purchaseButton.Click += new System.EventHandler(this.PurchaseButton_Click);

            // cancelButton
            this.cancelButton.Text = "Cancel";
            this.cancelButton.Location = new System.Drawing.Point(260, 210);
            this.cancelButton.Size = new System.Drawing.Size(100, 30);
            this.cancelButton.Click += new System.EventHandler(this.CancelButton_Click);

            // errorMessageLabel
            this.errorMessageLabel.AutoSize = true;
            this.errorMessageLabel.ForeColor = System.Drawing.Color.Red;
            this.errorMessageLabel.Location = new System.Drawing.Point(30, 250);
            this.errorMessageLabel.Size = new System.Drawing.Size(100, 20);

            // PurchaseForm
            this.ClientSize = new System.Drawing.Size(400, 300);
            this.Controls.Add(this.gameDetailsLabel);
            this.Controls.Add(this.seatsAvailableLabel);
            this.Controls.Add(this.nameLabel);
            this.Controls.Add(this.clientNameTextBox);
            this.Controls.Add(this.seatCountLabel);
            this.Controls.Add(this.seatsTextBox);
            this.Controls.Add(this.purchaseButton);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.addressLabel);
            this.Controls.Add(this.addressTextBox);
            this.Controls.Add(this.errorMessageLabel);
            this.Text = "Purchase Tickets";
            this.Load += new System.EventHandler(this.PurchaseForm_Load);

            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
