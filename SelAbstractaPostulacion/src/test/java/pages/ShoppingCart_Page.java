package pages;

import framework.WebAutomator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class ShoppingCart_Page {
    private final WebAutomator automator;

    private final By opcionIphone = By.linkText("iPhone");
    private final By botonEliminar = By.xpath("//button[contains(@onclick, 'cart.remove') and contains(@class, 'btn-danger')]");

    private final By mensajeVacio = By.xpath("//*[@id='content']//p[contains(text(), 'Your shopping cart is empty!')]");

    public ShoppingCart_Page(WebAutomator automator) {
        this.automator = automator;
    }


    public void removerProducto() throws IOException {
       automator.jsClick(botonEliminar);





    }

    public String validarCarroVacio(WebAutomator automator) {
        return automator.esperarYObtenerTexto(mensajeVacio, "Mensaje de carrito vacío");

    }


          public boolean validarCarro(WebAutomator automator) {
            return automator.isElementClickable(opcionIphone, "Opción iPhone");
        }


}
