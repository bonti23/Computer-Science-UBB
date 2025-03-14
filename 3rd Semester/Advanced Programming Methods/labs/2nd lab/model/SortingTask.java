package sem1_2.model;

import java.util.Arrays;

//derivare => extends clasa ...
public class SortingTask extends Task{
    private int[] vector;
    private AbstractSorter sorter;

    public SortingTask(String id, String description, int[] vector, AbstractSorter sorter) {
        super(id, description);
        this.vector = vector;
        this.sorter = sorter;
    }

    public AbstractSorter getSorter() {
        return sorter;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }

    public void setSorter(AbstractSorter sorter) {
        this.sorter = sorter;
    }

    @Override
    public void execute() {
        sorter.sort(vector);
        System.out.println(Arrays.toString(vector)+"\n");
    };


}
