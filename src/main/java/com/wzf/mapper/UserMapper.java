package com.wzf.mapper;

import com.wzf.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
//@Repository
public interface UserMapper {
    //根据用户名查询用户
    @Select("select  * from user where username = #{username}")
    User findByName(String username);
    //添加
    @Insert("insert into user(username, password, create_time, update_time)" +
            " values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password=#{pwd}, update_time=now() where id=#{id}")
    void updatePwd(Integer id, String pwd);
}
