package Visualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window{

    private JFrame w = new JFrame("Sorting Visualizer Beta v2.0");
    private ContentPanel cp = new ContentPanel();
    private IOPanel io = new IOPanel(cp);
    private Thread t = new Thread(new RunClock(cp));

    Window() {
        w.add(cp, BorderLayout.CENTER);
        w.add(io, BorderLayout.SOUTH);
        w.pack();

        t.start();
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(400, 400);
        w.setLocationRelativeTo(null);
        w.setMinimumSize(w.getSize());
        w.setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}