package org.example.service;


import org.example.dao.UserDAO;
import org.example.entity.User1;

import java.util.ArrayList;
import java.util.List;

import static org.example.dao.UserDAO.export;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public void addUser(User1 user) {
        userDAO.insert(user);
    }

    public int deleteUser(String id) {
        return userDAO.delete(id);
    }

    public int deleteUsers(List<String> ids) {
        return userDAO.deleteBatch(ids);
    }

    public User1 getUserById(String id) {
        return userDAO.findById(id);
    }

    public List<User1> getAllUsers() {
        return userDAO.findAll();
    }

    public List<User1> getUsersByPage(int page, int size) {
        int skip = (page - 1) * size;
        return userDAO.findPage(skip, size);
    }

    public long getUserCount() {
        return userDAO.getCount();
    }

    public int updateUser(User1 user) {
        return userDAO.update(user);
    }

    public int changeUserStatus(String id, int status) {
        return userDAO.updateStatus(id, status);
    }

    private String exportPath(String filename) {
        String directory = System.getenv("EXPORT_DIR");
        java.io.File folder = new java.io.File(directory == null ? "exports" : directory);
        if (!folder.isDirectory() && !folder.mkdirs()) {
            throw new IllegalStateException("Cannot create export directory");
        }
        return new java.io.File(folder, filename).getPath();
    }

    public void exportUsersToExcel() {
        List<User1> users = userDAO.findAll();
        // 这里实现导出到Excel的逻辑，可以使用Apache POI库
        // 导出到 Excel 文件
        export(users, exportPath("users.xlsx"));
    }

    public void exportCheckedUsersToExcel(List<String> ids) {
        List<User1> users = new ArrayList<>();
        for (String id : ids) {
            User1 user = userDAO.findById(id);
            if (user != null) {
                users.add(user);
            }
        }
        // 导出到 Excel 文件的逻辑
        export(users, exportPath("checked_users.xlsx"));
    }
}
