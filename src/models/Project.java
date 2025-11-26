package models;

public abstract class Project {

    private String id;
    private String name;

    public void setType(String type) {

        this.type = type;
    }

    private String type;
    private String description;
    private int budget;
    private int teamSize;

    public static Project[] getAllProjects() {
        return allProjects;
    }

    private static Project[] allProjects = new Project[5];
    private static int count = 0;

    public Project(String id, String name, String description, int budget, int teamSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
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
}
