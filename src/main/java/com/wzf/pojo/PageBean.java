package com.wzf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//分页返回结果数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T>{
    private Long total;//总条数
    private List<T> items;//当前页数据的集合
}
