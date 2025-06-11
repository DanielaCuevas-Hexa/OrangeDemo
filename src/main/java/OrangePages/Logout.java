package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class Logout extends BasePage{

    private By profileDropDown = By.xpath("//span[@class='oxd-userdropdown-tab']");
    private By logoutBtn = By.xpath("//a[contains(text(), 'Logout') and contains(@role, 'menuitem')]");

    private WebDriver driver;
    private WebDriverWait wait;

    public Logout(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Metodo
    public void profileLogout() {
        // Esperar a que el icono del usuario esté visible y clickeable
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileDropDown));
        wait.until(ExpectedConditions.elementToBeClickable(profileDropDown)).click();

        // Esperar a que aparezca el botón de logout y hacer clic
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();

        System.out.println("Sesión cerrada correctamente.");
    }

}
