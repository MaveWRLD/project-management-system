package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectRepository {
    private Collection<Project> projects = new ArrayList<>();
    private Map<Project, Collection<Task>> projectTaskMap = new HashMap<>();

    public ProjectRepository() {
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public Map<Project, Collection<Task>> getProjectTaskMap() {
        return projectTaskMap;
    }
}