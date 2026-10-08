package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Staff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author example.demo
 * @since 2023-11-04
 */
public interface StaffMapper extends BaseMapper<Staff> {
    @Insert("INSERT INTO staff(name,tel,potision,department) VALUES (#{name},#{tel},#{potision},#{department})")
    boolean staff_insert(Staff staff);
    @Update("update staff " +
            "set " +
            "name = #{name}," +
            "tel = #{tel}," +
            "potision = #{potision}," +
            "department = #{department} " +
            "where staffid = #{staffid}")
    boolean staff_update(Staff staff);
    @Delete("delete from staff where staffid= #{staffid}")
    boolean staff_deleteById(@Param("staffid") Integer staffid);
    @Select("select * from staff where (name like concat('%',#{username},'%') or tel like concat('%',#{username},'%') or position like concat('%',#{username},'%')) and department like concat('%',#{value},'%') limit ${pageNum}, ${pageSize}")
    List<Staff> staff_selectPage(Integer pageNum, Integer pageSize, String username, String value);
    @Select("select count(*) from staff where (name like concat('%',#{username},'%') or tel like concat('%',#{username},'%') or position like concat('%',#{username},'%') and department like concat('%',#{value},'%'))")
    Integer staff_selectTotal(String username,String value);

    @Select("SELECT * from staff where department = #{department} and position = '主管'")
    List<Staff> staff_getzg(String department);
}
