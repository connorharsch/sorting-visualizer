package Visualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Visualizer.ContentPanel.Sorting;

public class IOPanel extends JPanel {

    private GridBagConstraints gbc = new GridBagConstraints();
    private ContentPanel cp;

    private JPanel output = new JPanel();
    public static JLabel labelArray = new JLabel("-");

    private JPanel buttons = new JPanel();
    private String[] buttonNames = { "Start", "Reset", "Bubble V1", "Bubble V2", "Bubble V3", "Select", "Quick", "Merge", "Heap" };
    private JButton[] buttonArr = new JButton[buttonNames.length];

    private JPanel algs = new JPanel();
    private JPanel row0 = new JPanel();
    private JPanel row1 = new JPanel();

    IOPanel(ContentPanel c) {
        cp = c;

        buttons.setLayout(new GridBagLayout());
        algs.setLayout(new GridBagLayout());
        row0.setLayout(new GridBagLayout());
        row1.setLayout(new GridBagLayout());
        setLayout(new GridBagLayout());

        labelArray.setFont(new Font("Courier New", Font.BOLD, 14));
        labelArray.setForeground(Color.BLACK);

        output.add(labelArray);
        add(output);

        algs.add(row0,gbc);
        
        gbc.gridy = 1;
        add(buttons, gbc);
        algs.add(row1, gbc);

        gbc.gridy = 2;
        add(algs, gbc);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = e.getSource();

                if (o == buttonArr[0]) {
                    if (!cp.running) {
                        try {
                            cp.running = true;
                            cp.animate();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    reset();
                    if (o == buttonArr[2]) {
                        cp.algo = Sorting.BUBBLEV1;
                    } else if (o == buttonArr[3]) {
                        cp.algo = Sorting.BUBBLEV2;
                    } else if (o == buttonArr[4]) {
                        cp.algo = Sorting.BUBBLEV3;
                    } else if (o == buttonArr[5]) {
                        cp.algo = Sorting.SELECT;
                    } else if (o == buttonArr[6]) {
                        cp.algo = Sorting.QUICK;
                    } else if (o == buttonArr[7]) {
                        cp.algo = Sorting.MERGE;
                    } else if (o == buttonArr[8]) {
                        cp.algo = Sorting.HEAP;
                    }
                }
            }
        };

        for(int i = 0; i < buttonArr.length; i++){
            buttonArr[i] = new JButton(buttonNames[i]);
            if(i < 2){
                buttons.add(buttonArr[i]);
            }else if (i < 5){
                row0.add(buttonArr[i]);
            }else if (i < buttonArr.length){
                row1.add(buttonArr[i]);
            }
            format(buttonArr[i]);
            buttonArr[i].addActionListener(listener);
        }
    }

    private void reset() {
        if (cp.running) {
            try {
                if (cp.t != null) {
                    cp.t.stop();
                }
                labelArray.setText("-");
                cp.running = false;
                cp.init();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void format(JButton button) {
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
}