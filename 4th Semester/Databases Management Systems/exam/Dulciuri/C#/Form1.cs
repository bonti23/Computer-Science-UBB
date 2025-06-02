using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace Dulciuri
{
    public partial class Form1 : Form
    {
        readonly string connectionString = "Data Source=ALEXANDRA\\SQLEXPRESS;Initial Catalog=Dulciuri; Integrated Security=true";
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();

        public Form1()
        {
            InitializeComponent();
            dataGridView1.SelectionChanged += dataGridView1_SelectionChanged;
            button1.Click += button1_Click;
            buttonAdauga.Click += buttonAdauga_Click;
            buttonUpdate.Click += buttonUpdate_Click;
            buttonDelete.Click += buttonDelete_Click;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Producatori", cs);
                    da.SelectCommand = cmd;
                    ds.Clear();
                    da.Fill(ds, "Producatori");
                    dataGridView1.DataSource = ds.Tables["Producatori"];
                    dataGridView1.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
            }
        }

        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                int codProducator = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_p"].Value);
                AfiseazaBiscuiti(codProducator);
            }
        }

        private void AfiseazaBiscuiti(int codProducator)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Biscuiti WHERE cod_p = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codProducator);
                    SqlDataAdapter da2 = new SqlDataAdapter(cmd);
                    DataSet ds2 = new DataSet();
                    da2.Fill(ds2, "Biscuiti");

                    dataGridView2.DataSource = ds2.Tables["Biscuiti"];
                    dataGridView2.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Exceptie: " + ex.Message);
                }
            }
        }

        private void buttonAdauga_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează un producător.");
                return;
            }

            int codProducator = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_p"].Value);
            string nume_b = textBox1.Text.Trim();

            if (string.IsNullOrEmpty(nume_b))
            {
                MessageBox.Show("Numele nu poate fi gol.");
                return;
            }

            if (!int.TryParse(textBox2.Text, out int nr_calorii))
            {
                MessageBox.Show("Numărul caloriilor nu este valid.");
                return;
            }

            if (!float.TryParse(textBox3.Text, out float pret))
            {
                MessageBox.Show("Prețul este invalid.");
                return;
            }

            try
            {
                using (SqlConnection cs = new SqlConnection(connectionString))
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand(
                        "INSERT INTO Biscuiti (nume_b, nr_calorii, pret, cod_p) VALUES (@nume_b, @nr_calorii, @pret, @cod_p)",
                        cs);

                    cmd.Parameters.AddWithValue("@nume_b", nume_b);
                    cmd.Parameters.AddWithValue("@nr_calorii", nr_calorii);
                    cmd.Parameters.AddWithValue("@pret", pret);
                    cmd.Parameters.AddWithValue("@cod_p", codProducator);

                    int rows = cmd.ExecuteNonQuery();

                    MessageBox.Show(rows > 0 ? "Biscuiți adăugați cu succes." : "Nu s-a adăugat biscuitul.");
                    AfiseazaBiscuiti(codProducator);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: " + ex.Message);
            }
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează biscuiții.");
                return;
            }

            int codBiscuiti = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_b"].Value);
            string nume_b = textBox1.Text.Trim();

            if (string.IsNullOrEmpty(nume_b))
            {
                MessageBox.Show("Numele nu poate fi gol.");
                return;
            }

            if (!int.TryParse(textBox2.Text, out int nr_calorii))
            {
                MessageBox.Show("Numărul caloriilor nu este valid.");
                return;
            }

            if (!float.TryParse(textBox3.Text, out float pret))
            {
                MessageBox.Show("Prețul este invalid.");
                return;
            }

            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand(
                        "UPDATE Biscuiti SET nume_b = @nume_b, nr_calorii = @nr_calorii, pret = @pret WHERE cod_b = @cod_b", cs);

                    cmd.Parameters.AddWithValue("@nume_b", nume_b);
                    cmd.Parameters.AddWithValue("@nr_calorii", nr_calorii);
                    cmd.Parameters.AddWithValue("@pret", pret);
                    cmd.Parameters.AddWithValue("@cod_b", codBiscuiti);

                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Biscuiți actualizați.");
                    int codProducator = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_p"].Value);
                    AfiseazaBiscuiti(codProducator);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează biscuiții.");
                return;
            }

            int codBiscuiti = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_b"].Value);
            DialogResult confirm = MessageBox.Show("Ești sigur că vrei să ștergi acest biscuit?", "Confirmare", MessageBoxButtons.YesNo);

            if (confirm != DialogResult.Yes)
                return;

            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("DELETE FROM Biscuiti WHERE cod_b = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codBiscuiti);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Biscuit șters.");

                    int codProducator = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_p"].Value);
                    AfiseazaBiscuiti(codProducator);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {
            // poți lăsa gol sau șterge dacă nu e folosit
        }
    }
}
