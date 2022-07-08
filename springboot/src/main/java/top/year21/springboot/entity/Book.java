package top.year21.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/7/3 15:45
 */
@Data
@TableName("book")
public class Book {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;
    private BigDecimal price;
    private String author;
    private Date createdTime;
    private String cover;

    @TableLogic
    private Integer isDeleted;
}
