package com.andy.service.impl;

import com.andy.mapper.UserMapper;
import com.andy.service.UserService;
import com.andy.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    @Transactional/*(value = "master")*/
    public int addUser(User user) {
        int insert = 0;
        try {
            insert = userMapper.insertSelective(user);
            Long id = user.getId();
            System.out.println("id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("操作失败:{}",e.getMessage(),e);
        }
        log.info("插入返回值：{}",insert);
        return insert;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

}
