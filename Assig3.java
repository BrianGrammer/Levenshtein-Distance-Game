import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assig3 {
	private static final String PATH = "C:/Users/Brian/eclipse-workspace/Assignment3 CS0401/src/"; // Change this string for your personal computer's PATH.

	private static final String INSTRUCTIONS = "Here is how to play:\r\n" + // Final string just too make the code cleaner.
			"        For each round you will see two randomly selected words.\r\n"
			+ "        You will have 1 minute to convert the first word to the second\r\n"
			+ "        using only the following changes:\r\n"
			+ "                Insert a character at position k (with k starting at 0)\r\n"
			+ "                Delete a character at position k (with k starting at 0)\r\n"
			+ "                Change a character at position k (with k starting at 0)\r\n"
			+ "        For example, to change the word 'WEASEL' to 'SEASHELL' you could\r\n"
			+ "        do the following:\r\n" + "                1) Change 'W' at position 0 to 'S': SEASEL\r\n"
			+ "                2) Insert 'H' at position 4: SEASHEL\r\n"
			+ "                3) Insert 'L' at position 7: SEASHELL\r\n"
			+ "        Your goal is to make this conversion with the fewest changes.  Note\r\n"
			+ "        that there may be more than one way to achieve this goal.\n" + "What is your name?";

	private static final long RUNTIME = 120000;
	
	public static void main(String[] args) throws IOException {
		
		//Initializing Variables
		int rounds = 0, wins = 0, losses = 0, optimalRounds = 0, tries = 0, k =0;
		boolean loop = true, nameFound = false, mainLoop = true;
		int playerIndex= 0;
		GamePlayer player;
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> passwords = new ArrayList<String>();
		ArrayList<String> tempNames = new ArrayList<String>();
		String name, password = "";
		Scanner profileScanner = new Scanner(System.in);
		File tempdir = new File(PATH + "players.txt");
		Scanner profileScanner2 = new Scanner(tempdir).useDelimiter("(\\p{javaWhitespace}|\\.|,)+");
		FileWriter fileWrite = new FileWriter(tempdir, true);
		BufferedWriter buff = new BufferedWriter(fileWrite);
		String buddyboy = "";
		String word1, word2, choice;
		Scanner scanner = new Scanner(System.in);
		MyTimer timer = new MyTimer(RUNTIME);
		System.out.println("What dictionary would you like?\n dictionary\n smallDict");
		String dictChoice = scanner.next();
		Dictionary dictionary = new Dictionary(PATH + dictChoice + ".txt"); // Will grader change the directory for us?
		Dictionary checkRepeat = new Dictionary();
		System.out.println("Welcome to the Word Changer Program! \n\n" + INSTRUCTIONS);
		
		
		
		name = profileScanner.next();
		while(profileScanner2.hasNext()) {
			tempNames.add(profileScanner2.next().replaceAll("[,]",""));
		}
		//Splits up passwords and usernames into two different lists-- makes it easier to see if username already exists.
		for(int j = 0; j<tempNames.size(); j+=5) {
			names.add(tempNames.get(j));
			tempNames.remove(j);
		}
		for(int b = 0; b<tempNames.size(); b+=4) {
			passwords.add(tempNames.get(b));
			tempNames.remove(b);
		}
		for(int i = 0; i <names.size(); i++) {
			if(name.equals(names.get(i))) {
				while(loop == true) {
					System.out.println("Please Enter Your Password");
					password = profileScanner.next();
					if(password.equals(passwords.get(i))){
						System.out.println("Welcome Back, " + name + "!");
						nameFound = true;
						loop = false;
						//Gets index of player to get score data.
						for(int p = 0; p<names.size(); p++) {
							if(names.get(p).equals(name)){
								playerIndex = p;
								break;
							}
						}
						//Janky way of assigning the player data values to the variables. I keep track of player index, since there's 4 scores, I multiply the player index by 4 and get the next 3 values.
						for(int n= 1; n<2;) {
							n= n *4*playerIndex;
							rounds = Integer.parseInt(tempNames.get(n));
							wins = Integer.parseInt(tempNames.get(n+1));
							optimalRounds = Integer.parseInt(tempNames.get(n+2));
							tries =  Integer.parseInt(tempNames.get(n+3));
							break;
						}
						break;
					}
					else
						continue;
				}
			}
			else 
				continue;
		}
		
		//If player isn't found
		if(nameFound == false) {
			System.out.println("What is your password?");
			password = profileScanner.next();
			System.out.println("Welcome, new player!");
			buff.write(name + "," + password + "," + rounds +"," + wins + "," +optimalRounds + "," + tries + System.lineSeparator());
			buff.close();
		}
		else {
			buff.close();
		}
		player = new GamePlayer(name, password, rounds, wins, optimalRounds, tries);
		System.out.println("Would you like to play?");
		String yesNoAnswer = scanner.next();
		do {	
			mainLoop = false;
			if (yesNoAnswer.equalsIgnoreCase("Yes")) {
				// Initializing words
				// Find out how to not get same words?
				word1 = dictionary.randWord(5, 9);
				word2 = dictionary.randWord(5, 9);	
				StringBuilder newWord = new StringBuilder(word1);
				int levDist = Dictionary.distance(word1, word2);
				System.out.println("Your goal is to convert " + newWord + " to " + word2 + " in " + levDist + " edits");
				timer.start();
				while (loop && timer.check()) { // Loops the game part of the program. ADD TIMER!
					System.out.println("Index:        0123456789\r\n" + "------        ----------\r\n" + "Current Word: "
							+ newWord + "\r\n" + "Word 2:       " + word2 + "\r\n" + "Here are your options:\n" +

							"	C k v -- Change char at location k to value v\r\n"
							+ "        I k v -- Insert char at location k with value v\r\n"
							+ "        D k   -- Delete char at location k\n" + "Option Choice > ");
					choice = scanner.next();// Takes the players 'choice' and manipulates it so it is easier to interpret. AKA getting rid of spaces.
					choice += scanner.nextLine();
					choice = choice.replaceAll("\\s", "");
					
					//index out of bounds exception below
					String kString = "";
					if(choice.length()>=2)
						 kString = choice.substring(1, 2);
					else {
						System.out.println("Error entering your choice. Try again.");
						continue;
					}
					String vString;

					if (choice.length() >= 2) {
						vString = choice.substring(2);
						if (vString.length() > 1) {
							System.out.println("Error entering your choice. Try again.");
							continue;
						}
					} else
						vString = "";
					try {
						k = Integer.parseInt(kString);
					} catch (NumberFormatException e) {
						System.out.println("K was not chosen to be an integer, please enter again."); // Catches if 'k' was typed in as a letter instead of a number.
					}
					try {
						if ((choice.charAt(0) == 'C' || choice.charAt(0) == 'c')&&timer.check()) {
							newWord.replace(k, k + 1, vString);
							tries++;
							if (checkWordsEquals(newWord.toString(), word2)) {
								loop = false;
								player.success(tries, levDist);
								timer.stop();
								System.out.println("Congratulations! You Won! Would you like to play again?");
								String writeInfo = player.toStringFile();
								fileWrite = new FileWriter(tempdir);
								PrintWriter printWrite = new PrintWriter(fileWrite);
								printWrite.print(writeInfo); 
								printWrite.close();
								String a = scanner.next();
								if(a.equalsIgnoreCase("Yes")) {
									mainLoop=true;
									loop = true;
								}
								else
									System.exit(0);
							}
							continue;
						} else if ((choice.charAt(0) == 'I' || choice.charAt(0) == 'i')&&timer.check()) {
							newWord.insert(k, vString);
							tries++;
							if (checkWordsEquals(newWord.toString(), word2)) {
								loop = false;
								player.success(tries, levDist);
								timer.stop();
								System.out.println("Congratulations! You Won! Would you like to play again?");
								String writeInfo = player.toStringFile();
								fileWrite = new FileWriter(tempdir);
								PrintWriter printWrite = new PrintWriter(fileWrite);
								printWrite.print(writeInfo); 
								printWrite.close();
								String a = scanner.next();
								if(a.equalsIgnoreCase("Yes")) {
									mainLoop=true;
									loop = true;
								}
								else
									System.exit(0);
							}
							continue;
						} else if ((choice.charAt(0) == 'D' || choice.charAt(0) == 'd')&&timer.check()) {
							newWord.deleteCharAt(k);
							tries++;
							if (checkWordsEquals(newWord.toString(), word2)) {
								loop = false;
								player.success(tries, levDist);
								timer.stop();
								System.out.println("Congratulations! You Won! Would you like to play again?");
								String writeInfo = player.toStringFile();
								fileWrite = new FileWriter(tempdir);
								PrintWriter printWrite = new PrintWriter(fileWrite);
								printWrite.print(writeInfo); 
								printWrite.close();
								String a = scanner.next();
								if(a.equalsIgnoreCase("Yes")) {
									mainLoop=true;
									loop = true;
								}
								else
									System.exit(0);
							}
							continue;
						} else if(timer.check()){
							System.out.println("Your option was invalid.  Please re-enter.");
							continue;

						}
					} catch (StringIndexOutOfBoundsException e) {
						System.out.println("The index chosen does not exist for the current word. Please retry.");
					}
					if(!timer.check()) {
						loop = false;
						player.failure();
						System.out.println("Out of Time!");
						String writeInfo = player.toStringFile();
						fileWrite = new FileWriter(tempdir);
						PrintWriter printWrite = new PrintWriter(fileWrite);
						printWrite.print(writeInfo); 
						printWrite.close();
					}
				}
				
			} else {
				System.exit(0);
			}
		}while(mainLoop);
	}

		public static boolean checkWordsEquals(String w1, String w2) {
			if (w1.equalsIgnoreCase(w2))
				return true;
			else
				return false;
		}
		{
	}
}