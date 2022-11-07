package current_version;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MasterPanel extends JPanel {
    
    static JFrame w = new JFrame("Sorting Visualizer Beta v1.8");
    static ContentPanel cp = new ContentPanel(w);
    static IOPanel io = new IOPanel(w,cp);

    static Thread t = new Thread(new RunClock(w,cp,io));

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
        t.start();
        try {
            new MasterPanel();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}