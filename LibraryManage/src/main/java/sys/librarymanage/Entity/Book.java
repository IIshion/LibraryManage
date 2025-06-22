package sys.librarymanage.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    public enum Status {
        AVAILABLE, BORROWED
    }

    // getter/setter 略
}
