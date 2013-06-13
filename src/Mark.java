public class Mark
{
	private float X;
	private float Y;
	
	public float getX() { return X; }
	public float getY() { return Y; }
	
	public Mark(float X, float Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	public String toString()
	{
		return String.format("(%.2f;%.2f)", X, Y);
	}
}