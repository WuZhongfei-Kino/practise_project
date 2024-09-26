package com.wzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzf.mapper.ArticleMapper;
import com.wzf.pojo.Article;
import com.wzf.pojo.PageBean;
import com.wzf.service.ArticleService;
import com.wzf.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        //获取当前登录用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        //赋值创建时间和更新时间为当前时间
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //1.创建PageBean对象
        PageBean<Article> pageBean = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
        List<Article> articles = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
//        Page<Article> page = (Page<Article>) articles;
//        page.addAll(articles);
//        page.setTotal(articles.size());

        int total = articles.size();

        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getCreateTime().isAfter(o2.getCreateTime()) ? 1 : 0;
//                return o1.getCategoryId().compareTo(o2.getCategoryId());
            }
        });
        Collections.reverse(articles);

        articles = articles.subList((pageNum - 1) * pageSize, total > pageNum * pageSize ? pageNum * pageSize : total);

        PageInfo pageInfo = new PageInfo<>(articles);


        pageInfo.setTotal(total);
        //把数据填充到PageBean对象中
//        pageBean.setTotal(page.getTotal());
//        pageBean.setItems(page.getResult());
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setItems(pageInfo.getList());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        System.out.println(id);
        articleMapper.delete(id);
    }

    @Override
    public void update(Article article) {
        //补齐时间
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }
}
