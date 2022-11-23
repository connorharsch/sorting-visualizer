package SortingAlgorithms;

import Visualizer.ContentPanel;

public class SelectionSort extends Threaded {

    public SelectionSort(ContentPanel c) {
        super(c);
    }

    @Override
    protected void sort(int[] arr) {
        for(int i = 0; i < arr.length-1; i++){
            cp.low = i;
            sleep();
            int min = i;
            for(int j = i+1; j < arr.length; j++){
                cp.compareIndex = j;
                sleep();
                if(arr[j] < arr[min]){
                    min = j;
                }
            }

            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    } 
}