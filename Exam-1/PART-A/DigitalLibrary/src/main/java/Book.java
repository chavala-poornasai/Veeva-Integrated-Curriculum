
import java.util.Objects;

public class Book implements Searchable {

    private String isbn;
    private String title;
    private String author;
    private int borrowCount;

    public Book(String isbn, String title, String author, int borrowCount) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    @Override
    public boolean matchesKeyword(String keyword) {

        return title.toLowerCase().contains(keyword.toLowerCase())
                || author.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Book))
            return false;

        Book b = (Book) obj;

        return isbn.equals(b.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return title + " (" + borrowCount + ")";
    }
}
