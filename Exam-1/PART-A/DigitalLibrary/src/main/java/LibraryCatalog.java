
import java.util.*;
import java.util.stream.Collectors;

public class LibraryCatalog {

    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        books.add(book);
    }

    // Search books by keyword
    public List<Book> searchBooks(String keyword) {

        List<Book> searchResults = books.stream()
                .filter(b -> b.matchesKeyword(keyword))
                .sorted((b1, b2) ->
                        Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                .collect(Collectors.toList());

        return searchResults;
    }

    // Find the most borrowed book
    public Optional<Book> getMostBorrowedBook() {

        Optional<Book> mostBorrowed = books.stream()
                .max((b1, b2) ->
                        Integer.compare(b1.getBorrowCount(), b2.getBorrowCount()));

        return mostBorrowed;
    }
}
