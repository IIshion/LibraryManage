package sys.librarymanage.BookController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sys.librarymanage.ApiResponse.ApiResponse;
import sys.librarymanage.BookService.BookService;
import sys.librarymanage.Entity.Book;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 查询所有图书
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(ApiResponse.success("查询成功", books));
    }

    // 根据id查询图书
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable int id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(ApiResponse.success("查询成功", book)))  // 返回 200 + 数据
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "图书未找到"))); // 404 not found
    }

    // 添加图书
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody Book book) {
        Book saved = bookService.save(book);
        return ResponseEntity.status(201).body(ApiResponse.success("创建成功", saved)); // 201 Created
    }

    // 更新图书
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable int id, @RequestBody Book book) {

        return bookService.findById(id).map
                (foundBook -> {
            foundBook.setTitle(book.getTitle());
            foundBook.setAuthor(book.getAuthor());
            foundBook.setDescription(book.getDescription());
            foundBook.setStatus(book.getStatus());
            Book updatedBook = bookService.save(foundBook);
            return ResponseEntity.ok(ApiResponse.success("更新成功", updatedBook)); // 200 ok

        }).orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "图书未找到"))); // 404 not found
    }
    // 删除图书
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBook(@PathVariable int id) {
       return bookService.findById(id)
               .map(book -> {
                   bookService.deleteById(id);
                   return ResponseEntity.ok(ApiResponse.success("删除成功", null));
               })
               .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "图书未找到")));
    }
}
