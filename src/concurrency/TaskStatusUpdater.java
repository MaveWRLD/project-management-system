package concurrency;

import models.Task;
import utils.Status;

public class TaskStatusUpdater implements Runnable {

    private final Task task;
    private final Status newStatus;

    public TaskStatusUpdater(Task task, Status newStatus) {
        this.task = task;
        this.newStatus = newStatus;
    }

    @Override
    public void run() {
        updateTaskStatus();
    }

    private void updateTaskStatus() {
        synchronized (task) {
            System.out.println(
                    Thread.currentThread().getName() +
                            " updating " + task.getTaskID() +
                            " -> " + newStatus
            );

            task.setStatus(newStatus);

            try {
                Thread.sleep(5000); // simulate work
            } catch (InterruptedException ignored) {}

            System.out.println(
                    Thread.currentThread().getName() +
                            " finished Task " + task.getTaskID()
            );
        }
    }
}
