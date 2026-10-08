package org.example.dao;


import org.bson.types.ObjectId;
import org.example.entity.Admin;
import org.example.util.MorphiaPooUtil;
import org.example.vo.AdminVO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class AdminDAO {
    public List<Admin> findById(String id){
        Datastore db= MorphiaPooUtil.getDb();
        Query<Admin> admins = db.find(Admin.class, "_id =", new ObjectId(id));
        return admins.asList();
    }

    public Admin findByNameAndPwd(String name,String password){
        Datastore db=MorphiaPooUtil.getDb();
        Query<Admin> admins = db.createQuery(Admin.class).field("name").equal(name);
        Admin admin=new Admin();
        for (Admin admin1 : admins) {
            if (admin1.getPassword().equals(password)){
                return admin1;
            }
        }
        return null;

    }

    public List<Admin> findByNameAndPwd2(String name, String password){
        Datastore db=MorphiaPooUtil.getDb();
        Query<Admin> admins = db.createQuery(Admin.class)
                .field("name").equal(name)
                .field("password").equal(password);
        return admins.asList();
    }

    public List<Admin> findByName(String name){
        Datastore db= MorphiaPooUtil.getDb();
        Query<Admin> admins = db.find(Admin.class, "name =", name);
        return admins.asList();
    }

    public void updateAvatar(String id,String avatar){
        Datastore db = MorphiaPooUtil.getDb();
        Query<Admin> query = db.createQuery(Admin.class);
        query.filter("_id",new ObjectId(id));
        UpdateOperations<Admin> updateOperations = db.createUpdateOperations(Admin.class);
        updateOperations.set("avatar",avatar);
        db.update(query,updateOperations);
    }

    public void updateAdmin(AdminVO adminVO){
        Datastore db = MorphiaPooUtil.getDb();
        Query<Admin> query = db.createQuery(Admin.class);
        query.filter("_id",new ObjectId(adminVO.getId()));
        UpdateOperations<Admin> updateOperations = db.createUpdateOperations(Admin.class);
        updateOperations.set("name",adminVO.getName());
        updateOperations.set("mobile",adminVO.getMobile());
        updateOperations.set("email",adminVO.getEmail());
        db.update(query,updateOperations);
    }
}
