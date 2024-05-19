package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsp.hefengLibrary.model.entity.Book;
import generator.service.BookService;
import com.zsp.hefengLibrary.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
* @author j
* @description 针对表【book(图书)】的数据库操作Service实现
* @createDate 2024-05-19 14:45:04
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService{

}




