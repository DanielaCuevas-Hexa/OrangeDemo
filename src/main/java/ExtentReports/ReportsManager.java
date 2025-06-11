package ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportsManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("ReportResults/Report.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}
