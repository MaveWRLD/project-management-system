package models.services;

import models.AdminUser;
import models.RegularUser;
import models.User;

public class UserService {


    private static User adminUser = new AdminUser("Jacob Quaye", "kofimave@gmail.com" );
    private static User regularUser = new RegularUser("John Doe", "johndoe@gmail.com" );;


   // private static User currentUser = adminUser;

    public User switchUser(User currentUser){
        if (currentUser instanceof AdminUser)
           return regularUser;
        return adminUser;
    }



}
