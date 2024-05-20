package com.zsp.hefengLibrary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.zsp.hefengLibrary.annotation.AuthCheck;
import com.zsp.hefengLibrary.common.BaseResponse;
import com.zsp.hefengLibrary.common.DeleteRequest;
import com.zsp.hefengLibrary.common.ErrorCode;
import com.zsp.hefengLibrary.common.ResultUtils;
import com.zsp.hefengLibrary.constant.UserConstant;
import com.zsp.hefengLibrary.exception.BusinessException;
import com.zsp.hefengLibrary.exception.ThrowUtils;
import com.zsp.hefengLibrary.model.dto.book.BookAddRequest;
import com.zsp.hefengLibrary.model.dto.book.BookEditRequest;
import com.zsp.hefengLibrary.model.dto.book.BookQueryRequest;
import com.zsp.hefengLibrary.model.dto.book.BookUpdateRequest;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowAddRequest;
import com.zsp.hefengLibrary.model.dto.borrow.BorrowQueryRequest;
import com.zsp.hefengLibrary.model.entity.Book;
import com.zsp.hefengLibrary.model.entity.Borrow;
import com.zsp.hefengLibrary.model.entity.User;
import com.zsp.hefengLibrary.model.vo.BookEditVO;
import com.zsp.hefengLibrary.model.vo.BookVO;
import com.zsp.hefengLibrary.model.vo.BorrowVO;
import com.zsp.hefengLibrary.service.BookService;
import com.zsp.hefengLibrary.service.BorrowService;
import com.zsp.hefengLibrary.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
    @Resource
    private BookService bookService;

    @Resource
    private BorrowService borrowService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param bookAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addBook(@RequestBody BookAddRequest bookAddRequest, HttpServletRequest request) {
        if (bookAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookAddRequest, book);
        boolean result = bookService.save(book);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newBookId = book.getId();
        return ResultUtils.success(newBookId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBook(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR);
        boolean b = bookService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param bookUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest) {
        if (bookUpdateRequest == null || bookUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookUpdateRequest, book);
        long id = bookUpdateRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = bookService.updateById(book);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<BookVO> getBookVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = bookService.getById(id);
        if (book == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(bookService.getBookVO(book, request));
    }

    /**
     * 根据 id 获取 BookEditVO对象
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<BookEditVO> getBookEditVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = bookService.getById(id);
        if (book == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(bookService.getBookEditVO(book, request));
    }

//    /**
//     * 分页获取列表（封装类）
//     *
//     * @param bookQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<BookVO>> listBookVOByPage(@RequestBody BookQueryRequest bookQueryRequest,
//                                                               HttpServletRequest request) {
//        long current = bookQueryRequest.getCurrent();
//        long size = bookQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<Book> bookPage = bookService.page(new Page<>(current, size),
//                bookService.getQueryWrapper(bookQueryRequest));
//        return ResultUtils.success(bookService.getBookVOPage(bookPage, request));
//    }

//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param bookQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<BookVO>> listMyBookVOByPage(@RequestBody BookQueryRequest bookQueryRequest,
//                                                                 HttpServletRequest request) {
//        if (bookQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        long current = bookQueryRequest.getCurrent();
//        long size = bookQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<Book> bookPage = bookService.page(new Page<>(current, size),
//                bookService.getQueryWrapper(bookQueryRequest));
//        return ResultUtils.success(bookService.getBookVOPage(bookPage, request));
//    }

    // endregion


    /**
     * 编辑（用户）
     *
     * @param bookEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editBook(@RequestBody BookEditRequest bookEditRequest, HttpServletRequest request) {
        if (bookEditRequest == null || bookEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookEditRequest, book);
        User loginUser = userService.getLoginUser(request);
        long id = bookEditRequest.getId();
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = bookService.updateById(book);
        return ResultUtils.success(result);
    }

    /**
     * 下架图书
     *
     * @param request
     * @return
     */
    @PostMapping("/offshelf")
    public BaseResponse<Boolean> offShelfBook(long id, HttpServletRequest request) {
        Book book = bookService.getById(id);
        book.setStatus(2);
        // 判断是否存在
        Book oldBook = bookService.getById(id);
        ThrowUtils.throwIf(oldBook == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = bookService.updateById(book);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取图书列表（仅管理员）
     *
     * @param bookQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Book>> listBookByPage(@RequestBody BookQueryRequest bookQueryRequest,
                                                           HttpServletRequest request) {
        long current = bookQueryRequest.getCurrent();
        long size = bookQueryRequest.getPageSize();
        Page<Book> bookPage = bookService.page(new Page<>(current, size),
                bookService.getQueryWrapper(bookQueryRequest));
        return ResultUtils.success(bookPage);
    }


    /**
     * 提交题目
     *
     * @param borrowAddRequest
     * @param request
     * @return 提交记录的id
     */
    @PostMapping("/borrow/do")
    public BaseResponse<Long> doBorrow(@RequestBody BorrowAddRequest borrowAddRequest,
                                               HttpServletRequest request) {
        if (borrowAddRequest == null || borrowAddRequest.getBookId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long bookId = borrowAddRequest.getBookId();
        //book表库存+1
        Book book = bookService.getById(bookId);
        if(book.getQuantity() <= 0  || book.getStatus() == 2){//校验库存是否充足
            throw new BusinessException(ErrorCode.OPERATION_ERROR);//库存不足
        }
        book.setQuantity(book.getQuantity() - 1);
        if(book.getQuantity() <= 0  && book.getStatus() != 2){
            book.setStatus(1);//设置为库存不足
        }
        bookService.updateById(book);
        //生成borrow记录
        long bookSubmitId = borrowService.doBorrow(borrowAddRequest, loginUser);
        return ResultUtils.success(bookSubmitId);
    }

    /**
     * 分页获取题目提交列表（非管理员的普通用户，只能看到非答案、提交代码等公开信息）
     *
     * @param borrowQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/borrow/list/page")
    public BaseResponse<Page<BorrowVO>> listBorrowByPage(@RequestBody BorrowQueryRequest borrowQueryRequest,
                                                         HttpServletRequest request) {
        long current = borrowQueryRequest.getCurrent();
        long size = borrowQueryRequest.getPageSize();
        Page<Borrow> borrowPage = borrowService.page(new Page<>(current, size),
                borrowService.getQueryWrapper(borrowQueryRequest));
        //脱敏
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(borrowService.getBorrowVOPage(borrowPage,loginUser));
    }

    /**
     * 归还图书
     *
     * @param request
     * @return
     */
    @PostMapping("/borrow/return")
    public BaseResponse<Boolean> returnBook(long id, HttpServletRequest request) {
        Borrow borrow = borrowService.getById(id);
        borrow.setIsReturned(1);
        borrow.setReturnDate(new Date());
        //book表库存+1
        Long bookId = borrow.getBookId();
        Book book = bookService.getById(bookId);
        book.setQuantity(book.getQuantity() + 1);
        if(book.getQuantity() >= 0 && book.getStatus() != 2){//下架状态，也不能设置为库存充足
            book.setStatus(0);//设置为库存充足
        }
        boolean resultBook = bookService.updateById(book);
        // 判断是否存在
        Borrow oldBorrow = borrowService.getById(id);
        ThrowUtils.throwIf(oldBorrow == null, ErrorCode.NOT_FOUND_ERROR);
        boolean resultBorrow = borrowService.updateById(borrow);
        return ResultUtils.success(resultBook && resultBorrow);
    }
}
