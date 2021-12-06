import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Sprite 
{

	private int x = 0;
	private int y = 0;
	private int w;
	private int h;
	private boolean isVisible = true;
	private int xSpeed;
	private int ySpeed;
	Rectangle hitBox;
	
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	
	
	
	String imgP = "boy_left_1.png";
	
	Image image;
	BufferedImage left1, left2, right1, right2;
	String direction;
	int spriteCounter = 0;//used for animation of sprite
	int spriteNum = 1; //either 1 or 2 based on sprite
	
	Sprite(){
		loadImage();
		hitBox = new Rectangle(x, y, w, h);
		direction = "left";//setting hitbox after loading image in order to get w and h
	}
	
	public void update()
	{
		
		move();
		if (keyLeft == true)
		{
			direction = "left";
		}
		if (keyRight == true)
		{
			direction = "right";
		}
		
		//System.out.println(spriteCounter);
		
		if (keyLeft == true || keyRight == true)
		{
		spriteCounter++;
		if(spriteCounter > 5)// every 5 frames the image will be changed
		{
			if (spriteNum ==1) {
				
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				
				spriteNum = 1;
				
			}
			//System.out.println(spriteNum);
			spriteCounter = 0;
		}
		}
	}
	public void loadImage()
	{
		//ImageIcon ii = new ImageIcon("boy_left_1.png");
       // image = ii.getImage(); 
        
        
		try {
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        w = 48; // set to 48 this will also be useful to mutiply the size of the image by 3
        h = 48;
        //w = image.getWidth(null);
        //h = image.getHeight(null);
      
	}
	
	public void move() {
		if(keyLeft && keyRight || !keyLeft && !keyRight) xSpeed *= .8;
		else if(keyLeft && !keyRight) xSpeed--;
		else if(!keyLeft && keyRight) xSpeed++;
		
		
		if (xSpeed >0 && xSpeed <0.75 ) xSpeed = 0; //if xSpeed is between these numbers change it to 0
		if (xSpeed < 0 && xSpeed > -0.75 ) xSpeed = 0;
	
		if(xSpeed > 10) xSpeed = 10; //setting max speed
		if(xSpeed < -10) xSpeed = -10;
		
		if(keyUp) {
			
			hitBox.y ++; //checking 1 pixel away from hitbox
			for (wall wall: GamePanel.walls) {
				if (wall.hitBox.intersects(hitBox))
					{
					System.out.println("you touched");
					ySpeed =-15;
					}
			}
			hitBox.y--; // fix the hitboxes actual position
		}
			
		
		ySpeed += 1;	// gravity
		
		//checking for collisions
		
		//the hitbox is move closer and closer until it touches the wall then the player is moved to the hitbox
	
		//horizontal
		hitBox.x += xSpeed; //moving the hitbox 
		for (wall wall: GamePanel.walls) {
			if(hitBox.intersects(wall.hitBox))
			{
				hitBox.x -= xSpeed; //moving the hitbox back because there is a collision
				while(!wall.hitBox.intersects(hitBox)) hitBox.x += Math.signum(xSpeed);
				hitBox.x -= Math.signum(xSpeed);
				GamePanel.cameraX += x - hitBox.x;
				xSpeed = 0;
				hitBox.x = x;
				
			}
		}
		//vertical
		hitBox.y += ySpeed; //moving the hitbox 
		for (wall wall: GamePanel.walls) {
			if(hitBox.intersects(wall.hitBox))
			{
				hitBox.y -= ySpeed; //moving the hitbox back because there is a collision
				while(!wall.hitBox.intersects(hitBox)) hitBox.y += Math.signum(ySpeed);
				hitBox.y -= Math.signum(ySpeed);
				ySpeed = 0;
				y = hitBox.y; //moving player
			}
		}
		
		
		GamePanel.cameraX += xSpeed;
		y += ySpeed;
		
		hitBox.x = x;
		hitBox.y = y;
	}
	
	
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int x)
	{
		 this.x = x; 
	}
	public void setY(int y)
	{
		 this.y = y; 
	}
	public double getWidthI()
	{
		return w;
	}
	public Image getImage()
	{
		return image;
	}
	
	public double getHeightI()
	{
		return h;
	}
	
	public void setxSpeed(int x)
	{
		xSpeed = x;
	}
	public void setYSpeed(int x)
	{
		ySpeed = x;
	}
	
	
	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage image = null;
		
		
		
		
		if(isVisible)
		{
		
		switch(direction)
		{
		case "left":
			if(spriteNum == 1)
			{
				image = left1;
				//System.out.println("hi1");
				//System.out.println(spriteNum);
			}
			else if(spriteNum == 2)
			{
				image = left2;
				//System.out.println("hi2");
				//System.out.println(spriteNum);
			}
			break;
		case "right":
			if(spriteNum == 1)
			{
				image = right1;
				//System.out.println("hi3");
			}
			else if(spriteNum == 2)
			{
				image = right2;
				//System.out.println("hi4");
				//System.out.println(spriteNum);
			}
			break;
		}
			
		
			
		
		}
		
		
		g2d.drawImage(image, x, y, w, h, null);
		
		
	}
	// basic movement using wasd setting dx or dy to the speed and calling move
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
        	
        	
        	keyLeft = true;
        	
        	//setDX(-speed);
        	//this.dx = -2;
            //move();
            System.out.println(getX());
        }

        if (key == KeyEvent.VK_D) {
        	
        	keyRight = true;
        	//setDX(speed);
        	//this.dx = 2;
            //move();
            System.out.println(getX());
        }

        if (key == KeyEvent.VK_W) {
        	keyUp = true;
        	//setDY(-speed);
        	//this.dy = -2;
           // move();
            System.out.println(getY());
        }

        if (key == KeyEvent.VK_S) {
        	keyDown = true;
        	//setDY(speed);
        	//this.dy = 2;
        	//move();
            System.out.println(getY());
        }
      
    }

    public void keyReleased(KeyEvent e) {
    	  int key = e.getKeyCode();

          if (key == KeyEvent.VK_A) {
        	  //setDX(0);
        	  keyLeft = false;
          }

          if (key == KeyEvent.VK_D) {
        	  //setDX(0);
        	  keyRight = false;
          }

          if (key == KeyEvent.VK_W) {
        	  //setDY(0);
        	  keyUp = false;
          }

          if (key == KeyEvent.VK_S) {
        	  //setDY(0);
        	  keyDown = false;
          }
       
    }


	public boolean isVisible() {
		return isVisible;
	}


	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
