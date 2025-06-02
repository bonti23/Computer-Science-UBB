using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Muzica
{
    public partial class Form1: Form
    {
        readonly string connectionString = "Data Source=ALEXANDRA\\SQLEXPRESS;Initial Catalog=Muzica; Integrated Security=true";
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        public Form1()
        {
            InitializeComponent();
            dataGridView1.SelectionChanged += dataGridView1_SelectionChanged;
            this.buttonAdauga.Click += new System.EventHandler(this.buttonAdauga_Click);
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);

        }

        private void button1_Click(object sender, EventArgs e)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Artisti", cs);
                    da.SelectCommand = cmd;
                    ds.Clear();
                    da.Fill(ds, "Artisti");
                    dataGridView1.DataSource = ds.Tables["Artisti"];
                    dataGridView1.Refresh();
                }
                catch(Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
                finally
                {
                    cs.Close();
                }
            }
        }
        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                int codArtist = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_artist"].Value);
                AfiseazaMelodii(codArtist);
            }
        }
        private void AfiseazaMelodii(int codArtist)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Melodii WHERE cod_artist = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codArtist);
                    SqlDataAdapter da2 = new SqlDataAdapter(cmd);
                    DataSet ds2 = new DataSet();
                    da2.Fill(ds2, "Melodii");

                    dataGridView2.DataSource = ds2.Tables["Melodii"];
                    dataGridView2.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Exceptie: " + ex.Message);
                }
                finally
                {
                    cs.Close();
                }
            }
        }
        private void buttonAdauga_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează un artist.");
                return;
            }

            int codArtist = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_artist"].Value);
            string titlu = textBox1.Text.Trim();

            if (string.IsNullOrEmpty(titlu))
            {
                MessageBox.Show("Titlul nu poate fi gol.");
                return;
            }

            if (!int.TryParse(textBox2.Text, out int anLansare))
            {
                MessageBox.Show("Anul lansării nu este valid.");
                return;
            }

            if (!TimeSpan.TryParse(textBox3.Text, out TimeSpan durata))
            {
                MessageBox.Show("Durata trebuie să fie în format hh:mm:ss.");
                return;
            }

            try
            {
                using (SqlConnection cs = new SqlConnection(connectionString))
                {
                    cs.Open();

                    SqlCommand cmd = new SqlCommand(
                        "INSERT INTO Melodii (titlu, an_lansare, durata, cod_artist) VALUES (@titlu, @an, @durata, @cod)",
                        cs);

                    cmd.Parameters.AddWithValue("@titlu", titlu);
                    cmd.Parameters.AddWithValue("@an", anLansare);
                    cmd.Parameters.AddWithValue("@durata", durata);
                    cmd.Parameters.AddWithValue("@cod", codArtist);

                    int rows = cmd.ExecuteNonQuery();

                    MessageBox.Show(rows > 0 ? "Melodie adăugată cu succes." : "Nu s-a adăugat nicio melodie.");

                    AfiseazaMelodii(codArtist);
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
                MessageBox.Show("Selectează o melodie.");
                return;
            }

            int codMelodie = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_melodie"].Value);
            string titlu = textBox1.Text.Trim();

            if (!int.TryParse(textBox2.Text, out int anLansare))
            {
                MessageBox.Show("Anul lansării nu este valid.");
                return;
            }

            if (!TimeSpan.TryParse(textBox3.Text, out TimeSpan durata))
            {
                MessageBox.Show("Durata trebuie să fie în format hh:mm:ss.");
                return;
            }

            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand(
                        "UPDATE Melodii SET titlu = @titlu, an_lansare = @an, durata = @durata WHERE cod_melodie = @cod", cs);
                    cmd.Parameters.AddWithValue("@titlu", titlu);
                    cmd.Parameters.AddWithValue("@an", anLansare);
                    cmd.Parameters.AddWithValue("@durata", durata);
                    cmd.Parameters.AddWithValue("@cod", codMelodie);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Melodie actualizată.");

                    int codArtist = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_artist"].Value);
                    AfiseazaMelodii(codArtist); // trebuie să ai această funcție
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
                MessageBox.Show("Selectează o melodie.");
                return;
            }

            int codMelodie = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_melodie"].Value);
            DialogResult confirm = MessageBox.Show("Ești sigur că vrei să ștergi această melodie?", "Confirmare", MessageBoxButtons.YesNo);

            if (confirm != DialogResult.Yes)
                return;

            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("DELETE FROM Melodii WHERE cod_melodie = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codMelodie);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Melodie ștearsă.");

                    int codArtist = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_artist"].Value);
                    AfiseazaMelodii(codArtist); // asigură-te că există
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
