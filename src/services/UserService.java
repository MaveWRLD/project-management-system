package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.AdminUser;
import models.RegularUser;
import models.User;
import models.UserRepository;
import utils.exceptions.UserNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User service.
 */
public class UserService {
    private UserRepository repo;

    File file = new File("users.json");

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> loadUsers(){
        if (file.exists() && file.length() > 0)
            try {
                ObjectMapper mapper = new ObjectMapper();
                repo = mapper.readValue(file, UserRepository.class);
                return repo.getUsers();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        return new ArrayList<>();
    }

    public void saveUsers(List<User> users){
        try (FileOutputStream os = new FileOutputStream(file)){
            ObjectMapper mapper = new ObjectMapper();
            repo.setUsers(users);
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repo);
            os.write(jsonStr.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerUser(String userName, String email, String userType) {
        List<User> users = loadUsers();
        if (users.stream().anyMatch(u -> u.getEmail().equals(email)))
            throw new IllegalArgumentException("User already exist");
        if ("ADMIN".equalsIgnoreCase(userType))
            users.add(new AdminUser(userName, email));
        if ("Regular".equalsIgnoreCase(userType))
            users.add(new RegularUser(userName, email));
        saveUsers(users);
    }
    /**
     * Switches the current user to the alternate user type.
     *
     * <p>This method checks if the provided {@link User} instance is an {@link AdminUser}.
     * If so, it returns the {@code regularUser}; otherwise, it returns the {@code adminUser}.
     * This is used to toggle between administrative and regular user roles.</p>
     *
     * @return the alternate {@link User} instance (either {@code regularUser} or {@code adminUser}).
     */
    public User switchUser(String userName) throws UserNotFoundException {
        for (User user : loadUsers()) {
            if (userName.equalsIgnoreCase(user.getName())) {
                return user;
            }
        }
        throw new UserNotFoundException("User does not exist: " + userName);
    }

}
