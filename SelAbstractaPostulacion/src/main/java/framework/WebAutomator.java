package framework;

import framework.utils.PdfReportGenerator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class WebAutomator {
    private WebDriver driver;
    private WebDriverWait wait;
    private PdfReportGenerator pdfReport;

    public WebAutomator(WebDriver driver, PdfReportGenerator pdfReport) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.pdfReport = pdfReport;
    }

    public void openUrl(String url, String descripcion) {
        driver.get(url);
        if (pdfReport != null) {
            pdfReport.addStep("Abriendo URL: " + descripcion + " (" + url + ")", driver);
        }
    }


    public UiElement find(By locator, String description) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return new UiElement(driver, locator, this, description);
    }



    public void click(By locator, String description) throws IOException {
        int intentos = 5;
        while (intentos > 0) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();

                if (pdfReport != null)
                    pdfReport.addStep("Haciendo clic en: " + description, driver);
                return;
            } catch (StaleElementReferenceException e) {
                intentos--;
                if (intentos == 0) throw e;
            }
        }
    }


    public void type(By locator, String text, String description) throws IOException {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        wait.until(ExpectedConditions.attributeToBe(locator, "value", text));

    }

    public void scrollToElement(By locator, String description) throws IOException {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public void moveToElement(By locator, String description) throws IOException {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Actions(driver).moveToElement(element).perform();

    }

    public String getText(By locator, String description) throws IOException {
        String value = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        return value;
    }

    public String getTitle() {
        return driver.getTitle();
    }
    public void pressEnter(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(Keys.ENTER);
       }

    public WebDriver getDriver() {
        return this.driver;
    }

    public boolean isElementClickable(By locator, String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            if (pdfReport != null)
                pdfReport.addStep("Validación: '" + description + "' está presente y clickeable", driver);
            return true;
        } catch (TimeoutException e) {
            System.out.println(description + " no está clickeable.");
            return false;
        } catch (Exception e) {
            System.out.println("Error inesperado validando " + description + ": " + e.getMessage());
            return false;
        }
    }

    public void clickConReintento(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        int intentos = 3;

        while (intentos > 0) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                intentos--;
                if (intentos == 0) {
                    throw new RuntimeException("No se pudo hacer clic por referencia obsoleta: " + locator, e);
                }
            }
        }
    }
    public void jsClick(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        if (pdfReport != null)
            pdfReport.addStep("Click por JavaScript en: " + locator.toString(), driver);
    }


    public String esperarYObtenerTexto(By locator, String descripcion) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Elemento visible: " + descripcion);
            return elemento.getText();
        } catch (TimeoutException e) {
            System.out.println("No se encontró el elemento: " + descripcion);
            throw e;
        }
    }


}
