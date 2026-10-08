package org.example.dao;


import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.example.entity.User;
import org.example.util.MorphiaPooUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class UserDAO1 {

    //添加
    public void insert(User u) {
        Datastore db = MorphiaPooUtil.getDb();
        User user = new User(u.getUsername(), u.getPassword(), u.getAge());
        db.save(user);//有对应关系的映射，所以不用写表
    }

    //删除
    public boolean delete(String id) {
        Datastore db = MorphiaPooUtil.getDb();
        WriteResult writeResult = db.delete(db.createQuery(User.class).field("_id").equal(new ObjectId(id)));
        return writeResult.isUpdateOfExisting();

    }

    //根据id查询
    public List<User> findById(String id) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User> users = db.find(User.class, "_id =", new ObjectId(id));
        return users.asList();
    }

    //查询所有
    public List<User> findAll() {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User> users = db.find(User.class);
        return users.asList();
    }

    //5 分页查询
    public List<User> findPage(int skip, int limit) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User> query = db.createQuery(User.class);
        FindOptions findOptions = new FindOptions();
        findOptions.skip(skip).limit(limit);
        List<User> user = query.asList(findOptions);
        return user;
    }

    //统计总数
    public long count() {
        Datastore db = MorphiaPooUtil.getDb();
        long count = db.getCount(User.class);
        return count;
    }

    //修改
    public boolean update(User user) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<User> users = db.find(User.class, "_id =", user.getId());
        if (users.count() == 0) {
            System.out.println("修改的用户不存在");
            return false;
        }

        for (User user1 : users) {
            if (user.getAge() != null) {
                if (user1.getAge() != user.getAge()) {
                    db.update(user1, db.createUpdateOperations(User.class).set("age", user.getAge()));
                }
            }
            if (user.getUsername() != null) {
                if (!user1.getUsername().equals(user.getUsername())) {
                    db.update(user1, db.createUpdateOperations(User.class).set("username", user.getUsername()));
                }
            }
            if (user.getPassword() != null) {
                if (!user1.getPassword().equals(user.getPassword())) {
                    db.update(user1, db.createUpdateOperations(User.class).set("password", user.getPassword()));
                }
            }
            if (user.getMobile() != null) {
                if (!user1.getMobile().equals(user.getMobile())) {
                    db.update(user1, db.createUpdateOperations(User.class).set("mobile", user.getMobile()));
                }
            }
            if (user.getEmail() != null) {
                if (!user1.getEmail().equals(user.getEmail())) {
                    db.update(user1, db.createUpdateOperations(User.class).set("email", user.getEmail()));
                }
            }
            if (user.getAddress() != null) {
                if (!user1.getAddress().equals(user.getAddress())) {
                    db.update(user1, db.createUpdateOperations(User.class).set("address", user.getAddress()));
                }
            }
            if (user.getStatus()==1||user.getStatus()==0) {
                    db.update(user1, db.createUpdateOperations(User.class).set("status", user.getStatus()));
            }
        }
        return true;
    }
}
