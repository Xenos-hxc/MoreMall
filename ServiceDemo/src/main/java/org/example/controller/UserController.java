package org.example.controller;

import org.example.entity.User1;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService = new UserService();

    @PostMapping("/add")
    public void addUser(@RequestBody User1 user) {
        userService.addUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public int deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/deleteBatch")
    public int deleteUsers(@RequestBody List<String> ids) {
        System.out.println(ids);
        return userService.deleteUsers(ids);
    }

    @GetMapping("/{id}")
    public User1 getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<User1> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/page")
    public List<User1> getUsersByPage(@RequestParam int page, @RequestParam int size) {
        return userService.getUsersByPage(page, size);
    }

    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }

    @PutMapping("/update")
    public int updateUser(@RequestBody User1 user) {
        return userService.updateUser(user);
    }

    @PutMapping("/changeStatus")
    public int changeUserStatus(@RequestParam String id, @RequestParam int status) {
        return userService.changeUserStatus(id, status);
    }

    @GetMapping("/export")
    public void exportUsersToExcel() {
        userService.exportUsersToExcel();
    }

    @PostMapping("/exportChecked")
    public void exportCheckedUsersToExcel(@RequestBody List<String> ids) {
        userService.exportCheckedUsersToExcel(ids);
    }
}
