package visualizer.v1_7;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MasterPanel extends JPanel {
    static JFrame w = new JFrame("Sorting Visualizer Beta v1.7");
    static ContentPanel cp = new ContentPanel(w);
    static IOPanel io = new IOPanel(w,cp);

    MasterPanel(){
        w.add(cp, BorderLayout.CENTER);
        w.add(io, BorderLayout.SOUTH);
        w.pack();

        w.setSize(325,325);
        w.setMinimumSize(w.getSize());
        w.setLocationRelativeTo(null);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setVisible(true);
    }

    public static void main(String[] args){
        Thread t = new Thread(new RunClock(w,cp,io));
        new MasterPanel();
        
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}