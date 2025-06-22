package sys.librarymanage.BookRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sys.librarymanage.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
