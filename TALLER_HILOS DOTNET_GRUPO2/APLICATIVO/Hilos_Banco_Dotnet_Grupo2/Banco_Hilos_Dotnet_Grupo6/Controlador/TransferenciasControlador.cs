using Banco_Hilos_Dotnet_Grupo2.Modelo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Banco_Hilos_Dotnet_Grupo2.Controlador
{
    public class TransferenciasControlador
    {
        Banco banco = new Banco();
        BancoControlador controladorB = new BancoControlador();
        VistaBanco vista = new VistaBanco();

        public int deLaCuenta;
        public double cantidadMax;

        public TransferenciasControlador(Banco b, BancoControlador cB, int de, double max, VistaBanco vista)
        {
            this.banco = b;
            this.controladorB = cB;
            this.deLaCuenta = de;
            this.cantidadMax = max;
            this.vista = vista;
        }

        public void run()
        {
            while (true)
            {
                Random random = new Random();

                int paraLaCuenta = (int)(random.Next(1, 99));
                double cantidad = cantidadMax * (random.NextDouble());
                controladorB.transferirEntreCuentas(deLaCuenta, paraLaCuenta, cantidad, banco, this.vista);

                try
                {
                    Thread.Sleep(900);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }
    }
}
