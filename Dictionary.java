// CS 401 Fall 2019
// Assignment 2 Dictionary class
// Use this class to get new random words for your word finder program and
// to test words to see if they are in the dictionary.  A Dictionary can
// also be used to store the answers for a user in the game.

// Note that there may be some implementation details in this class that
// are not familiar to you.  Because of data abstraction, you do not need
// to know these details in order to to use this class.  We will look over
// these implementations later but you do not need to know them to
// complete Assignment 2.  Rather, focus on the descriptions of the methods
// and what they accomplish -- note that you are a client of this class so
// you need to know WHAT the methods do but not HOW they do it.

import java.io.*;
import java.util.*;
public class Dictionary
{
	private ArrayList<String> words;	// ArrayList to store words
	private Scanner dictFile;			// Scanner to read words from file
	private Random R;					// Random to get random words
	
	// Create a new Dictionary object, using the String argument as the name
	// of the file from which to read.  This will fill the Dictionary with
	// words from the file.
	public Dictionary(String fileName)
	{
		try
		{
			R = new Random();
			File inFile = new File(fileName);
			dictFile = new Scanner(inFile);
			words = new ArrayList<String>();
			while (dictFile.hasNextLine())	// Add words to end of ArrayList
			{
				String currWord = dictFile.nextLine();
				words.add(currWord);
			}
			Collections.sort(words);  // Sorting allows for binary search
		}
		catch (IOException e)
		{
			System.out.println("Problem reading input file " + fileName);
		}
	}
	
	// Create a new empty Dictionary object
	public Dictionary()
	{
		R = new Random();
		words = new ArrayList<String>();
	}
	
	// Return a pseudo-random word from the Dictionary with minimum length of
	// minlength and maximum length of maxlength. If no word of the desired 
	// length is found, null is returned.
	public String randWord(int minlength, int maxlength)
	{
		String newWord = null;
		int count = 0;
		boolean found = false;
		// Try at most 1000 times to find a word that is long enough.  After
		// that we give up and return null.  Note that it is possible that a
		// word of the desired length exists -- we just did not find it.  If
		// we wanted to we could make the max number of tries a parameter to 
		// this method and let the user decide how many tries to make before
		// giving up.
		while (count < 1000 && !found)
		{
			int loc = R.nextInt(words.size());  // Pick a random location
			newWord = words.get(loc);			// Get the word
			if (newWord.length() >= minlength && newWord.length() <= maxlength)	// Check the length
				found = true;
			count++;
		}
		if (newWord.length() >= minlength && newWord.length() <= maxlength)
			return newWord;
		else return null;
	}
	
	// Add a new word to the dictionary.  If the word is already present, do
	// nothing.  The word is added such that alphabetical order is maintained.
	public void addWord(String word)
	{
		int loc = 0;
		boolean found = false;
		boolean past = false;
		while (loc < words.size() && !found && !past)
		{
			int cmp = words.get(loc).compareTo(word);
			if (cmp == 0)
				found = true;
			else if (cmp > 0)
				past = true;
			else
				loc++;
		}
		if (!found)
		{
			words.add(loc, word);
		}
	}
	
	// Return true if word is in the dictionary, false otherwise.  To make the
	// search faster we are using binary search.  We will discuss binary search
	// in class soon.
	public boolean contains(String word)
	{
		int first = 0, last = words.size()-1, mid;
		boolean found = false;
		while (!found && first <= last)
		{ 
			mid = (first + last) / 2;
			int cmp = words.get(mid).compareTo(word);
			if (cmp == 0)
				return true; 		// found word
			else if (cmp > 0)		// words[mid] > word
				last = mid - 1;
			else // if (cmp < 0)  	// words[mid] < word
				first = mid + 1;
		}
		return false;  	// we only make it out of the loop if we never find
						// the word
	}	
	
	// Return the contents of the Dictionary as a single string.  Since the data is
	// stored in alphabetical order, this data will be shown in alphabetical order.
	public String toString()
	{
		StringBuilder B = new StringBuilder();
		for (int i = 0; i < words.size(); i++)
		{
			B.append(words.get(i) + "\n");
		}
		return B.toString();
	}
	
	// This static method calculates the Levenshtein edit distance of two strings.  
	// The algorithm was taken from 
	// https://en.wikipedia.org/wiki/Wagner-Fischer_algorithm
	// and converted into a Java method.
	// Note that because this is a static method it is not part of a Dictionary
	// object and has no access to instance variables.  All data must be local
	// or passed in via parameters.
    public static int distance(String s1, String s2)
    {
    	int [][] dist = new int[s1.length()+1][s2.length()+1];
    	int m = s1.length();
    	int n = s2.length();
    	for (int i = 1; i <= m; i++)
    		dist[i][0] = i;
    	for (int j = 1; j <= n; j++)
    		dist[0][j] = j;
    	
    	for (int j = 1; j <= n; j++)
    	{
    		for (int i = 1; i <= m; i++)
    		{
    			int sub = 0;
    			if (s1.charAt(i-1) != s2.charAt(j-1))
    				sub = 1;
    			
    			int newVal = Math.min(dist[i-1][j]+1, dist[i][j-1]+1);
    			newVal = Math.min(newVal, dist[i-1][j-1]+sub);
    			dist[i][j] = newVal;
    		}
    	}	
		return dist[m][n];
	}
}