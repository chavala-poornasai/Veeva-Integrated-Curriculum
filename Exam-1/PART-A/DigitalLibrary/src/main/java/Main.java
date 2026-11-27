
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryCatalog library = new LibraryCatalog();

        System.out.print("Enter number of books: ");
        int n = sc.nextInt();

        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter Book " + i + " Details:");

            System.out.print("Enter ISBN: ");
            String isbn = sc.nextLine();

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            System.out.print("Enter Borrow Count: ");
            int borrowCount = sc.nextInt();

            sc.nextLine();

            Book book = new Book(isbn, title, author, borrowCount);

            library.addBook(book);
        }

        System.out.print("\nEnter Either Title OR Author  to search: ");
        String keyword = sc.nextLine();

        System.out.println("\nSearch Results: "
                + library.searchBooks(keyword));

        Optional<Book> mostBorrowed = library.getMostBorrowedBook();

        if (mostBorrowed.isPresent()) {
            System.out.println("Most Borrowed: "
                    + mostBorrowed.get().getTitle());
        } else {
            System.out.println("No books available.");
        }

        sc.close();
    }
}
