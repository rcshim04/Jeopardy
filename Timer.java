/*
    Eric Shim
    Ms. Krasteva
    December 13, 2020
    ICS3U - ISP Improved (Jeopardy!)
*/
import java.io.*;                   //Gives access to the Java IO libraries
import java.awt.*;                  //Gives access to the Java command libraries
import hsa.Console;                 //Gives access to Console class file
import javax.swing.JOptionPane;     //Gives access to JOptionPane class file

public class Timer extends Thread {             //Creates new class called Timer

    private Console c;                      //Creates an instance variable of the Console class so the output window can be made
    private int sec;                        //Declares the variable 'sec' as an int
    volatile boolean end = false;           //Credit to Peter Lin for helping me fix buggy Thread stops
    
    //Timer class constructor
    public Timer(Console con, int s) {
	c = con;     //Creates a new Console object window
	sec = s;
    }
    public void timer() {
	for(int x = 0; x < sec; x++) {
	    c.setColor(new Color(70, 160, 255));
	    c.fillRect(100, 500, 600, 40);
	    c.setColor(new Color(10, 90, 230));
	    c.fillRect(110, 510, 580, 20);
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(110+x*570/(sec-1)/2, 510, 580-x*570/(sec-1), 20);
	    c.setColor(new Color(220, 50, 50));
	    c.fillRect(115+x*570/(sec-1)/2, 515, 570-x*570/(sec-1), 10);
	    try {
		Thread.sleep(1000);
	    } catch(Exception e) {
	    }
	}
	end = true;
    }
    public void run() {
	timer();
    }
}
