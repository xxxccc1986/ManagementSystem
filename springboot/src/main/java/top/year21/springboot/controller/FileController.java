package top.year21.springboot.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.year21.springboot.common.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: 处理文件上传接口
 * @date 2022/7/3 16:54
 */
@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*",maxAge = 3600)  //origins = "*"表示允许所有的请求跨域，处理前端跨域问题，允许value设定的请求进行跨域
public class FileController {

    //从配置文件中进行取值
    @Value("${server.port}")
    private String port;

    @Value("1.14.176.219")
    private String ip;


    @Value("${files.upload.path}")
    private String filePath;
    /**
     * Description : 处理普通文件上传
     * @date 2022/7/4
     * @param file 上传的文件
     * @return top.year21.springboot.common.Message
     **/
    @PostMapping("/upload")
    public Message fileUpload(MultipartFile file) throws IOException {

        //上传文件的原始名称
        String filename = file.getOriginalFilename();

        //获取上传文件的后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));

        //使用时间戳作为文件的新名字
        String name = UUID.randomUUID().toString();

        //重命名，即为保存服务器上的文件名字
        filename = name + suffixName;

        //获取项目在服务器上的绝对路径
        //System.getProperty("user.dir")这个命令可以获取springboot_vue_demo在服务器上的绝对路径
        //这里的地址很关键，别写错了 大坑属实
//        String  photoPath= System.getProperty("user.dir") + "/springboot/src/main/resources/static/img/" + filename;

        //先判断要保存图片的文件夹是否存在
        File pictureDir = new File(filePath);

        if (!pictureDir.exists()){
            pictureDir.mkdirs();
        }
        //创建要保存的图片
        File uploadFile = new File(filePath + filename);

        //进行保存
//        FileUtil.writeBytes(file.getBytes(),finalPath); //运用了工具类的写法
        //可以直接将文件写入到服务器硬盘上
        file.transferTo(uploadFile);

        return Message.success().add("url","http://" + ip + ":" + port + "/file/down/" + filename); //返回文件下载的url
    }

    /**
     * Description : 处理文件的下载
     * @date 2022/7/4
     * @param name 文件名
     * @return org.springframework.http.ResponseEntity<byte[]>
     **/
    @GetMapping("/down/{name}")
    public ResponseEntity<byte[]> down(@PathVariable("name") String name) throws IOException {
        //获取服务器中文件的真实路径
        File file = new File(filePath + name);
        System.out.println(file);
        String realPath =  file.getAbsolutePath();
        if (realPath != null){
            //创建输入流
            InputStream is = new FileInputStream(realPath);
            //创建字节数组
            //is.available() 这个方法可以在读写操作前先得知数据流里有多少个字节可以读取
            byte[] bytes = new byte[is.available()];
            //将流读到字节数组中
            is.read(bytes);
            //创建HttpHeaders对象设置响应头信息
            HttpHeaders headers = new HttpHeaders();
            //设置要下载方式以及下载文件的名字
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name,"UTF-8"));
            //设置响应状态码
            HttpStatus statusCode = HttpStatus.OK;
            //创建ResponseEntity对象
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
            //关闭输入流
            is.close();
            return responseEntity;
        }
        return null;
    }

    /**
     * Description : 专门处理富文本文件上传
     * @date 2022/7/4
     * @param file 上传的文件
     * @return top.year21.springboot.common.Message
     **/

    @PostMapping("/editorUpload")
    public JSON editorFileUpload(MultipartFile file) throws IOException {

        //上传文件的原始名称
        String filename = file.getOriginalFilename();

        //获取上传文件的后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));

        //使用时间戳作为文件的新名字
        String name = UUID.randomUUID().toString();

        //重命名，即为保存服务器上的文件名字
        filename = name + suffixName;

        //先判断要保存图片的文件夹是否存在
        File pictureDir = new File(filePath);

        if (!pictureDir.exists()){
            pictureDir.mkdir();
        }
        //创建要保存的图片
        File uploadFile = new File(filePath + filename);

        //进行保存
        //可以直接将文件写入到服务器硬盘上
        file.transferTo(uploadFile);

        String url = "http://" + ip + ":" + port + "/file/down/" + filename;
        JSONObject json = new JSONObject();
        json.set("errno",0);

        JSONArray dataArray = new JSONArray();
        JSONObject data= new JSONObject();
        data.set("url",url);
        dataArray.add(data);

        json.set("data",dataArray);

        return json;
    }


}
