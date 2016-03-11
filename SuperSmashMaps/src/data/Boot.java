package data;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import javax.swing.ImageIcon;

public class Boot extends Canvas implements Runnable{ //Main class for Running
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7669774831421332541L;
	public static final int WIDTH = 900, HEIGHT = 450; //Default
	
	private boolean running = false; //is Running?
	private Thread thread;
	
	Tile primary = new Tile(0, 0, 100, 100, TileType.Primary);
	Tile secondary = new Tile(100, 0, 100, 100, TileType.Secondary);
	Tile bg = new Tile(-10, -10, WIDTH+20, HEIGHT+20, TileType.Background);
	public synchronized void start(){
		if(running) //Safety precaution
			return;		
		running = true;
		thread = new Thread(this);	//Uses call in Window constructor to start thread
		thread.start();
	}
	
	public void run()	//game loop
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 25.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	//ticks = updates
	private void tick()
	{
		
	}
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();	//loads upto 3 buffers if time allows
		if(bs == null){									//only displays top buffer
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//--------------Draw Here--------------\\
		
		
		g.setColor(Color.black);
		g.drawImage(bg.getImage(), bg.getX(), bg.getY(), bg.getWidth(), bg.getHeight(), null);
		g.setColor(Color.white);
		g.drawImage(primary.getImage(), primary.getX(), primary.getY(), primary.getWidth(), primary.getHeight(), null);
		g.drawImage(secondary.getImage(), secondary.getX(), secondary.getY(), secondary.getWidth(), secondary.getHeight(), null);
		//--------------------------------------\\
		g.dispose();
		bs.show();
		
	}

	public static void main(String[] args)
	{
		new Window(WIDTH, HEIGHT, "Super Smash", new Boot()); //Calls Window constructor (sets up window and displays)
	}
}
