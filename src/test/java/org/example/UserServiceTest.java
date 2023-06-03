package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class UserServiceTest {
    @InjectMocks
    UserService userDatabase;
    User user;

    @BeforeEach //sets conditions before each test
    public void setUp() {
        userDatabase = new UserService(); //creates new instance of UserService and assigns it to userDatabase
        user = new User("sam", "sammyboy", "sammyboy@gmail.com"); //creates new user
        userDatabase.registerUser(user); //adds user to userDatabase
    }

    @AfterEach //sets conditions after each test
    public void tearDown() {
        userDatabase = null;
        user = null;
    }

    @DisplayName("Tests registerUser method to confirm new user information should allow registerUser to run successfully")
    @Test
    public void registerUserTestSuccessful() {
        User user2 = new User("rod", "rodney", "radknees@yahoo.com"); //creates new user object
        assertTrue(userDatabase.registerUser(user2)); //if successful, confirms new user was created
    }

    @DisplayName("Tests registerUser method to confirm that if the user already exists, we cannot add them again")
    @Test
    public void registerUserTestUnsuccessful() {
        assertFalse(userDatabase.registerUser(user));
    }

    @DisplayName("Tests registerUser method to confirm that a user account can be created with the same email since the original method checks for username only")
    @Test
    public void registerUserTestEmailAlreadyExists() {
        User existingEmail = new User("sammy", "sammy2", "sammyboy@gmail.com"); //creates new object with different username but same email
        assertTrue(userDatabase.registerUser(existingEmail)); //should return true since original method does not check for this condition
    }

    @DisplayName("Tests loginUser method to confirm existing username and password can login successfully")
    @Test
    public void loginUserTestSuccessful() {
        User loggedIn = userDatabase.loginUser("sam", "sammyboy");
        assertEquals(user, loggedIn);
    }

    @DisplayName("Tests loginUser to confirm an incorrect password would return null")
    @Test
    public void loginUserTestUnsuccessful() {
        assertNull(userDatabase.loginUser("sam", "eh"));
    }
    @DisplayName("Test loginUser for case sensitivity")
    @Test
    public void loginUserTestCaseSensitivity() {
        String username = "Sam"; //username with different case
        String password = "sammyboy"; //correct password
        User attempt = userDatabase.loginUser(username, password);
        assertNull(attempt); //should return null since the original case does not ignore case
    }

}