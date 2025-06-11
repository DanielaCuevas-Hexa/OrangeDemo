package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends BasePage{

    //Localizadores con xPath
    private By writeUsername = By.xpath("//input[@name='username']");
    private By writePassword = By.xpath("//input[@name='password']");
    private By loginButton = By.xpath("//button[@type='submit']");

    public Login(WebDriver driver) {
        super(driver); //Pasar el driver al constructor de BasePage
    }

    //Metodos Login
    public void inputUsername(String username) {
        waitForElementToBeVisible(writeUsername);
        getDriver().findElement(writeUsername).sendKeys(username);
    }
    public void inputPassword(String password) {
        waitForElementToBeVisible(writePassword);
        getDriver().findElement(writePassword).sendKeys(password);
    }
    public void clickBtnLogin() {
        waitForElementToBeVisible(loginButton);
        getDriver().findElement(loginButton).click();
    }
}

