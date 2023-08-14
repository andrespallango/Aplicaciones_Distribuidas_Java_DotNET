using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Banco_Hilos_Dotnet_Grupo2.Modelo
{
    public class Banco
    {
        public double[] cuentas;

        public Banco()
        {
            this.cuentas = new double[100];
        }
    }
}
