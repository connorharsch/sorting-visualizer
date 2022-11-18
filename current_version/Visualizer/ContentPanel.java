package Visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import SortingAlgorithms.*;

public class ContentPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static int panelHeight;
    private static int panelWidth;
    private static ContentPanel cp;

    private int rectWidth, rectOrigin;
    private Rectangle r;

    public static int delay = 100;

    public static int low;
    public static int high;
    public static int mainIndex;
    public static int compare_index;
    public static int[] array = new int[4];

    public static enum Sorting {
        BUBBLEV1, BUBBLEV2, BUBBLEV3, QUICK, MERGE
    }

    public boolean running;
    public Sorting algo;
    public Timer timer;
    public Thread t;

    ContentPanel(JFrame w) {
        cp = this;
        algo = Sorting.BUBBLEV1;

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.GRAY);
    }

    public void init() {
        low = 0;
        mainIndex = -1;
        compare_index = -1;
        high = array.length - 1;
    }
    
    public void animate() {
        genArray();
        if(algo == Sorting.MERGE){
            new MergeSort(cp);
        }else if (algo == Sorting.QUICK) { // QUICKSORT
            new QuickSort(cp);
        } else {                    // BUBBLESORT
            new BubbleSort(cp);
        }
    }

    private void genArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((Math.random() * (panelHeight * 3 / 4) + panelHeight / 4) - 5);
        }
    }

    public void getPanelSize() {
        r = getBounds();
        if (r.height != panelHeight || r.width != panelWidth) {
            if (t != null) {
                t.stop();
            }
            running = false;
            compare_index = -1;
            int newArraySize = ((r.width / 48)); // divide r.width by any multiple of 8?
            IOPanel.labelArray.setText("-");
            if (newArraySize > 0) {
                array = new int[newArraySize];
            }
            
            repaint();
        }

        panelWidth = r.width;
        panelHeight = r.height;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rectWidth = (panelWidth / array.length) - 1;
        rectOrigin = rectWidth;

        if (running) {
            for (int i = 0; i < array.length; i++) {
                g.setColor(Color.WHITE);
                if (i == compare_index) {
                    g.setColor(Color.RED);
                } else if (i == high || i == low) {
                    g.setColor(Color.BLUE);
                }

                int start = 0;

                if (panelWidth % 2 == 1) {
                    start = (panelWidth - (rectOrigin * array.length)) / 2;
                } else {
                    start = (panelWidth - (rectOrigin * array.length)) / 2 - 1;
                }

                g.drawRect(start + (i * rectOrigin), (panelHeight - (array[i])) - 1, rectWidth, array[i]);

                g.setColor(Color.BLACK);
                if (i == high || i == low) {
                    g.setColor(Color.WHITE);
                } else if (i == mainIndex) {
                    g.setColor(Color.GREEN.darker());
                } else if (i == compare_index) {
                    g.setColor(Color.RED);
                }

                g.fillRect(start + (i * rectOrigin + 1), panelHeight - array[i], rectWidth - 1, array[i]);
            }
            IOPanel.labelArray.setText(genLabelArray());
        }
    }

    public String genLabelArray() {
        String str = "";

        if (array.length == 1) {
            str += array[0];
        } else {
            str += "<html>[";
            for (int i = 0; i < array.length; i++) {
                if (i == low || i == high) {
                    str += "<font color='blue'>";
                    str += array[i];
                    str += "</font>";
                } else if (i == compare_index) {
                    str += "<font color='red'>";
                    str += array[i];
                    str += "</font>";
                } else if (i == mainIndex) {
                    str += "<font color='green'>";
                    str += array[i];
                    str += "</font>";
                } else {
                    str += array[i];
                }
                str = format(str, i);
            }
            str += "</html>";
        }

        return str;
    }

    private String format(String str, int i) {
        if (i == array.length - 1) {
            str += "]";
        } else {
            str += ", ";
        }

        return str;
    }
}