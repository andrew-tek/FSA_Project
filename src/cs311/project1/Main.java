/* Andrew Tek
 * January 26, 2017
 * CS 311, Sang
 * Project 1
 * Notes: All necessary program info is under source.
 *        The input file is in the same folder as 
 *        source. The file is located in the default
 *        directory. Program is ran in Java on Eclipse.
 * 
 *        First task of program is to take is 
 *        all info including number of states, list of
 * 		  final states, alphabet set, transition
 *        moves, and test strings. Then from here
 * 		  we test the strings. A class called States
 *        holds an array of the size of the alphabet
 * 		  for that machine, each value will hold the
 *        next state (given the input) or hold -1
 *        if that input will lead to trap. We use a 
 *        reference variable to check alphabets and
 *        simulate the transition until we finish the 
 *        string, until we find something not in the
 *        alphabet, or lead to trap. After, if we get 
 *        to end of the tape we check if the last state
 *        we were in is a final state. If not we reject
 *         the string.
 */


package cs311.project1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;



public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		boolean exit = false;
		boolean accept = true;
		int next, nextState;
		int numStates, finalStates;
		String first, second, last, testValues;
		int num = 1;
		String [] alph;
		States []states;
		States ptr = new States();
		alph = new String [50];        //Creating array of characters to be read from file
		FileInputStream in = new FileInputStream ("CS311FSA.txt");
		Scanner sc = new Scanner (in);
		while (sc.hasNextLine()) {
			for (int i = 0; i < 50; i++) {     //For each new machine, create new empty array
				alph [i] = new String ();
			}
			System.out.println("Finite State Automaton #" + num);
			numStates = sc.nextInt();           //number of states
			System.out.println("(1) Number of States: " + numStates);
			states = new States [numStates];    //create an array of class States
			for (int i = 0; i < numStates; i++) 
				states [i] = new States();
			System.out.print("(2) List of final states: ");
			while (sc.hasNextInt()) {
				finalStates = sc.nextInt();    //Reading final states from file
				states[finalStates].updateFinalState();  //calling class method to change final state
				System.out.print(finalStates + "   ");      
			}
			System.out.println();
			alph[0] = sc.next();
			alph[0] = alph[0].substring(1); //remove the '[' from the alphabet set
			int i = 1;
			while (!alph[i - 1].contains("]") && !alph[i - 1].contains("(")) {
				alph[i] = sc.next();
				i++;                  
			}
			alph [i - 1] = alph[i - 1].substring(0, alph[i - 1].length() - 1);
			System.out.println("(3) Alphabet:");
			for (int j = 0; j < i; j++) {  
				if (j % 10 == 0 && j != 0)
					System.out.println();
				System.out.print(alph[j] + "   ");
			}
			System.out.println();
			for (int j = 0; j < numStates; j++) {
				states[j].createArray(i);   //creating arrays in Class states
			}                               //with number of characters in alphabet
			
			first = sc.next();
			System.out.println("(4) Transition Moves");
			while (first.charAt(0) == '(') {
				first = first.substring(1); //remove '(' from transition move
				second = sc.next();
				last = sc.next();
				last = last.substring(0, last.length() - 1);
				System.out.println(first + " " + second + " " + last);
				int k = 0;
				while (!alph[k].equals(second)) //find index of alphabet character
					k++;
				states[Integer.parseInt(first)].updateAlph(k, Integer.parseInt(last)); //update states
				first = sc.next();
			}
			testValues = first;
			System.out.println("(5) Strings:"); 
			while (!testValues.equals ("****") && sc.hasNextLine()) {
				int currentState = 0;
				int l = 0;
				ptr = states[0];
				exit = false;
				accept = true;
				char value;
				while (l < testValues.length() && exit == false) {
					value = testValues.charAt(l);
					if (Character.isUpperCase(value)) {
						value = Character.toLowerCase(value); //make character lowercase
					}
					next = checkAlpha (alph, value); //check if character in alphabet
					if (next != -1){
						nextState = ptr.getNext(next); //check if there is a next state for given character
						if (nextState == -1) { //if dead end exit the loop
							accept = false;
							exit = true;
						}
						else {
							ptr = states[nextState]; //update current state
							currentState = nextState;
						}
					}
					else {
						accept = false; //done if character not in alphabet
						exit = true;
					}
					l++;
				}
				if (accept == false) {
					System.out.printf("%-40s %10s\n", testValues,  "rejected");
				}
				else if (ptr.getState() == true) { //check if end of tape is final state
					System.out.printf("%-40s %10s\n", testValues,  "accepted");
				}
				else
					System.out.printf("%-40s %10s\n", testValues,  "rejected");
				
				testValues = sc.next();
			}
			num++;
			System.out.println();
		}
		System.out.println("Program shutting down...");

	}
	static int checkAlpha (String alpha [], char key) { //checks alphabet for key
		for (int i = 0; i < alpha.length; i++) 
			if (alpha[i].equals(Character.toString(key)))
				return i;
		return -1;
	}
	
}
