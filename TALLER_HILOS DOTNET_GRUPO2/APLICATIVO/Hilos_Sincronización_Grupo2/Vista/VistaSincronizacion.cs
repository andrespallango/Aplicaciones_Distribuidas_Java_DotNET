using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Grupo01_Sincronizacion_MVC.Vista
{
    public partial class VistaSincronizacion : Form
    {
        public VistaSincronizacion()
        {
            InitializeComponent();
            CheckForIllegalCrossThreadCalls = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void VistaSincronizacion_Load(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        public void escribirHilos(String message)
        {
            txtMessage.AppendText(message + '\n');
        }
        public void run()
        {
            this.Show();
        }
    }
}
