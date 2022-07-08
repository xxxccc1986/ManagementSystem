package top.year21.springboot.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 封装后台返回给前台的数据
 * @date 2022/6/21 10:47
 */
@Data
public class Message {
    //状态码 100-成功 200-失败
    private int code;
    //提示信息
    private  String message;

    //服务器要返回给浏览器的数据
    private Map<String,Object> extend = new HashMap<>();

    public static Message success(){
        Message message = new Message();
        message.setCode(100);
        message.setMessage("处理成功");
        return message;
    }

    public static Message fail(){
        Message message = new Message();
        message.setCode(200);
        message.setMessage("处理失败");
        return message;
    }

    public Message add(String key,Object value){
        this.getExtend().put(key,value);
        return this;
    }
}
