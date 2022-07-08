package top.year21.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.year21.springboot.entity.User;
import top.year21.springboot.mapper.UserMapper;
import top.year21.springboot.service.UserService;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 12:03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User serviceFindPage(Integer id) {
        return userMapper.findPage(id);
    }
}
