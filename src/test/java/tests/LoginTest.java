package tests;

import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void openLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(description = "TC-01: Successful login with valid credentials")
    public void testSuccessfulLogin() {
        loginPage.login("student", "Password123");

        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("logged-in-successfully"),
                "Expected redirect to /logged-in-successfully, but got: " + currentUrl
        );
    }

    @Test(description = "TC-02: Login fails with invalid username")
    public void testInvalidUsername() {
        loginPage.login("wrongUser", "Password123");

        String error = loginPage.getErrorMessage();
        Assert.assertNotNull(error, "Error message did not appear");
        Assert.assertTrue(
                error.contains("Your username is invalid!"),
                "Unexpected error message: " + error
        );
    }

    @Test(description = "TC-03: Login fails with invalid password")
    public void testInvalidPassword() {
        loginPage.login("student", "wrongPassword");

        String error = loginPage.getErrorMessage();
        Assert.assertNotNull(error, "Error message did not appear");
        Assert.assertTrue(
                error.contains("Your password is invalid!"),
                "Unexpected error message: " + error
        );
    }

    @Test(description = "TC-04: Login fails with empty username")
    public void testEmptyUsername() {
        loginPage.login("", "Password123");

        String error = loginPage.getErrorMessage();
        Assert.assertNotNull(error, "Error message did not appear for empty username");
        Assert.assertFalse(error.isEmpty(), "Error message should not be empty");
    }

    @Test(description = "TC-05: Login fails with empty password")
    public void testEmptyPassword() {
        loginPage.login("student", "");

        String error = loginPage.getErrorMessage();
        Assert.assertNotNull(error, "Error message did not appear for empty password");
        Assert.assertFalse(error.isEmpty(), "Error message should not be empty");
    }

    @Test(description = "TC-06: Login fails with both fields empty")
    public void testBothFieldsEmpty() {
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertNotNull(error, "Error message did not appear for empty fields");
        Assert.assertFalse(error.isEmpty(), "Error message should not be empty");
    }
}