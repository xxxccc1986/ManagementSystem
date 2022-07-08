package top.year21.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.year21.springboot.entity.Book;
import top.year21.springboot.common.Message;
import top.year21.springboot.service.BookService;

import java.util.List;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 10:58
 */
@RestController
/*可以区分多个类中的有相同的请求地址
这个controller下的所有请求地址都是以localhost:9090/book/...开始的
 */
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    /**
     * Description : 增加新的用户
     * @date 2022/6/22
     * @param book 新增的书籍信息
     * @return top.year21.springboot.common.Message
     **/
    @PostMapping("/save") //例如这个，完整地址是localhost:9090/Book/save
    public Message savaBook(@RequestBody Book book){
        boolean result = bookService.save(book);
        if (result){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * Description : 更新用户
     * @date 2022/6/23
     * @param Book 待更新的用户数据
     * @return top.year21.springboot.common.Message
     **/
    @PutMapping("/update") //例如这个，完整地址是localhost:9090/Book/update
    public Message updateBook(@RequestBody Book Book){
        boolean result = bookService.updateById(Book);
        if(result){
            return Message.success().add("更新结果",result);
        }
        return Message.fail();

    }


    @DeleteMapping("/delete") //例如这个，完整地址是localhost:9090/Book/update
    public Message deleteBook(@RequestParam Long id){
        boolean result = bookService.removeById(id);
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
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam("search")  String search){
        Page<Book> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Book> BookQueryWrapper = new QueryWrapper<>();
        BookQueryWrapper.like(StringUtils.isNotBlank("search"),"name",search);
        bookService.page(page,BookQueryWrapper);
        return Message.success().add("data",page);
    }

    @DeleteMapping("/deleteBatch/{ids}")
    public Message deleteBatch(@PathVariable("ids") List<Integer> shouldDeteledId){
        boolean result = bookService.removeByIds(shouldDeteledId);
        if (result){
            return Message.success();
        }
        return Message.fail();
    }

}
