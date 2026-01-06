package services;

import models.User;
import utils.ValidationUtils;
import utils.exceptions.UserNotFoundException;

public class LoginPageService {
    UserService userService;
    ValidationUtils validationUtils = new ValidationUtils();

    public LoginPageService(UserService userService) {
        this.userService = userService;
    }

    public User login() throws UserNotFoundException {
        String username = validationUtils.getValidString("Enter User Name: ");
        return userService.switchUser(username);
    }
}
