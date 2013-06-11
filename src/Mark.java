public class Mark implements Comparable<Mark>
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
        
        @Override
        public int compareTo(Mark anotherMark) 
        {
            return 0;
        }
}