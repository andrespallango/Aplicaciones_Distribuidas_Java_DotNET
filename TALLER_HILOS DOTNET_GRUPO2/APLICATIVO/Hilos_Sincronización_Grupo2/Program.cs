using System;
using System.Threading;
using Grupo01_Sincronizacion_MVC.Controlador;
using Grupo01_Sincronizacion_MVC.Modelo;
using Grupo01_Sincronizacion_MVC.Vista;
namespace Grupo01_Sincronizacion_MVC
{
    static class Program
    {
        /// <summary>
        /// Punto de entrada principal para la aplicación.
        /// </summary>
        [STAThread]
        static void Main()
        {
            VistaSincronizacion vista = new VistaSincronizacion();
            ControladorHilos controller = new ControladorHilos(vista);
            controller.iniciar_vista();

            ModeloHilo1.hilo1 = new Thread(controller.ExecuteThread)
            {
                Name = "Hilo1"
            };
            ModeloHilo1.hilo1.Start();

            ModeloHilo2.hilo2 = new Thread(controller.ExecuteThread)
            {
                Name = "Hilo2"
            };
            ModeloHilo2.hilo2.Start();
        }
    }
}
