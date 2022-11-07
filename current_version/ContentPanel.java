package current_version;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ContentPanel extends JPanel{ 
    
    private static final long serialVersionUID = 1L;
    private static int panelWidth = 800;
    private static int panelHeight = 640;
    private static int[] array = new int[1];

    private Rectangle r;
    private int rectWidth, rectOrigin;

    static int low, high, compare_index;
    
    //BUBBLESORT
    static int array_index;

    //QUICKSORT
    static int pivot, i, j, mainIndex, tempIndex, count;
    private boolean pastMainIndex;
    
    //SORTINGCLOCK
    private boolean running;
    private int delay = 100;
    public static enum Sorting{
        BUBBLE,
        QUICK
    }
    public Sorting algo;
    public Timer timer;

    ContentPanel(JFrame w){
        algo = Sorting.BUBBLE;
        array_index = 0;
        compare_index = -1;
        
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

                if(i == mainIndex){
                    g.setColor(Color.GREEN.darker());
                }

                g.fillRect(start + (i*rectOrigin+1), panelHeight - array[i], rectWidth-1, array[i]);
            }

            if(isSorted()){
                IOPanel.labelArray.setText(genLabelArray());
            }
        }
    }

    private String genLabelArray(){
        String str = "";
        
        if(array.length == 1){
            str += array[0];
        }else{
            str += "<html>";
            for(int i = 0; i < array.length; i++){
                if(i == 0){
                    str += "[";
                }
                
                if(i == low){
                    str += "<font color='blue'>";
                    str += array[i];
                    str += "</font>";
                    if(i == array.length-1){
                        str += "]";
                    }else{
                        str += ", ";
                    }
                }else if(i == compare_index){
                    str += "<font color='red'>";
                    str += array[i];
                    str += "</font>";
                    if(i == array.length-1){
                        str += "]";
                    }else{
                        str += ", ";
                    }
                }else if(i == mainIndex){
                    str += "<font color='green'>";
                    str += array[i];
                    str += "</font>";
                    if(i == array.length-1){
                        str += "]";
                    }else{
                        str += ", ";
                    }
                }else if(i == high){
                    str += "<font color='blue'>";
                    str += array[i];
                    str += "</font>";
                    if(i == array.length-1){
                        str += "]";
                    }else{
                        str += ", ";
                    }
                }else{
                    str += array[i];
                    if(i == array.length-1){
                        str += "]";
                    }else{
                        str += ", ";
                    }
                }
            } 
            str += "</html>";
        }

        return str;
    }

    private void init() {
        if(!isSorted()){
            mainIndex = -1;
            tempIndex = -1;
            compare_index = 0;
            low = compare_index;
            high = array.length-1;
        }else{
            compare_index = -1;
            mainIndex = -1;
            tempIndex = -1;
            high = -1;
            low = -1;
        }
    }

    private void quicksteps() {
        timer = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted()){
                    init();
                    ((Timer)e.getSource()).stop();
                } else{
                    if(running){ // partition(arr, low, high)
                        IOPanel.labelArray.setText(genLabelArray());
                        if(compare_index < high){
                            if(compare_index == low){
                                pivot = array[high];
                                i = low - 1;
                            }
                            if (array[compare_index] <= pivot) {
                                i++;
                                swap(i, compare_index);
                            }
                            if(compare_index == high-1){
                                i += 1;
                                if(array[i] > array[high]){                                    
                                    swap(i, high);
                                }
                                tempIndex = i; // return i+1 from partition
                                IOPanel.labelArray.setText(genLabelArray());

                                if(i == 0){
                                    count++;
                                }
                            }
                            compare_index++; // replaces j, which is set equals low at the start of for-loop
                            IOPanel.labelArray.setText(genLabelArray());
                        }else{  // sort(arr, low, high)
                            if(mainIndex == -1){ //if the first int index hasn't been set
                                mainIndex = tempIndex;
                            }

                            if(!isSorted(0, mainIndex)){ //if the first partition isn't sorted
                                if(tempIndex < 1){
                                    tempIndex = mainIndex-count+1;
                                }else if(tempIndex == 1){
                                    tempIndex = mainIndex-count;
                                }
                                high = tempIndex-1;
                                compare_index = 0;
                                return;
                            }
                            
                            if(!pastMainIndex){ //prepares conditions for second partition
                                high = array.length-1;
                                tempIndex = mainIndex;
                                pastMainIndex = true;
                                count = 0;
                            }

                            if(!isSorted(mainIndex, array.length-1)){ //if the second partition isn't sorted
                                if(tempIndex >= high-1){
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

    private void bubblesteps() {
        timer = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSorted()){
                    init();
                    ((Timer)e.getSource()).stop();
                } else{
                    if(running){
                        if(array[compare_index] > array[compare_index+1]) {   
                            swap(compare_index + 1, compare_index);
                        }
                        if((compare_index+1) >= (array.length - array_index - 1)){
                            high = array.length - array_index - 1;
                            array_index++;
                            compare_index = 0;
                        }else{
                            compare_index++;
                        }
                        IOPanel.labelArray.setText(genLabelArray());
                    }
                }
            }
        });
        timer.start();
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

    public void getPanelSize() {
        r = getBounds();
        if(r.height != panelHeight || r.width != panelWidth){
            running = false;
            array_index = 0;
            compare_index = -1;
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

    public void animate(){
        init();

        if(algo == Sorting.BUBBLE){     //BUBBLESORT
            bubblesteps();
        }
        
        if(algo == Sorting.QUICK){      //QUICKSORT
            i = low-1;
            pivot = array[high];
            pastMainIndex = false;
            count = -1;
            quicksteps();
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