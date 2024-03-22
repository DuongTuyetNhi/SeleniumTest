package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterPage extends GeneralPage{
    private final By _txtEmail = By.xpath("//input[@id='email']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By _txtPidNumber = By.xpath("//input[@id='pid']");
    private final By _btnRegister = By.xpath("//input[@value='Register']");
//    private final By _lblRegisterErrorMsg = By.xpath("//p[@class='message error']");
    private final By _lblRegisterErrorMsg = By.xpath("//*[@id=\"content\"]/p[2]");
    private final By _lblPasswordLengthErrorMsg = By.xpath("//*[@id=\"RegisterForm\"]/fieldset/ol/li[2]/label[2]");
    private final By _lblIdLengthErrorMsg = By.xpath("//*[@id=\"RegisterForm\"]/fieldset/ol/li[4]/label[2]");

    //Elements
    public WebElement getTxtEmail(){
        return Constant.WEBDRIVER.findElement(_txtEmail);
    }
    public WebElement getTxtPassword(){
        return Constant.WEBDRIVER.findElement(_txtPassword);
    }
    public WebElement getTxtConfirmPassword(){
        return Constant.WEBDRIVER.findElement(_txtConfirmPassword);
    }
    public WebElement getTxtPidNumber(){
        return Constant.WEBDRIVER.findElement(_txtPidNumber);
    }
    public WebElement getBtnRegister(){
        return Constant.WEBDRIVER.findElement(_btnRegister);
    }
    public WebElement getLblRegisterErrorMsg(){
        return Constant.WEBDRIVER.findElement(_lblRegisterErrorMsg);
    }
    //TC10
    public WebElement getLblPasswordLengthErrorMsg(){
        return Constant.WEBDRIVER.findElement(_lblPasswordLengthErrorMsg);
    }
    public WebElement getLblIdLengthErrorMsg(){
        return Constant.WEBDRIVER.findElement(_lblIdLengthErrorMsg);
    }


    //Methods: register method returns HomePage
    public HomePage register(String email, String password, String confirmPassword, String pidNumber){
        //Submit login credentials: there are LoginPage's WebElements
        this.getTxtEmail().sendKeys(email);
        this.getTxtPassword().sendKeys(password);
        this.getTxtConfirmPassword().sendKeys(confirmPassword);
        this.getTxtPidNumber().sendKeys(pidNumber);

        this.getBtnRegister().click();

        //Land on Home page
        return new HomePage();
    }
}
