package top.year21.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.springboot.common.Message;
import top.year21.springboot.entity.User;
import top.year21.springboot.service.UserService;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/7/1 18:33
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Message login(@RequestParam(value = "username",required = false) String username,
                         @RequestParam(value = "password",required = false) String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username).eq("password",password);
        User  user = userService.getOne(queryWrapper);
        return Message.success().add("user",user);
    }

    @PostMapping("/register")
    public Message register(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User checkedUser = userService.getOne(queryWrapper);
        if (checkedUser == null){
            userService.save(user);
            return Message.success().add("result","注册成功");
        }
        return Message.fail().add("result","注册失败，用户名已存在");
    }

    @PutMapping("/resetPassword")
    public Message resetPassword(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User checkedUser = userService.getOne(queryWrapper);
        if (checkedUser != null){
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username",user.getUsername());
            userService.update(user,updateWrapper);
            return Message.success().add("result","密码重置成功");
        }
        return Message.fail().add("result","账户不存在，密码重置失败");
    }
}
