package stablemarriage;

public class Male {
	int now  = 0;
	int[] perfer;
	public Male(int[] perfer) {
		this.perfer = perfer;
	}
	
	int next(){
		return perfer[now++];
	}

}
