package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookServiceDatabase;
    private User user;
    private Book book1;
    private Book book2;


    @BeforeEach
    public void setUp() {
        user = new User("sam", "sammyboy", "sammyboy@gmail.com");

        book1 = new Book("Mine", "sam", "drama", 12);
        book2 = new Book("Mine 2", "sam", "children", 7);

        bookServiceDatabase = new BookService(); //assign instance of BookService class to bookServiceDatabase
        bookServiceDatabase.addBook(book1); //adds book
        bookServiceDatabase.addBook(book2); //adds book
    }

    @AfterEach
    public void tearDown() {
        user = null;
        bookServiceDatabase = null;
    }

    @DisplayName("Tests the searchBook method by using a keyword that's in both book objects")
    @Test
    public void searchBookTestKeyword() {
        String keyword = "sam"; //set up keyword
        List<Book> result = bookServiceDatabase.searchBook(keyword); //calls searchBook method to find keyword, "sam"
        assertEquals(Arrays.asList(book1, book2), result); //if this works correctly, we should see that lists contains book1 and book2
    }

    @DisplayName("Tests the searchBook method with a keyword that is not available in either book objects")
    @Test
    public void searchBookTestNoMatch() {
        String keyword = "match";
        List<Book> result = bookServiceDatabase.searchBook(keyword);
        assertTrue(result.isEmpty()); //since "match" is in neither book object, the resulting list should be empty
    }

    @DisplayName("Test the searchBook method for when a keyword is an empty string")
    @Test
    public void searchBookTestEmptyString() {
        String keyword = "";
        List<Book> result = bookServiceDatabase.searchBook(keyword);
        assertFalse(result.isEmpty());
    }

    @DisplayName("Tests the purchaseBook method to see confirm purchased book is in the database")
    @Test
    public void purchaseBookTestSuccessful() {
        boolean exists = bookServiceDatabase.purchaseBook(user, book1); //since book1 was added to the database earlier
        assertTrue(exists); //we should assertTrue
    }

    @DisplayName("Test the purchaseBook method that confirms the book is not in the database")
    @Test
    public void testPurchaseBook_BookDoesNotExist() {
        Book newBook = new Book("Not Mine 2", "Not sam", "mystery", 1); //create new book object here to confirm this does not exist in the database

        boolean nonexistent = bookServiceDatabase.purchaseBook(user, newBook);
        assertFalse(nonexistent); //since the book object does not exist int the database, this should assertFalse
    }

    @DisplayName("Test the purchaseBook method that confirms a null book cannot be purchase")
    @Test
    public void testPurchaseBook_NullBook() {
        boolean nah = bookServiceDatabase.purchaseBook(user, null);
        assertFalse(nah); //since a null book cannot exist in the database, this should assertFalse
    }
}