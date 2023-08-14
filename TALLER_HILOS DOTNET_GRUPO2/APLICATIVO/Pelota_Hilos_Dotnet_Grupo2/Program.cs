using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Pelota_Hilos_Dotnet_Grupo2.Vista;
using Pelota_Hilos_Dotnet_Grupo2.Controlador;

namespace Pelota_Hilos_Dotnet_Grupo2
{
    static class Program
    {
        /// <summary>
        /// Punto de entrada principal para la aplicación.
        /// </summary>
        [STAThread]
        static void Main()
        {
            try
            {
                PelotaVista vista = new PelotaVista();
                ControladorPelota controlador = new ControladorPelota(vista);
                controlador.iniciar_vista();
                Application.Run();
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
            }
        }
    }
}
