import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
	
	GameFrame()
	{
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("sup");
		this.setResizable(false);// do not want it to be resized
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();// will resize automatically based on compents added
		this.setVisible(true);
		this.setLocationRelativeTo(null); // will appear in middle of screen
	}

}
