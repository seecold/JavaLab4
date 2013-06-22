import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class MainFrame extends JFrame
{
    
    //result panel
    final JTextField xyTextField = new JTextField();
    
    final Outline newOutline = new Outline(1);
    
    public MainFrame() 
    {        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        initComponents();
    }
                        
    private void initComponents() 
    {
        setLayout(new GridLayout(1, 2));      
        newOutline.setBackground(Color.green);
        add(newOutline);     
        
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5,1));       
        add(controlPanel);
        
        //R spinner
        final JSpinner rSpinner = new JSpinner();
        JPanel rPanel = new JPanel();
        rPanel.add(new JLabel("Choose R"));
        rSpinner.setValue(100);
        newOutline.changeR(100);
        rSpinner.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) 
            {
                Integer R = Integer.parseInt(rSpinner.getValue().toString());
                if(R<=0){R=0;rSpinner.setValue(0);}
                newOutline.changeR((int)Math.ceil(R));
            }
        });
        rPanel.add(rSpinner);
        
        //X combo
        Integer[] xValues = {-75,-50,-25,0,25,50,75};
        final JComboBox xComboBox = new JComboBox(xValues);
        JPanel xPanel = new JPanel();
        xPanel.add(new JLabel("Choose X:"));
        xPanel.add(xComboBox);
        
        //Y checkbox
        final ButtonGroup yCheckBoxGroup = new ButtonGroup();
        JPanel yCheckBoxPanel = new JPanel();
        for(Integer i=-75;i<=75;i+=25)
        {
            JCheckBox tempCheckBox = new JCheckBox(i.toString());
            tempCheckBox.setSelected(true);
            tempCheckBox.setActionCommand(i.toString());
            yCheckBoxPanel.add(tempCheckBox);
            yCheckBoxGroup.add(tempCheckBox);        
        }
        JPanel yPanel = new JPanel();
        yPanel.add(new JLabel("Choose Y:"));
        yPanel.add(yCheckBoxPanel);   
        
        //refresh button
        JButton refreshButton = new JButton("Use predefined marks");
        refreshButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                Float X = new Float((Integer)xComboBox.getSelectedItem());
                Float Y = Float.parseFloat(yCheckBoxGroup.getSelection().getActionCommand());
                Mark tempMark = new Mark(X ,Y);
                showMarkInfo(tempMark);
                newOutline.placeMark(tempMark);
            }
        });
        
        //stacking
        controlPanel.add(xPanel);
        controlPanel.add(yPanel);
        controlPanel.add(rPanel);
        controlPanel.add(xyTextField);
        controlPanel.add(refreshButton);      
        add(controlPanel);
        refreshButton.doClick();
        pack();
        
        newOutline.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                Mark mark = newOutline.pointToMark(new Point(e.getX(), e.getY()));
                showMarkInfo(mark);
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
        });
    }
    
    public void showMarkInfo(Mark mark)
    {
        xyTextField.setText("Current mark is: "+mark.toString());
    }

}
