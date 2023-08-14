using Pelota_Hilos_Dotnet_Grupo2.Modelo;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Pelota_Hilos_Dotnet_Grupo2.Vista;

namespace Pelota_Hilos_Dotnet_Grupo2.Vista
{
    public partial class PelotaVista : Form
    {
        public PelotaVista()
        {
            InitializeComponent();
        }
        public Panel GetLamina()
        {
            return this.lamina;
        }
        public Control GetButton(String name)
        {
            return this.Controls[name];
        }
        public void run()
        {
            this.Show();
        }

        private void MarcoVista_Load(object sender, EventArgs e)
        {
            CheckForIllegalCrossThreadCalls = false;
        }

        private void btnPelota1_Click(object sender, EventArgs e)
        {
            Button btn = (Button)sender;
            btn.BackColor = Color.Gold;

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void btnPelota2_Click(object sender, EventArgs e)
        {
            Button btn = (Button)sender;
            btn.BackColor = Color.Blue;
        }

        private void btnPelota3_Click(object sender, EventArgs e)
        {
            Button btn = (Button)sender;
            btn.BackColor = Color.Red;
        }
    }
}
