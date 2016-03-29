package stablemarriage;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class StableMarriage {

	int n;
	int[] match; // every row male every column female
	HashMap<Integer, Male> males = new HashMap<Integer, Male>();
	HashMap<Integer, String> names = new HashMap<Integer, String>();
	HashMap<Integer, Integer> femalePerfer = new HashMap<Integer, Integer>();
	Stack<Integer> singleMales = new Stack<Integer>();

	void galeShapley() {
		while (!singleMales.isEmpty()) {
			int mNr = singleMales.pop();
			Male m = males.get(mNr);
			while (true) {
				int f = m.next() / 2;
				if (match[f] == 0) {
					match[f] = mNr;
					break;
				} else if (more(f, match[f], mNr)) {
					singleMales.push(match[f]);
					match[f] = mNr;
					break;
				}
			}
		}
		
	}

	void print() {
		int[] p = new int[2*n+1];
		for (int i = 1; i < match.length; i++) {
			p[match[i]]=i*2;
		}
		for (int i = 1; i < p.length && i < 10; i+=2) {
			System.out.println(names.get(i) + " -- " + names.get(p[i]));
		}
	}

	boolean more(int female, int now, int next) { // perfer next more ==
													// return true
		female *= 2;
		return femalePerfer.get(female * n + now) > femalePerfer.get(female * n + next);
	}

	void read(String path) {
//		path = "/home/john/ws/lab1/sm-random-5000.in";
		File file = new File(path);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()).charAt(0) == '#') {
			}
			n = Integer.parseInt(line.substring(2, line.length())); // fix index
			match = new int[n + 1];
			while (!(line = reader.readLine()).isEmpty()) { // read names
				String s[] = line.split(" ");
				names.put(Integer.parseInt(s[0]), s[1]);
			}
			boolean male = true;
			while ((line = reader.readLine()) != null) {
				String s[] = line.split(" ");
				int now = Integer.parseInt(s[0].substring(0, s[0].length() - 1));
				if (male) {
					int[] perfer = new int[n];
					for (int i = 1; i < s.length; i++) {
						perfer[i - 1] = Integer.parseInt(s[i]);
					}
					males.put(now, new Male(perfer));
					singleMales.add(now);
					male = !male;
				} else {
					now *= n;
					for (int i = 1; i < s.length; i++) {
						femalePerfer.put(now + Integer.parseInt(s[i]), i);
					}
					male = !male;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		StableMarriage sb = new StableMarriage();
		sb.read(args[0]);
		sb.galeShapley();
		sb.print();
	}

}
