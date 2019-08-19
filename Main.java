import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

	public static void main (String [] args) throws InterruptedException
	{		
		// Creating a new image to draw on
		BufferedImage img = new BufferedImage(Render3D.viewPortXSize, Render3D.viewPortYSize, BufferedImage.TYPE_INT_RGB);
		
		
		
		// Instantiating the Render3D class
		Render3D render = new Render3D();
		
		// Creating a new cube with the coordinates of (100,50,250) and a "radius" of 100
		Heart heart1 = new Heart(0,0,2000,500);
		Heart heart2 = new Heart(0,0,2000,500);

	

		
		//Creates the GUI to see the image in real time
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		JLabel view = new JLabel(new ImageIcon(img));
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		heart1.rotate(Math.PI/2, 0, 0);
		heart2.rotate(Math.PI/2, Math.PI/2, 0);
		
		double totalRotation = 0;
		while (true) // keeps going forever
		{	        
			
			Graphics2D g2d = img.createGraphics();

			g2d.setPaint ( Color.WHITE );
			g2d.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
			//cube.moveCenter(new double [] {75 * Math.sin(2 * totalRotation),75 * Math.cos(2 * totalRotation),0}); 
			// Distance to translate the cube
			
			heart1.rotate(0,Math.PI/100, 0); // Angles to rotate the cube on
			heart2.rotate(0,Math.PI/100, 0); // Angles to rotate the cube on

			img = render.drawPoints(heart1.getCorners(), img);	// redraws points
			img = render.drawPoints(heart2.getCorners(), img);	// redraws points
				        
			view.setIcon(new ImageIcon(img)); //updates the image in the GUI
			
			totalRotation += Math.PI/100;
			img = new BufferedImage(Render3D.viewPortXSize, Render3D.viewPortYSize, BufferedImage.TYPE_INT_RGB);
			
			Thread.sleep(25);
		} // end 
	}
	
}