
import java.util.ArrayList;



public class Step {

	public static final int COMPARE = 0;
	public static final int SWAP = 1;
	public static final int TOBUFFER = 3;
	public static final int TOBARS = 4;
	private int index1;
	private int index2;
	private int type;	

	public Step(int i1, int i2, int t)
	{
		index1 = i1;
		index2 = i2;
		type = t;
	}

	

	public int getIndex1()
	{
		return index1;
	}

	public int getIndex2()
	{
		return index2;
	}

	public int getType()

	{
		return type;
	}
}

