package ben.mellogame.source;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Dot {
	private int x;
	private int y;
	private int rad;
	private Color color;
	
	private Random r;
	private Random ry;
	
	private Random xSpeed;
	private Random ySpeed;
	
	private int xs;
	private int ys;
	
	public Dot(){
		r = new Random();
		ry = new Random();
		ySpeed = new Random();
		xSpeed = new Random();
		
		ys = ySpeed.nextInt(10) + 1;
		xs = xSpeed.nextInt(10) + 1;
		
		y = ry.nextInt(GamePanel.HEIGHT);
		x = r.nextInt(GamePanel.WIDTH - GamePanel.WIDTH / 4) + GamePanel.WIDTH / 4;
		
		rad = 0;
		color = new Color(244, 244, 244);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void update(){
		x-=xs;
		y+=ys;
		
		if(x >= GamePanel.WIDTH || x <= 0){
			xs = -xs;
		}
		if(y >= GamePanel.HEIGHT || y <= 0){
			ys = -ys;
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.fillOval(x, y, rad, rad);
	}
}
