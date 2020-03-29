/*
    Eric Shim
    Ms. Krasteva
    January 13, 2020
    ICS3U - ISP (Jeopardy!)
				    
*/
import java.io.*;                   //Gives access to the Java IO libraries
import java.awt.*;                  //Gives access to the Java command libraries
import hsa.Console;                 //Gives access to Console class file
import javax.swing.JOptionPane;     //Gives access to JOptionPane class file

public class SplashScreen extends Thread {             //Creates new class called SplashScreen

    private Console c;
    //SplashScreen class constructor
    public SplashScreen(Console con) {
	c = con;     //Creates a new Console object window
    }
    public void splashScreen() {
	c.setColor(new Color(25, 100, 250));
	c.fillRect(0, 0, 800, 600);
	for(int x = 0; x < 100; x++) {
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(0, 0, 800, 600);


	    c.setFont(new Font("Impact", Font.PLAIN, x));
	    c.setColor(new Color(255, 210, 25));
	    c.drawString("JEOPARDY!", 395-x*2, 260);
	    try {
		Thread.sleep(50);
	    } catch(Exception e) {
	    }
	}
	c.setFont(new Font("Corbel", Font.BOLD, 32));
	for(int x = 0; x < 40; x++) {
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("AMERICA'S FAVOURITE QUIZ SHOW", 140, 320);
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(50, 285+x, 700, 40-x);
	    try {
		Thread.sleep(20);
	    } catch(Exception e) {
	    }
	}
	c.setFont(new Font("Corbel", Font.BOLD, 60));
	for(int x = 0; x < 70; x++) {
	    c.setColor(new Color(240, 185, 20));
	    c.drawString("SPECIAL EDITION", 165, 460);
	    c.setColor(new Color(25, 110, 255));
	    c.fillRect(75, 395+x, 650, 70-x);
	    try {
		Thread.sleep(20);
	    } catch(Exception e) {
	    }
	}
	for(int x = 0; x < 19; x++) {
	    c.setColor(new Color(240, 185, 20));
	    c.fillOval(149-x/2, 441-x/2, x, x);
	    try {
		Thread.sleep(20);
	    } catch(Exception e) {
	    }
	}
	for(int x = 0; x < 19; x++) {
	    c.setColor(new Color(240, 185, 20));
	    c.fillOval(650-x/2, 441-x/2, x, x);
	    try {
		Thread.sleep(20);
	    } catch(Exception e) {
	    }
	}
	try {
	    Thread.sleep(1000);
	} catch(Exception e) {
	}
	for(int x = 0; x < 100; x+=2) {
	    c.setColor(new Color(25, 110, 255, 90));
	    c.fillRect(0, 0, 800, 600);
	    try {
		Thread.sleep(1);
	    } catch(Exception e) {
	    }
	}
    }
    public void run() {
	splashScreen(); //Executes 'splashScreen' method
    }
}
