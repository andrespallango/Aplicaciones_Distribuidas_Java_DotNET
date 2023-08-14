using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Grupo01_Sincronizacion_MVC.Modelo;
using Grupo01_Sincronizacion_MVC.Vista;

namespace Grupo01_Sincronizacion_MVC.Controlador
{
    class ControladorHilos
    {
        VistaSincronizacion vista = new VistaSincronizacion();

        public ControladorHilos(VistaSincronizacion vista)
        {
            this.vista = vista;
        }

        public void ExecuteThread()
        {
            for (int i = 0; i < 10; i++)
            {
                if (Thread.CurrentThread.Name == "Hilo2" &&
                    !ModeloHilo1.hilo1.ThreadState.Equals(System.Diagnostics.ThreadState.Terminated))
                {
                    ModeloHilo1.hilo1.Join();

                }

                Thread.Sleep(700);
                
                this.escribirPantalla("Ejecutando:" + Thread.CurrentThread.Name);
            }
            
            this.escribirPantalla("\n Ejecución finalizada : " + Thread.CurrentThread.Name);
        }

        public void escribirPantalla(String message)
        {
            vista.escribirHilos(message);
        }
        public void iniciar_vista()
        {
            this.vista.run();
        }

    }
}
