package com.wzf.service;


import com.wzf.pojo.Article;
import com.wzf.pojo.PageBean;

public interface ArticleService {
    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //删除
    void delete(Integer id);

    //修改
    void update(Article article);
}
