import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReadFile {

	public static BufferedImage readImageDesktop(String path)
	{
		try
	    {
			BufferedImage image = ImageIO.read(new File("C:\\Users\\drewh\\Downloads\\test1.jpg" + path));
			return image;
	    } 
	    catch (IOException e)
	    {
	    	System.out.println("frick");
	    	return null;
	    }
	  
	}
	
}
