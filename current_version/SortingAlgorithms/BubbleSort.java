package SortingAlgorithms;

import Visualizer.ContentPanel;
import Visualizer.ContentPanel.Sorting;

public class BubbleSort extends Threaded {

    public BubbleSort(ContentPanel c) {
        super(c);
    }

    private static void swap(int[] arr, int index) {
        int temp = arr[index];
        arr[index] = arr[index + 1];
        arr[index + 1] = temp;
    }

    @Override
    protected void sort(int[] arr) {
        if (cp.algo == Sorting.BUBBLEV1) {
            for (int j = 0; j < arr.length - 1; j++) {
                for (int i = 0; i < arr.length - 1; i++) {
                    cp.compareIndex = i + 1;
                    if (arr[i] > arr[i + 1]) {
                        swap(arr, i);
                    }
                    sleep();
                }
            }
        } else if (cp.algo == Sorting.BUBBLEV2) {
            for (int j = 0; j < arr.length - 1; j++) {
                for (int i = 0; i < (arr.length - 1) - j; i++) {
                    cp.high = (arr.length - 1) - j;
                    cp.compareIndex = i + 1;
                    if (arr[i] > arr[i + 1]) {
                        swap(arr, i);
                    }
                    sleep();
                }
            }
        } else if (cp.algo == Sorting.BUBBLEV3) {
            int j = 0;
            boolean flag = true;
            while (j < arr.length - 1 && (flag)) {
                flag = false;
                for (int i = 0; i < (arr.length - 1) - j; i++) {
                    cp.high = (arr.length - 1) - j;
                    cp.compareIndex = i + 1;
                    if (arr[i] > arr[i + 1]) {
                        swap(arr, i);
                        flag = true;
                    }
                    sleep();
                }
                j++;
            }
        }
    }
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
     *
     * v2 of the bubble sort algorithm
     * parameters: int type array
     * output: int type array
     * 
     * we increment j for each complete pass so we don't re-sort sorted numbers at
     * the end of the array.
     *
     * v1 of the bubble sort algorithm
     * parameters: int type array
     * output: int type array
     * 
     * sort each element regardless if it's already in a sorted position, slow.
     * brute force method of bubble sort, takes the longest, gets the job done.
     */