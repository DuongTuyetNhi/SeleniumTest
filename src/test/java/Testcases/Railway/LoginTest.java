package Testcases.Railway;

import Common.Common.Utilities;
import Common.Constant.Constant;
import PageObjects.Railway.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

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

        boolean isLoginPageDisplayed = true;
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

        registerPage.getTxtEmail().sendKeys("tuyetnhiduong120@gmail.com");
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

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        ForgotPasswordPage forgotPasswordPage = homePage.gotoForgotPasswordPage();

        forgotPasswordPage.getTxtEmailAddress().sendKeys("duongtuyetnhi1608@gmail.com");
        forgotPasswordPage.getBtnSendInstructors().click();

        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        boolean managePasswordIsDisplay = true;
        try {
            WebElement changePasswordButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Change Password']"));
            managePasswordIsDisplay = changePasswordButton.isDisplayed();
        } catch (NoSuchElementException e) {
            managePasswordIsDisplay = false;
        }

        Assert.assertTrue(managePasswordIsDisplay, "Could not reset password. Please correct the errors and try again.");

        changePasswordPage.getTxtCurrentPassword().sendKeys(Constant.PASSWORD);
        changePasswordPage.getTxtNewPassword().sendKeys("123Conmeo");
        changePasswordPage.getTxtConfirmPassword().sendKeys("123Conmeo");
        changePasswordPage.getBtnChangePassword().click();

        String actualSuccessMsg = changePasswordPage.getLblChangePasswordErrorMsg().getText();
        String expectedSuccessMsg = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");

        // Bước 1: Mở trang web QA Railway
        HomePage homePage = new HomePage();
        homePage.open();

        // Bước 2: Đăng nhập bằng tài khoản hợp lệ
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Bước 3: Chuyển đến trang đặt vé
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Bước 4: Chọn một "Depart date" từ danh sách
        Random random = new Random();
        int randomDateIndex = random.nextInt(28) + 3; // Random từ 3 đến 30
        bookTicketPage.selectDepartDate(String.valueOf(randomDateIndex));

        // Bước 5: Chọn "Sài Gòn" cho "Depart from" và "Nha Trang" cho "Arrive at"
        bookTicketPage.selectDepartFrom("Sài Gòn");
        bookTicketPage.selectArriveStation("Nha Trang");

        // Bước 6: Chọn "Soft bed with air conditioner" cho "Seat type"
        bookTicketPage.selectSeatType("Soft bed with air conditioner");

        // Bước 7: Chọn "1" cho "Ticket amount"
        bookTicketPage.selectTicketAmount("1");

        // Bước 8: Nhấp vào nút "Book ticket"
        bookTicketPage.clickBookTicketButton();

        // Chờ cho đến khi trang mới được tải hoàn tất
        Duration timeout = Duration.ofSeconds(10); // 10 giây
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, timeout);
        wait.until(ExpectedConditions.urlContains("SuccessPage.cshtml"));

        // Lấy thông báo thành công từ tiêu đề trang mới
        String successMessage = Constant.WEBDRIVER.findElement(By.xpath("//h1")).getText();

        // Kiểm tra xem thông báo thành công có hiển thị đúng không
        String expectedSuccessMessage = "Ticket booked successfully!";
        Assert.assertEquals(successMessage, expectedSuccessMessage, "Success message is not displayed as expected.");
    }


}
