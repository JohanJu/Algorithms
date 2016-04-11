package USA;

import java.util.HashMap;

class City implements Comparable<City>{
	int dist = Integer.MAX_VALUE;
	HashMap<City, Integer> nei = new HashMap<City, Integer>();
	String name;
	public City(String n){
		name = n;
	}
	@Override
	public int compareTo(City o) {
		return dist-o.dist;
	}
	public boolean update(int i){
		if(i<dist){
			dist = i;
			return true;
		}
		else{
			return false;
		}
	}
}