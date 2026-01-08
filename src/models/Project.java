package models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import utils.IdGenerator;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "projectType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HardwareProject.class, name = "HARDWARE"),
        @JsonSubTypes.Type(value = SoftwareProject.class, name = "SOFTWARE")
})
public abstract class Project {

    private String id;
    private String name;
    private String description;
    private int budget;
    private String projectType;
    private int teamSize;

    private ArrayList<Task> tasks;

    private final IdGenerator idGenerator = new IdGenerator();

    private static int projectCounter = 1;

    private int taskCounter = 1;

    public Project(){}

    public Project(String name, String projectType, String description, int budget, int teamSize) {
        this.id = idGenerator.idGenerator('P', projectCounter++);
        this.name = name;
        this.projectType = projectType;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        this.tasks = new ArrayList<>();
    }


    @Override
    public String toString(){
        return getName();
    }

    public abstract String[] getProjectDetails();


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

    public String getProjectType() {
        return projectType;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String generateTaskId() {
        return idGenerator.idGenerator('T', taskCounter++);
    }
}
