import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Render3D extends Thread
{
	public Render3D()
	{
		
	}
	
	public double[] viewPoint = new double [] {0,0,0};
	public double[] viewportPlane = new double [] {0,0,1,500}; // one of these is depricated
	
	public double[] viewportPlaneInit = new double [] {0,0,1,300};
		
	public static final int viewPortXSize = 1500;
	public static final int viewPortYSize = 1000;
	
	// xyzOfPoint : (x,y,z) 
	// planeEquation a(x + c1) + b(y + c2) + c(z + c3) = n : (a,b,c,n) , (c1,c2,c3)
	// 
	// cameraLocation : (x,y,z)
	
	private int[] getIntersection(double [] xyzOfPoint, double [] planeEquation, double [] cameraLocation, double [] XYLookAngles) 
	{
		// finds the slope between the point in space and the camera location for each dimension
		double lineSlopeX = xyzOfPoint[0] - cameraLocation[0];
		double lineSlopeY = xyzOfPoint[1] - cameraLocation[1];
		double lineSlopeZ = xyzOfPoint[2] - cameraLocation[2];
		
		// makes the eqution for the vector in parametric form
		double [] lnEquX = new double [] {0, lineSlopeX};
		double [] lnEquY = new double [] {0, lineSlopeY};
		double [] lnEquZ = new double [] {0, lineSlopeZ};
	
		// solving for the lambda
		double lambdaTop = planeEquation[3] - (planeEquation[0] * lnEquX[0] + planeEquation[1] * lnEquY[0] + planeEquation[2] * lnEquZ[0]);
		double lambdaBottom = planeEquation[0] * lnEquX[1] + planeEquation[1] * lnEquY[1] + planeEquation[2] * lnEquZ[1];
		double lambda = lambdaTop/lambdaBottom;
		
		// plugging in lambda in the parametric equations for the vector finds the XYZ coordinates of where it intersects the plane
		double intersectionX = lnEquX[0] + lnEquX[1] * lambda;
		double intersectionY = lnEquY[0] + lnEquY[1] * lambda;
		double intersectionZ = lnEquZ[0] + lnEquZ[1] * lambda;
		
		System.out.println(lambda > 0);
		
		if (lambda < 0) {
			return null;
		}
		
		// returns the coordinates in an array
		return new int [] {(int) intersectionX,(int) intersectionY,(int) intersectionZ};
	}
	
	// 3D points must already be calculated
	public BufferedImage drawPoly(poly polygon, BufferedImage img, double [] lookAngles, double [] userLoc)
	{
		Graphics2D g = img.createGraphics();
	
		g.setColor(polygon.color);
		
		Corner [] corners = polygon.corners;
				
		int [] screenLocationsX = new int[corners.length];
		int [] screenLocationsY = new int[corners.length];
	
		boolean draw = true;
		
		for ( int i = 0; i < corners.length; i ++) //runs what is next for every corner in the input
		{
			// gets the location of the point in 3D space
			double [] pXYZ = corners[i].locationIJK.clone();
			
			//offset based on user location
			pXYZ[0] -= userLoc[0];
			pXYZ[1] -= userLoc[1];
			pXYZ[2] -= userLoc[2];
			
			Corner c = new Corner(pXYZ[0],pXYZ[1], pXYZ[2]);
			
			//somethings really fucked up with the coords will fix later (probably not tho)
			pXYZ = c.rotateCornerGet(lookAngles[1], -lookAngles[0],0, new double[] {0,0,0});
			
			// calls the method which calculates where a vector drawn from the origin to the point in 3D Space would intersect with the viewport plane
			int [] locxy = getIntersection(pXYZ, viewportPlane, viewPoint, lookAngles);
			
			if(locxy == null)
			{
				draw = false;
				break;
			}
			else
			{
				// tells the corner where it is on the screen
				screenLocationsX[i] = locxy[0] + (viewPortXSize/2);
				screenLocationsY[i] = locxy[1] + (viewPortYSize/2);	
			}
		}
		
		if (draw)
		{
			g.fillPolygon(screenLocationsX, screenLocationsY, screenLocationsY.length);		
		}		
		
		return img;
	}
	
	public BufferedImage drawPolys(poly[]polys, BufferedImage img, double [] lookAngles, double [] userLoc)
	{
		Arrays.sort(polys, (a,b) -> a.getFurthestCorner().isFurtherThan(b.getFurthestCorner()));
		
		for (int i = 0; i < polys.length; i ++)
		{
			img = drawPoly(polys[i], img, lookAngles, userLoc);
		}
		
		return img;
	}	
}