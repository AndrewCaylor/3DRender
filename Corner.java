import java.util.ArrayList;

public class Corner {

	// THIS IS A UNIT COORDS (USED FOR ROTATING)
	// ROTATION IS DONE AT 0,0,0
	
	static final int I = 0;
	static final int J = 1;
	static final int K = 2;
	
	double [] locationIJK = new double [3]; 
	
	// REAL COORDS OF LOCATION USED FOR DRAWING
	int [] locationXYZ = new int [3]; 
	
	// LOCATIONS OF POINT ON THE SCREEN
	double [] screenLocations = new double [2];
	static final int X = 0;
	static final int Y = 1;
		
	ArrayList<Corner> neighbors;
	
	//Corner [] neighbors;
	
	String [][] xRotationMatrixData = new String [][] {
		{"1",  "0","0"   },
		{"0","cos","-sin"},
		{"0","sin","cos" }
	};
	
	String [][] yRotationMatrixData = new String [][] {
		{"cos" ,"0","sin"},
		{"0"   ,"1","0"  },
		{"-sin","0","cos"}
	};
	
	String [][] zRotationMatrixData = new String [][] {
		{"cos","-sin","0"},
		{"sin","cos" ,"0"},
		{"0"  ,"0"   ,"1"}
	};
	
	double [][] xRotationMatrix = new double [3][3];
	double [][] yRotationMatrix = new double [3][3];
	double [][] zRotationMatrix = new double [3][3];
	
	double [][][] matrices = new double [][][] {xRotationMatrix,yRotationMatrix,zRotationMatrix};
	String [][][] matricesData = new String [][][] {xRotationMatrixData,yRotationMatrixData,zRotationMatrixData};

	public Corner(double i, double j, double k)
	{
		locationIJK[I] = i;
		locationIJK[J] = j;
		locationIJK[K] = k;
	}
	
	public void setNeighbors(Corner... corners )
	{
		ArrayList<Corner> temp = new ArrayList<Corner>();
		for (int i = 0; i < corners.length; i ++) temp.add(corners[i]);
		neighbors = temp;
	}
	
	public ArrayList<Corner> getNeighbors()
	{
		return neighbors;
	}
	
	public void rotateCorner(double angleX, double angleY, double angleZ)
	{
		double [] angles = new double [] {angleX, angleY, angleZ};
		
		for (int ind = 0; ind < 3; ind ++)
		{
			makeRotationMatrix(matricesData[ind], matrices[ind], angles[ind]);
		}
		
		for (int ind = 0; ind < 3; ind ++)
		{
			// i need temps because it uses the matrix for future computations
			
			double newi = dotProduct(locationIJK,matrices[ind][I]);
			double newj = dotProduct(locationIJK,matrices[ind][J]);
			double newk = dotProduct(locationIJK,matrices[ind][K]);
			
			locationIJK[I] = newi;
			locationIJK[J] = newj;
			locationIJK[K] = newk;
			
		}
	}
	

	// THIS WORKS
	public void makeRotationMatrix(String [][] strArr, double [][] dubArr, double theta) 
	{
		for (int x = 0; x < strArr.length; x ++)
		{
			for (int y = 0 ; y < strArr[0].length; y ++)
			{
				dubArr[x][y] = evaluateMatrixDataEntry(strArr[x][y],theta);
			}
		}
	}
	
	// IDK
	public double dotProduct(double [] ijk, double [] matrix)
	{
		double total = 0;
		for (int ind = 0; ind < ijk.length; ind ++)
		{
			total += ijk[ind] * matrix[ind];
		}
		return total;
	}
	
	//THIS WORKS
	public double evaluateMatrixDataEntry(String str, double angle) //num1 comes from current IJK ; num2 comes from rotation matrix
	{
		if (str.contains("sin"))
		{
			if (str.contains("-"))
			{
				return -Math.sin(angle);
			}
			else
			{
				return Math.sin(angle);
			}
		}
		else if (str.contains("cos"))
		{
			if (str.contains("-"))
			{
				return -Math.cos(angle);
			}
			else
			{
				return Math.cos(angle);
			}
		}
		else if(str.contains("0"))
		{
			return 0;
		}
		else if(str.contains("1"))
		{
			return 1.0;
		}
		else
		{
			System.out.println("idk how this happened");
			return 5318008;
		}
	}
}