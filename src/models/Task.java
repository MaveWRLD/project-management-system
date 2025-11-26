package models;

public class Task {
    private String id;
    private String name;
    private String status;

    private static Task[] allTask = new Task[25];
    private static int count = 0;

    public Task(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
        allTask[count++] = this;
    }

    public static Task[] getAllTask() {
        return allTask;
    }

    public static void setAllTask(Task[] allTask) {
        Task.allTask = allTask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Task.count = count;
    }


}
