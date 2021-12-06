
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class metor extends Rectangle {
	
	private int x;
	private int y;
	private int w;
	private int h;
	
	BufferedImage rock;
	
	
	metor(int x, int y)
	{
		this.x = x;
		this.y = y;
		loadImage();
	}
	
	public void loadImage()
	{
	
		try {
			rock = ImageIO.read(getClass().getResourceAsStream("/player/rock.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        w = 48; // set to 48 this will also be useful to mutiply the size of the image by 3
        h = 48;
      
      
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage image = rock;
		g2d.drawImage(image, x, y, w, h, null);
		
	}
	public void move()
	{
		if (x > GamePanel.game_width){
		      x = 0;
		    }
		    if (x < 0){
		    	x = GamePanel.game_width;
		    } 
		    if (y > GamePanel.game_height){
		    	y = 0;
		    } // end if
		    if (y < 0){
		    	y = (GamePanel.game_height);
		    }
		    
		    x += 5;
		    y += 5;
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
}
