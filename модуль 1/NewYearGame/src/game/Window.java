package game;

import javax.swing.JFrame;

public class Window extends JFrame{
   public Window() {
	   setFocusable(true);
	   setResizable(false);
	   setBounds(100,100,800,600);
	   setTitle("");
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setVisible(true);
   }
}
