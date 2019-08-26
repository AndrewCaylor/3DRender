import java.awt.Color;
import java.util.Arrays;

public class poly {
	
	//TODO: only show if the poly if it is facing towards you
	
	double [] rotationPoint = new double[3];
	
	public Corner [] corners;
	
	public Color color;
	
	public double [] centerXYZ;
	
	public poly(Corner[] corners, double [] centerXYZ, Color color)
	{
		this.color = color;
		this.corners = corners;
		this.centerXYZ = centerXYZ;
	}
	
	// rotates around given center
	public void rotate(double [] angles, double [] center)
	{
		for (int i = 0; i < corners.length; i ++)
		{
			corners[i].rotateCornerSet(angles[0], angles[1], angles[2], center);
		}
	}
	
	// rotates around given center
	public poly getRotated(double [] angles, double [] center)
	{
		poly pTemp = new poly(this.corners, this.centerXYZ, this.color);
		
		for (int i = 0; i < pTemp.corners.length; i ++)
		{
			pTemp.corners[i].rotateCornerSet(angles[0], angles[1], angles[2], center);
		}
		
		return pTemp;
	}
	
	public Corner getFurthestCorner()
	{
		Corner furthest = corners[0];
		for(int i = 0; i < corners.length; i ++)
		{
			if(corners[i].locationIJK[Corner.K] > furthest.locationIJK[Corner.K])
			{
				furthest = corners[i];
			}
		}
		return furthest;
	}
}
