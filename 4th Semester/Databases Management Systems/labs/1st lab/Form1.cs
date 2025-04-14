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
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace _1stLaboratory
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=ALEXANDRA\\SQLEXPRESS;Initial Catalog=CabinetStomatologic; Integrated Security=true");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                cs.Open();
                SqlCommand cmd = new SqlCommand("SELECT * FROM Medici", cs);
                da.SelectCommand = cmd;
                ds.Clear();
                da.Fill(ds, "Medici");
                dataGridView1.DataSource = ds.Tables["Medici"];
                dataGridView1.Refresh();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
            finally
            {
                cs.Close();
            }
        }
        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            foreach (DataGridViewColumn column in dataGridView1.Columns)
            {
                Console.WriteLine(column.Name);
            }

            if (dataGridView1.SelectedRows.Count > 0)
            {
                int idMedic = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["IDMedic"].Value);
                Console.WriteLine("IDMedic selectat: " + idMedic);
                AfiseazaAsistenti(idMedic);
            }
        }

        private void AfiseazaAsistenti(int idMedic)
        {
            //            this.dataGridView1.SelectionChanged += new System.EventHandler(this.dataGridView1_SelectionChanged);
            try
            {
                if (cs.State != ConnectionState.Closed)
                    cs.Close();
                cs.Open();
                SqlCommand cmd = new SqlCommand("SELECT * FROM Asistenti WHERE Medic = @Medic", cs);
                cmd.Parameters.AddWithValue("@Medic", idMedic);

                SqlDataAdapter da2 = new SqlDataAdapter(cmd);
                DataSet ds2 = new DataSet();
                da2.Fill(ds2, "Asistenti");

                dataGridView2.AutoGenerateColumns = true;
                dataGridView2.DataSource = ds2.Tables["Asistenti"];
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

        private void button2_Click(object sender, EventArgs e)
        {
            int idAsistent = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["IDAsistent"].Value);

            try
            {
                if (cs.State == ConnectionState.Open)
                {
                    cs.Close();
                }
                cs.Open();
                SqlCommand cmd = new SqlCommand("UPDATE Asistenti SET Nume = @Nume, Prenume = @Prenume, Telefon = @Telefon, Medic = @Medic WHERE IDAsistent = @ID", cs);
                cmd.Parameters.AddWithValue("@ID", idAsistent);
                cmd.Parameters.AddWithValue("@Nume", textBox1.Text);
                cmd.Parameters.AddWithValue("@Prenume", textBox2.Text);
                cmd.Parameters.AddWithValue("@Telefon", textBox3.Text);
                cmd.Parameters.AddWithValue("@Medic", textBox4.Text);

                int rowsAffected = cmd.ExecuteNonQuery();
                if (rowsAffected > 0)
                {
                    MessageBox.Show("Asistent actualizat cu succes!");
                    AfiseazaAsistenti(Convert.ToInt32(textBox4.Text)); // Reîncarcă lista asistenților
                }
                else
                {
                    MessageBox.Show("Eroare la actualizare!");
                }
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
   


        private void button3_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count > 0)
            {
                int idAsistent = Convert.ToInt32(dataGridView2.SelectedRows[0].Cells["IDAsistent"].Value);

                DialogResult result = MessageBox.Show("sigur vrei sa stergi acest asistent?", "Confirmare", MessageBoxButtons.YesNo);
                if (result == DialogResult.Yes)
                {
                    try
                    {
                        cs.Open();
                        SqlCommand cmd = new SqlCommand("DELETE FROM Asistenti WHERE IDAsistent = @ID", cs);
                        cmd.Parameters.AddWithValue("@ID", idAsistent);

                        int rowsAffected = cmd.ExecuteNonQuery();
                        if (rowsAffected > 0)
                        {
                            MessageBox.Show("Asistent sters cu succes!");
                            AfiseazaAsistenti(Convert.ToInt32(textBox4.Text)); // Reîncarcă lista
                        }
                        else
                        {
                            MessageBox.Show("Eroare la stergere!");
                        }
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

        private void button4_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                int idMedic = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells["IDMedic"].Value); // ID-ul medicului selectat

                string nume = textBox1.Text;
                string prenume = textBox2.Text;
                string telefon = textBox3.Text;
                string medic = textBox4.Text;

                if (string.IsNullOrEmpty(nume) || string.IsNullOrEmpty(prenume) || string.IsNullOrEmpty(telefon) || string.IsNullOrEmpty(medic))
                {
                    MessageBox.Show("Te rog completeaza toate campurile.");
                    return;
                }

                try
                {
                    cs.Open();
                    SqlCommand cmd = new SqlCommand("INSERT INTO Asistenti (Nume, Prenume, Telefon, Medic) VALUES (@Nume, @Prenume, @Telefon, @Medic)", cs);
                    cmd.Parameters.AddWithValue("@Nume", nume);
                    cmd.Parameters.AddWithValue("@Prenume", prenume);
                    cmd.Parameters.AddWithValue("@Telefon", telefon);
                    cmd.Parameters.AddWithValue("@Medic", medic);

                    cmd.ExecuteNonQuery();
                    cs.Close();

                    MessageBox.Show("Asistent adaugat cu succes!");

                    AfiseazaAsistenti(idMedic);
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare: " + ex.Message);
                }
            }
            else
            {
                MessageBox.Show("Te rog selecteaza un medic pentru a adauga un asistent.");
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}

