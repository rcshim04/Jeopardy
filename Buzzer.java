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

public class Buzzer extends Thread {             //Creates new class called Buzzer

    private Console c;
    volatile boolean hit = false; //Credit to Peter Lin for helping me fix buggy Thread stops
    boolean bot;
    char k = ' ';
    
    //Buzzer class constructor
    public Buzzer(Console con, boolean b) {
	c = con;     //Creates a new Console object window
	bot = b;
    }
    public void buzzer() {
	if(!bot) {
	    while(k != '1' && k != '2') {
		k = c.getChar();
	    }
	} else {
	    while(k != '1') {
		k = c.getChar();
	    }
	}
	hit = true;
    }
    public void run() {
	buzzer(); //Executes 'buzzer' method
    }
}
