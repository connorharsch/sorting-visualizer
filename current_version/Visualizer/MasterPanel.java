package Visualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MasterPanel extends JPanel {

    static JFrame w = new JFrame("Sorting Visualizer Beta v2.0");
    static ContentPanel cp = new ContentPanel(w);
    static IOPanel io = new IOPanel(cp);
    static Thread t = new Thread(new RunClock(cp));

    MasterPanel() {
        w.add(cp, BorderLayout.CENTER);
        w.add(io, BorderLayout.SOUTH);
        w.pack();

        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(800, 640);
        w.setLocationRelativeTo(null);
        w.setMinimumSize(w.getSize());
        w.setVisible(true);
    }

    public static void main(String[] args) {
        t.start();
        try{
            new MasterPanel();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}