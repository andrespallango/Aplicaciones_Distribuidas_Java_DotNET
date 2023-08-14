using Pelota_Hilos_Dotnet_Grupo2.Modelo;
using Pelota_Hilos_Dotnet_Grupo2.Vista;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Drawing;

namespace Pelota_Hilos_Dotnet_Grupo2.Controlador
{
    internal class ControladorPelota
    {
        private Pelota pelota1 = new Pelota("pelota1", 50, 15);
        private Pelota pelota2 = new Pelota("pelota1", 50, 115);
        private Pelota pelota3 = new Pelota("pelota1", 50, 215);
        private PelotaVista vista;

        public ControladorPelota(PelotaVista vista)
        {
            this.vista = vista;
            this.vista.GetLamina().Controls.Add(this.pelota1);
            this.vista.GetLamina().Controls.Add(this.pelota2);
            this.vista.GetLamina().Controls.Add(this.pelota3);
            this.pelota1.DibujarPelota(Brushes.Yellow);
            this.pelota2.DibujarPelota(Brushes.Blue);
            this.pelota3.DibujarPelota(Brushes.Red);
            this.vista.GetButton("btnPelota1").Click += delegate (object sender, EventArgs e)
              {
                  this.pelota1.lanzar_pelota();
              };
            this.vista.GetButton("btnPelota2").Click += delegate (object sender, EventArgs e)
            {
                this.pelota2.lanzar_pelota();

            };
            this.vista.GetButton("btnPelota3").Click += delegate (object sender, EventArgs e)
            {
                this.pelota3.lanzar_pelota();
            };
            this.vista.GetButton("btnDetener1").Click += delegate (object sender, EventArgs e)
            {
                this.pelota1.Pause();
            };
            this.vista.GetButton("btnDetener2").Click += delegate (object sender, EventArgs e)
            {
                this.pelota2.Pause();

            };
            this.vista.GetButton("btnDetener3").Click += delegate (object sender, EventArgs e)
            {
                this.pelota3.Pause();
            };
        }

        public void iniciar_vista()
        {
            this.vista.run();
        }

    }
}
