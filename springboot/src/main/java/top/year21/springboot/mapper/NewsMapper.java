package top.year21.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.year21.springboot.entity.Book;
import top.year21.springboot.entity.News;

/**
 * @author hcxs1986
 * @version 1.0
 * @description: TODO
 * @date 2022/6/21 12:02
 */
@Repository
public interface NewsMapper extends BaseMapper<News> {
}
