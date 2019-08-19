import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Render3D extends Thread
{
	public Render3D() // constructor
	{
		
	}
	
	public int[] viewPoint = new int [] {0,0,0};
	public int[] viewportPlane = new int [] {0,0,1,300};
	
	public static final int viewPortXSize = 1000;
	public static final int viewPortYSize = 1000;
	
	// xyzOfPoint : (x,y,z) 
	// planeEquation ax + by + cz = n : (a,b,c,n)
	// cameraLocation : (x,y,z)
	
	private double [] getIntersection(int [] xyzOfPoint, int [] planeEquation, int [] cameraLocation) 
	{
		// finds the slope between the point in space and the camera location for each dimension
		int lineSlopeX = xyzOfPoint[0] - cameraLocation[0];
		int lineSlopeY = xyzOfPoint[1] - cameraLocation[1];
		int lineSlopeZ = xyzOfPoint[2] - cameraLocation[2];
		
		// makes the eqution for the vector in parametric form
		int [] lnEquX = new int [] {cameraLocation[0], lineSlopeX};
		int [] lnEquY = new int [] {cameraLocation[1], lineSlopeY};
		int [] lnEquZ = new int [] {cameraLocation[2], lineSlopeZ};
	
		// solving for the lambda
		double lambdaTop = planeEquation[3] - (planeEquation[0] * lnEquX[0] + planeEquation[1] * lnEquY[0] + planeEquation[2] * lnEquZ[0]);
		double lambdaBottom = planeEquation[0] * lnEquX[1] + planeEquation[1] * lnEquY[1] + planeEquation[2] * lnEquZ[1];
		double lambda = lambdaTop/lambdaBottom;
		
		// plugging in lambda in the parametric equations for the vector finds the XYZ coordinates of where it intersects the plane
		double intersectionX = lnEquX[0] + lnEquX[1] * lambda;
		double intersectionY = lnEquY[0] + lnEquY[1] * lambda;
		double intersectionZ = lnEquZ[0] + lnEquZ[1] * lambda;
		
		// returns the coordinates in an array
		return new double [] {intersectionX,intersectionY,intersectionZ};
	}
	
	public BufferedImage drawPoints(Corner [] corners, BufferedImage img)
	{
		// Creates a new "Drawer"
		Graphics2D g = img.createGraphics();
	
		for ( int i = 0; i < corners.length; i ++) //runs what is next for every corner in the input
		{
			// gets the location of the point in 3D space
			int [] pXYZ = corners[i].locationXYZ;
			
			// calls the method which calculates where a vector drawn from the origin to the point in 3D Space would intersect with the viewport plane
			double [] screenLocations = getIntersection(pXYZ, viewportPlane, viewPoint);
			
			// tells the corner where it is on the screen
			corners[i].screenLocations = screenLocations;
		}
		
		
		for (int i = 0; i < corners.length; i++) //runs what is next for every corner in the input
		{
			for (int n = 0; n < corners[i].neighbors.size(); n ++) //runs what is next for every neighbor a particular corner
			{
				// gets the screenlocations from the corner
				int x1 = (int) (corners[i].screenLocations[0]);
				int y1 = (int) (corners[i].screenLocations[1]);
				
				// gets the screenlocations from a neighbor
				int x2 = (int) (corners[i].neighbors.get(n).screenLocations[0]);
				int y2 = (int) (corners[i].neighbors.get(n).screenLocations[1]);
	
				g.setColor(Color.RED);
				
				// draws a line between them
				// 0,0 would be drawn at the top left corner of the image so I have to add Render3D.viewPortXSize/2 to make 0,0 the center of the image
				g.drawLine(x1 + Render3D.viewPortXSize/2,y1 + Render3D.viewPortYSize/2,x2 + Render3D.viewPortXSize/2,y2+ Render3D.viewPortYSize/2);
			}
		}
		return img;
	}
}