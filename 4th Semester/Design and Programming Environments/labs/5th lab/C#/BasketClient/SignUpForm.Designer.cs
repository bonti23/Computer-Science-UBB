using System.ComponentModel;
using System.Windows.Forms;

namespace BasketClient
{
    partial class SignUpForm
    {
        private System.ComponentModel.IContainer components = null;
        private TextBox nameTextBox;
        private TextBox usernameTextBox;
        private TextBox passwordTextBox;
        private Label errorMessageLabel;
        private Button signUpButton;
        private Label signUpLabel;
        private Button cancelButton;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.nameTextBox = new TextBox();
            this.usernameTextBox = new TextBox();
            this.passwordTextBox = new TextBox();
            this.errorMessageLabel = new Label();
            this.signUpButton = new Button();
            this.signUpLabel = new Label();
            this.cancelButton = new Button();
            this.SuspendLayout();

            // nameTextBox
            this.nameTextBox.Location = new System.Drawing.Point(150, 50);
            this.nameTextBox.Name = "nameTextBox";
            this.nameTextBox.Size = new System.Drawing.Size(200, 20);
            this.nameTextBox.TextChanged += new EventHandler(this.Fields_TextChanged);

            // usernameTextBox
            this.usernameTextBox.Location = new System.Drawing.Point(150, 90);
            this.usernameTextBox.Name = "usernameTextBox";
            this.usernameTextBox.Size = new System.Drawing.Size(200, 20);
            this.usernameTextBox.TextChanged += new EventHandler(this.Fields_TextChanged);

            // passwordTextBox
            this.passwordTextBox.Location = new System.Drawing.Point(150, 130);
            this.passwordTextBox.Name = "passwordTextBox";
            this.passwordTextBox.Size = new System.Drawing.Size(200, 20);
            this.passwordTextBox.UseSystemPasswordChar = true;
            this.passwordTextBox.TextChanged += new EventHandler(this.Fields_TextChanged);

            // errorMessageLabel
            this.errorMessageLabel.AutoSize = true;
            this.errorMessageLabel.ForeColor = System.Drawing.Color.Red;
            this.errorMessageLabel.Location = new System.Drawing.Point(150, 160);
            this.errorMessageLabel.Name = "errorMessageLabel";
            this.errorMessageLabel.Size = new System.Drawing.Size(0, 13);

            // signUpButton
            this.signUpButton.Location = new System.Drawing.Point(200, 200);
            this.signUpButton.Name = "signUpButton";
            this.signUpButton.Size = new System.Drawing.Size(100, 23);
            this.signUpButton.Text = "Sign Up";
            this.signUpButton.UseVisualStyleBackColor = true;
            this.signUpButton.Click += new EventHandler(this.SignUpButton_Click);
            this.signUpButton.Enabled = false;

            // signUpLabel
            this.signUpLabel.AutoSize = true;
            this.signUpLabel.Font = new System.Drawing.Font("Arial", 24F, System.Drawing.FontStyle.Bold);
            this.signUpLabel.Location = new System.Drawing.Point(150, 10);
            this.signUpLabel.Name = "signUpLabel";
            this.signUpLabel.Size = new System.Drawing.Size(120, 37);
            this.signUpLabel.Text = "Sign Up";

            // cancelButton
            this.cancelButton.Location = new System.Drawing.Point(200, 230);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(100, 23);
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new EventHandler(this.CancelButton_Click);

            // SignUpForm
            this.ClientSize = new System.Drawing.Size(400, 300);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.signUpLabel);
            this.Controls.Add(this.signUpButton);
            this.Controls.Add(this.errorMessageLabel);
            this.Controls.Add(this.passwordTextBox);
            this.Controls.Add(this.usernameTextBox);
            this.Controls.Add(this.nameTextBox);
            this.Name = "SignUpForm";
            this.Text = "Sign Up";
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
