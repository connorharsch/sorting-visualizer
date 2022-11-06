package visualizer.v1_7;

import javax.swing.JFrame;

public class RunClock implements Runnable{

    JFrame window;
    ContentPanel cp;
    IOPanel io;

    int FPS = 30;
    
    RunClock(JFrame w, ContentPanel c, IOPanel o){
        window = w;
        cp = c;
        io = o;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        //int drawCount = 0;

        while (this != null) {
    
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                cp.getPanelSize();
                cp.repaint();
                delta--;
                //drawCount++;
            }

            if(timer >= 1000000000){
                //System.out.println("FPS: " + drawCount);
                //drawCount = 0;
                timer = 0;
            }
        }
    }   
}