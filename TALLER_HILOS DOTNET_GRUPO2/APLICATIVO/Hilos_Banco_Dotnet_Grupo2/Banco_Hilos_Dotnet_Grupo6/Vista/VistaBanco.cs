using Banco_Hilos_Dotnet_Grupo2.Controlador;
using Banco_Hilos_Dotnet_Grupo2.Modelo;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Banco_Hilos_Dotnet_Grupo2
{
    public partial class VistaBanco : Form
    {
        public TextBox txtCampo { get; set; }

        public VistaBanco()
        {
            InitializeComponent();
            txtCampo = txtConsola;
            btnPausar.Enabled = false;
        }

        private void btnEmpezar_Click(object sender, EventArgs e)
        {
            Banco b = new Banco();
            BancoControlador cB = new BancoControlador();

            cB.acreditarSaldoCuentas(b, 2000);

            for (int i = 0; i < 100; i++)
            {
                TransferenciasControlador r = new TransferenciasControlador(b, cB, i, 2000, this);
                Thread t = new Thread(r.run);
                t.Start();
            }
            btnEmpezar.Enabled = false;
            btnPausar.Enabled = true;
        }

        private void btnSalir_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void btnPausar_Click(object sender, EventArgs e)
        {
            Thread.Sleep(7000);
        }

        private void VistaBanco_Load(object sender, EventArgs e)
        {

        }
    }
}
