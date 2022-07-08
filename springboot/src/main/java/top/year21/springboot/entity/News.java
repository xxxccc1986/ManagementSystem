package top.year21.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/7/4 15:35
 */
@Data
@TableName("news")
public class News {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String title;
    private String context;
    private String author;
    private Date createTime;

    @TableLogic
    private Integer isDeleted;


}
