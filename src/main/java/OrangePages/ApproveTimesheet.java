package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Properties;

public class ApproveTimesheet extends BasePage{

    private WebDriver driver;
    private WebDriverWait wait;
    private Properties properties;

    public By timeSection = By.xpath("//span[text()='Time']");
    public By employeeName = By.xpath("//input[@placeholder='Type for hints...']");
    public By firstEmployee = By.xpath("(//div[@role='option'])[1]");
    public By viewBtn = By.xpath("(//button[@type='submit' and contains(@class, 'oxd-button')])[1]");
    public By commentBox = By.xpath("//textarea[@placeholder='Type here ...']");
    public By approveBtn = By.xpath("//button[contains(@class, 'oxd-button--success')]");
    public By statusText = By.xpath("//p[@class='oxd-text oxd-text--p oxd-text--subtitle-2']");

    public ApproveTimesheet(WebDriver driver, Properties properties) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.properties = properties;
    }

    //Metodo
    public boolean searchAndApprove() {
        try {
            // Paso 1: Click en la sección Time y esperar a que el boton view esté visible
            clickElement(timeSection);
            wait.until(ExpectedConditions.elementToBeClickable(viewBtn));

            // Paso 2: Ingresar el employee.name del archivo .properties en el campo de employeeName y espere 2 segundos y luego seleccione la primer opcion (firstEmployee)
            wait.until(ExpectedConditions.visibilityOfElementLocated(employeeName));
            enterText(employeeName, properties.getProperty("employee.name"));
            Thread.sleep(2000);
            clickElement(firstEmployee);

            //Paso 3: Dar click en el boton view
            wait.until(ExpectedConditions.elementToBeClickable(viewBtn));
            clickElement(viewBtn);

            //Paso 4: Click en el comment box y ahi mismo ingresar la palabra "Approved"
            wait.until(ExpectedConditions.elementToBeClickable(commentBox));
            clickElement(commentBox);
            enterText(commentBox, "Approved");

            //Paso 5: Click en el boton approved y esperar que cargue la pagina y el status text diga approved
            wait.until(ExpectedConditions.elementToBeClickable(approveBtn));
            clickElement(approveBtn);

            // Paso 6: Verificar que el status diga "Approved"
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(statusText, "Approved"));
        } catch (Exception e) {
            System.out.println("Error durante el llenado y aprobado del timesheet: " + e.getMessage());
            return false;
        }
    }

    //Metodo para verificar si el timesheet fue aprobado
    public boolean verifyTimesheetApproved() {
        try {
            clickElement(timeSection);
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(statusText, "Approved"));
        } catch (Exception e) {
            System.out.println("Error al verificar el estado del timesheet: " + e.getMessage());
            return false;
        }
    }
}

