// CS 0401 Fall 2019
// Shell of the PlayerList class.
// This class represents a collection of players -- a very simple database.  The
// collection can be represented in various ways.  However, for this assignment
// you are required to use an array of GamePlayer.

// Note the imports below.  java.util.* is necessary for ArrayList and java.io.* is
// necessary for the file reading and writing.
import java.util.*;
import java.io.*;

public class PlayerList {
	private Scanner profileScanner;
	private static final String PATH = "C:/Users/Brian/eclipse-workspace/Assignment3 CS0401/src/";
	private GamePlayer[] players; // array of GamePlayer
	private int size; // logical size
	private String file; // name of file associated with this PlayerList
	private int lines;
//	File tempdir = new File(PATH + file);
//	FileWriter fileWrite = new FileWriter(tempdir, true);
//	BufferedWriter buff = new BufferedWriter(fileWrite);

	// Initialize the list from a file. Note that the file name is a parameter. You
	// must open the file, read the data, making a new GamePlayer object for each
	// line
	// and putting them into the array. Your initial size for the array MUST be 3
	// and
	// if you fill it should resize by doubling the array size.

	// Note that this method throws IOException. Because of this, any method that
	// CALLS
	// this method must also either catch IOException or throw IOException. Note
	// that
	// the main() in PlayerListTest.java throws IOException. Keep this in mind for
	// your
	// main program (Assig3.java). Note that your saveList() method will also need
	// throws IOException in its header, since it is also accessing a file.
	public PlayerList(String fName) throws IOException {
		file = fName;
		BufferedReader reader = new BufferedReader(new FileReader(PATH + file));
		lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		players = new GamePlayer[3];
		File tempdir = new File(PATH + file);
		profileScanner = new Scanner(tempdir).useDelimiter("(\\p{javaWhitespace}|\\.|,)+");
		FileWriter fileWrite = new FileWriter(tempdir, true);
		BufferedWriter buff = new BufferedWriter(fileWrite);
		while (players.length < lines) {
			GamePlayer[] tmp = new GamePlayer[2 * players.length];
			System.arraycopy(tmp, 0, players, 0, players.length);
			players = tmp;
		}
		for (int i = 0; i < players.length; i++) {
			if (profileScanner.hasNextLine())
				players[i] = new GamePlayer(profileScanner.next().replaceAll(",", ""),
						profileScanner.next().replaceAll(",", ""), profileScanner.nextInt(), profileScanner.nextInt(),
						profileScanner.nextInt(), profileScanner.nextInt());
			else
				break;
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder("");
		for (GamePlayer i : players) {
			if (i == null) {
				s.append("");
			} else
				s.append(i.toString());
		}
		return s.toString();

	}

	public boolean addPlayer(GamePlayer G){
			for(int i=0; i<players.length; i++) {
			if(players[i]!=null) {	
				if(players[i].equals(G)) {
					return false;
				}
				else
					continue;
			}
			return true;
			}
				return false;
	}

	// Occupied Spots in Array
	public int size() {
		int logSize = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null)
				logSize++;
			else
				continue;
		}
		return logSize;

	}

	public GamePlayer authenticate(GamePlayer G) {
		for(int i =0; i<players.length; i++) {
			if(players[i]!=null) {
				if(G.equals(players[i])) {
					return players[i];
				}
			}
			else
				return null;
		}
		return null;
}

	// Physical Size of array
	public int capacity() {
		return players.length;
	}

	public boolean containsName(String name) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].getName().equals(name))
					return true;
				else
					return false;
			} 
		}
		return false;
	}
	
	public void saveList() {
		File t = new File(PATH + file);
		FileWriter f=null;
		try {
			f = new FileWriter(t, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter b = new BufferedWriter(f);
		for(GamePlayer i : players) {
			if(i==null)
				break;
			try {
				b.write(i.toStringFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// See program PlayerListTest.java to see the other methods that you will need
	// for
	// your PlayerList class. There are a lot of comments in that program explaining
	// the required effects of the methods. Read that program very carefully before
	// completing the PlayerList class. You will also need to complete the modified
	// GamePlayer class before the PlayerList class will work, since your array is
	// an
	// array of GamePlayer objects.

	// You may also want to add some methods that are not tested in
	// PlayerListTest.java.
	// Think about what you need to do to a PlayerList in your Assig3 program and
	// write
	// the methods to achieve those tasks. However, make sure you are always
	// thinking
	// about encapsulation and data abstraction.
}