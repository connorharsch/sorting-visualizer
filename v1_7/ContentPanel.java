package visualizer.v1_7;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ContentPanel extends JPanel{ 
    
    static enum Sorting{
        BUBBLE,
        QUICK
    }

    private static final long serialVersionUID = 1L;
    static int panelWidth = 800;
    static int panelHeight = 640;
    static int[] array = new int[1];

    //BUBBLESORT
    static int array_index, compare_index;


    //QUICKSORT
    static int low, high, pivot, i, j, mainIndex, tempIndex, count = -1;

    private int rectWidth = 0;
    private int rectOrigin = 0;
    private boolean running;
    private boolean firstsort, secondsort = false;

    private Rectangle r;
    Sorting algo;
    Timer timer;
    
    JFrame window;
    Thread thread;
    int FPS = 24;

    ContentPanel(JFrame w){
        window = w;
        algo = Sorting.BUBBLE;
        array_index = 0;
        compare_index = Integer.MAX_VALUE;
        
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.GRAY);
        this.setLayout(null);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rectWidth = (panelWidth/array.length) -1;
        rectOrigin = rectWidth;

        if(running){
            for(int i = 0; i < array.length; i++){
                g.setColor(Color.WHITE);
    
                if(i == compare_index){
                    g.setColor(Color.RED);
                }

                if(i == high || i == low){
                    g.setColor(Color.BLUE);
                }

                int start = 0;
                if(panelWidth % 2 == 1){
                    start = (panelWidth - (rectOrigin * array.length))/2;
                }else{
                    start = (panelWidth - (rectOrigin * array.length))/2 - 1;
                }
                g.drawRect(start + (i*rectOrigin), (panelHeight - (array[i])) - 1, rectWidth, array[i]);
                
                if(i == high || i == low){
                    g.setColor(Color.WHITE);
                }else{
                    g.setColor(Color.BLACK);
                }

                g.fillRect(start + (i*rectOrigin+1), panelHeight - array[i], rectWidth-1, array[i]);
            }

            if(isSorted()){
                IOPanel.labelArray.setText(genLabelArray());
            }
        }
    }
    
    public void getPanelSize() {
        r = getBounds();
        if(r.height != panelHeight || r.width != panelWidth){
            running = false;
            array_index = 0;
            compare_index = Integer.MAX_VALUE;
            int newArraySize = ((r.width/64)); //divide r.width by any mult
            if(newArraySize > 0){
                array = new int[newArraySize];
            }
            IOPanel.labelArray.setText("-");
            genArray();
            repaint();
        }
        
        panelWidth = r.width;
        panelHeight = r.height;
    }

    private String genLabelArray(){
        String str = "";
        
        if(array.length == 1){
            str += array[0];
        }else{
            str += "<html>";
            for(int i = 0; i < array.length; i++){
                if(i == 0){
                    str += "[" + array[i] + ", ";
                }else if(i == compare_index){
                    str += "<font color='red'>";
                    str += array[compare_index];
                    str += "</font>";
                    str += ", ";
                }else if(i == array.length-1){
                    str += array[i] + "]";
                }else{
                    str += array[i] + ", ";
                }
            } 
            str += "</html>";
        }

        return str;
    }

    private static boolean isSorted() {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    } 

    private boolean isSorted(int low, int pivot) {
        for (int i = low; i < pivot; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void animate(){
        compare_index = 0;

        if(algo == Sorting.BUBBLE){     //BUBBLESORT
            timer = new Timer(100, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(isSorted()){
                        compare_index = Integer.MAX_VALUE;
                        ((Timer)e.getSource()).stop();
                    } else{
                        if(running){
                            Bubble.sort(array, compare_index);
                            if((compare_index+1) >= (array.length - array_index - 1)){
                                array_index++;
                                compare_index = 0;
                            }
                    
                            else compare_index++;

                            IOPanel.labelArray.setText(genLabelArray());
                        }
                    }
                }
            });
            timer.start();
        }
        
        if(algo == Sorting.QUICK){      //QUICKSORT
            low = 0;
            i = low-1;
            j = low;
            compare_index = j;
            high = array.length-1;
            mainIndex = -1;
            tempIndex = -1;
            pivot = array[high];
            secondsort = false;
            count = -1;
            timer = new Timer(100, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(isSorted()){
                        high = Integer.MAX_VALUE;
                        low = Integer.MAX_VALUE;
                        compare_index = Integer.MAX_VALUE;
                        ((Timer)e.getSource()).stop();
                    } else{
                        if(running){
                            IOPanel.labelArray.setText(genLabelArray());
                            if(compare_index < high){
                                if(compare_index == low){
                                    pivot = array[high];
                                    i = low - 1;
                                }
                                //System.out.println(Arrays.toString(array));
                                if (array[compare_index] <= pivot) {
                                    i++;
                                    swap(i, compare_index);
                                }

                                if(compare_index == high-1){
                                    i += 1;
                                    //System.out.println("SWAP: " + (i) + " high: " + high);
                                    if(array[i] > array[high]){                                    
                                        swap(i, high);
                                    }
                                    tempIndex = i; // return i+1 from partition
                                    IOPanel.labelArray.setText(genLabelArray());

                                    if(i == 0){
                                        count++;
                                    }
                                    //System.out.println(Arrays.toString(array) + "\n");
                                    //System.out.println("tempIndex = " + tempIndex + " secondsort: " + secondsort + " mainIndex: " + mainIndex);
                                }

                                compare_index++; // replaces j, which is set equals low at the start of for-loop

                                IOPanel.labelArray.setText(genLabelArray());
                            }else{
                                if(mainIndex == -1){
                                    System.out.println("first");
                                    mainIndex = tempIndex;
                                    System.out.println("mainIndex = " + mainIndex);
                                    firstsort = true;
                                }

                                if(!isSorted(0, mainIndex)){
                                    if(tempIndex <= 1){
                                        System.out.println("first " + tempIndex + " " + count);
                                        System.out.println(Arrays.toString(array) + "\n");
                                        tempIndex = mainIndex - tempIndex + 1;
                                    }
                                    low = 0;
                                    high = tempIndex-1;
                                    compare_index = 0;
                                    return;
                                }
                                
                                if(!secondsort){
                                    System.out.println("second");
                                    tempIndex = mainIndex;
                                    firstsort = false;
                                    secondsort = true;
                                    high = array.length-1;
                                    count = 0;
                                }

                                if(!isSorted(mainIndex, array.length-1)){
                                    if(tempIndex >= high-1){
                                        System.out.println("second " + tempIndex);
                                        System.out.println(Arrays.toString(array) + "\n");
                                        high = (tempIndex - 1);
                                        tempIndex = mainIndex+count;
                                    }
                                    low = tempIndex + 1;
                                    compare_index = low;
                                    return;
                                }
                            }
                        }
                    }
                }
            });
            timer.start();
        }
    }

    public static void swap(int i, int j){
        // swap array[i] and array[j]
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void genArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((Math.random() * (panelHeight * 3/4) + panelHeight/4) - 5);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean b) {
        running = b;
    }
}