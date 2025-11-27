package models;

public abstract class Project {

    private String id;
    private String name;
    private String type;
    private String description;
    private int budget;
    private int teamSize;
    private Task[] tasks;
    private static int count = 0;
    private static Project[] allProjects = new Project[5];

//    private int numberOfTask = Task.getAllTask().length;

    public static Project[] getAllProjects() {
        return allProjects;
    }


    public Project(String id, String name, String description, int budget, int teamSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        // this.tasks = new Task[numberOfTask];
        allProjects[count++] = this;
    }


    public abstract  void getProjectDetails();

    public static void displayProjects(){
        System.out.println("ID  | NAME | Type | BUDGET ");
        for (Project project : allProjects) {
            if (project != null){
                System.out.println(project.id + " | " + project.name + " | " + project.type + " | " + project.budget);
            }
        }
    }

    public void addTask(Task task){
        tasks[numberOfTask] = task;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setType(String type) {
        this.type = type;
    }
}
