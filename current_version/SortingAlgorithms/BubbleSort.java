package SortingAlgorithms;

import Visualizer.ContentPanel;
import Visualizer.ContentPanel.Sorting;

public class BubbleSort implements Runnable {

    private static ContentPanel cp;

    private static void sleep() {
        try {
            Thread.sleep(ContentPanel.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public BubbleSort(ContentPanel c) {
        cp = c;
        cp.init();
        cp.t = new Thread(this);
        cp.t.start();
    }
    
    private static void swap(int[] arr, int index) {
        int temp = arr[index];
        arr[index] = arr[index + 1];
        arr[index + 1] = temp;
    }

    /*
     * v3 of the bubble sort algorithm
     * parameters: int type array
     * output: int type array
     * 
     * utilizes a boolean "flag", flag is initialized as true, set false in each
     * pass and made true if a swap occurs during iteration.
     * if a pass goes through a complete iteration and the flag remains false,
     * one of the "j" while conditions will be false,
     * making the entire condition false, exiting from the while loop.
     */
    private static void v3(int[] arr) {

        int j = 0;
        boolean flag = true;
        while (j < arr.length - 1 && (flag)) {
            flag = false;
            for (int i = 0; i < (arr.length - 1) - j; i++) {
                ContentPanel.high = (arr.length - 1) - j;
                ContentPanel.compare_index = i;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i);
                    flag = true;
                }
                sleep();
            }
            j++;
        }
    }

    /*
     * v2 of the bubble sort algorithm
     * parameters: int type array
     * output: int type array
     * 
     * we increment j for each complete pass so we don't re-sort sorted numbers at
     * the end of the array.
     */
    private static void v2(int[] arr) {

        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < (arr.length - 1) - j; i++) {
                ContentPanel.high = (arr.length - 1) - j;
                ContentPanel.compare_index = i;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i);
                }
                sleep();
            }
        }
    }

    /*
     * v1 of the bubble sort algorithm
     * parameters: int type array
     * output: int type array
     * 
     * sort each element regardless if it's already in a sorted position, slow.
     * brute force method of bubble sort, takes the longest, gets the job done.
     */
    private static void v1(int[] arr) {

        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1; i++) {
                ContentPanel.compare_index = i;
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i);
                }
                sleep();
            }
        }
    }

    @Override
    public void run() {
        do {
            if (cp.algo == Sorting.BUBBLEV1) {
                v1(ContentPanel.array);
            } else if (cp.algo == Sorting.BUBBLEV2) {
                v2(ContentPanel.array);
            } else {
                v3(ContentPanel.array);
            }
        } while (!cp.running);
        cp.init();
    }
}