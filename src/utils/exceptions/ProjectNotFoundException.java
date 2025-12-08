package utils.exceptions;

public class ProjectNotFoundException extends Exception{

    public ProjectNotFoundException(String projectID) {
        super("Project with ID " + projectID + " was not found");
    }
}
