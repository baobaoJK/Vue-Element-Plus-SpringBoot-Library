package com.ksamar.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksamar.library.entitys.Book;
import com.ksamar.library.entitys.Borrow;
import com.ksamar.library.entitys.Type;
import com.ksamar.library.repository.BookRepository;
import com.ksamar.library.repository.BorrowRepository;
import com.ksamar.library.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 图书控制器
 * @author KSaMar
 */
@RestController
@RequestMapping("/book")
public class BookHandler {

    /**
     * 图书数据库操作接口
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * 借阅图书数据库操作接口
     */
    @Autowired
    private BorrowRepository borrowRepository;

    /**
     * 图书种类数据库接口
     */
    @Autowired
    private TypeRepository typeRepository;

    /**
     * 仪表盘信息
     * @return 仪表盘信息
     */
    @GetMapping("/dashboard")
    public JSONObject getDashboard() {
        JSONObject dashboardInfo = new JSONObject();

        // 图书数量
        List<Book> bookList = bookRepository.findAll();
        Integer bookCount = 0;
        for (Book book : bookList) {
            bookCount += book.getQuantity();
        }

        // 借阅图书数量
        List<Borrow> borrowList = borrowRepository.findAll();
        Integer borrowCount = borrowList.size();

        // 超时未归还图书数量
        Integer overtimeCount = 0;
        Calendar calendar = Calendar.getInstance();
        for (Borrow borrow : borrowList) {
            if (calendar.getTime().after(borrow.getReturnTime())) {
                overtimeCount++;
            }
        }

        // 存入信息
        dashboardInfo.put("bookCount", bookCount);
        dashboardInfo.put("borrowCount", borrowCount);
        dashboardInfo.put("overtimeCount", overtimeCount);

        return dashboardInfo;
    }

    /**
     * 获取图书数据
     * @param page 页码
     * @param size 大小
     * @return 图书数据
     */
    @GetMapping("/{page}/{size}")
    public Page<Book> getPageBook(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return bookRepository.findAll(pageable);
    }

    /**
     * 图书信息搜索
     * @param text 搜索信息
     * @return 图书信息
     */
    @GetMapping("/search/{type}/{text}/{page}/{size}")
    public Page<Book> findBook(@PathVariable("type") String type,
                                 @PathVariable("text") String text,
                                 @PathVariable("page") Integer page,
                                 @PathVariable("size") Integer size) {
        Page<Book> bookPage;
        PageRequest pageable = PageRequest.of(page - 1, size);
        switch (type) {
            case "name":
                bookPage = bookRepository.findByNameLike(text, pageable);
                break;
            case "author":
                bookPage = bookRepository.findByAuthorLike(text, pageable);
                break;
            case "press":
                bookPage = bookRepository.findByPressLike(text, pageable);
                break;
            case "isbn":
                bookPage = bookRepository.findByIsbnLike(text, pageable);
                break;
            default:
                bookPage = null;
                break;
        }

        return bookPage;
    }

    /**
     * 图书种类获取
     * @return 图书种类列表
     */
    @GetMapping("/type")
    public List<Type> getType() {
        return typeRepository.findAll();
    }

    /**
     * 添加图书
     * 0 - 添加图书失败
     * 1 - 添加图书成功
     * 2 - ISBN 号码存在
     * @param book 图书信息
     * @return 添加图书状态
     */
    @PostMapping("/save")
    public JSONObject save(@RequestBody Book book) {
        JSONObject bookMessage = new JSONObject();
        bookMessage.put("message", "addBook");
        bookMessage.put("statusCode", 0);

        // 判断图书是否为空
        if (book != null) {
            Book tempBook = bookRepository.findByIsbn(book.getIsbn());
            if (tempBook == null) {
                bookRepository.save(book);
                bookMessage.replace("statusCode", 1);
            }
            else {
                bookMessage.replace("statusCode", 2);
            }
        }

        return bookMessage;
    }

    /**
     * 编辑图书
     * 0 - 编辑图书失败
     * 1 - 编辑图书成功
     * 2 - ISBN 号码存在
     * @param book 图书信息
     * @return 编辑图书状态
     */
    @PostMapping("/update")
    public JSONObject edit(@RequestBody Book book) {
        JSONObject bookMessage = new JSONObject();
        bookMessage.put("message", "editBook");
        bookMessage.put("statusCode", 0);

        // 判断图书是否为空
        if (book != null) {
            Book tempBook = bookRepository.findByIsbn(book.getIsbn());
            if (tempBook == null || book.getId().equals(tempBook.getId())) {
                bookRepository.save(book);
                bookMessage.replace("statusCode", 1);
            }
            else {
                bookMessage.replace("statusCode", 2);
            }
        }
        return bookMessage;
    }

    /**
     * 删除图书
     * 0 - 删除失败
     * 1 - 删除成功
     * 2 - 删除失败 Id 不存在
     * @param id Id
     * @return 删除图书信息
     */
    @PostMapping("/delete/{id}")
    public JSONObject delete(@PathVariable("id") Integer id) {
        JSONObject bookMessage = new JSONObject();
        bookMessage.put("message", "deleteBook");
        bookMessage.put("statusCode", 0);

        // 判断图书是否存在
        boolean book = bookRepository.existsById(id);
        if (book) {
            bookRepository.deleteById(id);
            bookMessage.replace("statusCode", 1);
        }
        else {
            bookMessage.replace("statusCode", 2);
        }

        return bookMessage;
    }

    /**
     * ISBN号码搜索图书
     * @param isbn ISBN号码
     * @return 图书信息
     */
    @GetMapping("/find/isbn/{isbn}")
    public Book findByIsbn(@PathVariable("isbn") String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
