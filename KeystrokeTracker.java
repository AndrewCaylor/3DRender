import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeystrokeTracker {

	public ArrayList<String> recentStrokes = new ArrayList<String>();
	
	public double [] offset = new double[] {0,0,0};
	
	private int cursorXOffset = 0;
	
	KeyListener listener;
	
	public KeystrokeTracker () {
		
		AWTEventListener listener = new AWTEventListener() {
			  @Override
			  public void eventDispatched(AWTEvent event) {
			    try {
			      KeyEvent evt = (KeyEvent)event;
			      if(evt.getID() == KeyEvent.KEY_PRESSED) {
			    	  if (!recentStrokes.contains(Character.toString( ( (char)evt.getKeyCode() ) ) ))
			    	  {
				    	  recentStrokes.add( Character.toString( ( (char)evt.getKeyCode() ) ) );
			    	  }
			      }
			      if(evt.getID() == KeyEvent.KEY_RELEASED) {
			    	  recentStrokes.remove( Character.toString( ( (char) evt.getKeyCode() ) ) );
			      }
			      
			      
			      System.out.println(recentStrokes.size());
			      
			    }
			    catch(Exception e) {
			      e.printStackTrace();
			    }
			  }
			};
			
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);

	}
	
	public void clear()
	{
		recentStrokes = new ArrayList<String>();
	}
	
	public double [] getDirection() 
	{
		double [] out = new double[] {0,0,0};
				
		if(recentStrokes.contains("W"))
		{
			out[2] = 100;
			
			if(recentStrokes.contains("S"))
			{
				out[2] = 0;
			}
		}
		
		if(recentStrokes.contains("A"))
		{
			out[0] = -100;
			
			if(recentStrokes.contains("D"))
			{
				out[2] = 0;
			}
		}
		
		if(recentStrokes.contains("S"))
		{
			out[2] = -100;
			
			if(recentStrokes.contains("W"))
			{
				out[2] = 0;
			}
		}
		
		if(recentStrokes.contains("D"))
		{
			out[0] = 100;
			if(recentStrokes.contains("A"))
			{
				out[2] = 0;
			}
		}
		return out;
	}

	public double [] getLookAngles()
	{

		//gets screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		
		//gets pointer location
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		double x = b.getX();
		double y = b.getY();
		
		if(x <= 0)
		{
			try {
			    Robot robot = new Robot();
			    robot.mouseMove((int) screenWidth - 2, (int)y);
			} catch (AWTException e) {
			}
			
			this.cursorXOffset -= screenWidth;
		}
		else if(x >= screenWidth - 1){
			try {
			    Robot robot = new Robot();
			    robot.mouseMove(1, (int)y);
			} catch (AWTException e) {
			}
			
			this.cursorXOffset += screenWidth;
		}
		
		x+=this.cursorXOffset;
		
		//generates angle based on pointer position on screen
		double angleX = ((x - (screenWidth / 2)) / screenWidth) * (Math.PI);
		double angleY = ((y - (screenHeight / 2)) / screenHeight) * (Math.PI);
		
		return new double[] {angleX,angleY,0};
	}
	
}
