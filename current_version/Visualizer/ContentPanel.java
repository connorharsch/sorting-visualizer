package Visualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import SortingAlgorithms.*;

public class ContentPanel extends JPanel {

    private Rectangle r;
    private int panelHeight, panelWidth;
    private int rectWidth, rectOrigin;

    public int[] array = new int[1];
    public int low, high, mainIndex, compareIndex;

    public Sorting algo;
    public boolean running;
    public Thread t;
    public int delay;

    public static enum Sorting { // SUPPORTED ALGORITHMS
        BUBBLEV1, BUBBLEV2, BUBBLEV3, SELECT, QUICK, MERGE, HEAP
    }

    ContentPanel() {
        setBackground(Color.GRAY);

        algo = Sorting.BUBBLEV1;
        delay = 100;
    }

    public void init() {
        low = 0;
        mainIndex = -1;
        compareIndex = -1;
        high = array.length - 1;
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
                } else if (i == compareIndex) {
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

    private void genArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) ((Math.random() * (panelHeight * 3 / 4) + panelHeight / 4) - 5);
        }
    }

    public void animate() {
        genArray();
        if (algo == Sorting.HEAP) {             // HEAPSORT
            new HeapSort(this);
        } else if (algo == Sorting.MERGE) {     // MERGESORT
            new MergeSort(this);
        } else if (algo == Sorting.QUICK) {     // QUICKSORT
            new QuickSort(this);
        } else if (algo == Sorting.SELECT) {    // SELECTIONSORT
            new SelectionSort(this);
        } else {                                // BUBBLESORT
            new BubbleSort(this);
        }
    }

    public void getPanelSize() {
        r = getBounds();
        if (r.height != panelHeight || r.width != panelWidth) {
            if (t != null) {
                t.stop();
            }
            running = false;
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

        int padding = 5;

        int start = ((panelWidth - (rectOrigin * array.length)) / 2);

        if (running) {
            for (int i = 0; i < array.length; i++) {

                g.setColor(Color.WHITE); // Default Bar Outline Color

                if (i == high || i == low) {
                    g.setColor(Color.BLUE); // High-low Outline Color
                }

                g.drawRect(start + padding / 2 + (i * rectOrigin), (panelHeight - array[i]) - 1, rectWidth - padding, array[i]);

                g.setColor(Color.BLACK); // Default Bar Color

                if (i == high || i == low) {
                    g.setColor(Color.WHITE); // High-low Bar Color
                } else if (i == compareIndex) {
                    g.setColor(Color.RED); // Compare Index Bar Color
                } else if (i == mainIndex) {
                    g.setColor(Color.GREEN.darker()); // Main Index Bar Color
                }

                g.fillRect(start + (padding / 2) + (i * rectOrigin + 1), panelHeight - array[i], rectWidth - (padding + 1), array[i]);
                //g.drawString(""+array[i], start + (padding * 2) + i * rectOrigin, panelHeight);
            }

            IOPanel.labelArray.setText(genLabelArray());
        }
    }
}