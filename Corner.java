import java.util.Arrays;

public class Corner {
	
	// ROTATES AROUND 000
	// TODO: be able to rotate around not 0,0,0
	// subtract point you want to rotate around then add the value back after rotated
	
	// THIS IS A UNIT COORDS (USED FOR ROTATING)
	
	static final int I = 0;
	static final int J = 1;
	static final int K = 2;
	
	public double [] locationIJK = new double [3]; 
		
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


	public void rotateCornerSet(double angleX, double angleY, double angleZ, double [] centerXYZ)
	{
		double [] angles = new double [] {angleX, angleY, angleZ};
		
		for (int ind = 0; ind < 3; ind ++)
		{
			makeRotationMatrix(matricesData[ind], matrices[ind], angles[ind]);
		}
		
		for (int ind = 0; ind < matrices.length; ind ++) // applies rotation matrix for each rotation axis
		{
			double [] tempLocationIJK = new double[3];
			
			// subtracts the center location from current location to allow it to rotate around any point
			// automatically rotates around 0,0,0
			
			tempLocationIJK[I] = locationIJK[I] - centerXYZ[I];
			tempLocationIJK[J] = locationIJK[J] - centerXYZ[J];
			tempLocationIJK[K] = locationIJK[K] - centerXYZ[K];
			
			// i need temps because it uses the matrix for future computations
			
			double newi = dotProduct(tempLocationIJK,matrices[ind][I]);
			double newj = dotProduct(tempLocationIJK,matrices[ind][J]);
			double newk = dotProduct(tempLocationIJK,matrices[ind][K]);
			
			//adding center back in after rotation
			
			locationIJK[I] = newi + centerXYZ[I];
			locationIJK[J] = newj + centerXYZ[J];
			locationIJK[K] = newk + centerXYZ[K];
		}
	}
	

	public double [] rotateCornerGet(double angleX, double angleY, double angleZ, double [] centerXYZ)
	{
		double [] angles = new double [] {angleX, angleY, angleZ};
		
		for (int ind = 0; ind < 3; ind ++)
		{
			makeRotationMatrix(matricesData[ind], matrices[ind], angles[ind]);
		}
		
		double [] locationIJKtemp = locationIJK.clone();
		
		for (int ind = 0; ind < matrices.length; ind ++) // applies rotation matrix for each rotation axis
		{
			double [] tempLocationIJK = new double[3];
			
			// subtracts the center location from current location to allow it to rotate around any point
			// automatically rotates around 0,0,0
			
			tempLocationIJK[I] = locationIJKtemp[I] - centerXYZ[I];
			tempLocationIJK[J] = locationIJKtemp[J] - centerXYZ[J];
			tempLocationIJK[K] = locationIJKtemp[K] - centerXYZ[K];
			
			// i need temps because it uses the matrix for future computations
			
			double newi = dotProduct(tempLocationIJK,matrices[ind][I]);
			double newj = dotProduct(tempLocationIJK,matrices[ind][J]);
			double newk = dotProduct(tempLocationIJK,matrices[ind][K]);
			
			//adding center back in after rotation
			
			locationIJKtemp[I] = newi + centerXYZ[I];
			locationIJKtemp[J] = newj + centerXYZ[J];
			locationIJKtemp[K] = newk + centerXYZ[K];
		}
		
		return locationIJKtemp;
	}
	
	
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
	
	public double dotProduct(double [] ijk, double [] matrix)
	{
		double total = 0;
		for (int ind = 0; ind < ijk.length; ind ++)
		{
			total += ijk[ind] * matrix[ind];
		}
		return total;
	}
	
	public int isFurtherThan(Corner c)
	{
		if (locationIJK[K] < c.locationIJK[K])
		{
			return 1;
		}
		else if (locationIJK[K] > c.locationIJK[K])
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	// uses string value of rotation matrix entry to make calculations
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