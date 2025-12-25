package services;

import models.AdminUser;
import models.RegularUser;
import models.User;

/**
 * The type User service.
 */
public class UserService {
    private final User adminUser;
    private final User regularUser;

    /**
     * Instantiates a new User service.
     */
    public UserService() {

        this.adminUser = new AdminUser("Jacob Quaye", "kofimave@gmail.com");
        this.regularUser = new RegularUser("John Doe", "johndoe@gmail.com");
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
