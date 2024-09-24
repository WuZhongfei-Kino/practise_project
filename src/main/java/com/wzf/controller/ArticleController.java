package com.wzf.controller;

import com.wzf.anno.Limiter;
import com.wzf.pojo.Article;
import com.wzf.pojo.PageBean;
import com.wzf.pojo.Result;
import com.wzf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
    public Result<String> list(){
//        //验证token
//        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//        } catch (Exception e) {
////            throw new RuntimeException(e);
//            //http响应状态码为401
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("所有的文章数据。。。");
    }

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    @Limiter(limitNum = 5, name = "ArticleController")
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
       PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
       return Result.success(pageBean);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        System.out.println(article);
        articleService.update(article);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
       articleService.delete(id);
       return Result.success();
    }
}
