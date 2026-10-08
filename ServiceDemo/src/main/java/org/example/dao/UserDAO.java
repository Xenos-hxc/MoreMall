package org.example.dao;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.User1;
import org.example.util.MorphiaPooUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.FindOptions;
import com.mongodb.WriteResult;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;


import java.io.FileOutputStream;
import java.io.IOException;
public class UserDAO {

    // 添加
    public void insert(User1 user) {
        Datastore db = MorphiaPooUtil.getDb();
        db.save(user);
    }

    // 删除
    public int delete(String id) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        Query<User1> id1 = query.filter("_id", new ObjectId(id));
        WriteResult delete = db.delete(id1);
        return delete.getN();
    }

    public int deleteBatch(List<String> ids) {

        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class)
                .filter("_id in", ids.stream()
                        .map(ObjectId::new)
                        .collect(Collectors.toList()));
        WriteResult delete = db.delete(query);
        return delete.getN();
    }

    // 根据id查询
    public User1 findById(String id) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        Query<User1> id1 = query.filter("_id", new ObjectId(id));
        User1 user = query.get();
        return user;
    }

    // 查询所有
    public List<User1> findAll() {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        List<User1> users = query.asList();
        return users;
    }

    // 分页查询
    public List<User1> findPage(int skip, int limit) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        FindOptions findOptions = new FindOptions();
        findOptions.skip(skip).limit(limit);
        List<User1> users = query.asList(findOptions);
        return users;
    }

    // 统计总数
    public long getCount() {
        return MorphiaPooUtil.getDb().getCount(User1.class);
    }

    // 修改
    public int update(User1 user) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        query.filter("_id", new ObjectId(String.valueOf(user.getId())));
        UpdateOperations<User1> update = db.createUpdateOperations(User1.class);
        if (user.getUsername() != null) {
            update.set("username", user.getUsername());
        }
        if (user.getPassword() != null) {
            update.set("password", user.getPassword());
        }
        if (user.getAge() != null) {
            update.set("age", user.getAge());
        }
        if (user.getMobile() != null) {
            update.set("mobile", user.getMobile());
        }
        if (user.getAddress() != null) {
            update.set("address", user.getAddress());
        }
        if (user.getEmail() != null) {
            update.set("email", user.getEmail());
        }
        if (user.getStatus() == 0 || user.getStatus() == 1) {
            update.set("status", user.getStatus());
        }
        // 执行修改
        UpdateResults update1 = db.update(query, update);
        return update1.getUpdatedCount();
    }

    // 激活/冻结用户
    public int updateStatus(String id, int status) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User1> query = db.createQuery(User1.class);
        query.filter("_id", new ObjectId(id));
        UpdateOperations<User1> update = db.createUpdateOperations(User1.class).set("status", status);
        UpdateResults update1 = db.update(query, update);
        return update1.getUpdatedCount();
    }

    public static void export(List<User1> users, String filePath) {
        // 创建一个新的工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建一个工作表
            Sheet sheet = workbook.createSheet("用户列表");

            // 创建表头行
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("用户名");
            headerRow.createCell(2).setCellValue("密码");
            headerRow.createCell(3).setCellValue("年龄");
            headerRow.createCell(4).setCellValue("手机");
            headerRow.createCell(5).setCellValue("邮箱");
            headerRow.createCell(6).setCellValue("地址");
            headerRow.createCell(7).setCellValue("状态");

            // 遍历用户列表，创建数据行
            int rowNum = 1;
            for (User1 user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getPassword());
                row.createCell(3).setCellValue(user.getAge());
                row.createCell(4).setCellValue(user.getMobile());
                row.createCell(5).setCellValue(user.getEmail());
                row.createCell(6).setCellValue(user.getAddress());
                row.createCell(7).setCellValue(user.getStatus());
            }

            // 将工作簿写入文件
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Excel 文件导出成功：" + filePath);

        } catch (IOException e) {
            System.out.println("导出 Excel 文件时发生错误：" + e.getMessage());
        }
    }
}
