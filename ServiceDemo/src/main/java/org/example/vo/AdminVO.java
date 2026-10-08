package org.example.vo;

import lombok.Data;
import org.bson.types.ObjectId;
@Data
public class AdminVO {
    private String id;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private String avatar;
}
