package SortingAlgorithms;

import Visualizer.ContentPanel;
import Visualizer.ContentPanel.Sorting;

public class Threaded implements Runnable{

    static ContentPanel cp;

    protected void sort(int[] arr){}
    protected void sort(int[] arr, int l, int r){}

    public Threaded(ContentPanel c){
        cp = c;
        cp.init();
        cp.t = new Thread(this);
        cp.t.start();
    }

    protected static void sleep() {
        try {
            Thread.sleep(cp.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            if(cp.algo == Sorting.MERGE || cp.algo == Sorting.QUICK){            
                sort(cp.array, 0, cp.array.length-1);
            } else{            
                sort(cp.array);
            }
        } while (!cp.running);
        cp.init();
    }
}