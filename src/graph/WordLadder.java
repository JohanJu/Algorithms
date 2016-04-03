package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class WordLadder {

	HashMap<String, Set<String>> endMap = new HashMap<String, Set<String>>();
	Set<String> visited;

	int bfs(Set<String> toVisit, String end, int depth) {
		Set<String> nextToVisit = new HashSet<String>();
		for (String s : toVisit) {
			if (s.equals(end))
				return depth;
			if (!visited.contains(s)) {
				visited.add(s);
				for (String ss : endMap.get(delSort(s, 0))) {
					nextToVisit.add(ss);
				}
			}
		}
		if (nextToVisit.isEmpty())
			return -1;
		return bfs(nextToVisit, end, depth + 1);

	}

	String delSort(String s, int i) {
		StringBuilder sb = new StringBuilder(s);
		sb.deleteCharAt(i);
		char[] c = sb.toString().toCharArray();
		java.util.Arrays.sort(c);
		return String.valueOf(c);
	}

	void addEnd(String word) {
		for (int i = 0; i < word.length(); i++) {
			String s = delSort(word, i);
			if (endMap.containsKey(s))
				endMap.get(s).add(word);
			else {
				Set<String> l = new HashSet<String>();
				l.add(word);
				endMap.put(s, l);
			}
		}
	}

	void read(String path, boolean graph) {
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter("out.txt", "UTF-8");
			String line;
			while ((line = reader.readLine()) != null) {
				if (graph)
					addEnd(line);
				else {
					String[] s = line.split(" ");
					visited = new HashSet<String>();
					Set<String> nextToVisit = new HashSet<String>();
					nextToVisit.add(s[0]);
					int i = bfs(nextToVisit, s[1], 0);
					System.out.println(i);
					writer.println(i);
				}
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WordLadder g = new WordLadder();
		if (args.length == 2) {
			g.read(args[0], true);
			g.read(args[1], false);
		} else {
			g.read("words-10.dat", true);
			g.read("words-10-test.in", false);
		}
		// g.printGraph();
	}

	void printGraph() {
		for (Entry<String, Set<String>> e : endMap.entrySet()) {
			System.out.print(e.getKey());

			for (String s : e.getValue()) {
				System.out.print(" " + s);
			}
			System.out.println();
		}
	}
}
