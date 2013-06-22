import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Outline extends JPanel
{
    public enum PanelAxis { x, y }

    private int R;
    private int centerX, centerY;
    private float pixelsPerUnit;
    private Mark lastClickedMark;
    private int animationStep = 0;
    private Thread animationThread;
    
    public Outline(int newR)
    {
        try 
        {
            if (newR <= 0)
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) 
        {
            System.out.println("R <= 0 is invalid, changind to R = 1");
            newR = 1;
        }
        
        this.R = newR;
        
        addComponentListener(new ComponentListener(){
            public void componentResized(ComponentEvent e) {
                updateMeasurements();
            }
            
            public void componentHidden(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
        });
        
        addMouseListener(new MouseListener() { 
          public void mouseClicked(MouseEvent e) { 
            placeMark(pointToMark(new Point(e.getX(), e.getY())));
          }
          public void mouseExited(MouseEvent e) {}
          public void mouseEntered(MouseEvent e) {}
          public void mouseReleased(MouseEvent e) {}
          public void mousePressed(MouseEvent e) {}
        });
    }
    
    public Mark getLastClickedMark()
    {
        return (lastClickedMark != null) ? lastClickedMark : new Mark(0, 0);
    }
    
    public void placeMark(Mark mark)
    {
        lastClickedMark = mark;
        if (isMarkInside(getLastClickedMark())==0) {
            animationThread = null;
            animationStep = 5;
            repaint();
        }
        else 
        { // animation here
            animationThread = new Thread(new Runnable() 
            {
                
                public void run() 
                {
                    boolean reverseFlag = false;
                    for(;;) 
                    {
                        try 
                        {
                            if(!reverseFlag)
                                animationStep++;
                            else
                                animationStep--;
                            
                            repaint();
                            if((animationStep==10)||(animationStep==0))
                                reverseFlag=!reverseFlag;
                            Thread.sleep(10);
                            if (animationThread != Thread.currentThread())
                                return;
                        }
                        catch (InterruptedException e) 
                        {
                            animationStep = 5;
                        }
                    }
                }
            });
            animationThread.start();
        }
    }
    
    public int getR()
    {
        return R;
    }
    
    public void changeR(int R)
    {
        this.R = R;
        updateMeasurements();
    }
    
    public void updateMeasurements()
    {
        Dimension size = getSize();
        centerX = size.width / 2;
        centerY = size.height / 2;
        pixelsPerUnit = Math.min(size.width, size.height) / 3.0f / R;
        repaint();
    }
    
    public int isMarkInside(Mark mark)
    {
        String recieved = null;
        try
        {   
            Socket clientSocket = new Socket("localhost", 6789);   
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
            
            outToServer.writeBytes(mark.toString()+((Integer)R).toString());   
            recieved = inFromServer.readLine();
            clientSocket.close();
            
        }
        catch(IOException e)
        {            
        }
        return Integer.parseInt(recieved);        
        /*float X = (float)Math.ceil(mark.getX());
        float Y = (float)Math.ceil(mark.getY());
        
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
                        return (X-Y<(R)) ? 1 : 0;
        }*/
    }
    
    public float pixelsToUnits(int pixels, PanelAxis axis)
    {
        if (axis == PanelAxis.x)
            return (pixels - centerX) / pixelsPerUnit;
        else // if (axis == PanelAxis.y)
            return (getHeight() - (pixels + centerY)) / pixelsPerUnit;
    }
    
    public int unitsToPixels(float units, PanelAxis axis)
    {
        if (axis == PanelAxis.x)
            return Math.round(lastClickedMark.getX() * pixelsPerUnit) + centerX;
        else // if (axis == PaneAxis.y)
            return getHeight() - 
                Math.round(lastClickedMark.getY() * pixelsPerUnit) - centerY;
    }
    
    public Point markToPoint(Mark mark)
    {
        return new Point(unitsToPixels(mark.getX(), PanelAxis.x), 
            unitsToPixels(mark.getY(), PanelAxis.y));
    }
    
    public Mark pointToMark(Point point)
    {
        return new Mark(pixelsToUnits(point.x, PanelAxis.x), 
            pixelsToUnits(point.y, PanelAxis.y));
    }
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        g.setColor(Color.black);
        
        float rToPixelsFloat = R * pixelsPerUnit;
        int rToPixels = Math.round(rToPixelsFloat);
        
        g.fillRect(centerX - rToPixels/2, centerY , rToPixels/2, rToPixels);
        g.fillArc(centerX - rToPixels/2 , centerY - rToPixels/2, 
            rToPixels, rToPixels, 90, 90);
        g.fillPolygon(new int[]{centerX + rToPixels, centerX, centerX}, 
            new int[]{centerY, centerY + rToPixels, centerY}, 3);
        
        g.setColor(Color.blue);
        g.drawLine(centerX, 0, centerX, centerY * 2);
        g.drawLine(0, centerY, centerX * 2, centerY);
        
        g.setColor(Color.red);
        if (lastClickedMark != null) 
        {
            Point point = markToPoint(lastClickedMark);
            g.fillOval(point.x - R/(21-animationStep)/2, point.y - R/(21-animationStep)/2,
                R/(21-animationStep), R/(21-animationStep));            
        }
    }
}