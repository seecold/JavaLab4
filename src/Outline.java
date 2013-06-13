import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Outline extends JPanel
{
    public enum PanelAxis { x, y }

    private int R;
    private int centerX, centerY;
    private float pixelsPerUnit;
    private Mark lastClickedMark;
    private boolean isLastClickedMarkInside;
    private int animationStep = 10;
    private Thread animationThread;
    
    public Outline(int newR)
    {
        try {
            if (newR <= 0)
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
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
            clickMark(pointToMark(new Point(e.getX(), e.getY())));
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
    
    public void clickMark(Mark mark)
    {
        lastClickedMark = mark;
        isLastClickedMarkInside = isMarkInside(lastClickedMark) == 1;
        if (isLastClickedMarkInside) {
            animationThread = null;
            animationStep = 10;
            repaint();
        }
        else { // animation here
            animationThread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i <= 10; i++) {
                        try {
                            animationStep = i;
                            repaint();
                            Thread.sleep(100);
                            if (animationThread != Thread.currentThread())
                                return;
                        }
                        catch (InterruptedException e) {
                            animationStep = 10;
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
        isLastClickedMarkInside = isMarkInside(getLastClickedMark()) == 1;
        updateMeasurements();
    }
//    
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
        float X = (float)Math.ceil(mark.getX());
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
                        return ((R - X/R) / 2 >= Y) ? 1 : 0;
        }
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
        
        if (lastClickedMark != null) {
            if (isLastClickedMarkInside)
                g.setColor(new Color(0.0f, 1.0f, 0.0f, animationStep / 10.0f));
            else
                g.setColor(new Color(1.0f, 0.0f, 0.0f, animationStep / 10.0f));
            
            Point point = markToPoint(lastClickedMark);
            g.fillOval(point.x - 3, point.y - 3, R/(21-animationStep), R/(21-animationStep));
        }
    }
}