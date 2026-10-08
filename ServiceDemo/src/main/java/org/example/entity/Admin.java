package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private String avatar;
}
