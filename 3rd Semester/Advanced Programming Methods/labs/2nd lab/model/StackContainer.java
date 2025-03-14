package sem1_2.model;

import java.util.ArrayList;
import java.util.List;

public class StackContainer extends AbstractContainer {
    List<Task> tasks = new ArrayList<>();

    @Override
    public Task remove() {
        if (!tasks.isEmpty()) {
            return tasks.remove(tasks.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }
}
