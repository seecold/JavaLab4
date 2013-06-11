import java.util.*;
import java.io.*;

public class Lab4 
{
	public static void main(String[] args) 
	{
                try
		{                        
                        System.out.println("Input R in format \"xx...\"");
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        int R=0;
                        for(;;)
                        {
                            try
                            {
                                String Buffer = in.readLine();
                                R = Integer.parseInt(Buffer);
                                if(R<0)
                                    System.err.println("Wrong R format, try again!");
                                else
                                    break;
                            }
                            catch (NumberFormatException e)
                            {
                                System.err.println("Wrong R format, try again!");
                            }                            
                        }
                        
                        
                        Outline area = new Outline(R);                        
                        PriorityQueue<Mark> marks = 
                                new PriorityQueue<Mark>(7);
                        marks.add(new Mark(5 ,5));
                        marks.add(new Mark(1 ,2));
                        marks.add(new Mark(3 ,-5));
                        marks.add(new Mark(1 ,-2));
                        marks.add(new Mark(-3 ,-5));
                        marks.add(new Mark(-2 ,2));
                        marks.add(new Mark(-5 ,5));                        
		
			System.out.println("Mispointed marks:");
                        Mark temp = marks.poll();
                        do                        
                        {                              
                                if(area.isMarkInside(temp) < 1)
                                    System.out.println(temp.toString());
                        }
                        while ((temp = marks.poll()) != null);
                }
                catch (IOException e)
		{
			System.err.println(e.toString());
		}
                                
	}
}