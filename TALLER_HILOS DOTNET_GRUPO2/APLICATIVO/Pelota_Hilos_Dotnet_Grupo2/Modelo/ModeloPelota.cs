using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Pelota_Hilos_Dotnet_Grupo2.Modelo
{
    internal class Pelota : PictureBox
    {
        private int TAMX = 645;
        private int TAMY = 291;
        private int dx = 2;
        private int dy = 2;
        private int x,y=0;
        private bool paused = false;
        private bool end=false;
        private Thread thread;
        private Graphics p;
        public Pelota(string name,int x, int y)
        {
            //this.CreateGraphics = System.Drawing.Graphics;
            this.BackColor = System.Drawing.Color.Transparent;
            this.Location = new System.Drawing.Point(x, y);
            this.Name = name;
            this.Size = new System.Drawing.Size(20, 20);
            this.ResizeRedraw = true;
            this.p=this.CreateGraphics();
            this.thread = new Thread(this.mueve_pelota);
        }
        public void DibujarPelota(Brush color)
        {
            Image bmp = new Bitmap(20, 20);
            using (Graphics g = Graphics.FromImage(bmp))
            {
                g.FillEllipse(color, 0, 0, 20, 20);
            }
            this.Image = bmp;
        }
        public void Pause()
        {
            lock (this)
            {
                this.paused = true;
            }
        }

        public void lanzar_pelota()
        {
            if (this.thread.ThreadState == ThreadState.Unstarted)
            {
                this.thread.IsBackground = true;
                this.thread.Start();
            }
            else
            {
                lock (this)
                {
                    this.paused = false;
                    Monitor.PulseAll(this);
                }
            }

        }

        public void finalizar()
        {
            lock (this)
            {
                this.end = true;
                Monitor.PulseAll(this);
            }
        }
        public void mueve_pelota()
        {

            while (true)
            {
                x = this.Location.X + dx;
                y = this.Location.Y + dy;
                this.Location = new System.Drawing.Point(x, y);
                Thread.Sleep(1);
                lock (this)
                {
                    if (this.paused) Monitor.Wait(this);
                }
                if (this.Location.X > this.TAMX-10)
                {
                    dx = -dx;
                }
                else if (this.Location.X < 10)
                {
                    dx = -dx;
                }
                    
                if (this.Location.Y > this.TAMY-10)
                {

                    dy = -dy;
                }
                else if (this.Location.Y < 10)
                {

                    dy = -dy;
                }
            }
        }
    }
}
