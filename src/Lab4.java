import javax.swing.*;
public class Lab4 
{        
    public static void main(String[] args) 
    {
        try 
        {
            UIManager.setLookAndFeel 
            (UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}