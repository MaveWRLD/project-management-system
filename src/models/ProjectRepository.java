package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectRepository {
    private final Map<String, Project> projects = new HashMap<>();
    private final Map<Project, ArrayList<Task>> projectTaskMap = new HashMap<>();

    public ProjectRepository() {
    }

    public Map<String, Project> getProjects() {
        return projects;
    }

    public Map<Project, ArrayList<Task>> getProjectTaskMap() {
        return projectTaskMap;
    }
}