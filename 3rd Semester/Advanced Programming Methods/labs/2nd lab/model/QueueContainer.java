package sem1_2.model;

import java.util.ArrayList;
import java.util.List;

public class QueueContainer extends AbstractContainer {
    List<Task> tasks = new ArrayList<>();

    @Override
    public Task remove() {
        if(tasks.isEmpty()){
            return tasks.remove(0);
        }
        else
            return null;
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }
}
