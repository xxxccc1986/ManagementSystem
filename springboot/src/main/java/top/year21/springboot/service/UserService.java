package top.year21.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.year21.springboot.entity.User;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 12:03
 */
public interface UserService extends IService<User> {

    User serviceFindPage(Integer id);
}
