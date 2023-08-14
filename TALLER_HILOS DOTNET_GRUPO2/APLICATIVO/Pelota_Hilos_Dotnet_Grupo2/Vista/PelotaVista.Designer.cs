using System.Windows.Forms;

namespace Pelota_Hilos_Dotnet_Grupo2.Vista
{
    partial class PelotaVista
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lamina = new System.Windows.Forms.Panel();
            this.btnPelota1 = new System.Windows.Forms.Button();
            this.btnPelota2 = new System.Windows.Forms.Button();
            this.btnPelota3 = new System.Windows.Forms.Button();
            this.btnDetener1 = new System.Windows.Forms.Button();
            this.btnDetener2 = new System.Windows.Forms.Button();
            this.btnDetener3 = new System.Windows.Forms.Button();
            this.btnSalir = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lamina
            // 
            this.lamina.BackColor = System.Drawing.Color.White;
            this.lamina.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.lamina.Location = new System.Drawing.Point(157, 12);
            this.lamina.Name = "lamina";
            this.lamina.Size = new System.Drawing.Size(665, 301);
            this.lamina.TabIndex = 0;
            // 
            // btnPelota1
            // 
            this.btnPelota1.AutoSize = true;
            this.btnPelota1.BackColor = System.Drawing.Color.Gold;
            this.btnPelota1.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnPelota1.ForeColor = System.Drawing.SystemColors.ButtonFace;
            this.btnPelota1.Location = new System.Drawing.Point(27, 38);
            this.btnPelota1.Name = "btnPelota1";
            this.btnPelota1.Size = new System.Drawing.Size(87, 33);
            this.btnPelota1.TabIndex = 1;
            this.btnPelota1.Text = "Pelota 1";
            this.btnPelota1.UseVisualStyleBackColor = false;
            this.btnPelota1.Click += new System.EventHandler(this.btnPelota1_Click);
            // 
            // btnPelota2
            // 
            this.btnPelota2.AutoSize = true;
            this.btnPelota2.BackColor = System.Drawing.Color.Blue;
            this.btnPelota2.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnPelota2.ForeColor = System.Drawing.Color.White;
            this.btnPelota2.Location = new System.Drawing.Point(27, 102);
            this.btnPelota2.Name = "btnPelota2";
            this.btnPelota2.Size = new System.Drawing.Size(87, 33);
            this.btnPelota2.TabIndex = 2;
            this.btnPelota2.Text = "Pelota 2";
            this.btnPelota2.UseVisualStyleBackColor = false;
            this.btnPelota2.Click += new System.EventHandler(this.btnPelota2_Click);
            // 
            // btnPelota3
            // 
            this.btnPelota3.AutoSize = true;
            this.btnPelota3.BackColor = System.Drawing.Color.Red;
            this.btnPelota3.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnPelota3.ForeColor = System.Drawing.Color.White;
            this.btnPelota3.Location = new System.Drawing.Point(27, 171);
            this.btnPelota3.Name = "btnPelota3";
            this.btnPelota3.Size = new System.Drawing.Size(88, 33);
            this.btnPelota3.TabIndex = 3;
            this.btnPelota3.Text = "Pelota 3";
            this.btnPelota3.UseVisualStyleBackColor = false;
            this.btnPelota3.Click += new System.EventHandler(this.btnPelota3_Click);
            // 
            // btnDetener1
            // 
            this.btnDetener1.AutoSize = true;
            this.btnDetener1.BackColor = System.Drawing.Color.Gold;
            this.btnDetener1.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnDetener1.ForeColor = System.Drawing.Color.White;
            this.btnDetener1.Location = new System.Drawing.Point(838, 38);
            this.btnDetener1.Name = "btnDetener1";
            this.btnDetener1.Size = new System.Drawing.Size(99, 33);
            this.btnDetener1.TabIndex = 4;
            this.btnDetener1.Text = "Detener 1";
            this.btnDetener1.UseVisualStyleBackColor = false;
            // 
            // btnDetener2
            // 
            this.btnDetener2.AutoSize = true;
            this.btnDetener2.BackColor = System.Drawing.Color.Blue;
            this.btnDetener2.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnDetener2.ForeColor = System.Drawing.Color.White;
            this.btnDetener2.Location = new System.Drawing.Point(838, 102);
            this.btnDetener2.Name = "btnDetener2";
            this.btnDetener2.Size = new System.Drawing.Size(99, 33);
            this.btnDetener2.TabIndex = 5;
            this.btnDetener2.Text = "Detener 2";
            this.btnDetener2.UseVisualStyleBackColor = false;
            // 
            // btnDetener3
            // 
            this.btnDetener3.AutoSize = true;
            this.btnDetener3.BackColor = System.Drawing.Color.Red;
            this.btnDetener3.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnDetener3.ForeColor = System.Drawing.Color.White;
            this.btnDetener3.Location = new System.Drawing.Point(838, 171);
            this.btnDetener3.Name = "btnDetener3";
            this.btnDetener3.Size = new System.Drawing.Size(99, 33);
            this.btnDetener3.TabIndex = 6;
            this.btnDetener3.Text = "Detener 3";
            this.btnDetener3.UseVisualStyleBackColor = false;
            // 
            // btnSalir
            // 
            this.btnSalir.BackColor = System.Drawing.Color.DarkGreen;
            this.btnSalir.Font = new System.Drawing.Font("Microsoft YaHei UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSalir.ForeColor = System.Drawing.Color.White;
            this.btnSalir.Location = new System.Drawing.Point(446, 319);
            this.btnSalir.Name = "btnSalir";
            this.btnSalir.Size = new System.Drawing.Size(85, 33);
            this.btnSalir.TabIndex = 0;
            this.btnSalir.Text = "Salir";
            this.btnSalir.UseVisualStyleBackColor = false;
            this.btnSalir.Click += new System.EventHandler(this.button1_Click);
            // 
            // PelotaVista
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Teal;
            this.ClientSize = new System.Drawing.Size(949, 390);
            this.Controls.Add(this.btnSalir);
            this.Controls.Add(this.btnDetener3);
            this.Controls.Add(this.btnDetener2);
            this.Controls.Add(this.btnDetener1);
            this.Controls.Add(this.btnPelota3);
            this.Controls.Add(this.btnPelota2);
            this.Controls.Add(this.btnPelota1);
            this.Controls.Add(this.lamina);
            this.Name = "PelotaVista";
            this.Text = "Pelotas";
            this.Load += new System.EventHandler(this.MarcoVista_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Panel lamina;
        private Button btnPelota1;
        private Button btnPelota2;
        private Button btnPelota3;
        private Button btnDetener1;
        private Button btnDetener2;
        private Button btnDetener3;
        private Button btnSalir;
    }
}