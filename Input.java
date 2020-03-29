/*
    Eric Shim
    Ms. Krasteva
    January 13, 2020
    ICS3U - ISP Improved (Jeopardy!)
				    
*/
import java.io.*;                   //Gives access to the Java IO libraries
import java.awt.*;                  //Gives access to the Java command libraries
import hsa.Console;                 //Gives access to Console class file
import javax.swing.JOptionPane;     //Gives access to JOptionPane class file
import javax.swing.ImageIcon;       //Gives access to ImageIcon class file

public class Input extends Thread {             //Creates new class called Input

    private Console c;
    String[][] answers;
    String str;
    int qX;
    int qY;
    int player;
    ImageIcon icon;
    boolean contains;
    volatile boolean exit = false; //Credit to Peter Lin for helping me fix buggy Thread stops
    boolean cheats;
    boolean bot;
    char k;
    int level;
    int dDouble[][];
    //Fake answer bank for bot
    String[] falseAnswers = {"Sit", "Plagiarize", "Distribute", "Blind", "Export", "Candidate", "History", "Ethnic", "Orange", "Lodge", "Cell", "Retirement", "Reliable", "Illustrate", "Complain", "Notorious", "Mechanical", "Match", "Pottery", "Banner", "Queen", "Fear", "Replacement", "Wrong", "Infrastructure", "First-hand", "Spite", "Trail", "Rib", "Deputy", "Midnight", "Consensus", "Pace", "Therapist", "Field", "Beat", "Forestry", "Venture", "Reproduce", "See", "Romance", "Job", "Reservoir", "Coach", "Tribe", "Sulphur", "Delicate", "Remedy", "Surgeon", "Finish"};
    
    //Input class constructor
    public Input(Console con, String[][] a, int x, int y, ImageIcon ii, int lvl, boolean ch, boolean b, int p, int[][] dd) {
	c = con;     //Creates a new Console object window
	answers = a;
	qX = x;
	qY = y;
	icon = ii;
	level = lvl;
	cheats = ch;
	bot = b;
	player = p;
	dDouble = dd;
    }
    
    public void input() {
	str = "";
	int randBot = (int)Math.round(Math.random()*100);
	int fakeAns = (int)Math.floor(Math.random()*falseAnswers.length);
	c.setColor(new Color(240, 185, 20));
	c.setFont(new Font("Corbel", Font.BOLD, 20));
	c.drawString(str+"|", 115, 368);
	if(bot && player == 1) {
	    String ansBot = "";
	    if(randBot < 30) {
		if(level == 1) {
		    ansBot += answers[qX][qY];
		} else if(level == 2 || dDouble[qX][qY] > 0) {
		    ansBot += "What is ";
		    ansBot += answers[qX][qY];
		}
		for(int x = 0; x < ansBot.length(); x++) {
		    str += ansBot.charAt(x);
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(110, 348, 550, 28);
		    c.setColor(new Color(240, 185, 20));
		    c.drawString(str+"|", 115, 368);
		    try {
			Thread.sleep(100);
		    } catch(Exception e) {
		    }
		}
	    } else {
		if(level == 1) {
		    ansBot += falseAnswers[fakeAns];
		} else if(level == 2 || dDouble[qX][qY] > 0) {
		    ansBot += "What is ";
		    ansBot += falseAnswers[fakeAns].toLowerCase();
		}
		for(int x = 0; x < ansBot.length(); x++) {
		    str += ansBot.charAt(x);
		    c.setColor(new Color(70, 160, 255));
		    c.fillRect(110, 348, 550, 28);
		    c.setColor(new Color(240, 185, 20));
		    c.drawString(str+"|", 115, 368);
		    try {
			Thread.sleep(100);
		    } catch(Exception e) {
		    }
		}
	    }
	} else {
	    while(true) {
		k = c.getChar();
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
		c.fillRect(110, 348, 550, 28);
		c.setColor(new Color(240, 185, 20));
		c.drawString(str, 115, 368);
	    }
	}
	String[] temp = answers[qX][qY].split(" ");
	contains = true;
	for(int x = 0; x < temp.length; x++) {
	    if(temp[x].charAt(0) != '(' && temp[x].charAt(temp[x].length()-1) != ')') {
		if(level == 1) {
		    if(str.toLowerCase().indexOf(temp[x].toLowerCase()) == -1) {
			contains = false;
			break;
		    }
		} else if(level > 1 || dDouble[qX][qY] > 0) {
		    if(str.toLowerCase().indexOf("what is") != 0 || str.toLowerCase().indexOf(temp[x].toLowerCase()) == -1) {
			contains = false;
			break;
		    }
		}
	    }
	}
	if(!contains) {
	    if(level < 3)
		JOptionPane.showMessageDialog(null, "Wrong!", "Jeopardy! Wrong Answer", JOptionPane.ERROR_MESSAGE, icon);
	}
	exit = true;
    }
    public void run() {
	input(); //Execute 'input' method
    }
}
