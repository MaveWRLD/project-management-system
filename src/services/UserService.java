package services;

import models.AdminUser;
import models.RegularUser;
import models.User;
import models.UserRepository;

import java.util.List;

/**
 * The type User service.
 */
public class UserService {
    private User adminUser;
    private User regularUser;

    private UserRepository repo = new UserRepository();
    List<User> users = repo.loadUsers();


    public void registerUser(String userName, String email, String userType){
        if (users.stream().map(u -> u ).anyMatch( u -> u.getEmail().equals(email)))
            System.out.println("User already exists");
        if ("ADMIN".equalsIgnoreCase(userType))
            users.add(new AdminUser(userName, email));
        if ("USER".equalsIgnoreCase(userType))
            users.add(new RegularUser(userName, email));
        repo.saveUsers(users);
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
    public User switchUser(User currentUser) {
        if (currentUser instanceof AdminUser)
            return regularUser;
        return adminUser;
    }

    /**
     * Gets admin user.
     *
     * @return the admin user
     */
    public User getAdminUser() {
        return adminUser;
    }
}
