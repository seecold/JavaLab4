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
        setPreferredSize(new Dimension(500, 500));
        setMinimumSize(getPreferredSize());
        
        initComponents();
    }
                        
    private void initComponents() 
    {
        this.setLayout(new GridLayout(1, 2));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 3));       
        this.add(controlPanel);
        
        final JSpinner RSpinner = new JSpinner();
        
        Integer[] xValues = {-5,-4,-3,-2,-1,0,1,2,3,4,5};
        final JComboBox xComboBox = new JComboBox(xValues);
        
        final ButtonGroup yCheckBoxGroup = new ButtonGroup();
        JPanel yCheckBoxPanel = new JPanel();
        for(Integer i=-5;i<=5;i++)
        {
            JCheckBox tempCheckBox = new JCheckBox("y="+i.toString());
            tempCheckBox.setSelected(true);
            tempCheckBox.setActionCommand(i.toString());
            yCheckBoxPanel.add(tempCheckBox);
            yCheckBoxGroup.add(tempCheckBox);        
        } 
        
        final JTextField xyTextField = new JTextField();        
        //xyTextField.setEditable(false);
        
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
                xyTextField.setText(X.toString()+Y.toString()+R.toString());
                else
                xyTextField.setText("nope");    
            }
        });
        
        
        
        controlPanel.add(xComboBox);
        controlPanel.add(yCheckBoxPanel);
        controlPanel.add(RSpinner);
        controlPanel.add(xyTextField);
        controlPanel.add(refreshButton);
        
        getContentPane().add(new JSeparator());
        getContentPane().add(controlPanel);
        pack();
    }
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) 
        {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
