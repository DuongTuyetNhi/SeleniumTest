package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyTicketPage extends GeneralPage{
    private final By _lblManageTicket = By.xpath("//*[@id=\"content\"]/h1[text()='Manage ticket']");
    public WebElement getLblManageTicket(){
        return Constant.WEBDRIVER.findElement(_lblManageTicket);
    }

    private final By ticketTable = By.xpath("//table[@class='MyTable']//tr[@class='OddRow' or @class='EvenRow']");
    private final By cancelButtons = By.xpath("//input[@type='button'][@value='Cancel']");
    private final By okButton = By.xpath("//input[@type='button'][@value='OK']");

    public boolean isTicketPresent() {
        List<WebElement> tickets = Constant.WEBDRIVER.findElements(ticketTable);
        return !tickets.isEmpty();
    }

    public int getNumberOfTickets() {
        List<WebElement> tickets = Constant.WEBDRIVER.findElements(ticketTable);
        return tickets.size();
    }

    public void cancelTicketByIndex(int index) {
        List<WebElement> cancelBtns = Constant.WEBDRIVER.findElements(cancelButtons);
        if (index >= 1 && index <= cancelBtns.size()) {
            cancelBtns.get(index - 1).click(); // Click on the "Cancel" button of the specified ticket
        } else {
            throw new IndexOutOfBoundsException("Invalid ticket index.");
        }
    }

    public void confirmCancel() {
        Alert alert = Constant.WEBDRIVER.switchTo().alert();
        alert.accept();
    }
}
