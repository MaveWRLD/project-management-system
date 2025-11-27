package models;

public class Task {
    private String id;
    private String name;
    private String status;
    // private Project project;



    // private Task[] allTask = new Task[25];
    // private int count = 0;

    public Task(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
       //  this.project = project;
        // allTask[count++] = this;
    }

//    public static Task[] getProjectTasks(Project project) {
//        Task[] projectTasks = new Task[25];
//        for (int i = 0; i < allTask.length; i++){
//            if (allTask[i] == null){
//                continue;
//            }
//            if (allTask[i].getProject() == null){
//                continue;
//            }
//            if (allTask[i].getProject().equals(project)){
//                projectTasks[i] = allTask[i];
//            }
//        }
//        return projectTasks;
//    }

//    public Task[] getAllTask() {
//        return allTask;
//    }

//    public void setAllTask(Task[] allTask) {
//        allTask = allTask;
//    }

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

//    public static int getCount() {
//        return count;
//    }
//
//    public static void setCount(int count) {
//        Task.count = count;
//    }

//    public Project getProject() {
//        return project;
//    }


}
