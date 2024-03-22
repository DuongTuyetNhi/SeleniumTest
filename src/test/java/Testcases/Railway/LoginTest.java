package Testcases.Railway;

import Common.Common.Utilities;
import Common.Constant.Constant;
import PageObjects.Railway.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class LoginTest {
    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC01(){
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC02(){
        System.out.println("TC02 - User can't login with blank 'Username' textbox");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.getTxtUsername().sendKeys("");
        loginPage.getTxtPassword().sendKeys(Constant.PASSWORD);
        loginPage.getBtnLogin().click();

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC03(){
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
        loginPage.getTxtPassword().sendKeys("invalid password");
        loginPage.getBtnLogin().click();

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC04() {
        System.out.println("TC04 - Login page displays when un-logged User clicks on 'Book ticket' tab");
        HomePage homePage = new HomePage();
        homePage.open();

        if (!homePage.isUserLoggedIn()) {
            homePage.gotoLoginPage();
        }
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        boolean isLoginPageDisplayed = false;
        try {
            WebElement usernameTextbox = Constant.WEBDRIVER.findElement(By.id("username"));
            WebElement passwordTextbox = Constant.WEBDRIVER.findElement(By.id("password"));
            WebElement loginButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='login']"));

            isLoginPageDisplayed = usernameTextbox.isDisplayed() && passwordTextbox.isDisplayed() && loginButton.isDisplayed();
        } catch (NoSuchElementException e) {
            isLoginPageDisplayed = false;
        }

        Assert.assertTrue(isLoginPageDisplayed, "Login page is not displayed when un-logged User clicks on 'Book ticket' tab");
    }

    @Test
    public void TC05(){
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        for(int i=0; i<4; i++){
            loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
            loginPage.getTxtPassword().sendKeys("invalid password");
            loginPage.getBtnLogin().click();
        }

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC06(){
        System.out.println("TC06 - Additional pages display once user logged in");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
        loginPage.getTxtPassword().sendKeys(Constant.PASSWORD);
        loginPage.getBtnLogin().click();

        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();
        boolean ticketPageIsDisplay = true;
        try {
            WebElement _lblManageTicket = Constant.WEBDRIVER.findElement(By.xpath("//*[@id=\"content\"]/h1[text()='Manage ticket']"));
            ticketPageIsDisplay = _lblManageTicket.isDisplayed();
        } catch (NoSuchElementException e) {
            ticketPageIsDisplay = false;
        }
        Assert.assertTrue(ticketPageIsDisplay, "My ticket page is not displayed when user click My ticket tab");



        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        boolean managePasswordIsDisplay = true;
        try {
            WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Change Password']"));
            managePasswordIsDisplay = changePasswordButton.isDisplayed();
        } catch (NoSuchElementException e) {
            managePasswordIsDisplay = false;
        }

        Assert.assertTrue(managePasswordIsDisplay, "Change password page is not displayed when user click Change password tab");
    }

    @Test
    public void TC07(){
        System.out.println("TC07 - User can create new account");
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        registerPage.getTxtEmail().sendKeys("tuyetnhiduong123@gmail.com");
        registerPage.getTxtPassword().sendKeys("123Conmeo");
        registerPage.getTxtConfirmPassword().sendKeys("123Conmeo");
        registerPage.getTxtPidNumber().sendKeys("123456789");
        registerPage.getBtnRegister().click();

        String actualSuccessMsg = registerPage.getLblRegisterErrorMsg().getText();
        String expectedSuccessMsg = "Thank you for registering your account";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");
    }

    @Test
    public void TC08(){
        System.out.println("TC08 - User can't login with an account hasn't been activated");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        loginPage.getTxtUsername().sendKeys("invaliduser@gmail.com");
        loginPage.getTxtPassword().sendKeys("invalid password");
        loginPage.getBtnLogin().click();

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC09(){
        System.out.println("TC09 - User can change password");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.getTxtUsername().sendKeys(Constant.USERNAME);
        loginPage.getTxtPassword().sendKeys(Constant.PASSWORD);
        loginPage.getBtnLogin().click();


        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        changePasswordPage.getTxtCurrentPassword().sendKeys(Constant.PASSWORD);
        changePasswordPage.getTxtNewPassword().sendKeys("123Conmeo");
        changePasswordPage.getTxtConfirmPassword().sendKeys("123Conmeo");
        changePasswordPage.getBtnChangePassword().click();

        String actualSuccessMsg = changePasswordPage.getLblChangePasswordErrorMsg().getText();
        String expectedSuccessMsg = "Your password has been updated!"; //phai co dau ! sau
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");
    }

    @Test
    public void TC10(){
        System.out.println("TC10 - User can't create account with Confirm password is not the same with Password");
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        registerPage.getTxtEmail().sendKeys("tuyetnhiduong123@gmail.com");
        registerPage.getTxtPassword().sendKeys("123Conmeo");
        registerPage.getTxtConfirmPassword().sendKeys("123Convit");
        registerPage.getTxtPidNumber().sendKeys("123456789");
        registerPage.getBtnRegister().click();

        String actualSuccessMsg = registerPage.getLblRegisterErrorMsg().getText();
        String expectedSuccessMsg = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");
    }

    @Test
    public void TC11(){
        System.out.println("TC11 - User can't create account while password and PID fields are empty");
        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        registerPage.getTxtEmail().sendKeys("nhikiki123@gmail.com");
        registerPage.getTxtPassword().sendKeys("");
        registerPage.getTxtConfirmPassword().sendKeys("");
        registerPage.getTxtPidNumber().sendKeys("");
        registerPage.getBtnRegister().click();

        String actualSuccessMsg = registerPage.getLblRegisterErrorMsg().getText();
        String expectedSuccessMsg = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message is not displayed as expected");

        String actualPasswordLengthMsg = registerPage.getLblPasswordLengthErrorMsg().getText();
        String expectedPasswordLengthMsg = "Invalid password length.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Invalid password length message is not displayed as expected");

        String actualIdLengthMsg = registerPage.getLblIdLengthErrorMsg().getText();
        String expectedIdLengthMsg = "Invalid ID length.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Invalid ID length message is not displayed as expected");
    }


}
