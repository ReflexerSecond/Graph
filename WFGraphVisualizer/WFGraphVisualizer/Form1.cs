using System;
using System.Windows.Forms;

namespace WFGraphVisualizer
{
	public partial class Граф : Form
	{
		public Граф(String[] args)
		{
			InitializeComponent();
			GraphShower.ShowGraph(this,args);

		}

	}
}
