package com.zsp.hefengLibrary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zsp.hefengLibrary.constant.CommonConstant;
import com.zsp.hefengLibrary.mapper.BookMapper;
import com.zsp.hefengLibrary.model.dto.book.BookQueryRequest;
import com.zsp.hefengLibrary.model.entity.Book;
import com.zsp.hefengLibrary.model.vo.BookEditVO;
import com.zsp.hefengLibrary.model.vo.BookVO;
import com.zsp.hefengLibrary.service.BookService;
import com.zsp.hefengLibrary.service.UserService;
import com.zsp.hefengLibrary.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author j
* @description 针对表【book(题目)】的数据库操作Service实现
* @createDate 2023-11-24 16:53:49
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService {

    private final static Gson GSON = new Gson();

    @Resource
    private UserService userService;


    /**
     * 获取查询包装类(用户根据了哪些字段查询，根据前端传来的请求对象，得到mybatis框架支持的查询QueryWrapper类)
     *
     * @param bookQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Book> getQueryWrapper(BookQueryRequest bookQueryRequest) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (bookQueryRequest == null) {
            return queryWrapper;
        }
//        Long id = bookQueryRequest.getId();
        String title = bookQueryRequest.getTitle();
        String author = bookQueryRequest.getAuthor();
        String publisher = bookQueryRequest.getPublisher();
        String isbn = bookQueryRequest.getIsbn();
        String category = bookQueryRequest.getCategory();

//        Long id = bookQueryRequest.getId();
//        String title = bookQueryRequest.getTitle();
//        String content = bookQueryRequest.getContent();
//        List<String> tags = bookQueryRequest.getTags();
//        String answer = bookQueryRequest.getAnswer();
//        Long userId = bookQueryRequest.getUserId();
        String sortField = bookQueryRequest.getSortField();
        String sortOrder = bookQueryRequest.getSortOrder();


        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(author), "author", author);
        queryWrapper.like(StringUtils.isNotBlank(publisher), "publisher", publisher);

        queryWrapper.like(StringUtils.isNotBlank(isbn), "isbn", isbn);
        queryWrapper.like(StringUtils.isNotBlank(category), "category", category);

//        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public BookEditVO getBookEditVO(Book book, HttpServletRequest request) {
        BookEditVO bookEditVO = BookEditVO.objToVo(book);
//        long bookId = book.getId();
//        // 1. 关联查询用户信息
//        Long userId = book.getUserId();
//        User user = null;
//        if (userId != null && userId > 0) {
//            user = userService.getById(userId);
//        }
        //无需设置创建人的信息
//        UserVO userVO = userService.getUserVO(user);
//        bookVO.setUserVO(userVO);
        return bookEditVO;
    }


    @Override
    public BookVO getBookVO(Book book, HttpServletRequest request) {
        BookVO bookVO = BookVO.objToVo(book);
//        long bookId = book.getId();
//        // 1. 关联查询用户信息
//        Long userId = book.getUserId();
//        User user = null;
//        if (userId != null && userId > 0) {
//            user = userService.getById(userId);
//        }
//        UserVO userVO = userService.getUserVO(user);
//        bookVO.setUserVO(userVO);
        return bookVO;
    }

    @Override
    public Page<BookVO> getBookVOPage(Page<Book> bookPage, HttpServletRequest request) {
        List<Book> bookList = bookPage.getRecords();
        Page<BookVO> bookVOPage = new Page<>(bookPage.getCurrent(), bookPage.getSize(), bookPage.getTotal());
        if (CollectionUtils.isEmpty(bookList)) {
            return bookVOPage;
        }
//        // 1. 关联查询用户信息
//        Set<Long> userIdSet = bookList.stream().map(Book::getUserId).collect(Collectors.toSet());
//        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
//                .collect(Collectors.groupingBy(User::getId));
//
//        // 填充信息
//        List<BookVO> bookVOList = bookList.stream().map(book -> {
//            BookVO bookVO = BookVO.objToVo(book);
//            Long userId = book.getUserId();
//            User user = null;
//            if (userIdUserListMap.containsKey(userId)) {
//                user = userIdUserListMap.get(userId).get(0);
//            }
//            bookVO.setUserVO(userService.getUserVO(user));
//            return bookVO;
//        }).collect(Collectors.toList());
//        bookVOPage.setRecords(bookVOList);
        return bookVOPage;
    }

}




