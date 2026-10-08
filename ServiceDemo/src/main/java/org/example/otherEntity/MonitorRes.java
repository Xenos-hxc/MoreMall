package org.example.otherEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author example.demo
 * @since 2023-11-21
 */
@Data
public class MonitorRes {


    /**
     * 监控id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工id
     */
    private Integer staffid;

    /**
     * 进程名
     */
    private String processname;

    /**
     * 使用时间戳差
     */
    private Long usetime;

    /**
     * cpu占用率
     */
    private Float cpu;

    /**
     * 进程pid
     */
    private Integer pid;

    /**
     * 开始时间戳
     */
    private Long starttime;

    /**
     * 检测时间
     */
    @TableField("checkTime")
    private LocalDateTime checktime;
    private String name;//设备名


}
