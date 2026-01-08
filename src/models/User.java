package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import services.TaskService;
import utils.IdGenerator;
import utils.exceptions.TaskNotFoundException;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminUser.class, name = "admin"),
        @JsonSubTypes.Type(value = RegularUser.class, name = "regular")
})
public abstract class User{
    private String id;
    private String name;
    private String email;

    private static int userCounter = 1;

    public User(String name, String email) {
        IdGenerator idGenerator = new IdGenerator();
        this.id = idGenerator.idGenerator('U', userCounter++);
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public void removeTask(String projectID, String taskId, TaskService taskService) throws TaskNotFoundException {
        throw new UnsupportedOperationException("You are not allowed to perform this action");
    }

    public String getName() {
        return name;
    }

    public abstract String getRole();

    public String getEmail() {
        return email;
    }
}
