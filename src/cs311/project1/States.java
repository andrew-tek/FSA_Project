package cs311.project1;

public class States {
	private int alphabet[];
	private boolean finalState;
	public States () {
		finalState = false;
	}
	public void updateFinalState () {finalState = true;}
	public void createArray (int size) {
		alphabet = new int [size];
		for (int i = 0; i < size; i++)
			alphabet[i] = -1;
	}
	public void updateAlph (int input, int nextState) {
		alphabet[input] = nextState;
		
	}
	public int getNext (int n) {return alphabet[n];}
	public boolean getState () {return finalState;}
	

}
