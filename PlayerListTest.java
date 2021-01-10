// CS 0401 Fall 2019
// Program to test PlayerList and GamePlayer classes.  Note the sample output shown
// for this program -- the first time run some new players are added but the
// second time none are added since the updated list was saved back to the file.

// Your GamePlayer class is a modification of the GamePlayer class from
// Assignment 2.  A new field has been added for the password and an equals()
// method has also been added.  The toStringFile() method has also been changed.
// See details on these and other changes below.

// Your PlayerList class can be implemented in various ways and you are free to add
// additional methods to those used below.  However, the methods used below are required
// and must have the effect discussed / demonstrated in this program.  After completing
// your updated GamePlayer class and your new PlayerList class, this program should
// compile and execute and produce the same results as shown in playertest-out.txt.

// For some help with these files you should look at Lab 7 and, in particular the
// Movie class and the MovieDB class.  You should also look at the SimpleAList.java
// handout and class lecture notes.

import java.util.*;
import java.io.*;

public class PlayerListTest
{
	public static void main(String [] args) throws IOException
	{
		System.out.println("Testing PlayerList and GamePlayer classes");
		
		// Create a PlayerList from a file of players.  Note the format of the file
		// and note that after this constructor all of the player information is stored
		// within the allPlayers object.  Note also the effect of the toString() method.
		// Parse the output carefully in order to figure out what it entails.  To help with
		// the grading of this project, you MUST:
		// 1) Initialize the your array within your PlayerList to size 3
		// 2) Resize your array when necessary to double its previous size, as discussed in
		//    in lecture.
		// 3) Implement a capacity() method, which indicates the current size of the array
		//    within your PlayerList object
		PlayerList allPlayers = new PlayerList("players.txt");
		System.out.println("Here are the player stats: ");
		System.out.println(allPlayers.toString());
	

		
		System.out.println("Current list size is: " + allPlayers.size());  // logical size
		System.out.println("Current array capacity is: " + allPlayers.capacity());
																			// physical size
		
		System.out.println();
		
		// Some names and passwords are hard coded here just to test the classes.  Initially
		// Marge and Ingmar will NOT be in the PlayerList.  Also, the password here for Inigo
		// is incorrect, so Inigo will not be authenticated.
		String [] names = {"Marge", "Fezzik", "Ingmar", "Inigo"};
		String [] passes = {"IHeartCS401", "Sportsmanlike", "Programming!", "Hello"};
		System.out.println("Checking for names in the PlayerList");
		for (int i = 0; i < names.length; i++)
		{
			String S = names[i];
			String P = passes[i];
			// containsName will return true if the GamePlayer's name is within the PlayerList
			// and false otherwise.  This will be useful in your main program.
			boolean found = allPlayers.containsName(S);
			if (found)
			{
				System.out.println(S + " is a name in the PlayerList\n");
			}
			else
			{
				System.out.println(S + " is not in the list -- will be added:");
				// Create a new player with just a String for the name.  The remaining
				// fields should be null or 0
				GamePlayer onePlayer = new GamePlayer(S);
				// Set the password for the player
				onePlayer.setPass(passes[i]);
				// Show player info using the toString() method.
				System.out.println("\tNew player info: ");
				System.out.println(onePlayer.toString());
				// Add the player to the PlayerList.  Note that addPlayer() should not add
				// a new GamePlayer whose name matches one already found in the list. If this
				// is tried the method should return false.
				if (allPlayers.addPlayer(onePlayer))
					System.out.println("has been added to the PlayerList\n");
				else
					System.out.println("was already in the list -- NOT added!");
			}
		}
		
		System.out.println("Checking for valid GamePlayers in the PlayerList");
		for (int i = 0; i < names.length; i++)
		{
			String S = names[i];  // Try each name / password combo from the arrays
			String P = passes[i]; // above to see which are valid.
			GamePlayer temp = new GamePlayer(S);  // Make a temp GamePlayer with just
			temp.setPass(P);	// the name and then set the password.
								// This object will now be passed as an argument to the
								// authenticate method.
								
			// authenticate() takes a GamePlayer argument and checks to see if a GamePlayer
			// within the PlayerList matches that GamePlayer (using the equals() method, which
			// requires both the name and the password to match exactly).  If a GamePlayer
			// matches, return that GamePlayer; otherwise return null.  The idea is that
			// we can ask a user to enter an id and password and create a simple GamePlayer
			// object out of those.  However, in order to get the rest of the information
			// (game stats) we need to retrieve the GamePlayer in question from the PlayerList.
			// authenticate() will do that, provided that the id and password are valid.  If
			// they are not the authenticate() method will return null.  The null return
			// indicates that either the id or password is incorrect.  If we want to be specific
			// as to why authenticate failed we can first call containsName() to see if the
			// name is present, and only call authenticate() if the name in fact matches a name
			// in the PlayerList.
			GamePlayer play = allPlayers.authenticate(temp);
			if (play != null)
			{
				// The authenticate() method will return the reference of a GamePlayer within the
				// PlayerList and NOT a copy of the GamePlayer.  We discussed this issue (or
				// will discuss this issue) in lecture (see Lecture 18 Powerpoint slides).
				// It may not always be the best idea to do this, but since we have a reference into
				// the same GamePlayer that is in the PlayerList, we can mutate it and the effect
				// will occur within the PlayerList.  Note the mutations below.
				System.out.println(play.toString() + " has been authenticated and can play!");
				// The methods below can be used to update a GamePlayer during the game (after each
				// completed round).  These are the same as they were for Assignment 2.
				System.out.println("\t\tSimulating a success and a failure for " + play.getName());
				System.out.println();
				play.success(6, 4);
				play.failure();
			}
			else // In this example Inigo will not be valid since the password we have
			{	 // for him in the passes[] array does not match the password in the file
				System.out.println(temp.toString() + " is not authenticated\n");
			}
		}
		// Note the format and information returned by the toString() method.  Your
		// output should contain the same, well formatted information.  Note that this
		// call shows ALL of the GamePlayers -- this is because the toString() method in the
		// PlayerList class will iterate through all of the GamePlayer objects and call the
		// toString() method for each, appending them all into one (very big) StringBuilder.
		// It will then return the single string representing the entire list.
		System.out.println("Here are the player stats: ");
		System.out.println(allPlayers.toString());
		
		System.out.println("Current list size is: " + allPlayers.size());
		System.out.println("Current array capacity is: " + allPlayers.capacity());
		
		// Write the players back to a text file so that they can be retrieved later.
		// Clearly to write the GamePlayer information to the file, the PlayerList class must
		// be able to get the information from each of the GamePlayer objects.  Use this to
		// help you design both the GamePlayer and PlayerList classes.  The idea of this method
		// is as follows:  Iterate through the list of players, writing the information for
		// each player back to the file.  In Assignment 2 you wrote a toSringFile() method for
		// your GamePlayer class.  You must modify that method here to return a comma separated
		// list of the data fields for the GamePlayer.  Thus, the method
		//     public String toStringFile() {}
		// will return all of the information from the GamePlayer appended together as
		// a single comma separated String.  Note that this is NOT the same as toString(), 
		// since toString() has formatting in it.  toStringFile() is simply the raw data in
		// the format necessary for the file.  Also note that toString does NOT have the password
		// in it (since we would not want to display that) but toStringFile() DOES have the password
		// since we want to save that to the file.
		allPlayers.saveList();
		
		// To see the effect of this program and its output, see the file playertest-out.txt. Note that
		// the contents of this file shows two runs of the program and also the contents of the data
		// files before and after the runs.
	}		
}