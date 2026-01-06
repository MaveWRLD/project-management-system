package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectRepository {
    private Map<String, Project> projects = new HashMap<>();
    private Map<String, ArrayList<Task>> projectTaskMap = new HashMap<>();

    public ProjectRepository() {
    }

    public ProjectRepository(Map<String, Project> projects, Map<String, ArrayList<Task>> projectTaskMap) {
        this.projects = projects;
        this.projectTaskMap = projectTaskMap;
    }

    public Map<String, Project> getProjects() {
        return projects;
    }

    public Map<String, ArrayList<Task>> getProjectTaskMap() {
        return projectTaskMap;
    }

    public void setProjects(Map<String, Project> projects) {
        this.projects = projects;
    }

    public void setProjectTaskMap(Map<String, ArrayList<Task>> projectTaskMap) {
        this.projectTaskMap = projectTaskMap;
    }
}