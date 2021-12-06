import java.awt.*;


public class wall extends Rectangle {

	int x;
	int y;
	int width;
	int height;
	Rectangle hitBox;
	Color color;
	int startX;    
	
	wall(Color color, int x, int y, int width, int height)
	{
		this.x = x;
		startX = x;
		this.y= y;
		this.color = color;
		this.width = width;
		this.height = height;
		hitBox = new Rectangle(x, y, width, height);
	}
	
	
	public void draw(Graphics g)
	{
		//outline for wall
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		//actual wall is drawn 1 pixel forward so we can see the border w and h are adjusted
		g.setColor(color);
		g.fillRect(x+1, y+1, width-2, height-2);
		

	}

	public int set(int cameraX)
	{
		x = startX - cameraX;
		hitBox.x = x;
		
		return x;
	}
	
	public void setY(int i) {
		this.y = i;
		
	}

	public void setX(int i) {
		// TODO Auto-generated method stub
		this.x = i;
	}
	
}
