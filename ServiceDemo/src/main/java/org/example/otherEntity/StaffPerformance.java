package org.example.otherEntity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StaffPerformance {
    private Integer staffid;
    private String name;
    private String tel;
    private String position;
    private String department;
    private Integer num;
    private Integer workingnum;
    private float rate;
    private String workingtime;
    private String entertaintime;
    private float performance;

}
