package SortingAlgorithms;

import Visualizer.ContentPanel;

public class MergeSort implements Runnable {

    private static ContentPanel cp;

    private static void sleep() {
        try {
            Thread.sleep(ContentPanel.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public MergeSort(ContentPanel c) {
        cp = c;
        cp.init();
        cp.t = new Thread(this);
        cp.t.start();
    }

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    private static void merge(int arr[], int l, int m, int r) {
        ContentPanel.low = l;
        ContentPanel.high = r;
        ContentPanel.mainIndex = m;
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /* Copy data to temp arrays */
        for (int i = 0; i < n1; ++i){
            L[i] = arr[l + i];
        }

        for (int j = 0; j < n2; ++j){
            R[j] = arr[m + 1 + j];
        }

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                sleep();
                arr[k] = L[i];
                ContentPanel.compare_index = k;
                i++;
            } else {
                sleep();
                arr[k] = R[j];
                ContentPanel.compare_index = k;
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            sleep();
            arr[k] = L[i];
            ContentPanel.compare_index = k;
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            sleep();
            arr[k] = R[j];
            ContentPanel.compare_index = k;
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    private static void sort(int arr[], int l, int r) {
        if (l < r) {
            sleep();
            // Find the middle point
            int m = (l + r) / 2;

            sleep();
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            sleep();
            // Merge the sorted halves
            merge(arr, l, m, r);
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