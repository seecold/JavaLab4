public class Outline
{
	private int R;
	
	public Outline(int R)
	{
		this.R = R;
	}
	
	public int isMarkInside(Mark mark)
	{
		float X = mark.getX();
		float Y = mark.getY();
		
		if (Y >= 0)
		{
			if (X >= 0) // first quarter
				return 0;
			else // second quarter
				return ((X*X + Y*Y) <= (R/2)*(R/2)) ? 1 : 0;
		}
		else
		{
			if (X <= 0) // third quarter
				return ((X >= -R/2) && (Y >= -R)) ? 1 : 0;
			else // fourth quarter
				return ((R - X/R) / 2 >= Y) ? 1 : 0;
		}
	}
}