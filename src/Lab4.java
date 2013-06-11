import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

class MainWindow extends JFrame
{
    private Figure figure;
    private JTextArea markTextArea;
    
    public MainWindow(String caption)
    {
        super(caption);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 350));
        setMinimumSize(getPreferredSize());
        setLayout(new GridLayout(1, 2));

        // a Figure to draw
        figure = new Figure(1);
        figure.setBackground(Color.yellow);
        add(figure);
        
        // Panel with parameter controls
        JPanel parametersPanel = new JPanel();
        parametersPanel.setLayout(
            new BoxLayout(parametersPanel, BoxLayout.Y_AXIS));
        add(parametersPanel);
        
        // X coordinate - combo box
        Integer[] xValues = {-2, -1, 0, 1, 2};
        final JComboBox<Integer> xComboBox = new JComboBox<Integer>(xValues);
        xComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int X = (int)xComboBox.getSelectedItem();
                Mark mark = new Mark(X, figure.getLastClickedMark().getY());
                figure.clickMark(mark);
                updateMarkTextArea(mark);
            }
        });
        JPanel xPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        xPanel.add(new JLabel("X coordinate:"));
        xPanel.add(xComboBox);
        parametersPanel.add(xPanel);
        
        // Y coordinate - radio buttons
        JPanel yRadioPanel = new JPanel(); 
        yRadioPanel.setLayout(new BoxLayout(yRadioPanel, BoxLayout.X_AXIS));
        ButtonGroup yButtonGroup = new ButtonGroup();
        for (Integer i = -2; i <= 2; i++) {
            JRadioButton radioButton = new JRadioButton(i.toString());
            radioButton.setActionCommand(i.toString());
            radioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int Y = Integer.parseInt(e.getActionCommand());
                    Mark mark = new Mark(figure.getLastClickedMark().getX(), Y);
                    figure.clickMark(mark);
                    updateMarkTextArea(mark);
                }
            });
            yButtonGroup.add(radioButton);
            yRadioPanel.add(radioButton);
        }
        JPanel yPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        yPanel.add(new JLabel("Y coordinate:"));
        yPanel.add(yRadioPanel);
        parametersPanel.add(yPanel);
        
        // R - slider
        final JSlider rSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        final JLabel rValueLabel = new JLabel("1");
        rSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Integer R = rSlider.getValue();
                figure.changeR(R);
                rValueLabel.setText(R.toString());
                updateMarkTextArea(figure.getLastClickedMark());
            }
        });
        JPanel rPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        rPanel.add(new JLabel("R parameter:"));
        rPanel.add(rSlider);
        rPanel.add(rValueLabel);
        parametersPanel.add(rPanel);
        
        // Coordinates - Text Area
        markTextArea = new JTextArea();
        figure.addMouseListener(new MouseListener() { 
            public void mouseClicked(MouseEvent e) {
                Mark mark = figure.pointToMark(new Point(e.getX(), e.getY()));
                updateMarkTextArea(mark);
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
        });
        parametersPanel.add(markTextArea);
        
        pack();
    }
    
    private void updateMarkTextArea(Mark mark)
    {
        markTextArea.setText(
            String.format("Last mark: %s\nInside of figure: %s", 
                mark.toString(),
                (figure.isMarkInside(mark) == 1) ? "yes" : "no"));
    }
}

public class Lab4
{
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainWindow mainWindow = new MainWindow("Lab4");
                    mainWindow.setVisible(true);
                }
            }
        );
    }
}
