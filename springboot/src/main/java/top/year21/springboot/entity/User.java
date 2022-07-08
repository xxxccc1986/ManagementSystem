package top.year21.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import top.year21.springboot.enums.SexEnum;

import java.util.List;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 11:35
 */
@Data
@TableName("user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

//    @TableField("username")
    private String username;
//    @TableField("password")
    private String password;
    private String nickName;
    private Integer age;

    private SexEnum sex;

    private String address;

    private Integer role;

    //@TableField(exist = false)强调此字段在数据库表中不存在
    @TableField(exist = false)
    private List<Book> bookList;

    @TableLogic
    private Integer isDeleted;
}
