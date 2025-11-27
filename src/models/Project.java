package models;

public abstract class Project {

    private String id;
    private String name;
    private String description;
    private int budget;
    private String type;
    private int teamSize;
    

    private static int count = 0;
    private static Project[] allProjects = new Project[5];


    public static Project[] getAllProjects() {
        return allProjects;
    }


    public Project(String id, String name, String type, String description, int budget, int teamSize) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        allProjects[count++] = this;
    }


    public abstract  void getProjectDetails();

    public static void displayProjects(){
        System.out.println("ID  | NAME | BUDGET ");
        for (Project project : allProjects) {
            if (project != null){
                System.out.println(project.id + " | " + project.name + " | " + project.budget);
            }
        }
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
    
    public String getType() {
        return type;
    }
}
