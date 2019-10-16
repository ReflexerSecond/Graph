namespace WFGraphVisualizer
{
	partial class Граф
	{
		/// <summary>
		/// Обязательная переменная конструктора.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Освободить все используемые ресурсы.
		/// </summary>
		/// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Код, автоматически созданный конструктором форм Windows

		/// <summary>
		/// Требуемый метод для поддержки конструктора — не изменяйте 
		/// содержимое этого метода с помощью редактора кода.
		/// </summary>
		private void InitializeComponent()
		{
			this.shuffle = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// shuffle
			// 
			this.shuffle.Location = new System.Drawing.Point(690, 12);
			this.shuffle.Name = "shuffle";
			this.shuffle.Size = new System.Drawing.Size(82, 23);
			this.shuffle.TabIndex = 0;
			this.shuffle.Text = "Перемешать";
			this.shuffle.UseVisualStyleBackColor = true;
			// 
			// Граф
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(784, 411);
			this.Controls.Add(this.shuffle);
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "Граф";
			this.ShowIcon = false;
			this.Text = "Form1";
			this.ResumeLayout(false);

		}

		#endregion

		public System.Windows.Forms.Button shuffle;
	}
}

