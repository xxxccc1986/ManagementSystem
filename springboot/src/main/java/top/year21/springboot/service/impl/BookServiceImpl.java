package top.year21.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.year21.springboot.entity.Book;
import top.year21.springboot.mapper.BookMapper;
import top.year21.springboot.service.BookService;


/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 12:03
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
