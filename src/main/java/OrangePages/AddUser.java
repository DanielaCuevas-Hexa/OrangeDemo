package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AddUser extends BasePage{

    //Add Employee
    private By pimSection = By.xpath("//span[text()='PIM']");
    private By addBtn = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--secondary']");
    private By firstName = By.xpath("//input[@name='firstName']");
    private By middleName = By.xpath("//input[@name='middleName']");
    private By lastNameF = By.xpath("//input[@name='lastName']");
    private By employeeId = By.xpath("//label[text()='Employee Id']/following::input[1]");

    //Create Login Details
    private By loginDetailsBtn = By.xpath("//span[contains(@class,'oxd-switch-input')]");
    private By loginUsername = By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]");
    private By loginPassword = By.xpath("(//input[@type='password'])[1]");
    private By confirmPassword = By.xpath("(//input[@type='password'])[2]");
    private By saveBtn = By.xpath("//button[@type='submit']");

    private WebDriver driver;
    private WebDriverWait wait;

    public AddUser(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Metodo
    public void addEmployee(Properties props) throws IOException {
        // Leer datos del archivo
        String name = props.getProperty("employee.name");
        String middle = props.getProperty("employee.middle");
        String last = props.getProperty("employee.last");
        String id = props.getProperty("employee.id");

        String username = props.getProperty("pim.username");
        String password = props.getProperty("pim.password");

        // Navegar y llenar formulario
        clickElement(pimSection);
        clickElement(addBtn);

        // Esperar que el formulario esté listo
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        enterText(firstName, name);
        enterText(middleName, middle);
        enterText(lastNameF, last);
        enterText(employeeId, id);

        // Esperar que desaparezca el loader
        waitForLoaderToDisappear();
        // Hacer clic en el switch de "Create Login Details"
        clickElement(loginDetailsBtn);

        // Llenar los campos de login
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUsername));
        enterText(loginUsername, username);
        enterText(loginPassword, password);
        enterText(confirmPassword, password);

        // Guardar
        clickElement(saveBtn);
        // Espera a que cargue la página "Personal Details"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']")));

    }

}
