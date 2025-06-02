using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace Cofetarie
{
    public partial class Form1 : Form
    {
        readonly string connectionString = "Data Source=ALEXANDRA\\SQLEXPRESS;Initial Catalog=Cofetarie; Integrated Security=true";
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();

        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {

                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Cofetarii", cs);
                    da.SelectCommand = cmd;
                    ds.Clear();
                    da.Fill(ds, "Cofetarii");
                    dataGridView1.DataSource = ds.Tables["Cofetarii"];
                    dataGridView1.Refresh();


                }
                catch (Exception ex)
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
                int codCofetarie = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_cofetarie"].Value);
                AfiseazaBriose(codCofetarie);
            }
        }

        private void AfiseazaBriose(int codCofetarie)
        {
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM Briose WHERE cod_cofetarie = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codCofetarie);
                    SqlDataAdapter da2 = new SqlDataAdapter(cmd);
                    DataSet ds2 = new DataSet();
                    da2.Fill(ds2, "Briose");

                    dataGridView2.DataSource = ds2.Tables["Briose"];
                    dataGridView2.Refresh();
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
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
                MessageBox.Show("Selectează o cofetărie.");
                return;
            }

            int codCofetarie = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_cofetarie"].Value);
            string nume = textBox1.Text;
            string descriere = textBox2.Text;
            if (!double.TryParse(textBox3.Text, out double pret))
            {
                MessageBox.Show("Preț invalid.");
                return;
            }
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("INSERT INTO Briose (nume_briosa, descriere, pret, cod_cofetarie) VALUES (@nume, @descriere, @pret, @cod)", cs);
                    cmd.Parameters.AddWithValue("@nume", nume);
                    cmd.Parameters.AddWithValue("@descriere", descriere);
                    cmd.Parameters.AddWithValue("@pret", pret);
                    cmd.Parameters.AddWithValue("@cod", codCofetarie);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Brioșă adăugată.");
                    AfiseazaBriose(codCofetarie);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
                finally
                {
                    cs.Close();
                }
            }
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează o brioșă.");
                return;
            }

            int codBriosa = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_briosa"].Value);
            string nume = textBox1.Text;
            string descriere = textBox2.Text;
            if (!double.TryParse(textBox3.Text, out double pret))
            {
                MessageBox.Show("Preț invalid.");
                return;
            }
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("UPDATE Briose SET nume_briosa = @nume, descriere = @desc, pret = @pret WHERE cod_briosa = @cod", cs);
                    cmd.Parameters.AddWithValue("@nume", nume);
                    cmd.Parameters.AddWithValue("@desc", descriere);
                    cmd.Parameters.AddWithValue("@pret", pret);
                    cmd.Parameters.AddWithValue("@cod", codBriosa);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Brioșă actualizată.");
                    int codCofetarie = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_cofetarie"].Value);
                    AfiseazaBriose(codCofetarie);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
                finally
                {
                    cs.Close();
                }
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count == 0)
            {
                MessageBox.Show("Selectează o brioșă.");
                return;
            }

            int codBriosa = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["cod_briosa"].Value);
            DialogResult confirm = MessageBox.Show("Ești sigur că vrei să ștergi această brioșă?", "Confirmare", MessageBoxButtons.YesNo);
            if (confirm != DialogResult.Yes)
                return;
            using (SqlConnection cs = new SqlConnection(connectionString))
            {
                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("DELETE FROM Briose WHERE cod_briosa = @cod", cs);
                    cmd.Parameters.AddWithValue("@cod", codBriosa);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Brioșă ștearsă.");
                    int codCofetarie = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["cod_cofetarie"].Value);
                    AfiseazaBriose(codCofetarie);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
                finally
                {
                    cs.Close();
                }
            }
        }
    }
}
