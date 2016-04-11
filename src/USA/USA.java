package USA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class USA {

	HashMap<String, City> cities = new HashMap<String, City>();
	PriorityQueue<City> closest;
	City start = null;
	int n = 0;
	int len = 0;

	void MST() {
		HashSet<City> visited = new HashSet<City>();
		visited.add(start);
		System.out.println(start.name);
		for (Map.Entry<City, Integer> e : start.nei.entrySet()) {
			e.getKey().dist = e.getValue();
			closest.add(e.getKey());
		}
//		for (City c : closest) {
//			System.out.println(c.name + " " + c.dist);
//		}
		for (; n > 1; --n) {
			City c = closest.remove();
//			System.out.println(c.name);
			visited.add(c);
			len += c.dist;
			for (Map.Entry<City, Integer> e : c.nei.entrySet()) {
				if ((!visited.contains(e.getKey()))&&e.getKey().update(e.getValue())){
					closest.remove(e.getKey());
					closest.add(e.getKey());
				}
			}
		}
		System.out.println(len);
	}

	void read(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (!line.matches(".*--.*")) {
					line = line.substring(0, line.length()-1);
					cities.put(line, new City(line));
					if (start == null) {
						start = cities.get(line);
					}
					++n;
				} else {
					String[] s = line.split("--");
					String[] ss = s[1].split(" \\[");
					City a = cities.get(s[0]);
					City b = cities.get(ss[0]);
					int d = Integer.parseInt(ss[1].substring(0, ss[1].length() - 1));
					a.nei.put(b, d);
					b.nei.put(a, d);
				}
			}
			closest = new PriorityQueue<City>(n);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		USA g = new USA();
		if (args.length == 1) {
			g.read(args[0]);
		} else {
			g.read("usa.txt");
		}

		g.MST();

		// g.printGraph();
	}
}
