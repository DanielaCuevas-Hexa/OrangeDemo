package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchUser extends BasePage {

    private By pimSection = By.xpath("//span[text()='PIM']");
    private By employeeNameB = By.xpath("//input[@placeholder='Type for hints...']");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By result= By.xpath("//div[@class='oxd-table orangehrm-employee-list']//div[contains(text(), 'Maria')]");

    private WebDriver driver;
    private WebDriverWait wait;

    public SearchUser(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Metodo
    public boolean searchEmployeeByName(String employeeName) {
        clickElement(pimSection);
        enterText(employeeNameB, employeeName);

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();
        searchBtn.click();

        // XPath dinámico basado en el nombre buscado
        By dynamicResult = By.xpath("//div[@class='oxd-table orangehrm-employee-list']//div[contains(text(), '" + employeeName + "')]");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dynamicResult));
            return true;
        } catch (TimeoutException e) {
            System.out.println("No se encontró el empleado con nombre: " + employeeName);
            return false;
        }
    }

}
