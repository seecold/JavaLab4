import javax.swing.*;
import java.awt.*;

public class MainFrame extends javax.swing.JFrame 
{
    public MainFrame(String name) 
    {
        super(name);
        initComponents();
        this.setPreferredSize(new Dimension(1000, 1000));
    }
                        
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        GridLayout mainLayout = new GridLayout(1, 2);
        GridLayout controlLayout = new GridLayout(3, 3);
        JPanel controlPanel = new JPanel();
        
        this.setLayout(mainLayout);
        controlPanel.setLayout(controlLayout);  
        
        this.add(controlPanel);
        
        jSpinner1 = new javax.swing.JSpinner();
        controlPanel.add(jSpinner1);
        
        getContentPane().add(new JSeparator());
        getContentPane().add(controlPanel);
        pack();
    }
    private javax.swing.JSpinner jSpinner1;                
}
