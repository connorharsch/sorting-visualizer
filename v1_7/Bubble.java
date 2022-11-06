package visualizer.v1_7;

public class Bubble {

    public static void sort(int[] array, int compare_index){
        if(array[compare_index] > array[compare_index+1]) {
            int temp = array[compare_index];
            array[compare_index] = array[compare_index+1];
            array[compare_index+1] = temp;
        }
    }
}
