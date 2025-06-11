package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    private WebDriver driver;
    private WebDriverWait wait;

    //Constructor
    public BasePage() {
        //Inicializar browser firefox
        driver = new FirefoxDriver();
    }

    //Constructor para herencia
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setDriver() {
        //Iniciar el WebDriver de firefox
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    public void quiteDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    //Para el escenario de search, para esperar elementos
    public void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    //Metodos de interaccion
    public void clickElement(By locator) {
        waitForElementToBeClickable(locator);
        driver.findElement(locator).click();
    }
    public void enterText(By locator, String text) {
        waitForElementToBeVisible(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    public void waitForLoaderToDisappear() {
        By loader = By.cssSelector("div.oxd-form-loader");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
    }

}
