package current_version;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import current_version.ContentPanel.Sorting;

public class IOPanel extends JPanel{
    
    JFrame window;
    
    static JPanel buttons;
    static JPanel output;
    ContentPanel cp;

    static JLabel labelArray;
    JButton start = new JButton("start");
    JButton reset = new JButton("reset");
    JButton bubble = new JButton("bubblesort");
    JButton quick = new JButton("quicksort");

    GridBagConstraints gbc = new GridBagConstraints();

    IOPanel(JFrame w, ContentPanel c){
        window = w;
        cp = c;
        setLayout(new GridBagLayout());
        buttons = new JPanel();
        buttons.setLayout(new GridBagLayout());
        output = new JPanel();


        labelArray = new JLabel("-");
        labelArray.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        labelArray.setForeground(Color.BLACK);
        labelArray.setVisible(true);

        start.setFocusPainted(false);
        format(start);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!cp.isRunning()){
                    try {
                        cp.setRunning(true);
                        cp.genArray();
                        labelArray.setVisible(true);
                        cp.animate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }});
        
        format(reset);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }});
        
        format(bubble);
        bubble.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.BUBBLE;
            }});

        format(quick);
        quick.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.QUICK;
            }});

        add(output);
        output.add(labelArray);
        buttons.add(start);
        buttons.add(reset);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(buttons,gbc);
        buttons.add(bubble,gbc);
        
        gbc.gridx = 1;
        buttons.add(quick,gbc);
    }
    
    private void reset(){
        if(cp.isRunning()){
            System.out.println("RESET");
            try {
                if(cp.timer != null){
                    cp.timer.stop();
                }
                cp.setRunning(false);
                labelArray.setText("-");
                ContentPanel.array_index = 0;
                ContentPanel.compare_index = Integer.MAX_VALUE;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void format(JButton button){
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
    }
}