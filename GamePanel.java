package ben.mellogame.source;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt. *;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage. *;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	//Fields
	public static int WIDTH;
	public static int HEIGHT;
	
	private long redTime;
	private long redDelay;
	private boolean redTrue = false;
	
	private Dimension screenSize;
	private String pass = "";
	
	private Thread thread;
	private boolean running;
	private int FPS = 30;
	private double averageFPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private boolean line;
	private String nothing = "";
	
	private static ArrayList<Dot> dots;
	
	private String password = "jj7wb8pp";
	private int stringNum;
	
	private Color background = new Color(21,21,40);
	private Color lines = new Color(244, 244, 200, 1);
	private Color lines2 = new Color(244, 200, 244, 1);
	private Color text = new Color(0, 255, 0, 225);
	
	private long clearTime;
	private int clearDelay;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	//Constructor
	public GamePanel(){
		super();
		
		dots = new ArrayList<Dot>();
		line = true;
		stringNum = 0;
		redTime = 0;
		redDelay = 20;
		redTrue = false;
		
		clearTime = 0;
		clearDelay = 200;
	
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		HEIGHT = screenSize.height;
		WIDTH = screenSize.width;
		
		initDots();
	}
	public void initDots(){
		//initializes dots
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
		GamePanel.dots.add(new Dot());
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	public void run(){
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		long totalTime = 0;
		
		//GameLoop
		while(running){
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			try{
				if(waitTime >= 0){
					Thread.sleep(waitTime);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	public void gameUpdate(){
		for(int i = 0; i < dots.size(); i++){
			dots.get(i).update();
		}
	}
	
	public void gameRender(){
		clearTime++;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		if(clearTime >= clearDelay){
			g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			clearTime = 0;
		}
		if(redTrue == true){
			redTime++;
			lines = new Color(244, 0, 0, 10);
			lines2 = new Color(244, 0, 0, 10);
		}
		if(redTime >= redDelay){
			redTrue = false;
			redTime = 0;
		}
		if(redTrue == false){
			lines = new Color(244, 244, 200, 10);
			lines2 = new Color(244, 200, 244, 10);
		}
		
		for(int i = 0; i < dots.size(); i++){
			dots.get(i).draw(g);
		}	
			g.setStroke(new BasicStroke(6));
		for(int i = 0; i < dots.size(); i++){
			if(line == true){
				g.setColor(lines2);
				g.drawLine(dots.get(0).getX() + 5, dots.get(0).getY() + 5, dots.get(1).getX() + 5, dots.get(1).getY() + 5);
				g.drawLine(dots.get(1).getX() + 5, dots.get(1).getY() + 5, dots.get(2).getX() + 5, dots.get(2).getY() + 5);	
				g.drawLine(dots.get(2).getX() + 5, dots.get(2).getY() + 5, dots.get(3).getX() + 5, dots.get(3).getY() + 5);
				g.drawLine(dots.get(3).getX() + 5, dots.get(3).getY() + 5, dots.get(4).getX() + 5, dots.get(4).getY() + 5);
				g.drawLine(dots.get(4).getX() + 5, dots.get(4).getY() + 5, dots.get(5).getX() + 5, dots.get(5).getY() + 5);
				g.drawLine(dots.get(5).getX() + 5, dots.get(5).getY() + 5, dots.get(6).getX() + 5, dots.get(6).getY() + 5);
				g.drawLine(dots.get(6).getX() + 5, dots.get(6).getY() + 5, dots.get(0).getX() + 5, dots.get(0).getY() + 5);
			}
		}
		Calendar cal = Calendar.getInstance();
		
		g.setFont(new Font("Power Green Small", Font.PLAIN, 72)); 
		g.setColor(text);
		g.drawString("" + dateFormat.format(cal.getTime()), GamePanel.WIDTH / 2 - 275, 80);
		g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
		g.setColor(text);
		g.drawString("Ben Davey's PC", GamePanel.WIDTH / 2 - 85, GamePanel.HEIGHT - 30);
		
		g.drawString("Password: ", GamePanel.WIDTH / 2 - 160, GamePanel.HEIGHT / 2);
		if(stringNum >= 1){ 
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 - 60, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 2){
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 - 36, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 3){ 
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 - 12, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 4){
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 + 12, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 5){ 
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 + 36, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 6){
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 + 60, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 7){ 
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 + 84, GamePanel.HEIGHT / 2);
		}
		if(stringNum >= 8){
			g.setFont(new Font("Power Green Small", Font.PLAIN, 24)); 
			g.setColor(text);
			g.drawString("*", GamePanel.WIDTH / 2 + 108, GamePanel.HEIGHT / 2);
		}
	}
	
	public void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	public void keyPressed(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode != KeyEvent.VK_BACK_SPACE && keyCode != KeyEvent.VK_TAB && keyCode != KeyEvent.VK_ALT && keyCode != KeyEvent.VK_SHIFT &&
				keyCode != KeyEvent.VK_CAPS_LOCK && keyCode != KeyEvent.VK_F1 && keyCode != KeyEvent.VK_F2 &&
				keyCode != KeyEvent.VK_F3 && keyCode != KeyEvent.VK_F4 && keyCode != KeyEvent.VK_F5 && keyCode != KeyEvent.VK_F6 &&
				keyCode != KeyEvent.VK_F7 && keyCode != KeyEvent.VK_F8 && keyCode != KeyEvent.VK_F9 && keyCode != KeyEvent.VK_F10 &&
				keyCode != KeyEvent.VK_F11 && keyCode != KeyEvent.VK_F12 && keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_WINDOWS){
			if(stringNum <= 7){
				stringNum++;
			}
		}
		if(keyCode == KeyEvent.VK_J){
			if(stringNum <= 8){
				pass += "j";
			}
		}
		if(keyCode == KeyEvent.VK_7){
			if(stringNum <= 8){
				pass += "7";
			}
		}
		if(keyCode == KeyEvent.VK_W){
			if(stringNum <= 8){
				pass += "w";
			}
		}
		if(keyCode == KeyEvent.VK_B){
			if(stringNum <= 8){
				pass += "b";
			}
		}
		if(keyCode == KeyEvent.VK_8){
			if(stringNum <= 8){
				pass += "8";
			}
		}
		if(keyCode == KeyEvent.VK_P){
			if(stringNum <= 8){
				pass += "p";
			}
		}
		if(keyCode == KeyEvent.VK_A){
			if(stringNum <= 8){
				pass += "a";
			}
		}
		if(keyCode == KeyEvent.VK_C){
			if(stringNum <= 8){
				pass += "c";
			}
		}
		if(keyCode == KeyEvent.VK_D){
			if(stringNum <= 8){
				pass += "d";
			}
		}
		if(keyCode == KeyEvent.VK_BACK_SPACE){
			stringNum = 0;
			pass = nothing;
			if(stringNum > 0){
				stringNum--;
			}
		}
		if(keyCode == KeyEvent.VK_ENTER){
			if(pass.equals(password)){
				System.exit(0);
			}else{
				redTrue = true;
				stringNum = 0;
				pass = nothing;
			}
		}
		if(keyCode == KeyEvent.VK_WINDOWS){
			
		}
	}
	
	public void keyReleased(KeyEvent key){

	}

	public void keyTyped(KeyEvent key) {
		
	}
}
