package peaksoft;

import peaksoft.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Chak", "Norris", (byte) 78);
        userService.saveUser("Silwester", "Stallone", (byte) 64);
        userService.saveUser("Salvador", "Dali", (byte) 101);
        userService.saveUser("Mona", "Lisa", (byte) 23);

//        System.out.println(userService.getAllUsers());
//
//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();


        System.out.println(userService.existsByFirstName("Ch"));
    }
}
