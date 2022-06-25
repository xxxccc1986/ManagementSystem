package top.year21.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.springboot.entity.Message;
import top.year21.springboot.entity.User;
import top.year21.springboot.service.UserService;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 10:58
 */
@RestController
/*可以区分多个类中的有相同的请求地址
这个controller下的所有请求地址都是以localhost:9090/user/...开始的
 */
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Description : 增加新的用户
     * @date 2022/6/22
     * @param user 新增的用户信息
     * @return top.year21.springboot.entity.Message
     **/
    @PostMapping("/save") //例如这个，完整地址是localhost:9090/user/save
    public Message savaUser(@RequestBody User user){
        if (user.getPassWord() == null){
            user.setPassWord("default");
        }
        boolean result = userService.save(user);
        return Message.success().add("插入结果",result);
    }

    /**
     * Description : 更新用户
     * @date 2022/6/23
     * @param user 待更新的用户数据
     * @return top.year21.springboot.entity.Message
     **/
    @PutMapping("/update") //例如这个，完整地址是localhost:9090/user/update
    public Message updateUser(@RequestBody User user){
        boolean result = userService.updateById(user);
        return Message.success().add("更新结果",result);
    }

//    @DeleteMapping("/delete/{id}") //例如这个，完整地址是localhost:9090/user/update
//    public Message deleteUser(@PathVariable("id") Long id){
//        boolean result = userService.removeById(id);
//        return Message.success().add("更新结果",result);
//    }

    @DeleteMapping("/delete") //例如这个，完整地址是localhost:9090/user/update
    public Message deleteUser(@RequestParam Long id){
        boolean result = userService.removeById(id);
        return Message.success().add("更新结果",result);
    }

    /**
     * Description : 模糊查询
     * @date 2022/6/22
     * @param pageNum 当前页码
     * @param pageSize 每页展示的数据量
     * @param search 模糊查询的关键字
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page
     **/
    @GetMapping("/query")
    public Message getPageData(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize")  Integer pageSize,
                               @RequestParam("search")  String search){
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank("search"),"username",search);
        userService.page(page,userQueryWrapper);
        return Message.success().add("data",page);
    }

}
