package models;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProjectRepository {
    private Map<String, Project> projects = new LinkedHashMap<>();

    public ProjectRepository() {
    }

    public ProjectRepository(Map<String, Project> projects) {
        this.projects = projects;
    }

    public Map<String, Project> getProjects() {
        return projects;
    }

    public void setProjects(Map<String, Project> projects) {
        this.projects = projects;
    }
}