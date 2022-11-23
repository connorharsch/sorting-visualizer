package SortingAlgorithms;

import Visualizer.ContentPanel;

public class QuickSort extends Threaded{

    public QuickSort(ContentPanel c) {
        super(c);
    }

    /*
     * Desc
     * Selects the largest element as the pivot and puts all the elements greater
     * than it to the right and smaller than it to the left
     * 
     * Params
     * arr : array to be sorted
     * l : lowest index for the algorithm to start
     * r : highest index for the algorithm to stop
     * 
     * Returns
     * returns i which is the index of the pivot in its sorted location
     */

    private static int partition(int arr[], int l, int r) {
        int pivot_index = r;
        int i = l - 1;
        for (int j = l; j < r; j++) {
            cp.compareIndex = j+1;
            sleep();
            if (arr[j] <= arr[pivot_index]) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;  
            } 
            cp.array = arr;
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;
        cp.array = arr;
        return i + 1;
    }

    /*
     * Desc
     * sorts the array recrusively by calling sort on sub-portions on the array
     * 
     * Params
     * arr : array to be sorted
     * l : lowest index for the algorithm to start
     * r : highest index for the algorithm to stop
     * 
     * Returns
     * None
     */
    @Override
    protected void sort(int arr[], int l, int r) {
        if (l < r) {

            int index = partition(arr, l, r);

            cp.mainIndex = index;
            cp.compareIndex = -1;
            cp.high = index - 1;
            cp.low = l;
            sort(arr, l, index - 1);

            cp.mainIndex = index;
            cp.compareIndex = -1;
            cp.low = index + 1;
            cp.high = r;
            sort(arr, index + 1, r);
        }
    }
}