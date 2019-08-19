
public class Heart 
{
	int centerX;
	int centerY;
	int centerZ;
	int scale;
	
	protected final boolean virginity = true;
	
	public Heart(int cX, int cY, int cZ, int sc)
	{
		centerX = cX;
		centerY = cY;
		centerZ = cZ;
		scale = sc;
		setCornerNeighbors();
	}
	
	Corner [] corners = new Corner[] {
			new Corner(0,0,-3),
			
			new Corner(-2,0,-1), 
			new Corner(2,0,-1),
			
			new Corner(-2.5,0,0), 
			new Corner(2.5,0,0), 
			
			new Corner(-2.5,0,1), 
			new Corner(2.5,0,1), 
			
			new Corner(-2,0,1.75), 
			new Corner(2,0,1.75), 
			
			new Corner(-1,0,1.75), 
			new Corner(1,0,1.75), 

			new Corner(0,0,1)
			//cornsers.size() = 11
	};
	
	private void setCornerNeighbors()
	{
		corners[0].setNeighbors(corners[1], corners[2]);
		corners[1].setNeighbors(corners[3]);
		corners[2].setNeighbors(corners[4]);
		corners[3].setNeighbors(corners[5]);
		corners[4].setNeighbors(corners[6]);
		corners[5].setNeighbors(corners[7]);
		corners[6].setNeighbors(corners[8]);
		corners[7].setNeighbors(corners[9]);
		corners[8].setNeighbors(corners[10]);
		corners[9].setNeighbors(corners[11]);
		corners[10].setNeighbors(corners[11]);
		corners[11].setNeighbors(corners[10], corners[9]);
	}
	
	public void rotate(double angleX, double angleY, double angleZ)
	{
		for (int ind = 0; ind < corners.length; ind ++)
		{
			corners[ind].rotateCorner(angleX, angleY, angleZ);
		}
	}
	
	public Corner [] getCorners()
	{
		setCornerLocationsXYZ();
		return corners;
	}
	
	public void setCornerLocationsXYZ()
	{
		for (int ind = 0; ind < corners.length; ind ++)
		{					
			corners[ind].locationXYZ[0] = (int) ( centerX + (scale * corners[ind].locationIJK[0]) );
			corners[ind].locationXYZ[1] = (int) ( centerY + (scale * corners[ind].locationIJK[1]) );
			corners[ind].locationXYZ[2] = (int) ( centerZ + (scale * corners[ind].locationIJK[2]) );
		}
	}
	
	public void moveCenter(double [] direction)
	{
		centerX += direction[0];
		centerY += direction[1];
		centerZ += direction[2];
	}
	
	public void printCornersIJK()
	{
		for (int ind = 0; ind < corners.length; ind ++)
		{	
			System.out.print(corners[ind].locationIJK[0] + " ");
			System.out.print(corners[ind].locationIJK[1] + " ");
			System.out.print(corners[ind].locationIJK[2] + "\n");
		}
		System.out.println("\n");
	}
	public double Error0()
	{
		double i = corners[0].locationIJK[0];
		double j = corners[0].locationIJK[1];
		double k = corners[0].locationIJK[2];

		double ijksqu = Math.pow(i, 2) + Math.pow(j, 2) + Math.pow(k, 2);
		
		return ijksqu - 3;
	}
}
