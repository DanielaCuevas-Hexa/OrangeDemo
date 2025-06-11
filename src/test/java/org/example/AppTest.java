package org.example;

import OrangePages.*;
import ExtentReports.ReportsManager;
import ExtentReports.ScreenshotClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


//Tests
public class AppTest extends BasePage {

    private WebDriver driver;
    private Login loginPage;
    private Properties properties;
    private SearchUser pimSearchPage;

    private ExtentReports extent;
    private ExtentTest test;


    @BeforeTest
    public void setTest() throws IOException {
        //Reportes
        extent = ReportsManager.getInstance();
        //Iniciar firefox
        driver = new FirefoxDriver();
        //Inicializar loginPage
        loginPage = new Login(driver);
        //Cargar propiedades de config.properties
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        //Navegar a la URL de la app
        driver.get(properties.getProperty("loginPage.url"));
    }

    @Test
    public void testApp() throws IOException {
        //Reporte
        test = extent.createTest("GESTION DE EMPLEADO");

        try {
            //1. Hacer Login como Admin
            //Obtener credenciales de config.properties e intentar hacer login
            loginPage.inputUsername(properties.getProperty("loginPage.username"));
            loginPage.inputPassword(properties.getProperty("loginPage.password"));
            //Screenshot login
            String loginScreenshot = ScreenshotClass.captureScreenshot(driver, "LoginCaptura");
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Esperar que el dashboard cargue completamente, usando un elemento clave -
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-layout-context']")));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            test.pass("Login con usuario Admin exitoso").addScreenCaptureFromPath(loginScreenshot);


            //2. Crear Un Usuario (Empleado)
            AddUser addUserPIM = new AddUser(driver);
            addUserPIM.addEmployee(properties);
            String addEmployeeSS = ScreenshotClass.captureScreenshot(driver, "EmpleadoCaptura");
            test.pass("Empleado agregado exitosamente").addScreenCaptureFromPath(addEmployeeSS);


            //2.1 Buscar empleado
            pimSearchPage = new SearchUser(driver);
            boolean found = pimSearchPage.searchEmployeeByName("Maria");
            Assert.assertTrue(found, "El empleado fue encontrado en los resultados");
            String searchEmployeeSS = ScreenshotClass.captureScreenshot(driver, "BuscarEmpleadoCaptura");
            test.pass("Empleado encontrado exitosamente").addScreenCaptureFromPath(searchEmployeeSS);
            //2.2 Hacer Logout
            Logout userLogout = new Logout(driver);
            userLogout.profileLogout();
            test.pass("Logout con nuevo usuario exitoso");


            //3. Hacer Login con ese usuario
            //Obtener credenciales de config.properties e intentar hacer login
            loginPage.inputUsername(properties.getProperty("pim.username"));
            loginPage.inputPassword(properties.getProperty("pim.password"));
            String userLoginSS = ScreenshotClass.captureScreenshot(driver, "LoginUsuarioCaptura");
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Nueva espera explícita
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.urlContains("dashboard"));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            test.pass("Login con usuario creado exitoso").addScreenCaptureFromPath(userLoginSS);


            //4. Ir a Time y agregar un timesheet
            AddTimesheet addUserTime = new AddTimesheet(driver);
            addUserTime.fillTimesheetAndSubmit();
            String addTimeSS = ScreenshotClass.captureScreenshot(driver, "TimesheetCaptura");
            test.pass("Timesheet agregado a empleado exitoso").addScreenCaptureFromPath(addTimeSS);

            //5. Hacer Login como Admin
            //Logout
            userLogout.profileLogout();
            //Login Admin
            loginPage.inputUsername(properties.getProperty("loginPage.username"));
            loginPage.inputPassword(properties.getProperty("loginPage.password"));
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Esperar que el dashboard cargue completamente, usando un elemento clave -
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-layout-context']")));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            test.pass("Logout con usuario admin exitoso");


            //6. Ir a Time y aprobar el timesheet del empleado.
            ApproveTimesheet approveTimesheet = new ApproveTimesheet(driver, properties);
            boolean approved = approveTimesheet.searchAndApprove();
            Assert.assertTrue(approved, "Error, No se ha aprobaddo el timesheet del empleado");
            String approveTimeSS = ScreenshotClass.captureScreenshot(driver, "TimesheetApprovedCaptura");
            test.pass("Timesheet agregado a empleado exitoso").addScreenCaptureFromPath(approveTimeSS);


            //7. Hacer Login con ese usuario
            //Logout
            userLogout.profileLogout();
            //Login como usuario
            loginPage.inputUsername(properties.getProperty("pim.username"));
            loginPage.inputPassword(properties.getProperty("pim.password"));
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Esperar que el dashboard cargue completamente, usando un elemento clave -
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-layout-context']")));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            test.pass("Login con usuario nuevo exitoso");


            //8. Validar timesheet aprobado
            boolean validate = approveTimesheet.verifyTimesheetApproved();
            Assert.assertTrue(validate, "Error, el timesheet no esta aprobado");
            String validateTimeSS = ScreenshotClass.captureScreenshot(driver, "TimesheetValidatedCaptura");
            test.pass("Timesheet validado exitoso").addScreenCaptureFromPath(validateTimeSS);


        }catch (Exception e) {
            String screenshotPath = ScreenshotClass.captureScreenshot(driver, "TestFailure");
            test.fail("Test fallido: " + e.getMessage()).addScreenCaptureFromPath(screenshotPath);
            throw e;
        }

    }

    @AfterTest
    public void closeTest() {
        driver.close();
        quiteDriver();
        extent.flush(); //Lanza el reporte
    }


}
