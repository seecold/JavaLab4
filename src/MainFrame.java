import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;


public class MainFrame extends JFrame
{
    //result panel must be public to be acsessed from figure
    final public JTextField xyTextField = new JTextField();
    final Outline figure = new Outline(1);
    
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
        setLayout(new GridLayout(1, 2));      
        figure.setBackground(Color.green);
        add(figure);     
        
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5,1));       
        this.add(controlPanel);
        
        //R spinner
        final JSpinner RSpinner = new JSpinner();
        JPanel rPanel = new JPanel();
        rPanel.add(new JLabel("Choose R"));
        RSpinner.setPreferredSize(new Dimension(50, 20));
        RSpinner.setValue(110);
        figure.changeR(110);
        RSpinner.addChangeListener(new ChangeListener() 
        {
            public void stateChanged(ChangeEvent e) 
            {
                Integer R = Integer.parseInt(RSpinner.getValue().toString());
                figure.changeR((int)Math.ceil(Integer.parseInt(RSpinner.getValue().toString())));                   
            }
        });
        rPanel.add(RSpinner);
        
        //X combo
        Integer[] xValues = {-75,-50,-25,0,25,50,75};
        final JComboBox xComboBox = new JComboBox(xValues);
        JPanel xPanel = new JPanel();
        xPanel.add(new JLabel("Choose X"));
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
        yPanel.add(new JLabel("Choose Y"));
        yPanel.add(yCheckBoxPanel);   
        
        //refresh button
        JButton refreshButton = new JButton("Use predefined marks");
        refreshButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                Float X = new Float((int)xComboBox.getSelectedItem());
                Float Y = Float.parseFloat(yCheckBoxGroup.getSelection().getActionCommand());
                Mark tempMark = new Mark(X ,Y);
                showMarkInfo(tempMark, figure.getR());
                figure.clickMark(tempMark);
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
    }
    
    public void showMarkInfo(Mark mark, int R)
    {
        xyTextField.setText("X="+mark.getX()+"\n"+" Y="+mark.getY()+" R="+R);
    }

}
