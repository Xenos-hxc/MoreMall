package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private String id;
    private String name;
    private double price;
    private Integer sum;
    private Long visitCount;
    private int status;
    private Date addDate;
    private String info;
}
