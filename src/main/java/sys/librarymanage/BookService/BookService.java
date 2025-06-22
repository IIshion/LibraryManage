package sys.librarymanage.BookService;

import org.springframework.stereotype.Service;
import sys.librarymanage.BookRepository.BookRepository;
import sys.librarymanage.Entity.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 查询所有图书
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // 根据id查询图书
    public Optional<Book> findById(Integer id){
        return bookRepository.findById(id);
    }

    // 新增或更新图书
    public Book save(Book book){
        return bookRepository.save(book);
    }

    // 删除图书
    public void deleteById(Integer id){
        bookRepository.deleteById(id);
    }
}
