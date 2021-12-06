import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {

	public static int game_width = 720; 
	public static int game_height = 720;
	// game width and height are the size of the background image of grass.jpeg
	private Dimension panel_size = new Dimension(game_width, game_height);
	
	Thread gameThread;
	Image image;
	
	Sprite sprite;
	metor rock;
	
	int score = 0;
	int Highscore;
	
	
	
	public static ArrayList<wall> walls = new ArrayList<>();
	public static ArrayList<metor> rocks = new ArrayList<>();
	
	public static int cameraX;
	int offset;
	int amountOfRocks = 1;
	
	
	GamePanel()
	{
		
		
		//makeWalls(50);
		//sprite.setX(200);
		//sprite.setY(150);
		
		try {
			ReadFile();
			System.out.println(Highscore);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newSprite();
		Reset();
		
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(panel_size);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void WriteFile() throws FileNotFoundException
	{
		FileOutputStream fos = new FileOutputStream("highscore.txt");
		PrintWriter pw = new PrintWriter(fos);
		pw.println(Highscore);
		//pw.write(Highscore);
		pw.close();
	}
	
	public void ReadFile() throws FileNotFoundException
	{
		FileInputStream fis = new FileInputStream("highscore.txt");
		Scanner in = new Scanner(fis);
		
		while(in.hasNext()) {
			Highscore = in.nextInt();
		}
	}
	
	

	public void Reset() {
		sprite.setX(200);
		sprite.setY(50);
		
		cameraX = 50;
		sprite.setxSpeed(0);
		sprite.setYSpeed(0);
		walls.clear();
		rocks.clear();
		rocks.add(new metor(550, 600));
		offset = 50;
		makeWalls(offset);
		score = 0;
		
	}
	
	//creating walls
	public void makeWalls(int offset)
	{
		int s = 50;
		Random rand = new Random();
		int index = rand.nextInt(8);
		if(index == 0) {
			for(int i = 0; i < 14; i++) walls.add(new wall(Color.PINK, offset + i * 50, 600, s, s));
			} else if(index == 1) {
				for(int i = 0; i < 5; i++) walls.add(new wall(Color.WHITE, offset + i * 50, 600, s, s));
				
				walls.add(new wall(Color.WHITE, offset + 500, 600, s, s));
				walls.add(new wall(Color.WHITE, offset + 550, 600, s, s));
				walls.add(new wall(Color.WHITE, offset + 600, 600, s, s));
				walls.add(new wall(Color.WHITE, offset + 650, 600, s, s));
				walls.add(new wall(Color.WHITE, offset + 700, 600, s, s));
				walls.add(new wall(Color.WHITE, offset + 750, 600, s, s));
				
				score++;
				rocks.add(new metor(rand.nextInt(650), rand.nextInt(650)));
				
				
			} else if(index == 2) {
				for(int i = 0; i < 14; i++) walls.add(new wall(Color.RED, offset + i * 50, 600, s, s));
				for(int i = 0; i < 12; i++) walls.add(new wall(Color.RED, offset + 50 + i * 50, 550, s, s));
				for(int i = 0; i < 10; i++) walls.add(new wall(Color.RED, offset + 100 + i * 50, 500, s, s));
				for(int i = 0; i < 8; i++) walls.add(new wall(Color.RED, offset + 150 + i * 50, 450, s, s));
				for(int i = 0; i < 6; i++) walls.add(new wall(Color.RED, offset + 200 + i * 50, 400, s, s));
				score++;
			} else if(index == 3) {
				for(int i = 0; i < 5; i++) walls.add(new wall(Color.GREEN, offset + i * 50, 600, s, s));
				for(int i = 0; i < 5; i++) walls.add(new wall(Color.GREEN, offset + 450 + i * 50, 600, s, s));
				walls.add(new wall(Color.GREEN, offset + 150, 550, s, s));
				walls.add(new wall(Color.GREEN, offset + 200, 550, s, s));
			
		        	walls.add(new wall(Color.GREEN, offset + 200, 500, s, s));
				walls.add(new wall(Color.GREEN, offset + 200, 500, s, s));
				walls.add(new wall(Color.GREEN, offset + 500, 550, s, s));
				walls.add(new wall(Color.GREEN, offset + 450, 550, s, s));
				walls.add(new wall(Color.GREEN, offset + 450, 500, s, s));
				walls.add(new wall(Color.GREEN, offset + 450, 450, s, s));
				score++;
			} else if (index == 4) {
				for(int i = 0; i < 5; i++) walls.add(new wall(Color.BLUE, offset + i * 50, 600, s, s));
				for(int i = 0; i < 4; i++) walls.add(new wall(Color.BLUE, offset + 50 + i * 50, 550, s, s));
				for(int i = 0; i < 3; i++) walls.add(new wall(Color.BLUE, offset + 100 + i * 50, 500, s, s));
				for(int i = 0; i < 2; i++) walls.add(new wall(Color.BLUE, offset + 150 + i * 50, 450, s, s));
				for(int i = 0; i < 4; i++) walls.add(new wall(Color.BLUE, offset + 500 + i * 50, 600, s, s));
				score++;
			} else if(index == 5) {
				for(int i = 0; i < 5; i++) walls.add(new wall(Color.YELLOW, offset + i * 50, 600, s, s));
				for(int i = 0; i < 3; i++) walls.add(new wall(Color.YELLOW, offset + 100 + i * 50, 550, s, s));
				for(int i = 0; i < 2; i++) walls.add(new wall(Color.YELLOW, offset + 150 + i * 50, 500, s, s));
				for(int j = 0; j < 4; j++) {
					for(int i = 0; i < 4; i++) walls.add(new wall(Color.YELLOW, offset + 350 + i * 50, 400 + 50*j, s, s));
				}
				for(int i = 0; i < 7; i++) walls.add(new wall(Color.YELLOW, offset + 350 + i * 50, 600, s, s));
				for(int i = 0; i < 2; i++) walls.add(new wall(Color.YELLOW, offset + 200 + i * 50, 450, s, s));
				score++;
			} else if(index == 6) {
				for(int i = 0; i < 14; i++) walls.add(new wall(Color.CYAN, offset + i * 50, 600, s, s));
				for(int i = 0; i < 7; i++) walls.add(new wall(Color.CYAN, offset + 200 + i * 50, 450, s, s));
				score++;
			} else if(index == 7) {
				walls.add(new wall(Color.MAGENTA, offset, 600, s,s));
				walls.add(new wall(Color.MAGENTA, offset + 50, 600, s, s));
				
				walls.add(new wall(Color.MAGENTA, offset + 150, 500, s, s));
				walls.add(new wall(Color.MAGENTA, offset + 200, 500, s, s));
				
				walls.add(new wall(Color.MAGENTA, offset + 300, 400, s, s));
				walls.add(new wall(Color.MAGENTA, offset + 350, 400, s, s));
				
				walls.add(new wall(Color.MAGENTA, offset + 450, 500, s, s));
				walls.add(new wall(Color.MAGENTA, offset + 500, 500, s, s));
				
				walls.add(new wall(Color.MAGENTA, offset + 600, 600, s, s));
				walls.add(new wall(Color.MAGENTA, offset + 650, 600, s, s));
				score++;
			}
		
		
	}
	
	
	
	
	
	public void playSound()
	{
		String soundName = "yum.wav";    
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void newSprite() {
		
		
		sprite = new Sprite();
		System.out.println(amountOfRocks + "hi");
		
		System.out.println(rocks.size());
		
	}
	
	
	public void paint(Graphics g) {
		
		ImageIcon ii = new ImageIcon("space.png");
		image = ii.getImage(); 
		Graphics2D g2d = (Graphics2D) g;
		
		
	
		g2d.drawImage(image,0,0,null);
		draw(g2d);
		
		
	}
	
	public void draw(Graphics g)
	{
		for (wall wall: walls) wall.draw(g);
		for (metor rock: rocks) rock.draw(g);
		
		
		//rock.draw(g);
		
		sprite.draw(g);
		Font f = new Font("Arial", Font.BOLD, 40);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("Score: " +Integer.toString(score), 100,100);
		
		Font t = new Font("Arial", Font.BOLD, 40);
		g.setFont(t);
		g.setColor(Color.white);
		g.drawString("Highscore: " +Integer.toString(Highscore), 100,60);
		
		
	}
	
	public void update() {
		sprite.update();
		for (metor rock: rocks) rock.move();
		
	}
	
	public void checkBounds() {
		 
		if (sprite.getY() > 800)
		{
			if( Highscore < score)
				Highscore = score;
			Reset();
			try {
				WriteFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public boolean collision() {

		boolean touched = false;
		
		for (metor rock: rocks) {
			if(sprite.hitBox.intersects(rock.getBounds()))
					{
				
				touched = true;
				//System.out.println("you touched rock lol");
					}
		}
		
		return touched;
			
			
			
			
		
		
	}

	
	@Override 
	public void run() {
		//basic game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 30.0; // target framerate
		double ns = 1000000000 / amountOfTicks;
		double d = 0;
		boolean keepGoing = true;
		
		while(keepGoing)
		{
			long now = System.nanoTime();
			d += (now - lastTime)/ns;
			lastTime = now;
			if(d >= 1)
			{
				if(walls.get(walls.size() - 1).x < 800)
				{
					
					offset += 700;
					makeWalls(offset);
				}
				update();
				checkBounds();
				for(wall wall: walls) wall.set(cameraX);
				
				for(int i = 0; i<walls.size(); i++)
				{
					if(walls.get(i).x < - 800) walls.remove(i);
				}
				
				
				if (collision())
				{
					//System.out.println("touched rock lol");
					
					if( Highscore < score)
						Highscore = score;
					
					try {
						WriteFile();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Reset();
					
				}
				
				
				repaint();
				d--;
				//System.out.print("TEST");
			}
		}
	}
	//inner class for action listener
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			sprite.keyPressed(e);
			
		}
		public void keyReleased(KeyEvent e) {
			sprite.keyReleased(e);
			
		}
	}
	
	
}
