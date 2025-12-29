package models;

import java.io.*;

import java.util.List;

public class UserRepository  {
    public List<User> loadUsers(){
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("users.ser"));
            return (List<User>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUsers(List<User> users){
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("users.ser"));
            os.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
