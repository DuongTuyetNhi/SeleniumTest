package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BookTicketPage extends GeneralPage {
    // Locators
    private final By departDateDropdown = By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/ol/li[1]/select");
    private final By departFromDropdown = By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/ol/li[2]/select");
    private final By arriveStationDropdown = By.xpath("//*[@id=\"ArriveStation\"]/select");
    private final By seatTypeDropdown = By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/ol/li[4]/select");
    private final By ticketAmountDropdown = By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/ol/li[5]/select");
    private final By bookTicketButton = By.xpath("//*[@id=\"content\"]/div[1]/form/fieldset/input");
    private final By _lblBookTicketSuccessMsg = By.xpath("//*[@id=\"content\"]/h1");

    // Methods
    public void selectDepartDate(String value) {
        WebElement departDateDropdownElement = Constant.WEBDRIVER.findElement(departDateDropdown);
        departDateDropdownElement.sendKeys(value);
    }

    public void selectDepartFrom(String departFrom) {
        WebElement departFromDropdownElement = Constant.WEBDRIVER.findElement(departFromDropdown);
        departFromDropdownElement.sendKeys(departFrom);
    }

    public void selectArriveStation(String arriveStation) {
        WebElement arriveStationDropdownElement = Constant.WEBDRIVER.findElement(arriveStationDropdown);
        arriveStationDropdownElement.sendKeys(arriveStation);
    }

    public void selectSeatType(String seatType) {
        WebElement seatTypeDropdownElement = Constant.WEBDRIVER.findElement(seatTypeDropdown);
        seatTypeDropdownElement.sendKeys(seatType);
    }

    public void selectTicketAmount(String amount) {
        WebElement ticketAmountDropdownElement = Constant.WEBDRIVER.findElement(ticketAmountDropdown);
        ticketAmountDropdownElement.sendKeys(amount);
    }

    public void clickBookTicketButton() {
        WebElement bookTicketButtonElement = Constant.WEBDRIVER.findElement(bookTicketButton);
        bookTicketButtonElement.click();
    }

    public WebElement getLblBookTicketSuccessMsg(){
        return Constant.WEBDRIVER.findElement(_lblBookTicketSuccessMsg);
    }

    //, String ticketAmount
    public void bookTicket(String selectDepartDate, String selectDepartFrom, String selectArriveStation, String seatType){
        selectDepartDate(selectDepartDate);
        selectDepartFrom(selectDepartFrom);
        selectArriveStation(selectArriveStation);
        selectSeatType(seatType);
//        selectTicketAmount(ticketAmount);
        clickBookTicketButton();

        // Chờ cho đến khi trang mới được tải hoàn tất
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, timeout);
        wait.until(ExpectedConditions.urlContains("SuccessPage.cshtml"));
    }

    // Phương thức kiểm tra thông tin vé đã đặt trên trang "SuccessPage"
    public void checkTicket(String selectDepartDate, String selectDepartFrom, String selectArriveStation, String seatType, String ticketAmount) {
        // Trích xuất thông tin từ các element trên trang "SuccessPage"
        WebElement departDateElement = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']//td[4]"));
        WebElement departFromElement = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']//td[1]"));
        WebElement arriveStationElement = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']//td[2]"));
        WebElement seatTypeElement = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']//td[3]"));
        WebElement ticketAmountElement = Constant.WEBDRIVER.findElement(By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow']//td[7]"));

        // Lấy text từ các element trên trang "SuccessPage"
        String actualDepartDate = departDateElement.getText();
        String actualDepartFrom = departFromElement.getText();
        String actualArriveStation = arriveStationElement.getText();
        String actualSeatType = seatTypeElement.getText();
        String actualTicketAmount = ticketAmountElement.getText();

        // So sánh thông tin vé trên trang "SuccessPage" với thông tin đã đặt
        Assert.assertEquals(actualDepartDate, selectDepartDate, "Depart Date does not match.");
        Assert.assertEquals(actualDepartFrom, selectDepartFrom, "Depart From station does not match.");
        Assert.assertEquals(actualArriveStation, selectArriveStation, "Arrive Station does not match.");
        Assert.assertEquals(actualSeatType, seatType, "Seat Type does not match.");
        Assert.assertEquals(actualTicketAmount, ticketAmount, "Ticket Amount does not match.");
    }

    public String getIdTicket(String selectDepartDate, String selectDepartFrom, String selectArriveStation, String seatType, String ticketAmount){
        selectDepartDate(selectDepartDate);
        selectDepartFrom(selectDepartFrom);
        selectArriveStation(selectArriveStation);
        selectSeatType(seatType);
        selectTicketAmount(ticketAmount);
        clickBookTicketButton();

        // Chờ cho đến khi trang mới được tải hoàn tất
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, timeout);
        wait.until(ExpectedConditions.urlContains("SuccessPage.cshtml"));

        String urlBookTicket = Constant.WEBDRIVER.getCurrentUrl();
        String idTicket = urlBookTicket.split("id=")[1];

        return "idTicket";
    }
}
