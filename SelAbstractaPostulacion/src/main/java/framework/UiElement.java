package framework;

import org.openqa.selenium.*;

import java.io.IOException;

public class UiElement {
    private WebDriver driver;
    private By locator;
    private WebAutomator automator;
    private String description;

    public UiElement(WebDriver driver, By locator, WebAutomator automator, String description) {
        this.driver = driver;
        this.locator = locator;
        this.automator = automator;
        this.description = description;
    }

    public void click() throws IOException {
        automator.click(locator, description);
    }

    public void type(String text) throws IOException {
        automator.type(locator, text, description);
    }

    public void scrollTo() throws IOException {
        automator.scrollToElement(locator, description);
    }

    public void moveTo() throws IOException {
        automator.moveToElement(locator, description);
    }

    public String getText() throws IOException {
        return automator.getText(locator, description);
    }

    public Void pressEnter()  {
        automator.pressEnter(locator);
        return null;
    }

}
