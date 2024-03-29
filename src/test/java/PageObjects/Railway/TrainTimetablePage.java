package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TrainTimetablePage extends GeneralPage {

    public final By allRows = By.xpath("//table[@class='MyTable WideTable']//tr[@class='OddRow' or @class='EvenRow']");
    public  List<WebElement> getAllRows() {
        return Constant.WEBDRIVER.findElements(allRows);
    }

//    public static List<WebElement> getAllRows(WebDriver driver) {
//        return driver.findElements(allRows);
//    }
}
