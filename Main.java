import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {
	
	public static void main (String [] args) throws InterruptedException
	{		
		
		
		KeystrokeTracker keyTracker = new KeystrokeTracker();

		// Creating a new image to draw on
		BufferedImage img = new BufferedImage(Render3D.viewPortXSize, Render3D.viewPortYSize, BufferedImage.TYPE_INT_RGB);
		
		
		
		// Instantiating the Render3D class
		Render3D render = new Render3D();
		
		Corner [] cornersArr = {
			new Corner(500,-500,1500),
			new Corner(500,500,1500),
			new Corner(-500,500,1500),
			new Corner(-500,-500,1500)
		};
		
		poly p = new poly(cornersArr, new double [] {0,0,2000}, new Color(0, 200, 255));
		
		Corner [] cornersArr2 = {
			new Corner(-500,-500,2500),
			new Corner(-500,500,2500),
			new Corner(-500,500,1500),
			new Corner(-500,-500,1500)
		};
			
		poly p2 = new poly(cornersArr2, new double [] {0,0,2000}, new Color(255, 0, 0));
		
		Corner [] cornersArr3 = {
				new Corner(500,-500,2500),
				new Corner(500,500,2500),
				new Corner(500,500,1500),
				new Corner(500,-500,1500)
		};
				
		poly p3 = new poly(cornersArr3, new double [] {0,0,2000}, new Color(255, 200, 0));
			
		Corner [] cornersArr4 = {
			new Corner(-500,500,2500),
			new Corner(-500,-500,2500),
			new Corner(500,-500,2500),
			new Corner(500,500,2500)
		};
				
		poly p4 = new poly(cornersArr4, new double [] {0,0,2000}, new Color(0, 0, 255));
			
		
		Corner [] cornersArrFloor = {
				new Corner(-2000,1000,5000),
				new Corner(2000,1000,5000),
				new Corner(2000,1000,1000),
				new Corner(-2000,1000,1000)

			};
		
		
		poly pFloor = new poly(cornersArrFloor, new double [] {0,0,2000}, new Color(0, 0, 0));

		poly [] polys = new poly[] {p,p2,p3,p4,pFloor};
	
		
		// X <---->
		// Y ^
		// Z forward / back
		
		

		//Creates the GUI to see the image in real time
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		JLabel view = new JLabel(new ImageIcon(img));
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		double [] userLocation = new double[] {0,0,0};
		
		
		while(true)
		{
			Graphics2D g2d = img.createGraphics();

			g2d.setPaint ( Color.WHITE );
			g2d.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
			
			g2d.drawImage(ReadFile.readImageDesktop(""), null, 0, 0);

			
			double [] direction = keyTracker.getDirection();	
			
			userLocation[0] += direction[0];
			userLocation[1] += direction[1];
			userLocation[2] += direction[2];
			
			// translates and rotates polys

			p.rotate(new double[] {Math.PI/100, Math.PI/100, 0}, p.centerXYZ);
			p2.rotate(new double[] {Math.PI/100, Math.PI/100, 0}, p2.centerXYZ);
			p3.rotate(new double[] {Math.PI/100, Math.PI/100, 0}, p3.centerXYZ);
			p4.rotate(new double[] {Math.PI/100, Math.PI/100, 0}, p4.centerXYZ);

						
			double [] lookAngles = keyTracker.getLookAngles();
		
			img = render.drawPolys(polys, img, lookAngles, userLocation);
			
			view.setIcon(new ImageIcon(img)); //updates the image in the GUI
			
			Thread.sleep(10);
		}
	}
	
}