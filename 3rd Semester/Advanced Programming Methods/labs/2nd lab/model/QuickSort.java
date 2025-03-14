package sem1_2.model;

public class QuickSort extends AbstractSorter {

    @Override
    public void sort(int[] vector) {
        qs(vector, 0, vector.length - 1);
    }

    private void qs(int[] vector, int left, int right) {
        if (left < right) {
            int pivot = partition(vector, left, right);
            qs(vector, left, pivot - 1);
            qs(vector, pivot + 1, right);
        }
    }

    private int partition(int[] vector, int left, int right) {
        int pivot = vector[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (vector[j] < pivot) {
                i++;
                int temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;
            }
        }
        int temp = vector[i];
        vector[i+1] = vector[right];
        vector[right] = temp;
        return i+1;
    }
}
