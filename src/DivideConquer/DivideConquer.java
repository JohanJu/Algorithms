package DivideConquer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.Map.Entry;

import graph.WordLadder;

public class DivideConquer {

	class Point {
		int nr;
		double x, y;
		public Point(int nr, double x, double y){
			this.nr = nr;
			this.x = x;
			this.y = y;
			System.out.println(x);
		}
	}

	Point[] points;
	int n;

	void read(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(" ");
				int nr = 0;
				try {
					nr = Integer.parseInt(data[0]);
					points[i++]=new Point(nr, Double.parseDouble(data[1]), Double.parseDouble(data[2]));
				} catch (NumberFormatException ex) {
					if (data[0].equals("DIMENSION")) {
						n = Integer.parseInt(data[2]);
						points = new Point[n];
					}
					continue;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DivideConquer g = new DivideConquer();
		if (args.length == 1) {
			g.read(args[0]);
		} else {
			g.read("eli51.tsp");
		}
		// g.printGraph();
	}
}
