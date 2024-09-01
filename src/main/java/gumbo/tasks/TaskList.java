package gumbo.tasks;

import java.util.ArrayList;

public class TaskList {

    private static ArrayList<Task> taskArr = new ArrayList<>();

    public TaskList(ArrayList<Task> taskArr) {
        TaskList.taskArr = taskArr;
    }

    public Task get(int taskNumToGet) {
        return taskArr.get(taskNumToGet);
    }

    public ArrayList<Task> getAll() {
        return taskArr;
    }

    public void add(Task task) {
        taskArr.add(task);
    }

    public void remove(int taskNumToDelete) {
        taskArr.remove(taskNumToDelete);
    }

    public int size() {
        return taskArr.size();
    }
}
