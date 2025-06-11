package OrangePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddTimesheet extends BasePage{

    private WebDriver driver;
    private WebDriverWait wait;

    private By timeSection = By.xpath("//span[text()='Time']");
    private By calendar = By.xpath("//i[@class='oxd-icon bi-calendar oxd-date-input-icon']");
    private By leftBtn = By.xpath("//button[@class='oxd-icon-button orangehrm-timeperiod-icon --prev']//i[@class='oxd-icon bi-chevron-left']");
    private By editBtn = By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--ghost']");
    private By projectF = By.xpath("//input[@placeholder='Type for hints...']");
    private By activityF = By.xpath("(//div[contains(@class, 'oxd-select-text')])[2]");
    private By firstDropdown = By.xpath("//div[@role='listbox']//span[1]");
    private By saveBtn = By.xpath("//button[@type='submit']");
    private By submitBtn = By.xpath("//form//button[.=' Submit ']");
    private By statusText = By.xpath("//td[@class='orangehrm-timesheet-table-body-cell --center --freeze-right --highlight-2']");

    public AddTimesheet(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Metodo
    public boolean fillTimesheetAndSubmit() {
        try {
            // Paso 1: Click en la sección Time y esperar a que el botón Edit esté visible
            clickElement(timeSection);
            wait.until(ExpectedConditions.elementToBeClickable(editBtn));
            clickElement(editBtn);

            // Paso 2: Ingresar "Apache" en Project y seleccionar la primera opción
            wait.until(ExpectedConditions.visibilityOfElementLocated(projectF));
            enterText(projectF, "Apache");
            wait.until(ExpectedConditions.elementToBeClickable(firstDropdown));
            clickElement(firstDropdown);

            // Paso 3: Click en el campo Activity y seleccionar la primera opción
            wait.until(ExpectedConditions.elementToBeClickable(activityF));
            clickElement(activityF);
            wait.until(ExpectedConditions.elementToBeClickable(firstDropdown));
            clickElement(firstDropdown);

            // Paso 4: Ingresar 8 horas de lunes a viernes
            for (int i = 1; i <= 5; i++) {
                By day = By.xpath("(//input[@class='oxd-input oxd-input--active'])[" + (i + 1) + "]");
                wait.until(ExpectedConditions.visibilityOfElementLocated(day));
                enterText(day, "8");
            }

            // Paso 5: Guardar y Enviar
            wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
            clickElement(saveBtn);

            wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
            clickElement(submitBtn);

            // Paso 6: Verificar que el estado "Submitted" sea visible
            return wait.until(ExpectedConditions.visibilityOfElementLocated(statusText)).isDisplayed();

        } catch (Exception e) {
            System.out.println("Error durante el llenado y guardado del timesheet: " + e.getMessage());
            return false;
        }
    }

}
