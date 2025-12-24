package models;

import utils.exceptions.ProjectsNotCreatedException;

import java.util.ArrayList;
import java.util.Collection;

public class ProjectRepository {
    private Collection<Project> projects = new ArrayList<Project>();

    public ProjectRepository() {
    }

    public Collection<Project> getProjects() {
        return projects;
    }

}