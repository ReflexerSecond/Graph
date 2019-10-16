using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Windows.Forms;

namespace WFGraphVisualizer
{
	internal class GraphShower
	{
		static bool isDirected = false;
		static bool hasWeight = false;
		public static void ShowGraph(Граф form,String[] args)
		{
			
			PictureBox Backgr = new PictureBox
			{
				Dock = DockStyle.Fill,
				Image = new Bitmap(800, 450)
			};
			EventHandler func = null;
			func = (x, y) =>
			{
				Node.z.Clear(Color.AliceBlue);
				Node.timer.Tick -= func;
			};
			List<Node> pictures = new List<Node>();
			
			Random rand = new Random();
			FileReader(pictures, func, Backgr, form, args[0]);
			
			form.shuffle.Click += (x, y) => LocationShaffle(pictures, form, func, Backgr);

			LocationShaffle(pictures, form, func, Backgr);
			//init
			form.Controls.AddRange(pictures.ToArray());
			form.Controls.Add(Backgr);
		}

		public static Node NodeBuilder(PictureBox Backgr,String name, Graphics g,Граф form)
		{
			
			Node picture = new Node(Backgr)
			{
				BackColor = Color.LightSlateGray,
				Image = new Bitmap(Properties.Resources.node.Width, Properties.Resources.node.Height)
				//Image = Properties.Resources.nodeSquare
			};
			g = Graphics.FromImage(picture.Image);
			g.DrawString(name.ToString(), new Font("Arial", 20, FontStyle.Bold), Brushes.GhostWhite, Properties.Resources.node.Width / 2 - 10, Properties.Resources.node.Height / 2 - 13);
			
			picture.Width = Properties.Resources.node.Width;
			picture.Height = Properties.Resources.node.Height;
			return picture;
		}

		public static void LocationShaffle(List<Node> pictures, Граф form, EventHandler func, PictureBox Backgr)
		{
			Random rand = new Random();
			int itheratorX = rand.Next(50, 200);
			int itheratorY = rand.Next(50, 200);
			foreach (var picture in pictures)
			{
				var tempLoc = rand.Next(50, 200);
				tempLoc = (rand.Next(2) == 1) ? tempLoc * -1 : tempLoc;

				picture.Location = new Point(itheratorX, itheratorY);
				itheratorX = ((itheratorX + tempLoc > form.Width - 100) || (itheratorX + tempLoc < 100)) ? itheratorX - tempLoc/2 : itheratorX + tempLoc;

				tempLoc = rand.Next(1, 200);
				tempLoc = (rand.Next(2) == 1) ? tempLoc * -1 : tempLoc;
				itheratorY = ((itheratorY + tempLoc > form.Height - 100) || (itheratorY + tempLoc < 100)) ? itheratorY - tempLoc/2 : itheratorY + tempLoc;
			}
			Node.timer.Tick += func;

			foreach (Node item in pictures)
			{
				item.Redraw(Backgr);
			}
		}

		public static bool FileReader(List<Node> nodes, EventHandler func, PictureBox Backgr, Граф form, String path)
		{
			try
			{

			
			String tempString;
			Graphics g = null;
			var nodeTitles = new List<String>();
			var edgeTitles = new Dictionary<(String,String), String>(); 
			using (StreamReader sr = new StreamReader(path))
			{
				isDirected = Boolean.Parse(sr.ReadLine());
				hasWeight = Boolean.Parse(sr.ReadLine());
				while ((tempString = sr.ReadLine()) != null)
				{
					tempString = tempString.Trim();
					var tempNodes = tempString.Split(' ');
					nodeTitles.Add(tempNodes[0]);
					for (int i = 1; i < tempNodes.Length; i++)
					{
						if (hasWeight) edgeTitles.Add((tempNodes[0], tempNodes[i].Split(':')[0]), tempNodes[i].Split(':')[1]);
						else edgeTitles.Add((tempNodes[0], tempNodes[i]), "");
					}
				}
			}
			foreach (var item in nodeTitles)
			{
				Node picture = NodeBuilder(Backgr, item, g, form);
				picture.MouseMove += (sender, e) =>
				{
					if (e.Button == MouseButtons.Left)
					{
						Node.timer.Tick += func;

						foreach (Node item2 in nodes)
						{
							item2.Redraw(Backgr);
						}

					};
				};
				nodes.Add(picture);
			}
			foreach (var item in edgeTitles)
			{
				if (nodes[nodeTitles.IndexOf(item.Key.Item2)].edges.ContainsKey(nodes[nodeTitles.IndexOf(item.Key.Item1)]))
				{
					if (isDirected)
						nodes[nodeTitles.IndexOf(item.Key.Item2)].edges[nodes[nodeTitles.IndexOf(item.Key.Item1)]] += $"[{item.Key.Item1}:{item.Key.Item2}" + ((hasWeight) ? $":{item.Value}]" : "") + "]";
				}
				else
				{
					nodes[nodeTitles.IndexOf(item.Key.Item1)].edges.Add(nodes[nodeTitles.IndexOf(item.Key.Item2)], (isDirected) ? $"[{item.Key.Item1}:{item.Key.Item2}" + ((hasWeight) ? $":{item.Value}]" : "") + "]" : item.Value);
				}
			}
			return true;
			}
			catch (Exception)
			{
				var msg = new Label();
				msg.Text = "Не удалось прочитать";
				form.Controls.Add(msg);
				return false;
			}
		}
	}
}
