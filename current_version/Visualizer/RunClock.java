package Visualizer;

public class RunClock implements Runnable {

    ContentPanel cp;

    final int FPS = 24;

    RunClock(ContentPanel c) {
        cp = c;
    }

    @Override
    public void run() {
        double delta = 0;
        double drawInterval = 1000000000 / FPS;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (this != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                cp.getPanelSize();
                cp.repaint();
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }
}