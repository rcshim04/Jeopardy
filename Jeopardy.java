/*
    Eric Shim
    Ms. Krasteva
    January 13, 2020
    ICS3U - ISP Improved (Jeopardy!)
    
    This program contains 2 levels of a version of the American quiz show Jeopardy!. The program will start off with the splashscreen, then moves to the main menu.
    From the menu, there are 4 options: Instructions, Play, Highscores, and Exit. When the game starts, the user has a choice between single player and multiplayer.
    Single player (PvE), is against a bot, and multiplayer (PvP), is between two players. The user(s) can then enter their names. They will then be given the choice
    between level 1 and level 2, and to turn on cheat mode or not. Games played with cheat mode on will not be recorded in the highscores. When navigating around the
    program, use the WASD and ENTER keys. In the game, use WASD to navigate, and the respective player's number keys (Player 1 = '1' key, player 2 = '2' key). The
    program will show who's turn it is at the top. The game will not select a question unless the correct player key is pressed. The players will then have 5 seconds
    to hit their buzzer (their respective number keys, again) to answer the question. The player who hits the buzzer first may answer the question within 10 seconds.
    Spelling must be precise. If the first player answers incorrectly, the question immediately defaults to the other player, and they have 10 seconds to answer. If
    the question is answered correctly, the question's worth will be added to the player's score. If the question is answered incorrectly, the question's worth is
    subtracted. If buzzer is not pressed, the score is not changed, and the program returns to the questions panel. A daily double where points can be wagered, is
    randomly hidden in the questions. Level 2 has 2 daily doubles. When all the questions are answered, if nobody has a negative score, the program will move onto the
    final question. If somebody has a negative score, the other player automatically wins, and the final question is skipped. If both players have a negative score,
    nobody wins, and the final question is skipped. If the player(s)'s score(s) are in the top 10, they will be displayed in the highscores. In the highscore option,
    there are options to display the level top 10 scores, the level 2 top 10 scores, clear the highscore list that is being displayed, or returning to main menu.
    Exit the program using the exit option.
    
    Global Variable Dictionary:
    
     Variable Name    Variable Type               Variable Description    
    ______________________________________________________________________________
				      A reference variable that provides access
	  c              Console      to the built in Console class. 

				      The string that the user inputs during the
	choice           String       mainMenu method, decides program flow.

	 name           String[]      Stores the player names.
	 
	 str             String       General input string.
	 
       botName           String       Bot name.
       
				      General input character, used in all
	  k               char        instances of character input. k = key
	  
	level             int         Stores the current level or stage.
	
				      Stores the level chosen in askData, used
	 lvl              int         for highscore displaying.
	 
       player             int         Stores the current player.
       
       qX, qY             int         Stores the current question coordinates.
       
				      Stores current option. Used in 2D option
     optX, optY           int         movement in the highscores display. It's a
				      global var as to not reset it each time.

       cheats           boolean       Stores whether cheat mode in on or not.
       
	bot             boolean       Stores whether the bot will be used or not.
	
     leaveGame          boolean       Used to determine if the user wishes to exit                             
     
       wager             int[]        Stores the player bets/wagers.
       
       score             int[]        Stores the player scores.
       
	DD               int[]        Stores the daily double coordinates
	
     questions         String[][]     Stores the questions.
     
      answers          String[][]     Stores the answers.
      
     highscores        String[][]     Stores the highscores read off a file.
     
	fin             int[][]       Stores which player has answered which question
	
      dDouble           int[][]       Stores which player has answered each daily Double
      
      topics            String[]      Stores the topic names.
      
       icon            ImageIcon      Stores image to use in error message.
      
*/
import java.io.*;                       //Gives access to the Java IO libraries
import java.awt.*;                      //Gives access to the Java command libraries
import hsa.Console;                     //Gives access to Console class file
import javax.swing.JOptionPane;         //Gives access to JOptionPane class file
import javax.swing.ImageIcon;           //Gives access to ImageIcon class file
//import sun.audio.*;                     //Gives access to audio class libraries, not used in this submission

public class Jeopardy {                 //Creates new class called Jeopardy

    Console c;                          //Creates an instance variable of the Console class so the output window can be made
    String choice = "";                 //Declares the variable 'choice' as a String, and intializes it with an empty value
    String[] name = new String[2];      //Declares the array 'name' as a 1D String array, and intializes it with the size of 2
    String str = "";                    //Declares the variable 'str' as a String, and intializes it with an empty value
    String botName = "ShimmyBot v5";    //Declares the variable 'botName' as a String, and intializes it with the bot's name
    char k;                             //Declares the variable 'k' as a char
    int level;                          //Declares the variable 'level' as an int
    int lvl;                            //Declares the variable 'lvl' as an int
    int player;                         //Declares the variable 'player' as an int
    int qX = 0;                         //Declares the variable 'qX' as an int, and intializes it as 0
    int qY = 0;                         //Declares the variable 'qY' as an int, and intializes it as 0
    int optX = 0;                       //Declares the variable 'optX' as an int, and intializes it as 0
    int optY = 0;                       //Declares the variable 'optY' as an int, and intializes it as 0
    boolean cheats;                     //Declares the variable 'cheats' as a boolean
    boolean bot;                        //Declares the variable 'bot' as a boolean
    boolean leaveGame;                  //Declares the variable 'leaveGame' as a boolean
    int[] wager = new int[2];           //Declares the array 'wager' as a 1D int array, and intializes it with the size of 2
    int[] score = new int[2];           //Declares the array 'score' as a 1D int array, and intializes it with the size of 2
    int[] DD;                           //Declares the array 'DD' as a 1D int array
    String[][] questions = new String[5][5];    //Declares the array 'questions' as a 2D String array, and intializes it with the size of 5x5
    String[][] answers = new String[5][5];      //Declares the array 'answers' as a 2D String array, and intializes it with the size of 5x5
    String[][] highscore = new String[2][10];   //Declares the array 'highscore' as a 2D String array, and intializes it with the size of 2x10
    int[][] fin = new int[5][5];                //Declares the array 'fin' as a 2D int array, and intializes it with the size of 5x5
    int[][] dDouble = new int[5][5];            //Declares the array 'dDouble' as a 2D int array, and intializes it with the size of 5x5
    String[] topics = new String[5];            //Declares the array 'topics' as a 1D String array, and intializes it with the size of 5
    ImageIcon icon = new ImageIcon(System.getProperty("user.dir")+"\\Text.png"); //Declares the ImageIcon 'icon' as an ImageIcon, and intializes it with the image file
    //String soundFile = (System.getProperty("user.dir") + "\\Think.wav");
    //AudioStream audioStream;
    
    //Jeopardy class constructor
    public Jeopardy() {
	c = new Console(30, 100, "Jeopardy!");     //Creates a new Console object window
    }
    
    /* 'title' method: Clears the screen, then outputs a centred title on top of the screen
			
       Local Variable Dictionary:

	Variable Name    Variable Type              Variable Description    
       ____________________________________________________________________________
       
	     size             int        Title font size.

	     x, y             int        Title coordinates.
    */
    private void title(int size, int x, int y) {
	c.clear();
	c.setFont(new Font("Impact", Font.PLAIN, size));
	c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
	c.drawString("JEOPARDY!", x, y);
    }
    
    // 'splashScreen' method: Runs a thread that displays the splashscreen animation
    public void splashScreen() {
	SplashScreen ss = new SplashScreen (c);
	ss.run ();
    }
    
    //audio playing methods, not used in this submission
    /*
    private void playSound() {
	try {
	    InputStream in = new FileInputStream(soundFile);
	    audioStream = new AudioStream(in);
	    AudioPlayer.player.start(audioStream);
	} catch (Exception e) {
	}
    }
    private void stopSound() {
	try {
	    if (audioStream != null) {
		AudioPlayer.player.stop(audioStream);
	    }
	} catch (Exception e) {
	}
    }
    */
    
    /* 'mainMenu' method: calls the 'title' method
			  displays 4 options to the user, and lets the user choose
			  contains a while loop and an if statements to take input
			
       Local Variable Dictionary:

	Variable Name    Variable Type              Variable Description    
       ____________________________________________________________________________
       
	     opt             int         Stores current menu option.
    */
    public void mainMenu() {
	c.setTextBackgroundColor(new Color(25, 110, 255)); //Sets background color of the screens to blue
	c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
	//Assigns values to variables.
	int opt = 1;
	cheats = false;
	bot = true;
	leaveGame = false;
	player = 0;
	level = 1;
	score[0] = 0;
	score[1] = 0;
	wager[0] = 0;
	wager[1] = 0;
	k = ' ';
	title(35, 330, 40); //Executes 'title' method
	//Displays Main Menu, and its options
	c.setFont(new Font("Corbel", Font.BOLD, 50));
	c.drawString("MAIN MENU", 265, 120);
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	c.drawString("INSTRUCTIONS", 263, 220);
	c.drawString("PLAY", 345, 280);
	c.drawString("HIGHSCORES", 280, 340);
	c.drawString("EXIT", 355, 400);
	//Small navigation hint at the bottom
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	c.drawString("Use WASD to navigate", 300, 550);
	//Loop runs (program keeps taking input) until a valid choice is entered
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	while(true) {
	    c.setColor(new Color(25, 110, 255)); //Erase
	    c.fillRect(190, 180, 420, 240);
	    c.setColor(new Color(70, 160, 255)); //Highlighter
	    c.fillRect(250, 182+60*(opt-1), 302, 50);
	    //Options text
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("INSTRUCTIONS", 263, 220);
	    c.drawString("PLAY", 345, 280);
	    c.drawString("HIGHSCORES", 280, 340);
	    c.drawString("EXIT", 355, 400);
	    k = c.getChar(); //Sets value of 'k' to user input
	    //If the user hits ENTER, break out of the loop, after setting the choice to the selected option
	    if(k == 10) {
		choice = String.valueOf(opt);
		break;
	    }
	    //If the user hits WASD, move the highlighter
	    if(opt > 1 && opt < 4) {
		if(k == 's' || k == 'S' || k == 'd' || k == 'D')
		    opt++;
		else if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
		    opt--;
	    } else if(opt == 1) {
		if(k == 's' || k == 'S' || k == 'd' || k == 'D')
		    opt++;
	    } else {
		if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
		    opt--;
	    }
	}
    }
    /* 'instructions' method: calls the 'title' method
			      outputs all the instructions for the program
			
       Local Variable Dictionary:

	Variable Name    Variable Type              Variable Description    
       ____________________________________________________________________________
       
	     page             int        Stores current instruction book page.

	     opt              int        Stores current page option.
    */
    public void instructions() {
	//Assigns values to variables
	int page = 1;
	int opt;
	k = ' ';
	//Loop runs and displays instructions until user chooses to exit
	while(true) {
	    title(35, 330, 40); //Executes 'title' method
	    opt = 2; //Sets 'opt' to 2
	    //If on the first page, display text below
	    if(page == 1) {
		c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
		c.setFont(new Font("Corbel", Font.BOLD, 50));
		c.drawString("INSTRUCTIONS", 230, 120);
		c.setFont(new Font("Corbel", Font.BOLD, 22));
		c.drawString("This program is a version of the TV quiz show Jeopardy!. You can choose", 50, 180);
		c.drawString("to play either Jeopardy! or Double Jeopardy (Level 1 or 2). Each level will", 50, 210);
		c.drawString("end with Final Jeopardy!, or the last question.", 50, 240);
		c.drawString("There are 5 topics, each with 5 questions. Level 1 scores in increments of", 50, 280);
		c.drawString("$100, and level 2 in increments of $200.", 50, 310);
		c.drawString("Use the WASD and ENTER keys to navigate and select each panel. If it's", 50, 350);
		c.drawString("player 1's turn, press '1' to display the question. If it's player 2's turn, press", 50, 380);
		c.drawString("'2' to display. Both players have 5 seconds to read the question and press", 50, 410);
		c.drawString("their buzzer (keys '1' and '2', respectively). Whoever pressed the buzzer", 50, 440);
		c.drawString("first, may answer first. If the player answers incorrectly, the other player", 50, 470);
		c.drawString("automatically gets a chance to answer. Each player gets 10 seconds to", 50, 500);
		//Loop runs (program keeps taking input) until a valid choice is entered
		while(true) {
		    c.setColor(new Color(25, 110, 255)); //Erase
		    c.fillRect(45, 520, 710, 55);
		    c.setColor(new Color(70, 160, 255)); //Highlighter
		    c.fillRect(50+275*(opt-1), 522, 150, 50); 
		    //Options text
		    c.setColor(new Color(240, 185, 20));
		    c.setFont(new Font("Corbel", Font.BOLD, 40));
		    c.drawString("MENU", 345, 560);
		    c.drawString("NEXT", 625, 560);
		    k = c.getChar(); //Sets value of 'k' to user input
		    //If the user hits ENTER, break out of the loop
		    if(k == 10) {
			break;
		    }
		    //If the user hits WASD, move the highlighter
		    if(opt == 2) {
			if(k == 's' || k == 'S' || k == 'd' || k == 'D')
			    opt++;
		    } else {
			if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
			    opt--;
		    }
		}
		//If option is 2, return to menu
		if(opt == 2) {
		    return;
		//Else, next page
		} else {
		    page++;
		}
	    //If on the second page, display text below
	    } else if(page == 2) {
		c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
		c.setFont(new Font("Corbel", Font.BOLD, 50));
		c.drawString("INSTRUCTIONS", 230, 120);
		c.setFont(new Font("Corbel", Font.BOLD, 22));
		c.drawString("answer. If the buzzer is not pressed, or both answers are incorrect, the", 50, 180);
		c.drawString("answer will be displayed, and the game will continue. The player who", 50, 210);
		c.drawString("chose the previous question will choose again. Correct spelling is necessary.", 50, 240);
		c.drawString("You must answer the questions in level 2, daily doubles, and the final", 50, 280);
		c.drawString("question, with \"What is\" to get points.", 50, 310);
		c.drawString("A daily double is hidden among the questions on each level. Level 1 has", 50, 350);
		c.drawString("one daily double, while level 2 has two. In the daily double, the player who", 50, 380);
		c.drawString("chose the question gets exclusive rights to answer the question. In both the", 50, 410);
		c.drawString("final question and the daily double, the player(s) must make a wager on", 50, 440);
		c.drawString("their answer. They will win or lose the amount they bet depending if they", 50, 470);
		c.drawString("are correct or not. Players get 30 seconds each to answer these questions.", 50, 500);
		//Loop runs (program keeps taking input) until a valid choice is entered
		while(true) {
		    c.setColor(new Color(25, 110, 255)); //Erase
		    c.fillRect(45, 520, 710, 55);
		    c.setColor(new Color(70, 160, 255)); //Highlighter
		    c.fillRect(50+275*(opt-1), 522, 150, 50);
		    //Options text
		    c.setColor(new Color(240, 185, 20));
		    c.setFont(new Font("Corbel", Font.BOLD, 40));
		    c.drawString("PREV", 75, 560);
		    c.drawString("MENU", 345, 560);
		    c.drawString("NEXT", 625, 560);
		    k = c.getChar(); //Sets value of 'k' to user input
		    //If the user hits ENTER, break out of the loop
		    if(k == 10) {
			break;
		    }
		    //If the user hits WASD, move the highlighter
		    if(opt == 2) {
			if(k == 's' || k == 'S' || k == 'd' || k == 'D')
			    opt++;
			else if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
			    opt--;
		    } else if(opt == 1) {
			if(k == 's' || k == 'S' || k == 'd' || k == 'D')
			    opt++;
		    } else {
			if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
			    opt--;
		    }
		}
		//If option is 2, return to menu
		if(opt == 2) {
		    return;
		//Else if option is 3, next page
		} else if(opt == 3) {
		    page++;
		//Else, previous page
		} else {
		    page--;
		}
	    //Else, display text below
	    } else {
		c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
		c.setFont(new Font("Corbel", Font.BOLD, 50));
		c.drawString("INSTRUCTIONS", 230, 120);
		c.setFont(new Font("Corbel", Font.BOLD, 22));
		c.drawString("Please remember to press enter when choosing menu options and", 50, 180);
		c.drawString("answering questions, and press '1' or '2' when choosing questions or hitting", 50, 210);
		c.drawString("the buzzer ingame. Always use WASD to navigate.", 50, 240);
		c.drawString("When you press play in main menu, you will be prompted for either 1 or 2", 50, 280);
		c.drawString("players (PvE and PvP). In one player mode, the player will be going against", 50, 310);
		c.drawString("a bot. Two player mode can be played with a friend. Next, you can choose", 50, 340);
		c.drawString("a level to play. Finally, you can choose to turn on cheat mode or not.", 50, 370);
		c.drawString("Please remember that if you play with cheat mode on, your scores will not", 50, 400);
		c.drawString("count and will not be recorded. If you press highscores in main menu, it will", 50, 430);
		c.drawString("display the top 10 scores for each level. You can choose to clear highscores.", 50, 460);
		c.drawString("You can press '~' or SHIFT+ ` anytime to quit the game. Happy quizzing!", 50, 500);
		//Loop runs (program keeps taking input) until a valid choice is entered
		while(true) {
		    c.setColor(new Color(25, 110, 255)); //Erase
		    c.fillRect(45, 520, 710, 55);
		    c.setColor(new Color(70, 160, 255)); //Highlighter
		    c.fillRect(50+275*(opt-1), 522, 150, 50);
		    //Options Text
		    c.setColor(new Color(240, 185, 20));
		    c.setFont(new Font("Corbel", Font.BOLD, 40));
		    c.drawString("PREV", 75, 560);
		    c.drawString("MENU", 345, 560);
		    k = c.getChar(); //Sets value of 'k' to user input
		    //If the user hits ENTER, break out of the loop
		    if(k == 10) {
			break;
		    }
		    //If the user hits WASD, move the highlighter
		    if(opt == 2) {
			if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
			    opt--;
		    } else {
			if(k == 's' || k == 'S' || k == 'd' || k == 'D')
			    opt++;
		    }
		}
		//If option is 2, return to menu
		if(opt == 2) {
		    return;
		//Else, previous page
		} else {
		    page--;
		}
	    }
	}
    }
    /* 'highscores' method: calls the 'title' method
			    outputs top 10 highscores per level
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
       
	     in         BufferedReader     Reads files
	     
	    out          PrintWriter       Writes to files

	    temp            String         Stores strings temporarily
    */
    public void highscores() {
	title(35, 330, 40); //Executes 'title' method
	//Assigns values to variables
	BufferedReader in;
	String temp = "";
	k = ' ';
	//Sets highscore array to null
	for(int y = 0; y < 10; y++) {
	    highscore[0][y] = null;
	    highscore[1][y] = null;
	}
	c.setColor(new Color(240, 185, 20)); //Set text color to yellow
	c.setFont(new Font("Corbel", Font.BOLD, 50));
	c.drawString("HIGHSCORES", 250, 120);
	c.setFont(new Font("Corbel", Font.BOLD, 18));
	c.drawString("Level "+level, 375, 140);
	try {
	    //Checks if file exists
	    if (!new File("Highscores"+level+".txt").exists ()) {
		throw new IllegalArgumentException();
	    }
	    //Opens file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    temp = in.readLine();
	    //Closes file
	    in.close();
	    //Checks for correct file heading
	    if(!temp.equals("Jeopardy! Highscore File - By Eric Shim"))
		throw new NoSuchFieldException();
	} catch(NoSuchFieldException e) {
	    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //To exit screen
	    return;
	} catch(IllegalArgumentException e) {
	    JOptionPane.showMessageDialog(null, "Highscore file not found. Try again", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //To exit screen
	    return;
	} catch(IOException e) {
	}
	
	try {
	    int line = 0;
	    //Opens file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    while(true) {
		temp = in.readLine ();
		if (temp == null)
		    break;
		line++;
	    }
	    //Counts for correct number of lines in file
	    if(line != 22)
		throw new NoSuchFieldException();
	    //Closes file
	    in.close();
	} catch(NoSuchFieldException e) {
	    JOptionPane.showMessageDialog(null, "Invalid files. Please check questions folder and ensure that all question files are valid.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //To exit screen
	    return; 
	} catch(IOException e) {
	}
	try {
	    //Opens file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    in.readLine();
	    in.readLine();
	    for(int y = 0; y < 10; y++) {
		temp = in.readLine();
		if(temp.equals("null"))
		    break;
		//Checks if name contains only alphanumeric characters plus SPACE, '-', '_'
		if(!temp.matches("[a-zA-Z0-9 -_]+")) {
		    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		    choice = "4"; //To exit screen
		    return;
		}
		highscore[0][y] = temp;
		temp = in.readLine();
		if(temp.equals("null"))
		    break;
		//Checks if score is numeric
		try {
		    int num = Integer.parseInt(temp);
		} catch(NumberFormatException e) {
		    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		    choice = "4"; //To exit screen
		    return;
		}
		highscore[1][y] = temp;
	    }
	    //CLoses file
	    in.close();
	} catch(IOException e) {
	}
	c.setColor(new Color(240, 185, 20)); //Sets text color to yellow
	c.setFont(new Font("Corbel", Font.BOLD, 25));
	//Displays highscores
	if(highscore[0][0] == null && highscore[1][0] == null) {
	    c.drawString("No recorded players.", 285, 250);
	} else {
	    for(int y = 0; y < 10; y++) {
		if(highscore[0][y] == null || highscore[1][y] == null) {
		    break;
		}
		c.drawString((y+1)+".", 160, 240+(y*28));
		c.drawString(highscore[0][y], 240, 240+(y*28));
		c.drawString("$"+highscore[1][y], 560, 240+(y*28));
	    }
	}
	//Loop runs (program keeps taking input) until a valid choice is entered
	while(true) {
	    c.setColor(new Color(25, 110, 255)); //Erase
	    c.fillRect(45, 150, 710, 55);
	    c.fillRect(45, 520, 710, 55);
	    c.setColor(new Color(70, 160, 255)); //Highlighter
	    c.fillRect(200+250*optX, 152+370*optY, 150, 50);
	    //Options text
	    c.setColor(new Color(240, 185, 20)); //Set text color to yellow
	    c.setFont(new Font("Corbel", Font.BOLD, 38));
	    c.drawString("LEVEL 1", 208, 190);
	    c.drawString("LEVEL 2", 458, 190);
	    c.setFont(new Font("Corbel", Font.BOLD, 40));
	    c.drawString("MENU", 220, 560);
	    c.drawString("CLEAR", 465, 560);
	    k = c.getChar(); //Sets value of 'k' to user input
	    //If the user hits ENTER, break out of the loop
	    if(k == 10) {
		break;
	    }
	    //If the user hits WASD, move the highlighter
	    if(optX == 0) {
		if(k == 'd' || k == 'D')
		    optX++;
	    } else {
		if(k == 'a' || k == 'A')
		    optX--;
	    }
	    if(optY == 0) {
		if(k == 's' || k == 'S')
		    optY++;
	    } else {
		if(k == 'w' || k == 'W')
		    optY--;
	    }
	}
	//Options handler
	if(optY == 0) {
	    if(optX == 0)
		level = 1;
	    else
		level = 2;
	} else {
	    if(optX == 0) {
		return;
	    //Clears highscores
	    } else {
		PrintWriter out;
		try {
		    //Opens file
		    out = new PrintWriter(new FileWriter("Highscores"+level+".txt"));
		    out.println("Jeopardy! Highscore File - By Eric Shim");
		    out.println();
		    //Overwrites everything to "null"
		    for(int y = 0; y < 20; y++) {
			out.println("null");
		    }
		    //Closes file
		    out.close();
		} catch(IOException e) {
		}
	    }
	}
	highscores(); //Executes 'highscores' method
    }
    
    /* 'updateHighscores' method: calls the 'title' method
				  adds new scores to highscores(if needed)
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
       
	     in         BufferedReader     Reads files
	     
	    out          PrintWriter       Writes to files

	    temp            String         Stores strings temporarily
	    
	   write          String[][]       Stores highscores temporily before writing
	   
	 addScore1         boolean         Adds P1 score to highscores only once
	 
	 addScore2         boolean         Adds P2 score to highscores only once
	 
	     h               int           Highscores iterating variable
    */
    private void updateHighscores() {
	//Assigns values
	BufferedReader in;
	String temp = "";
	String[][] write = new String[2][10];
	//Sets highscore array to null
	for(int y = 0; y < 10; y++) {
	    highscore[0][y] = null;
	    highscore[1][y] = null;
	}
	try {
	    //Checks if file exists
	    if (!new File("Highscores"+level+".txt").exists ()) {
		throw new IllegalArgumentException();
	    }
	    //Opens file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    temp = in.readLine();
	    //Closes file
	    in.close();
	    //Checks for correct file heading
	    if(!temp.equals("Jeopardy! Highscore File - By Eric Shim"))
		throw new NoSuchFieldException();
	} catch(NoSuchFieldException e) {
	    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //To exit screen
	    return;
	} catch(IllegalArgumentException e) {
	    JOptionPane.showMessageDialog(null, "Highscore file not found. Please check game folder and ensure that the highscore file is there.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //To exit screen
	    return;
	} catch(IOException e) {
	}
	try {
	    int line = 0;
	    //opens file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    while(true) {
		temp = in.readLine ();
		if (temp == null)
		    break;
		line++;
	    }
	    //Counts for correct number of lines in file
	    if(line != 22)
		throw new NoSuchFieldException();
	    //closes file
	    in.close();
	} catch(NoSuchFieldException e) {
	    JOptionPane.showMessageDialog(null, "Invalid files. Please check questions folder and ensure that all question files are valid.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
	    choice = "4"; //to exit screen
	    return;
	} catch(IOException e) {
	}
	try {
	    //open file
	    in = new BufferedReader(new FileReader("Highscores"+level+".txt"));
	    in.readLine();
	    in.readLine();
	    for(int y = 0; y < 10; y++) {
		temp = in.readLine();
		if(temp.equals("null"))
		    break;
		//Checks if name contains only alphanumeric characters plus SPACE, '-', '_'
		if(!temp.matches("[a-zA-Z0-9 -_]+")) {
		    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		    choice = "4"; //to exit screen
		    return;
		}
		highscore[0][y] = temp;
		temp = in.readLine();
		if(temp.equals("null"))
		    break;
		//Checks if score is numeric
		try {
		    int num = Integer.parseInt(temp);
		} catch(NumberFormatException e) {
		    JOptionPane.showMessageDialog(null, "Invalid highscore file. Please check game folder and ensure that the highscore file is valid.", "Jeopardy! Highscore Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		    choice = "4"; //to exit screen
		    return;
		}
		highscore[1][y] = temp;
	    }
	    //closes file
	    in.close();
	} catch(IOException e) {
	}
	//Assigns values
	int h = 0;
	boolean addScore1 = false;
	boolean addScore2 = false;
	//Adds to highscores
	for(int w = 0; w < 10; w++) {
	    if(highscore[1][h] == null)
		break;
	    if(Integer.parseInt(highscore[1][h]) > score[player]) {
		write[0][w] = highscore[0][h];
		write[1][w] = highscore[1][h];
		h++;
	    } else if(!addScore1 && Integer.parseInt(highscore[1][h]) <= score[0]) {
		write[0][w] = name[0];
		write[1][w] = String.valueOf(score[0]);
		addScore1 = true;
	    } else if(!addScore2 && Integer.parseInt(highscore[1][h]) <= score[1]) {
		write[0][w] = name[1];
		write[1][w] = String.valueOf(score[1]);
		addScore2 = true;
	    } else {
		write[0][w] = highscore[0][h];
		write[1][w] = highscore[1][h];
		h++;
	    }
	}
	//Updates highscores
	PrintWriter out;
	try {
	    //opens file
	    out = new PrintWriter(new FileWriter("Highscores"+level+".txt"));
	    out.println("Jeopardy! Highscore File - By Eric Shim");
	    out.println();
	    for(int y = 0; y < 10; y++) {
		out.println(write[0][y]);
		out.println(write[1][y]);
	    }
	    //closes file
	    out.close();
	} catch(IOException e) {
	}
    }
    /* 'askData' method: calls the 'title' method
			 contains if statements for errortrapping
			 decides program flow
			
      Local Variable Dictionary:

       Variable Name    Variable Type              Variable Description    
      ____________________________________________________________________________

	    opt              int        Stores current option.

    */
    public void askData() {
	//Assigns values
	k = ' ';
	int opt = 1;
	title(35, 330, 40); //Executes 'title' method
	c.setFont(new Font("Corbel", Font.BOLD, 50));
	//Displays game modes
	c.drawString("GAME MODE", 260, 120);
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	//Loop runs (program keeps taking input) until a valid choice is entered
	while(true) {
	    c.setColor(new Color(25, 110, 255)); //Erase
	    c.fillRect(190, 180, 420, 240);
	    c.setColor(new Color(70, 160, 255)); //Highlighter
	    c.fillRect(250, 242+60*(opt-1), 302, 50);
	    //Options text
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("1 PLAYER (PvE)", 267, 280);
	    c.drawString("2 PLAYER (PvP)", 267, 340);
	    k = c.getChar(); //Sets value of 'k' to user input
	    //If the user hits TILDA, return to menu
	    if(k == '~') {
		return;
	    //Else if the user hits ENTER, break out of the loop
	    } else if(k == 10) {
		break;
	    }
	    //If the user hits WASD, move highlighter
	    if(opt == 1) {
		if(k == 's' || k == 'S' || k == 'd' || k == 'D')
		    opt++;
	    } else {
		if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
		    opt--;
	    }
	}
	//Checks for bot mode
	if(opt == 1) {
	    bot = true;
	} else {
	    bot = false;
	}
	//Assigns values
	k = ' ';
	name[0] = "";
	name[1] = "";
	title(35, 330, 40); //Executes 'title' method
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	//Name input
	c.drawString("Enter player 1's name:", 10, 148);
	c.drawString("Enter player 2's name:", 10, 218);
	c.setColor(new Color(70, 160, 255));
	c.fillRect(205, 128, 550, 28);
	c.setColor(new Color(240, 185, 20));
	c.drawString(name[0]+"|", 210, 148); //Cursor is '|'
	//Loop runs (program keeps taking input) until a valid choice is entered
	while(true) {
	    while(true) {
		k = c.getChar(); //Sets value of 'k' to user input
		//If the user hits TILDA, return to menu
		if(k == '~') {
		    return;
		//Else if the user hits ENTER, break out of the loop
		} else if(k == 10) {
		    break;
		//Else if the user hits BACKSPACE, delete last character
		} else if(k == 8) {
		    if(name[0].length() > 0)
			name[0] = name[0].substring(0, name[0].length()-1);
		//Else add character to string
		} else {
		    if(name[0].length() < 10)
			name[0] += k;
		}
		c.setColor(new Color(70, 160, 255));
		c.fillRect(205, 128, 550, 28);
		c.setColor(new Color(240, 185, 20));
		c.drawString(name[0]+"|", 210, 148); //Draws/types name
	    }
	    //Checks for invalid names
	    try {
		if(name[0].length() == 0)
		    throw new NoSuchFieldException();
		if(!name[0].matches("[a-zA-Z0-9 -_]+"))
		    throw new Exception();
		break;
	    } catch(NoSuchFieldException e) {
		JOptionPane.showMessageDialog(null, "Your name cannot be blank!", "Jeopardy! Name Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		name[0] = "";
	    } catch(Exception e) {
		JOptionPane.showMessageDialog(null, "Your name cannot contain special characters!", "Jeopardy! Name Error", JOptionPane.ERROR_MESSAGE, icon); //Creates JOptionPane object with the error message in it
		name[0] = "";
	    }
	}
	c.setColor(new Color(25, 110, 255));
	c.fillRect(205, 128, 550, 28);
	c.setColor(new Color(240, 185, 20));
	c.drawString(name[0], 210, 148); //Draws name
	
	c.setColor(new Color(70, 160, 255));
	c.fillRect(205, 198, 550, 28);
	c.setColor(new Color(240, 185, 20));
	c.drawString(name[1]+"|", 210, 218);
	if(!bot) { //Checks for bot
	    //name input, same procedure
	    while(true) {
		while(true) {
		    k = c.getChar();
		    if(k == '~') {
			return;
		    } else if(k == 10) {
			break;
		    } else if(k == 8) {
			if(name[1].length() > 0)
			    name[1] = name[1].substring(0, name[1].length()-1);
		    } else {
			if(name[1].length() < 10)
			    name[1] += k;
		    }
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(205, 198, 550, 28);
		    c.setColor(new Color(240, 185, 20));
		    c.drawString(name[1]+"|", 210, 218);
		}
		try {
		    if(name[1].length() == 0)
			throw new NoSuchFieldException();
		    if(name[1].equals(name[0]))
			throw new IllegalArgumentException();
		    if(!name[1].matches("[a-zA-Z0-9 -_]+"))
			throw new Exception();
		    break;
		} catch(NoSuchFieldException e) {
		    JOptionPane.showMessageDialog(null, "Your name cannot be blank!", "Jeopardy! Name Error", JOptionPane.ERROR_MESSAGE, icon);
		    name[1] = "";
		} catch(IllegalArgumentException e) {
		    JOptionPane.showMessageDialog(null, "Your name cannot be the same as Player 1's name!", "Jeopardy! Name Error", JOptionPane.ERROR_MESSAGE, icon);
		    name[1] = "";
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(205, 198, 550, 28);
		    c.setColor(new Color(240, 185, 20));
		    c.drawString(name[1], 210, 220);
		} catch(Exception e) {
		    JOptionPane.showMessageDialog(null, "Your name cannot contain special characters!", "Jeopardy! Name Error", JOptionPane.ERROR_MESSAGE, icon);
		    name[1] = "";
		}
	    }
	//Else, on bot mode, automatically type out bot's name
	} else {
	    for(int x = 0; x < botName.length(); x++) {
		name[1] += botName.charAt(x);
		c.setColor(new Color(70, 160, 255));
		c.fillRect(205, 198, 550, 28);
		c.setColor(new Color(240, 185, 20));
		c.drawString(name[1]+"|", 210, 218);
		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		}
	    }
	    try {
		Thread.sleep(400);
	    } catch(Exception e) {
	    }
	}
	//Assigns values
	k = ' ';
	opt = 1;
	title(35, 330, 40); //Execute 'title' method
	//Displays game levels
	c.setFont(new Font("Corbel", Font.BOLD, 50));
	c.drawString("LEVEL SELECT", 240, 120);
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	//Highlighter input
	while(true) {
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(190, 180, 420, 240);
	    c.setColor(new Color(70, 160, 255));
	    c.fillRect(250, 242+60*(opt-1), 302, 50);
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("LEVEL 1", 330, 280);
	    c.drawString("LEVEL 2", 330, 340);
	    k = c.getChar();
	    if(k == '~') {
		return;
	    } else if(k == 10) {
		break;
	    }
	    if(opt == 1) {
		if(k == 's' || k == 'S' || k == 'd' || k == 'D')
		    opt++;
	    } else {
		if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
		    opt--;
	    }
	}
	if(opt == 1) {
	    level = 1;
	} else {
	    level = 2;
	}
	//Stores chosen level to use in highscores later
	lvl = level;
	//Assign values
	k = ' ';
	opt = 1;
	title(35, 330, 40); //Execute 'title' method
	c.setFont(new Font("Corbel", Font.BOLD, 50));
	//Displays cheat mode options
	c.drawString("CHEAT MODE", 250, 120);
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	//highlighter input
	while(true) {
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(190, 180, 420, 240);
	    c.setColor(new Color(70, 160, 255));
	    c.fillRect(250, 242+60*(opt-1), 302, 50);
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("OFF", 365, 280);
	    c.drawString("ON", 370, 340);
	    k = c.getChar();
	    if(k == '~') {
		return;
	    } else if(k == 10) {
		break;
	    }
	    if(opt == 1) {
		if(k == 's' || k == 'S' || k == 'd' || k == 'D')
		    opt++;
	    } else {
		if(k == 'w' || k == 'W' || k == 'a' || k == 'A')
		    opt--;
	    }
	}
	if(opt == 1) {
	    cheats = false;
	} else {
	    cheats = true;
	}
    }
    
    /* 'dailyDouble' method: returns randomized daily double coordinates
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
       
	    arr             int[]          Stores coordinates
	     
	     p              int[]          Bin to make sure number isn't picked twice

	     a               int           Iterative variable
	    
	     n               int           Number of coordinate pairs needed
	   
       randX, randY          int           Random coordinates
    */
    private int[] dailyDouble(int n) {
	int[] arr = new int[2*n];
	int[] p = new int[n];
	int a = 0;
	while(a < n) {
	    try {
		int randX = (int)Math.round(Math.random()*4);
		int randY = (int)Math.round(Math.random()*3)+1;
		for(int i = 0; i < a; i++) {
		    if(randX == p[i])
			throw new Exception();
		}
		p[a] = randX;
		arr[a*2] = randX;
		arr[a*2+1] = randY;
		a++;
	    } catch(Exception e) {
	    }
	}
	return arr;
    }
    /* 'openQuestions' method: opens question files
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
       
	     in         BufferedReader     Reads file
	     
	 directory           File          File path of questions folder

	   files            File[]         Stores all files
	    
	    temp            String         Stores strings temporarily
	   
	    prev             int[]         Bin to make sure number isn't picked twice
	    
	    max               int          Stores files max index
	     
	     x                int          Iterative variable
    */
    private void openQuestions() {
	//Assign values
	BufferedReader in;
	DD = dailyDouble(level);
	File directory = new File(System.getProperty("user.dir")+"\\Round "+level); //https://stackoverflow.com/a/1844695/12454094, https://stackoverflow.com/a/7603444/12454094
	File[] files = directory.listFiles();
	String temp = "";
	int[] prev = new int[5];
	int max = files.length-1;
	int x = 0;
	//If level is 1 or 2
	if(level < 3) {
	    //Check for enough files
	    if(files.length < 5) {
		JOptionPane.showMessageDialog(null, "Not enough files. Please check questions folder and ensure that all question files are there.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon);
		choice = "4";
		return;
	    }
	    //Check for valid files
	    for(int i = 0; i < files.length; i++) {
		try {
		    int line = 0;
		    in = new BufferedReader(new FileReader(files[i]));
		    temp = in.readLine();
		    in.close();
		    if(!temp.equals("Jeopardy! Question File - By Eric Shim"))
			throw new NoSuchFieldException();
		    in = new BufferedReader(new FileReader(files[i]));
		    while(true) {
			temp = in.readLine ();
			if (temp == null)
			    break;
			line++;
		    }
		    if(line != 12)
			throw new NoSuchFieldException();
		    in.close();
		} catch(NoSuchFieldException e) {
		    JOptionPane.showMessageDialog(null, "Invalid files. Please check questions folder and ensure that all question files are valid.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon);
		    choice = "4";
		    return;
		} catch(IOException e) {
		}
	    }
	    //Store random questions, answers, topics
	    while(x < 5) {
		try {
		    int rand = (int)Math.round(Math.random()*max);
		    for(int i = 0; i < x; i++) {
			if(rand == prev[i])
			    throw new Exception();
		    }
		    prev[x] = rand;
		    x++;
		} catch(Exception e) {
		}
	    }
	    for(int i = 0; i < 5; i++) {
		try {
		    in = new BufferedReader(new FileReader(files[prev[i]]));
		    in.readLine();
		    topics[i] = in.readLine();
		    for(int j = 0; j < 5; j++) {
			questions[i][j] = in.readLine();
			answers[i][j] = in.readLine();
		    }
		    in.close();
		} catch(IOException e) {
		}
	    }
	//Else level is 3
	} else {
	    int line = 0;
	    //Check for right file
	    if(files.length != 1) {
		JOptionPane.showMessageDialog(null, "Invalid files. Please check questions folder and ensure that there is only 1 file there.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon);
		choice = "4";
		return;
	    }
	    try {
		if (!files[0].getName().equals("Final Jeopardy Questions.txt")) {
		    throw new IllegalArgumentException();
		}
		in = new BufferedReader(new FileReader(files[0]));
		temp = in.readLine();
		in.close();
		if(!temp.equals("Jeopardy! Question File - By Eric Shim")) {
		    throw new NoSuchFieldException();
		}
	    } catch(NoSuchFieldException e) {
		JOptionPane.showMessageDialog(null, "Invalid file. Please check questions folder and ensure that the file is valid.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon);
		choice = "4";
		return;
	    } catch(IllegalArgumentException e) {
		JOptionPane.showMessageDialog(null, "Question file not found. Please check game folder and ensure that the highscore file is there.", "Jeopardy! File Error", JOptionPane.ERROR_MESSAGE, icon);
		choice = "4";
		return;
	    } catch(IOException e) {
	    }
	    try {
		in = new BufferedReader(new FileReader(files[0]));
		while(true) {
		    temp = in.readLine ();
		    if (temp == null)
			break;
		    line++;
		}
		in.close();
	    } catch(IOException e) {
	    }
	    //Store random question and answer
	    try {
		in = new BufferedReader(new FileReader(files[0]));
		int rand = (int)Math.floor(Math.random()*(line-2)/2);
		in.readLine();
		in.readLine();
		for(int i = 0; i < rand; i++) {
		    in.readLine();
		    in.readLine();
		}
		questions[0][0] = in.readLine();
		answers[0][0] = in.readLine();
		in.close();
	    } catch(Exception e) {
	    }
	}
	//Set all array values to -1
	for(int j = 0; j < 5; j++) {
	    for(int i = 0; i < 5; i++) {
		fin[i][j] = -1;
	    }
	}
    }
    
    // 'coverScreen' method: Displays current round
    public void coverScreen() {
	openQuestions();
	if(choice.equals("4"))
	    return;
	c.clear();
	if(level == 1) {
	    c.setFont(new Font("Impact", Font.PLAIN, 100));
	    c.setColor(new Color(255, 210, 25));
	    c.drawString("JEOPARDY!", 200, 320);
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("LEVEL 1", 355, 350);
	} else if(level == 2) {
	    c.setFont(new Font("Impact", Font.PLAIN, 90));
	    c.setColor(new Color(255, 210, 25));
	    c.drawString("DOUBLE JEOPARDY!", 75, 320);
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("LEVEL 2", 355, 350);
	} else {
	    c.setFont(new Font("Impact", Font.PLAIN, 90));
	    c.setColor(new Color(255, 210, 25));
	    c.drawString("FINAL JEOPARDY!", 110, 320);
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("LAST QUESTION", 290, 350);
	}
	try {
	    Thread.sleep(2000);
	} catch(Exception e) {
	}
	//if level is 1 or 2
	if(level < 3) {
	    game();
	//else final question
	} else {
	    qX = 0;
	    qY = 0;
	    display();
	}
    }
    
    /* 'game' method: displays questions panel
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
	    
	    temp            String         Stores strings temporarily
	   
	    a, b             int           Used for bot path searching
    */
    public void game() {
	title(30, 340, 30); //Executes 'title' method
	//Assign values
	k = ' ';
	//Draws panels
	c.setColor(new Color(0, 51, 204));
	c.fillRect(0, 55, 800, 545);
	c.setColor(new Color(10, 90, 230));
	for(int x = 0; x < 5; x++) {
	    c.fillRect(5+x*159, 60, 154, 55);
	}
	for(int y = 0; y < 5; y++) {
	    for(int x = 0; x < 5; x++) {
		c.setFont(new Font("Corbel", Font.BOLD, 30));
		c.setColor(new Color(25, 110, 255));
		c.fillRect(5+x*159, 120+y*96, 154, 91);
		c.setColor(new Color(240, 185, 20));
		if(fin[x][y] == -1) {
		    if((y+1)*100*level != 1000)
			c.drawString("$"+String.valueOf((y+1)*100*level), 52+x*159, 170+y*96);
		    else
			c.drawString("$"+String.valueOf((y+1)*100*level), 44+x*159, 170+y*96);
		} else {
		    if(fin[x][y] == 2) {
			c.setFont(new Font("Corbel", Font.BOLD, 20));
			c.setColor(new Color(240, 185, 20));
			c.drawString("Nobody", 49+x*159, 170+y*96);
		    } else {
			c.setColor(new Color(240, 185, 20));
			if(dDouble[x][y] > 0) {
			    c.setFont(new Font("Corbel", Font.BOLD, 15));
			    c.drawString("Daily Double", 40+x*159, 190+y*96);
			}
			if(dDouble[x][y] == 2) {
			    c.setColor(new Color(220, 50, 50));
			}
			c.setFont(new Font("Corbel", Font.BOLD, 20));
			c.drawString("Player "+(fin[x][y]+1), 46+x*159, 170+y*96);
		    }
		}
	    }
	}
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 30));
	c.drawString("$"+String.valueOf(score[0]), 5, 46);
	c.drawString("$"+String.valueOf(score[1]), 535, 46);
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	c.drawString("It's player "+String.valueOf(player+1)+"'s turn", 325,48);
	for(int x = 0; x < 5; x++) {
	    for(int y = 0; y < Math.ceil((topics[x].length())/10.0); y++) {
		String temp = topics[x];
		if(y*10+10 >= topics[x].length())
		    temp = topics[x].substring(y*10, topics[x].length());
		else
		    temp = topics[x].substring(y*10, y*10+10);
		c.drawString(temp, 11+x*159, 80+y*25);
	    }
	}
	c.drawString("Player 1: "+name[0], 5, 20);
	c.drawString("Player 2: "+name[1], 535, 20);
	//Finds next unanswered panel
	while(fin[qX][qY] != -1) {
	    if(qX < 4) {
		qX++;
	    } else if(qX == 4 && qY == 4) {
		qX = 0;
		qY = 0;
	    } else if(qX == 4) {
		qX = 0;
		qY++;
	    }
	}
	//bot move
	if(bot && player == 1) {
	    //return to 0, 0
	    while(qX > 0) {
		c.setColor(new Color(240, 185, 20));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		try {
		    Thread.sleep(600);
		} catch(Exception e) {
		}
		c.setColor(new Color(0, 51, 204));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		qX--;
	    }
	    while(qY > 0) {
		c.setColor(new Color(240, 185, 20));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		try {
		    Thread.sleep(600);
		} catch(Exception e) {
		}
		c.setColor(new Color(0, 51, 204));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		qY--;
	    }
	    int a = qX;
	    int b = qY;
	    //path finding
	    while(fin[a][b] != -1) {
		if(a < 4) {
		    a++;
		} else if(a == 4 && b == 4) {
		    a = 0;
		    b = 0;
		} else if(a == 4) {
		    a = 0;
		    b++;
		}
	    }
	    //go to unanswered question
	    while(qX < a) {
		c.setColor(new Color(240, 185, 20));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		try {
		    Thread.sleep(600);
		} catch(Exception e) {
		}
		c.setColor(new Color(0, 51, 204));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		qX++;
	    }
	    while(qY < b) {
		c.setColor(new Color(240, 185, 20));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		try {
		    Thread.sleep(600);
		} catch(Exception e) {
		}
		c.setColor(new Color(0, 51, 204));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		qY++;
	    }
	    
	    //draw outline
	    c.setColor(new Color(240, 185, 20));
	    for(int x = 0; x < 5; x++) {
		c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
	    }
	    try {
		Thread.sleep(500);
	    } catch(Exception e) {
	    }
	    c.setColor(new Color(255, 255, 255, 40));
	    c.fillRect(5+qX*159, 120+qY*96, 154, 91);
	    try {
		Thread.sleep(100);
	    } catch(Exception e) {
	    }
	//Else player moves
	} else {
	    //Loops until '1', '2', or '~' is pressed
	    while(true) {
		//outline
		c.setColor(new Color(240, 185, 20));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		//WASD input
		while(true) {
		    k = c.getChar();
		    if(k == '~') {
			int confirm = JOptionPane.showConfirmDialog(null, "You have not finished the game! Current scores will not be saved. Exit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, icon);
			if(confirm == 0)
			    return;
		    } else if(k == '1' || k == '2' || k == 'a' || k == 'A'  || k == 'w' || k == 'W' || k == 's' || k == 'S' || k == 'd' || k == 'D') {
			break;
		    }
		}
		//erase
		c.setColor(new Color(0, 51, 204));
		for(int x = 0; x < 5; x++) {
		    c.drawRect(x+qX*159, 115+x+qY*96, 163-x*2, 100-x*2);
		}
		if(player == 0) {
		    if(k == '1' && fin[qX][qY] == -1) {
			break;
		    }
		} else if(player == 1) {
		    if(k == '2' && fin[qX][qY] == -1) {
			break;
		    }
		}
		if(qX > 0 && qX < 4) {
		    if(k == 'd' || k == 'D')
			qX++;
		    else if(k == 'a' || k == 'A')
			qX--;
		} else if(qX == 0) {
		    if(k == 'd' || k == 'D')
			qX++;
		} else {
		    if(k == 'a' || k == 'A')
			qX--;
		}
		if(qY > 0 && qY < 4) {
		    if(k == 's' || k == 'S')
			qY++;
		    else if(k == 'w' || k == 'W')
			qY--;
		} else if(qY == 0) {
		    if(k == 's' || k == 'S')
			qY++;
		} else {
		    if(k == 'w' || k == 'W')
			qY--;
		}
	    }
	}
	//check if location is a daily double
	for(int a = 0; a < level*2; a += 2) {
	    if(DD[a] == qX && DD[a+1] == qY) {
		dDouble[qX][qY] = 1;
		break;
	    }
	}
	//If a daily double
	if(dDouble[qX][qY] == 1) {
	    c.clear();
	    c.setFont(new Font("Impact", Font.PLAIN, 90));
	    c.setColor(new Color(255, 210, 25));
	    c.drawString("DAILY DOUBLE!", 150, 330);
	    try {
		Thread.sleep(2000);
	    } catch(Exception e) {
	    }
	    wager(); //Execute 'wager' method
	}
	if(leaveGame == true)
	    return;
	display(); //Execute 'display' method
    }
    
    /* 'wager' method: takes user input as their wager
			
      Local Variable Dictionary:

       Variable Name    Variable Type              Variable Description    
      ____________________________________________________________________________

	    wgr            String         Stores bot answer

    */
    public void wager() {
	c.clear();
	c.setColor(new Color(0, 51, 204));
	c.fillRect(0, 0, 800, 600);
	c.setColor(new Color(25, 110, 255));
	c.fillRect(20, 20, 760, 560);
	c.setColor(new Color(70, 160, 255));
	c.fillRect(100, 288, 550, 28);
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 30));
	c.drawString("It is player "+(player+1)+"'s wager", 270, 60);
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	c.drawString("Wager:", 30, 308);
	if(level == 3 && !bot) {
	    c.drawString("Player "+(Math.abs(player-1)+1)+" please look away", 290, 85);
	}
	if(bot && player == 1) {
	    String wgr = "$";
	    str = "";
	    if((qY+1)*100*level < score[player]) {
		wgr += String.valueOf(score[player]);
	    } else {
		wgr += String.valueOf((qY+1)*100*level);
	    }
	    c.drawString("|", 105, 308);
	    for(int x = 0; x < wgr.length(); x++) {
		str += wgr.charAt(x);
		c.setColor(new Color(70, 160, 255));
		c.fillRect(100, 288, 550, 28);
		c.setColor(new Color(240, 185, 20));
		c.drawString(str+"|", 105, 308);
		try {
		    Thread.sleep(100);
		} catch(Exception e) {
		}
	    }
	    str = str.substring(1);
	    wager[player] = Integer.parseInt(str);
	} else {
	    while(true) {
		str = "";
		c.setColor(new Color(70, 160, 255));
		c.fillRect(100, 288, 550, 28);
		c.setColor(new Color(240, 185, 20));
		c.drawString("|", 105, 308);
		while(true) {
		    k = c.getChar();
		    if(k == '~') {
			int confirm = JOptionPane.showConfirmDialog(null, "You have not finished the game! Current scores will not be saved. Exit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, icon);
			if(confirm == 0) {
			    leaveGame = true;
			    return;
			}
			continue;
		    }
		    if(k == 10) {
			break;
		    }
		    if(k == 8) {
			if(str.length() > 0)
			    str = str.substring(0, str.length()-1);
		    } else {
			if(str.length() < 28)
			    str += k;
		    }
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(100, 288, 550, 28);
		    c.setColor(new Color(240, 185, 20));
		    c.drawString(str+"|", 105, 308);
		}
		try {
		    if(str.indexOf("$") != -1)
			str = str.substring(1);
		    wager[player] = Integer.parseInt(str);
		    if(wager[player] == 666 || wager[player] == 69 || wager[player] == 14 || wager[player] == 88 || wager[player] == 1488) {
			throw new Exception();
		    }
		    if((qY+1)*100*level < score[player]) {
			if(wager[player] > score[player] || wager[player] < 5) {
			    throw new IllegalArgumentException();
			}
		    } else {
			if(wager[player] > (qY+1)*100*level || wager[player] < 5) {
			    throw new RuntimeException();
			}
		    }
		    break;
		} catch(NumberFormatException e) {
		    JOptionPane.showMessageDialog(null, "Please enter a numeric value as your wager. (or in format $####)", "Jeopardy! Invalid Wager Error", JOptionPane.ERROR_MESSAGE, icon);
		} catch(IllegalArgumentException e) {
		    JOptionPane.showMessageDialog(null, "Please enter a wager between 5 and $"+score[player], "Jeopardy! Invalid Wager Error", JOptionPane.ERROR_MESSAGE, icon);
		} catch(RuntimeException e) {
		    JOptionPane.showMessageDialog(null, "Please enter a wager between 5 and $"+(qY+1)*100*level, "Jeopardy! Invalid Wager Error", JOptionPane.ERROR_MESSAGE, icon);
		} catch(Exception e) {
		    JOptionPane.showMessageDialog(null, "This wager is banned!", "Jeopardy! Invalid Wager Error", JOptionPane.ERROR_MESSAGE, icon);
		}
	    }
	}
    }
    
    /* 'display' method: buzzer (sometimes)
			 question answering screen
			
       Local Variable Dictionary:

	Variable Name    Variable Type               Variable Description    
       ____________________________________________________________________________
	    
	    done             int           Keeps track of number of answered questions
	   
	   finalQ         boolean[]        Stores if answer is correct in final round
	   
	  finalAns         String[]        Stores player's answers in final round
	  
	   timer,          Thread
	   input,           class          Thread class reference variables
	   buzzer         reference
    */
    public void display() {
	//Assign values
	int done = 0;
	boolean[] finalQ = new boolean[2];
	String[] finalAns = new String[2];
	Timer timer;
	Input input;
	Buzzer buzzer;
	//2 wagers if in final round
	if(level == 3) {
	    dDouble[qX][qY] = 1;
	    player = 0;
	    wager();
	    player = 1;
	    wager();
	    player = 0;
	}
	c.clear();
	c.setColor(new Color(0, 51, 204));
	c.fillRect(0, 0, 800, 600);
	c.setColor(new Color(25, 110, 255));
	c.fillRect(20, 20, 760, 560);
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	c.drawString("Answer:", 30, 368);
	//Display answer if cheats are on
	if(cheats) {
	    if(level == 1) {
		c.drawString(answers[qX][qY], 115, 368);
	    } else if(level > 1 || dDouble[qX][qY] == 1) {
		c.drawString("What is "+answers[qX][qY], 115, 368);
	    }
	}
	c.setFont(new Font("Corbel", Font.BOLD, 26));
	c.drawString(questions[qX][qY], 30, 290);
	//if level 1 or 2 (and not daily double)
	if(dDouble[qX][qY] == 0) {
	    c.setColor(new Color(0, 51, 204));
	    c.fillRect(250, 50, 300, 90);
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(260, 60, 135, 70);
	    c.fillRect(405, 60, 135, 70);
	    c.setColor(new Color(10, 90, 230));
	    c.fillOval(265, 65, 60, 60);
	    c.fillOval(475, 65, 60, 60);
	    c.setColor(new Color(25, 110, 255));
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("(1)", 279, 102);
	    c.drawString("(2)", 489, 102);
	    c.setColor(new Color(240, 185, 20));
	    c.setFont(new Font("Corbel", Font.BOLD, 50));
	    c.drawString("P1", 330, 110);
	    c.drawString("P2", 415, 110);
	    //timer and buzzer
	    timer = new Timer(c, 5); //timer 5 seconds
	    timer.start();
	    buzzer = new Buzzer(c, bot);
	    buzzer.start();
	    k = ' ';
	    int randBot = (int)Math.floor(Math.random()*3);
	    long botStart = System.currentTimeMillis();
	    while(true) {
		//bot buzzer
		if(bot && Math.round((System.currentTimeMillis()-botStart)/1000) == randBot+2) {
		    k = '2';
		    timer.stop();
		    break;
		}
		//kill threads and break if either thread completes
		if(buzzer.hit) {
		    k = buzzer.k;
		    timer.stop();
		    break;
		}
		if(timer.end) {
		    timer.stop();
		    fin[qX][qY] = 2;
		    break;
		}
	    }
	    buzzer.stop();
	    if(fin[qX][qY] != 2) {
		if(k == '1')
		    player = 0;
		else if(k == '2')
		    player = 1;
		for(int i = 0; i < 2; i++) {
		    c.setColor(new Color(10, 90, 230));
		    c.fillOval(265, 65, 60, 60);
		    c.fillOval(475, 65, 60, 60);
		    c.setColor(new Color(220, 50, 50));
		    if(player == 0)
			c.fillOval(265, 65, 60, 60);
		    else
			c.fillOval(475, 65, 60, 60);
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(110, 348, 550, 28);
		    if(cheats) {
			c.setColor(new Color(25, 110, 255));
			c.setFont(new Font("Corbel", Font.BOLD, 20));
			if(level == 1) {
			    c.drawString(answers[qX][qY], 115, 368);
			} else if(level == 2 || dDouble[qX][qY] == 1) {
			    c.drawString("What is "+answers[qX][qY], 115, 368);
			}
		    }
		    //timer and input
		    timer = new Timer(c, 10); //timer 10 seconds
		    timer.start();
		    input = new Input(c, answers, qX, qY, icon, level, cheats, bot, player, dDouble);
		    input.start();
		    while(true) {
			//kill threads and break if either thread completes
			if(timer.end) {
			    timer.stop();
			    break;
			}
			if(input.exit) {
			    timer.stop();
			    break;
			}
		    }
		    
		    input.stop();
		    //if answer is right
		    if(input.contains) {
			c.setColor(new Color(102, 204, 51));
			for(int a = 0; a < 10; a++) {
			    c.fillOval(634+a, 360+a, 4, 4);
			    try {
				Thread.sleep(8);
			    } catch (Exception e) {
			    }
			}
			for(int a = 0; a < 10; a++) {
			    c.fillOval(643+a, 369-2*a, 4, 4);
			    try {
				Thread.sleep(8);
			    } catch (Exception e) {
			    }
			}
			fin[qX][qY] = player; //this question was finished by this player
			score[player] += (qY+1)*100; //deduct
			break;
		    //else is wrong
		    } else {
			c.setColor(new Color(220, 50, 50));
			for(int a = 0; a < 20; a++) {
			    c.fillOval(634+a, 350+a, 4, 4);
			    try {
				Thread.sleep(8);
			    } catch (Exception e) {
			    }
			}
			for(int a = 0; a < 20; a++) {
			    c.fillOval(653-a, 350+a, 4, 4);
			    try {
				Thread.sleep(16);
			    } catch (Exception e) {
			    }
			}
			score[player] -= (qY+1)*100; //deduct
			player = Math.abs(player-1); //switch player
			fin[qX][qY] = 2;
		    }
		    try {
			Thread.sleep(500);
		    } catch (Exception e) {
		    }
		}
	    }
	//else if its a daily double and level is 1 or 2
	} else if(dDouble[qX][qY] > 0 && level < 3) {
	    c.setColor(new Color(70, 160, 255));
	    c.fillRect(110, 348, 550, 28);
	    c.setColor(new Color(240, 185, 20));
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("It is player "+(player+1)+"'s turn", 280, 60);
	    //Display answer if cheats are on
	    if(cheats) {
		c.setColor(new Color(25, 110, 255));
		c.setFont(new Font("Corbel", Font.BOLD, 20));
		c.drawString("What is "+answers[qX][qY], 115, 68);
	    }
	    //playSound();
	    //timer and input
	    timer = new Timer(c, 30); //timer 30 seconds
	    timer.start();
	    input = new Input(c, answers, qX, qY, icon, level, cheats, bot, player, dDouble);
	    input.start();
	    while(true) {
		//kill threads and break if either thread completes
		if(timer.end) {
		    timer.stop();
		    break;
		}
		if(input.exit) {
		    timer.stop();
		    break;
		}
	    }
	    //stopSound();
	    input.stop();
	    //if answer is right
	    if(input.contains) {
		c.setColor(new Color(102, 204, 51));
		for(int a = 0; a < 10; a++) {
		    c.fillOval(634+a, 360+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 10; a++) {
		    c.fillOval(643+a, 369-2*a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		score[player] += wager[player]; //add
	    //else is wrong
	    } else {
		c.setColor(new Color(220, 50, 50));
		for(int a = 0; a < 20; a++) {
		    c.fillOval(634+a, 350+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 20; a++) {
		    c.fillOval(653-a, 350+a, 4, 4);
		    try {
			Thread.sleep(16);
		    } catch (Exception e) {
		    }
		}
		score[player] -= wager[player]; //deduct
		dDouble[qX][qY] = 2; //daily double answered wrong
	    }
	    fin[qX][qY] = player; //answered by player
	} else {
	    //final round
	    for(int x = 0; x < 2; x++) {
		c.setColor(new Color(25, 110, 255));
		c.fillRect(200, 20, 400, 100);
		c.setColor(new Color(240, 185, 20));
		c.setFont(new Font("Corbel", Font.BOLD, 30));
		c.drawString("It is player "+(player+1)+"'s turn", 280, 60);
		c.setFont(new Font("Corbel", Font.BOLD, 20));
		if(!bot) {
		    c.drawString("Player "+(Math.abs(player-1)+1)+" please look away", 290, 85);
		}
		c.setColor(new Color(70, 160, 255));
		c.fillRect(110, 348, 550, 28);
		if(cheats) {
		    c.setColor(new Color(25, 110, 255));
		    c.setFont(new Font("Corbel", Font.BOLD, 20));
		    c.drawString("What is "+answers[qX][qY], 115, 368);
		}
		//playSound();
		//timer, input
		timer = new Timer(c, 30); //timer 30 sec
		timer.start();
		input = new Input(c, answers, qX, qY, icon, level, cheats, bot, player, dDouble);
		input.start();
		while(true) {
		    //kill threads and break if either thread completes
		    if(timer.end) {
			timer.stop();
			break;
		    }
		    if(input.exit) {
			timer.stop();
			break;
		    }
		}
		//stopSound();
		input.stop();
		//stores player's answer
		finalAns[player] = input.str;
		//stores if player was correct or not
		if(input.contains) {
		    finalQ[player] = true;
		    score[player] += wager[player];
		} else {
		    finalQ[player] = false;
		    score[player] -= wager[player];
		}
		player = Math.abs(player-1); //switch player
	    }
	}
	if(level == 3) {
	    c.clear();
	    //displays answers
	    c.setColor(new Color(0, 51, 204));
	    c.fillRect(0, 0, 800, 600);
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(20, 20, 760, 560);
	    c.setColor(new Color(70, 160, 255));
	    c.fillRect(152, 75, 548, 34);
	    c.fillRect(120, 278, 580, 28);
	    c.fillRect(120, 378, 580, 28);
	    c.setColor(new Color(240, 185, 20));
	    c.setFont(new Font("Corbel", Font.BOLD, 30));
	    c.drawString("Answer:", 40, 100);
	    c.drawString("Player 1", 350, 250);
	    c.drawString("Player 2", 350, 350);
	    c.setFont(new Font("Corbel", Font.BOLD, 26));
	    c.drawString("What is "+answers[qX][qY], 160, 100);
	    c.setFont(new Font("Corbel", Font.BOLD, 20));
	    c.drawString("Answer:", 40, 298);
	    c.drawString("Answer:", 40, 398);
	    c.drawString("Press anything to display answers", 260, 150);
	    c.getChar();
	    c.drawString(finalAns[0], 125, 298);
	    c.drawString(finalAns[1], 125, 398);
	    //displays if answers are correct or not
	    if(finalQ[0]) {
		c.setColor(new Color(102, 204, 51));
		for(int a = 0; a < 10; a++) {
		    c.fillOval(672+a, 290+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 10; a++) {
		    c.fillOval(681+a, 299-2*a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
	    } else {
		c.setColor(new Color(220, 50, 50));
		for(int a = 0; a < 20; a++) {
		    c.fillOval(672+a, 280+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 20; a++) {
		    c.fillOval(691-a, 280+a, 4, 4);
		    try {
			Thread.sleep(16);
		    } catch (Exception e) {
		    }
		}
	    }
	    //displays if answers are correct or not
	    if(finalQ[1]) {
		c.setColor(new Color(102, 204, 51));
		for(int a = 0; a < 10; a++) {
		    c.fillOval(672+a, 390+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 10; a++) {
		    c.fillOval(681+a, 399-2*a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
	    } else {
		c.setColor(new Color(220, 50, 50));
		for(int a = 0; a < 20; a++) {
		    c.fillOval(672+a, 380+a, 4, 4);
		    try {
			Thread.sleep(8);
		    } catch (Exception e) {
		    }
		}
		for(int a = 0; a < 20; a++) {
		    c.fillOval(691-a, 380+a, 4, 4);
		    try {
			Thread.sleep(16);
		    } catch (Exception e) {
		    }
		}
	    }
	    try {
		Thread.sleep(2000);
	    } catch (Exception e) {
	    }
	    endScreen(); //execute 'endScreen' method
	    return;
	}
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	//if nobody got it right, display answer
	if(fin[qX][qY] == 2) {
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(110, 348, 550, 28);
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("Answer:", 30, 368);
	    if(level == 1) {
		c.drawString(answers[qX][qY], 115, 368);
	    } else if(level > 1 || dDouble[qX][qY] == 1) {
		c.drawString("What is "+answers[qX][qY], 115, 368);
	    }
	}
	try {
	    Thread.sleep(2000);
	} catch (Exception e) {
	}
	//counts finished questions
	for(int y = 0; y < 5; y++) {
	    for(int x = 0; x < 5; x++) {
		if(fin[x][y] != -1) {
		    done++;
		}
	    }
	}
	//program flow handler
	if(done < 25) {
	    game();
	} else if(score[0] <= 0 && score[1] <= 0) {
	    endScreen();
	    return;
	} else {
	    level = 3;
	    coverScreen();
	}
    }
    
    /* 'endScreen' method: Displays winner
			   updates highscores (sometimes)*/
    public void endScreen() {
	c.clear();
	c.setColor(new Color(0, 51, 204));
	c.fillRect(0, 0, 800, 600);
	c.setColor(new Color(25, 110, 255));
	c.fillRect(20, 20, 760, 560);
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 30));
	c.drawString("Player 1", 60, 300);
	c.drawString("Player 2", 430, 300);
	c.setFont(new Font("Corbel", Font.BOLD, 40));
	c.drawString("$"+score[0], 60, 400);
	c.drawString("$"+score[1], 430, 400);
	c.setFont(new Font("Impact", Font.PLAIN, 100));
	//if score is below zero or cheats on
	if(score[0] <= 0 && score[1] <= 0 || cheats) {
	    c.drawString("No winner!", 190, 200);
	    c.setFont(new Font("Corbel", Font.BOLD, 20));
	    if(cheats) {
		c.drawString("Don't cheat! :)", 345, 230);
	    } else {
		c.drawString("Everybody is broke :(", 315, 230);
	    }
	//else if score 1 is greater
	} else if(score[0] > score[1]) {
	    c.drawString("Player 1 wins!", 125, 200);
	//else if score 2 is greater
	} else if(score[1] > score[0]) {
	    c.drawString("Player 2 wins!", 125, 200);
	//else tie
	} else {
	    c.drawString("Tie!", 330, 200);
	}
	try {
	    Thread.sleep(2000);
	} catch (Exception e) {
	}
	level = lvl;
	if(!cheats) {
	    updateHighscores();
	}
    }
    /*'goodbye' method: calls the 'title' method
			outputs a goodbye message to user
			pauses the program so user can read
			closes Console window */
    public void goodbye() {
	title(35, 330, 40);
	c.setFont(new Font("Corbel", Font.BOLD, 22));
	c.drawString("Thank you for playing Jeopardy!. This game was programmed by Eric", 50, 180);
	c.drawString("Shim, and is based on the TV quiz show.", 50, 210);
	c.drawString("Press anything to exit", 300, 550);
	c.getChar();
	c.close();
    }
    
    /*Main Method: Calls previous methods in order they must be executed
		   Has an if/elseif/else statement and a loop that determines program flow based on user's input */
    public static void main(String[] args) {
	//Creates instance variable of Jeopardy and constructs a new Jeopardy object
	Jeopardy a = new Jeopardy();
	a.splashScreen(); //Executes 'splashScreen' method
	while(true) {
	    a.mainMenu(); //Executes 'mainMenu' method
	    if(a.choice.equals("1")) {
		a.instructions(); //Executes 'instructions' method
	    } else if(a.choice.equals("2")) {
		a.askData(); //Executes 'askData' method
		if(a.k == '~')
		    continue;
		a.coverScreen(); //Executes 'coverScreen' method
	    } else if(a.choice.equals("3")) {
	       a.highscores(); //Executes 'highscores' method
	    }
	    if(a.choice.equals("4"))
		break;
	}
	a.goodbye(); //Executes 'goodbye' method
    }
}
