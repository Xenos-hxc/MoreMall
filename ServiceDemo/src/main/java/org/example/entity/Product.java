package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity("products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private Double price;
    private Integer sum;
    private Long visitCount;
    private int status;
    private Date addDate;
    private String info;


    public Product(String name, double price, Integer sum, Long visitCount, int status, Date addDate, String info) {
        this.name = name;
        this.price = price;
        this.sum = sum;
        this.visitCount = visitCount;
        this.status = status;
        this.addDate = addDate;
        this.info = info;
    }

    public Product(String name, Double price, Integer sum, Date addDate) {
        this.name = name;
        this.price = price;
        this.sum = sum;
        this.addDate = addDate;
    }

    public Product(ObjectId id, String name, Double price, Integer sum, Date addDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sum = sum;
        this.addDate = addDate;
    }
    public Product(ObjectId id, String name, double price, Integer sum, String info) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sum = sum;
        this.info = info;
    }
}
