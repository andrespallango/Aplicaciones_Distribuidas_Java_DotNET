using Banco_Hilos_Dotnet_Grupo2.Modelo;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Banco_Hilos_Dotnet_Grupo2.Controlador
{
    public class BancoControlador
    {
        private object wait = new object();

        public BancoControlador()
        {
        }

        public void acreditarSaldoCuentas(Banco banco, double saldo)
        {
            double[] cuentasControlador = new double[100];
            for (int i = 0; i < cuentasControlador.Length; i++)
            {
                cuentasControlador[i] = saldo;
            }

            banco.cuentas = cuentasControlador;
        }

        public void transferirEntreCuentas(int cuentaOrigen, int cuentaDestino, double cantidad, Banco banco, VistaBanco vista)
        {
            lock (wait)
            {
                try
                {
                    while (banco.cuentas[cuentaOrigen] < cantidad)
                    {
                        Monitor.Wait(wait);
                    }

                    banco.cuentas[cuentaOrigen] -= cantidad;
                    banco.cuentas[cuentaDestino] += cantidad;
                    imprimirTransaccion(cuentaOrigen, cuentaDestino, cantidad, banco, getSaldoTotal(banco), Thread.CurrentThread.ManagedThreadId, vista);
                    
                    Monitor.PulseAll(wait);
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
        }

        public double getSaldoTotal(Banco modelo)
        {
            double suma_cuentas = 0;

            foreach (double a in modelo.cuentas)
            {
                suma_cuentas += a;
            }
            return suma_cuentas;
        }
        public void imprimirTransaccion(int cuentaOrigen, int cuentaDestino, double cantidad, Banco bancoModelo, double totalBanco, int hilo, VistaBanco vista)
        {
            try
            {
                String mensaje = String.Format(Environment.NewLine) + String.Format(Environment.NewLine) + "Ejecutando el hilo : " + hilo + String.Format(Environment.NewLine) + "Cantidad: " + cantidad + " | De: " + cuentaOrigen + " para: " + cuentaDestino + String.Format(Environment.NewLine) + "Saldo total: " + totalBanco;

                vista.txtCampo.Invoke((MethodInvoker)delegate
                {
                    int start = vista.txtCampo.Text.Length;
                    vista.txtCampo.AppendText(mensaje);
                    vista.txtCampo.SelectionStart = start;
                    vista.txtCampo.SelectionLength = mensaje.Length;
                    vista.txtCampo.ScrollToCaret();
                });

                Console.WriteLine(mensaje);
            }
            catch (Exception ex) 
            {
                Console.WriteLine(ex.Message);
            }
        }
    }
}
