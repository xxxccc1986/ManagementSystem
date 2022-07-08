package top.year21.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.year21.springboot.entity.User;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 12:02
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    //一对多查询
    User findPage(@Param("id") Integer id);
}
