package concurrency;

import models.Task;
import utils.Status;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulateTaskStatusUpdate {

    static void main() {
        simulate();
    }

    public static void simulate(){
        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            var task1 = new Task("T001", "Concurrency", Status.PENDING);
            var task2 = new Task("T002", "Synchronization", Status.PENDING);
            var task3 = new Task("T003", "Thread", Status.PENDING);

            executor.submit(new TaskStatusUpdater(task1, Status.IN_PROGRESS));
            executor.submit(new TaskStatusUpdater(task2, Status.COMPLETED));
            executor.submit(new TaskStatusUpdater(task3, Status.COMPLETED));
            executor.submit(new TaskStatusUpdater(task1, Status.COMPLETED));

            executor.shutdown();
        }
    }

}
