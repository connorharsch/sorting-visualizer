package SortingAlgorithms;

import Visualizer.ContentPanel;

public class QuickSort implements Runnable {

    private static ContentPanel cp;

    private static void sleep() {
        try {
            Thread.sleep(ContentPanel.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public QuickSort(ContentPanel c) {
        cp = c;
        cp.init();
        cp.t = new Thread(this);
        cp.t.start();
    }

    /*
     * Desc
     * Selects the largest element as the pivot and puts all the elements greater
     * than it to the right and smaller than it to the left
     * 
     * Params
     * arr : array to be sorted
     * low : lowest index for the algorithm to start
     * high : highest index for the algorithm to stop
     * 
     * Returns
     * returns i which is the index of the pivot in its sorted location
     */

    private static int partition(int arr[], int low, int high) {
        int pivot_index = high;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            ContentPanel.compare_index = j;
            if (arr[j] <= arr[pivot_index]) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                sleep();
                ContentPanel.array = arr;
            } else {

                sleep();
                ContentPanel.array = arr;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        ContentPanel.array = arr;
        return i + 1;
    }

    /*
     * Desc
     * sorts the array recrusively by calling sort on sub-portions on the array
     * 
     * Params
     * arr : array to be sorted
     * low : lowest index for the algorithm to start
     * high : highest index for the algorithm to stop
     * 
     * Returns
     * None
     */
    private static void sort(int arr[], int low, int high) {
        if (low < high) {
            sleep();
            int index = partition(arr, low, high);
            ContentPanel.mainIndex = index;

            sleep();
            ContentPanel.high = index - 1;
            ContentPanel.low = low;
            sort(arr, low, index - 1);

            sleep();
            ContentPanel.high = high;
            ContentPanel.low = index + 1;
            sort(arr, index + 1, high);
        }
    }

    @Override
    public void run() {
        do {
            sort(ContentPanel.array, 0, ContentPanel.array.length - 1);
        } while (!cp.running);
        cp.init();
    }
}