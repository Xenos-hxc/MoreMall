package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {
    //状装填数据的集合
    private List<T> list=new ArrayList<T>();
    //数据的总条数
    private Long total;
    //当前页号
    private int pageNum;
    //每页大小
    private int pageSize=10;

    public  boolean getIsLastPage(){
        long totalPage=total%pageNum==0?(total/pageSize):(total/pageSize-1);
        return pageNum>=totalPage;
    }

}
