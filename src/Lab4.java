import javax.swing.*;
import java.util.*;
import java.io.*;

public class Lab4 
{        
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
                public void run() {
                    new MainFrame().setVisible(true);
                }
            }
        );
    }
}