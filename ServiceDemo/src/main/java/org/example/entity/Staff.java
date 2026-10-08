package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author example.demo
 * @since 2023-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "staffid", type = IdType.AUTO)
    private Integer staffid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String tel;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;
    private String password;
    private Integer num;
    private Integer upperid;
    private Integer workingnum;


}
