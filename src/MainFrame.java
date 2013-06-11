import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;


public class MainFrame extends JFrame
{
    public MainFrame(String name) 
    {
        super(name);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 300));
        setMinimumSize(getPreferredSize());
        setResizable(false);
        
        initComponents();
    }
                        
    private void initComponents() 
    {
        setLayout(new GridLayout(1, 3));
        Outline figure = new Outline(1);
        figure.setBackground(Color.GREEN);
        add(figure);     
        
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5,1));       
        this.add(controlPanel);
        
        //R spinner
        final JSpinner RSpinner = new JSpinner();
        JPanel rPanel = new JPanel();
        rPanel.add(new JLabel("Choose R"));
        RSpinner.setPreferredSize(new Dimension(50, 20));
        rPanel.add(RSpinner);
        
        //X combo
        Integer[] xValues = {-2,-1,0,1,2};
        final JComboBox xComboBox = new JComboBox(xValues);
        JPanel xPanel = new JPanel();
        xPanel.add(new JLabel("Choose X"));
        xPanel.add(xComboBox);
        
        //Y checkbox
        final ButtonGroup yCheckBoxGroup = new ButtonGroup();
        JPanel yCheckBoxPanel = new JPanel();
        for(Integer i=-2;i<=2;i++)
        {
            JCheckBox tempCheckBox = new JCheckBox(i.toString());
            tempCheckBox.setSelected(true);
            tempCheckBox.setActionCommand(i.toString());
            yCheckBoxPanel.add(tempCheckBox);
            yCheckBoxGroup.add(tempCheckBox);        
        }
        JPanel yPanel = new JPanel();
        yPanel.add(new JLabel("Choose Y"));
        yPanel.add(yCheckBoxPanel);
        
        //result panel
        final JTextField xyTextField = new JTextField();
        
        
        //refresh button
        JButton refreshButton = new JButton("Refresh!");        
        refreshButton.setActionCommand("refresh");
        refreshButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                Integer X = (int)xComboBox.getSelectedItem();
                String Y = yCheckBoxGroup.getSelection().getActionCommand();
                Integer R = Integer.parseInt(RSpinner.getValue().toString());
                
                if((new Outline(R)).isMarkInside(new Mark(X,Integer.parseInt(Y)))!=1)                
                xyTextField.setText("X="+X.toString()+" Y="+Y.toString()+" R="+R.toString());
                else
                xyTextField.setText("nope");    
            }
        });
        
        
        //stacking
        controlPanel.add(xPanel);
        controlPanel.add(yPanel);
        controlPanel.add(rPanel);
        controlPanel.add(xyTextField);
        controlPanel.add(refreshButton);      
        add(controlPanel);
        pack();
    }
}
