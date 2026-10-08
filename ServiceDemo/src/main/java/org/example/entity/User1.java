package org.example.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("users")
@Data
public class User1 {
    @Id
    private String id;
    private String username;
    private String password;
    private Integer age;
    private String email;
    private String mobile;
    private String address;
    private int status;
}
