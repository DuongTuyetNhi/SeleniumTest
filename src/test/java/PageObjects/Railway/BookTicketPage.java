package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
}
