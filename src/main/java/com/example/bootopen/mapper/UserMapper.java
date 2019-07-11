package com.example.bootopen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bootopen.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User>{
    /*public List<User> listAll();
    int insertUser(User user);
    int deleteByName(String name);
*/
}
