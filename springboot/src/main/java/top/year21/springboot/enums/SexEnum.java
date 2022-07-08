package top.year21.springboot.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 11:44
 */
public enum SexEnum {
    MALE(1,"男"),
    FEMALE(2,"女");

    @EnumValue //将注解所标识的属性的值存储到数据库中
    private final Integer sex;

    @JsonValue //将注解所标识的属性的值传回前端
    private final String sexName;

    SexEnum(Integer sex,String sexName){
        this.sex = sex;
        this.sexName = sexName;
    }

    public Integer getSex() {
        return sex;
    }


    public String getSexName() {
        return sexName;
    }
}
