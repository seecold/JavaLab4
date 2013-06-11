import javax.swing.*;
import java.awt.*;


public class MainFrame extends javax.swing.JFrame 
{
    public MainFrame(String name) 
    {
        super(name);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 350));
        setMinimumSize(getPreferredSize());
        
        initComponents();
    }
                        
    private void initComponents() 
    {
        this.setLayout(new GridLayout(1, 2));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 3));       
        this.add(controlPanel);
        
        JSpinner RSpinner = new JSpinner();
        
        Integer[] xValues = {-5,-4,-3,-2,-1,0,1,2,3,4,5};
        JComboBox xComboBox = new JComboBox(xValues);
        
        ButtonGroup yCheckBoxGroup = new ButtonGroup();
        JPanel yCheckBoxPanel = new JPanel();
        for(Integer i=-5;i<=5;i++)
        {
            JCheckBox tempCheckBox = new JCheckBox("y="+i.toString());
            yCheckBoxPanel.add(tempCheckBox);
            yCheckBoxGroup.add(tempCheckBox);        
        } 
        
        controlPanel.add(xComboBox);
        controlPanel.add(yCheckBoxPanel);
        controlPanel.add(RSpinner);        
        
        getContentPane().add(new JSeparator());
        getContentPane().add(controlPanel);
        pack();
    }
    
}


class SevenCheckBoxPanel extends JPanel
{
    JCheckBox y1 = new JCheckBox("y1");
    JCheckBox y2 = new JCheckBox("y2");
    JCheckBox y3 = new JCheckBox("y3");
    JCheckBox y4 = new JCheckBox("y4");
    JCheckBox y5 = new JCheckBox("y5");
    JCheckBox y6 = new JCheckBox("y6");
    JCheckBox y7 = new JCheckBox("y7");
    
//    public SevenCheckBoxPanel()
//    {
//            JPanel yCheckBoxPanel = new JPanel();
//            yCheckBoxPanel.setLayout(new GridLayout(5, 5));
//            
//            yCheckBoxPanel.add(y1);
//            yCheckBoxPanel.add(y2);
//            yCheckBoxPanel.add(y3);
//            yCheckBoxPanel.add(y4);
//            yCheckBoxPanel.add(y5);
//            yCheckBoxPanel.add(y6);
//            yCheckBoxPanel.add(y7);
//    }
}