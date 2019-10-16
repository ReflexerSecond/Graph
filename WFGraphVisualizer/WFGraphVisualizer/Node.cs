using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

public class Node : PictureBox
{
	public Dictionary<Node, String> edges = new Dictionary<Node, String>();
	public static Pen pen = new Pen(Color.LightSlateGray, 3);
	public static Timer timer = new Timer();
	public static Graphics z;

	public Node(PictureBox Backgr) : base()
	{
		z = Graphics.FromImage(Backgr.Image);
		timer.Interval = 10;
		timer.Start();
		Point MouseDownLocation = new Point();
		MouseDown += (sender, e) =>
		{
			if (e.Button == MouseButtons.Left)
			{
				MouseDownLocation = e.Location;
			}
		};
		MouseMove += (sender, e) =>
		{
			if (e.Button == MouseButtons.Left)
			{
				Left = e.X + Left - MouseDownLocation.X;
				Top = e.Y + Top - MouseDownLocation.Y;
				Redraw(Backgr);
			};
		};
	}

	public static int Cut(int a, int b)
	{
		return (a + b) / 2;
	}

	public void Redraw(PictureBox Backgr)
	{
		EventHandler func = null;
		func = (x, y) =>
		{
			Backgr.Invalidate();
			foreach (Node item in edges.Keys)
			{
				
				z.DrawLine(pen, Location.X + Width/2, Location.Y + Width/2, item.Location.X + Width / 2, item.Location.Y + Width / 2);
				z.DrawString(edges[item].ToString(), new Font("Times New Roman", 15, FontStyle.Bold), Brushes.OrangeRed, Cut(Location.X, item.Location.X), Cut(Location.Y, item.Location.Y));
			}

			timer.Tick -= func;
		};
		timer.Tick += func;
	}
}