package Visualizer;

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

import Visualizer.ContentPanel.Sorting;

public class IOPanel extends JPanel {

    JFrame window;

    static JLabel labelArray = new JLabel("-");;
    static JPanel buttons = new JPanel();
    static JPanel output = new JPanel();
    static JPanel algs = new JPanel();
    ContentPanel cp;

    JButton bubblev1 = new JButton("Bubblesort V1");
    JButton bubblev2 = new JButton("Bubblesort V2");
    JButton bubblev3 = new JButton("Bubblesort V3");
    JButton quick = new JButton("Quicksort");
    JButton merge = new JButton("Mergesort");
    JButton start = new JButton("Start");
    JButton reset = new JButton("Reset");

    GridBagConstraints gbc = new GridBagConstraints();

    IOPanel(ContentPanel c) {
        cp = c;

        setLayout(new GridBagLayout());
        buttons.setLayout(new GridBagLayout());
        algs.setLayout(new GridBagLayout());

        labelArray.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        labelArray.setForeground(Color.BLACK);

        start.setFocusPainted(false);
        format(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cp.running) {
                    try {
                        cp.running = true;
                        cp.animate();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        format(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        format(bubblev1);
        bubblev1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.BUBBLEV1;
            }
        });

        format(bubblev2);
        bubblev2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.BUBBLEV2;
            }
        });

        format(bubblev3);
        bubblev3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.BUBBLEV3;
            }
        });

        format(quick);
        quick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.QUICK;
            }
        });

        format(merge);
        merge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                cp.algo = Sorting.MERGE;
            }
        });

        add(output);
        output.add(labelArray);
        buttons.add(start);
        buttons.add(reset);

        gbc.gridy = 1;
        add(buttons, gbc);

        algs.add(bubblev1);
        algs.add(bubblev2);
        algs.add(bubblev3);
        algs.add(quick);
        algs.add(merge);

        gbc.gridy = 2;
        add(algs, gbc);
    }

    private void reset() {
        if (cp.running) {
            System.out.println("RESET");
            try {
                if (cp.timer != null) {
                    cp.timer.stop();
                }
                if (cp.t != null) {
                    cp.t.stop();
                }
                cp.running = false;
                labelArray.setText("-");
                ContentPanel.compare_index = Integer.MAX_VALUE;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void format(JButton button) {
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
    }
}