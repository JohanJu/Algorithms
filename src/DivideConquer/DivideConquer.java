package DivideConquer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Set;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map.Entry;

import graph.WordLadder;

public class DivideConquer {

	class Point {
		int nr;
		double x, y;

		public Point(int nr, double x, double y) {
			this.nr = nr;
			this.x = x;
			this.y = y;
			// System.out.println(x + " " + y);
		}

		public double dist2(Point p) {
			return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
		}
	}

	public static Comparator<Point> xComp = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			if (p1.x - p2.x > 0)
				return 1;
			else if (p1.x - p2.x < 0)
				return -1;
			return 0;
		}

	};
	public static Comparator<Point> yComp = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			if (p1.y - p2.y > 0)
				return 1;
			else if (p1.y - p2.y < 0)
				return -1;
			return 0;
		}

	};

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
				String[] data = line.split(" +");

				int index = 0;
				if (data[0].isEmpty()) {
					index++;
				}
				int nr = 0;
				try {
					nr = Integer.parseInt(data[index]);
					points[i++] = new Point(nr, Double.parseDouble(data[index + 1]),
							Double.parseDouble(data[index + 2]));
				} catch (NumberFormatException ex) {

					if (data[index].equals("DIMENSION") || data[index].equals("DIMENSION:")) {
						n = Integer.parseInt(data[data.length - 1]);
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

	void dc() {
		Arrays.sort(points, xComp);
		System.out.println(Math.sqrt(rec(points)));
	}

	double rec(Point[] p) {
		double min = Double.MAX_VALUE;
		if (p.length < 4) {
			for (Point p1 : p) {
				for (Point p2 : p) {
					if (p1 != p2) {
						min = Math.min(p1.dist2(p2), min);
					}
				}
			}
			return min;
		} else {
			Point[] a1 = new Point[p.length / 2];
			Point[] a2 = new Point[p.length - p.length / 2];
			System.arraycopy(p, 0, a1, 0, p.length / 2);
			System.arraycopy(p, p.length / 2, a2, 0, p.length - p.length / 2);
			min = Math.min(rec(a1), min);
			min = Math.min(rec(a2), min);
			double min2 = Double.MAX_VALUE;
			int i, j;
			// binary search
			for (i = 0; i < a1.length; i++) {
				if (a1[a1.length - 1].x - a1[i].x < min)
					break;
			}
			for (j = 1; j < a2.length; j++) {
				if (a2[j].x - a2[0].x < min)
					break;
			}
			--j;
			// return and merge
			Point[] y1 = new Point[a1.length];
			System.arraycopy(a1, 0, y1, 0, a1.length);
			Point[] y2 = new Point[a2.length];
			System.arraycopy(a2, 0, y2, 0, a2.length);
			for (; i < a1.length; i++) {
				for (int k = 0; k < y2.length; k++) {
					if (a1[i].y - y2[k].y > min)
						continue;
					else if (y2[k].y - a1[i].y > min)
						break;
					if (y2[k] != a1[i])
						min = Math.min(y2[k].dist2(a1[i]), min);
				}
			}
			for (; j > -1; j--) {
				for (int k = 0; k < y1.length; k++) {
					if (a2[j].y - y1[k].y > min)
						continue;
					else if (y1[k].y - a2[j].y > min)
						break;
					if (y1[k] != a2[j])
						min = Math.min(y1[k].dist2(a2[j]), min);
				}
			}

		}
		return min;
	}

	public static void main(String[] args) {
		DivideConquer g = new DivideConquer();
		if (args.length == 1) {
			g.read(args[0]);
		} else {
			g.read("eli51.tsp");
		}
		g.dc();
	}
}
