import javax.swing.*;   
import java.awt.*;		
import java.awt.event.*; // for key listener
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Rectangle;


public class BallGameTemplate extends JPanel implements KeyListener, Runnable  
{
	public static final int frameWidth = 1000;  
	public static final int frameHeight = 800;
	public static int hiScore;
	public boolean lose;
	private String msg, msg1, msg2;
	public static Ball ball;  
	private Rectangle goal;
	private int score;
	private ArrayList<Ball> obstacles = new ArrayList<Ball>();
	private boolean run;
	public boolean playAgain;
	private String msg3;
	

	private boolean up, down, left, right;

	


	private JFrame frame;
	private Font f;
	private Thread t;

	public BallGameTemplate()
	{
		playAgain = false;
		score = 0;
		lose = false;
		run = false;
		// Load and configure Java Graphics
		frame=new JFrame();
		frame.add(this);  
		frame.setSize(frameWidth,frameHeight);
		
		frame.addKeyListener(this); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loadPropertiesFromFile("GameConfig.txt");

		up = false;
		down = false;
		left = false;
		right = false;
		
		f=new Font("TIMES NEW ROMAN",Font.BOLD,32);  
		t=new Thread(this);  
		t.start();
		frame.setVisible(true);
	}


	public void paintComponent(Graphics g)  
	{
		super.paintComponent(g);  
		Graphics2D g2d = (Graphics2D)g;  

		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0,frameWidth,frameHeight);

		g2d.setColor(Color.BLUE);
		g2d.setFont(f);  
		g2d.drawString(msg,60,30);  

		if (msg1 != null){
			g2d.drawString(msg1,60,60);
		}

		if (msg2 != null){
			g2d.drawString(msg2,60,90);
		}
		if (msg3 != null){
			g2d.drawString(msg3,60,60);
		}


	    g2d.setColor(ball.getColor());
        g2d.fill(ball);

		for (int i = 0; i < obstacles.size(); i++){
			g2d.setColor(obstacles.get(i).getColor());
			g2d.fill(obstacles.get(i));
		}
		
		g2d.setColor(new Color(0,255,130));  
		g2d.fill(goal);


	}


		
		public void run()
		{
			msg = "USE THE ARROW KEYS TO GET THE GREEN BLOCK.";
			msg1 = "AVOID THE MOVING BALLS";
			msg2= "PRESS ENTER TO START";

			while(!run){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e){}
			}
			msg1 = null;
			msg2 = null;
			
			try
			{
				Thread.sleep(2000);  // Delay a bit to allow user to read initial message
			}catch(InterruptedException e){}
			repaint();

			addBall();
			

			while(!lose) // Keep going until win or window is closed
			{
				msg = "Score: " + score + " | High Score: " + hiScore;
				for (int i = 0; i < obstacles.size(); i++){
					obstacles.get(i).move();
				}

				keysMove();

				if (goal.intersects(ball.getBounds2D())){    // Detect collision between ball and goal
					int xF= (int) (Math.random() * (frameWidth - 40 )) + 10;
					int yF= (int) (Math.random() * (frameHeight - 40)) + 10;
					score += 5;
					addBall();
					goal = new Rectangle(xF,yF,40,40);
				}

				for (int i = 0; i < obstacles.size(); i++){

					if(ballCollision(ball, obstacles.get(i))){
						lose = true;
					}

				}

				try
				{
					Thread.sleep(5);  
				} catch(InterruptedException e) {}
				repaint();
			}

			
			if(score > hiScore)
				updateHiScore("GameConfig.txt");
			
			replay();
			

	}

	public void replay(){
		msg = "You Lose. Your score was: " + String.valueOf(score) + ".";
		repaint();
		msg3 = "Press Shift to Play Again";
		repaint();
		
		while(!playAgain){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e){}
		}
			new BallGameTemplate();
	}	

	public void keysMove(){
		int x = (int)ball.getX();
		int y = (int)ball.getY();
		int speed = 3;

			if(left == true && x > (ball.getSize()/2))
				x -= speed;
			else
				left = false;
			if(down == true && y > (ball.getSize()/2))
				y -= speed;
			else 
				down = false;
			if(right == true && x < frameWidth - (ball.getSize()/2))
				x += speed;
			else
				right = false;
			if(up == true && y < frameHeight - (ball.getSize()/2))
				y += speed;
			else
				up = false;

			ball.setFrame(x, y, 30, 30);
	}

	
	public void keyPressed(KeyEvent ke)
	{

		if (ke.getKeyCode() == 10)
			run = true;
		if (ke.getKeyCode() == 16)
			playAgain = true;	
		if(ke.getKeyCode()==37 && ball.getX() > (-5))
			left = true;
		if(ke.getKeyCode()==38 && ball.getY() > (-5))
			down = true;
		if(ke.getKeyCode()==39 && ball.getX() < (frameWidth))
			right = true;
		if(ke.getKeyCode()==40 && ball.getY() < (frameHeight))
			up = true;

	}
	public void keyReleased(KeyEvent ke)
	{
		if(ke.getKeyCode() == 37)
			left = false;
		if(ke.getKeyCode() == 38)
			down = false;
		if(ke.getKeyCode() == 39)
			right = false;
		if(ke.getKeyCode() == 40 )
			up = false;


	}
	public void keyTyped(KeyEvent ke){}

	public void updateHiScore(String fileName){
		File name = new File(fileName);
		String fileContents = "";
		String line = "";

		try{
			BufferedReader input = new BufferedReader(new FileReader(name));
			while((line = input.readLine()) != null && !line.contains("HiScore")){
				fileContents += line + "\n";
			}
			input.close();
			PrintWriter writer = new PrintWriter(name);
			fileContents += "HiScore=" + score + "\n";
			writer.write(fileContents);
			writer.close();
		}
		catch (IOException e){
			System.err.println("File read error");
		}
	}

	public void loadPropertiesFromFile(String fileName){
		try {
			File name = new File(fileName);
			Scanner reader = new Scanner(name);

			while(reader.hasNextLine()) {
					String line = reader.nextLine();
					String[] tokens = line.trim().split("=");

					if (tokens[0].trim().equals("StartLocation")){

						String[] coords = tokens[1].split(",");
						int xS = Integer.parseInt(coords[0]);
						int yS = Integer.parseInt(coords[1]);
						ball = new Ball(xS,yS,30,Color.MAGENTA);

					} else{
						ball = new Ball(450,350,30,Color.MAGENTA);
					}

					if ( tokens[0].trim().equals("GoalLocation") ){

						String[] coords = tokens[1].split(",");
						int xF = Integer.parseInt(coords[0]);
						int yF = Integer.parseInt(coords[1]);
						goal = new Rectangle(xF,yF,40,40);

					}
					else {
						goal = new Rectangle(500,600,40,40);;
					}

					if (tokens[0].trim().equals("HiScore")){
						hiScore = Integer.parseInt(tokens[1]);
					}
					else {
						hiScore = 0;
					}
				}
				reader.close();
			}
			catch (IOException io)
			{
				System.err.println("File read error");
			}

	}

	public double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow( (x2-x1) , 2) + Math.pow( (y2-y1) , 2));
	}

	public boolean ballCollision(Ball b1, Ball b2){
		double d = distance(b1.getCenterX(), b1.getCenterY(), b2.getCenterX(), b2.getCenterY());
		return (d <= ((b1.getSize()/2) + (b2.getSize()/2)));
	}

	public void addBall(){

		obstacles.add(new MovingBall( (Math.random() * frameWidth), (Math.random() * frameHeight), 20.0, Color.ORANGE,
		((int)(Math.random() * 2) + 1), ((int)(Math.random() * 2) + 1) ));
		obstacles.add( new SHMBall(Math.random() * frameWidth, 20, Color.ORANGE));
		obstacles.add(new FollowingBall( (Math.random() * frameWidth), (Math.random() * frameHeight), 20.0, Color.ORANGE));
	
	}

	public static void main(String args[])
	{
		new BallGameTemplate();
	}
}