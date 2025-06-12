package tests;

import framework.WebAutomator;
import framework.utils.Config;
import framework.utils.PdfReportGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.time.LocalDate;

public class TestBase {
    protected WebDriver driver;
    protected WebAutomator automator;
    protected PdfReportGenerator pdfReport;

    @BeforeClass
    public void setUp() throws Exception {
        String navegador = Config.navegador();
        switch (navegador) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("w3c", true); // <- Desactiva DevTools Protocol (CDP)
                driver = new ChromeDriver(options);
                break;
        }



        if (Config.generarEvidencia()) {
            String testClassName = this.getClass().getSimpleName();

            // Obtener fecha y hora formateadas: yyyy_MM_dd_HH_mm_ss
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

            String outputDir = "output";
            File folder = new File(outputDir);
            if (!folder.exists()) folder.mkdirs();

            String pdfPath = outputDir + "/" + testClassName + "_" + timestamp + ".pdf";

            pdfReport = new PdfReportGenerator(pdfPath);
            automator = new WebAutomator(driver, pdfReport);
        } else {
            automator = new WebAutomator(driver, null);
        }

    }

    @AfterClass
    public void tearDown() {
        try {
            if (pdfReport != null) {
                pdfReport.closeReport();
            }
        } catch (Exception e) {
            System.err.println("Error cerrando el reporte: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit(); // esto se ejecuta pase lo que pase
            }
        }
    }

}
