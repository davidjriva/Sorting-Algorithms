import java.awt.Color;



public class Bar {

	private int height;

	private Color color;

	

	public Bar(int InputHeight)

	{

		height = InputHeight;

		int r = (int)(Math.random()*255);

		int g = (int)(Math.random()*255);

		int b = (int)(Math.random()*255);

		color = new Color(r,g,b);

	}

	

	public Color getColor()

	{

		return color;

	}

	

	public int getHeight()

	{

		return height;

	}

}

