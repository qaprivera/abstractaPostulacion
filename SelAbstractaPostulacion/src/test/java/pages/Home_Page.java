package pages;

import framework.UiElement;
import framework.WebAutomator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;

public class Home_Page {
    private final WebAutomator automator;

    private final By busquedaInput = By.name("search");
    private final By opcionIphone = By.linkText("iPhone");
    private final String producto ="iPhone";

    private final By agregarAlCarro = By.id("button-cart");
    private final By ingresandoAlcarro = By.id("cart-total");
    private final By verCompra = By.linkText("View Cart");


    public Home_Page(WebAutomator automator) {
        this.automator = automator;
    }

    public void store(WebAutomator automator) {
        automator.openUrl("http://opencart.abstracta.us/", "Página de inicio ");
        String tituloEsperado = "Your Store";
        String tituloActual = automator.getTitle();
        Assert.assertEquals(tituloActual, tituloEsperado, "El título de la pagina no coincide ,se esperaba" + tituloEsperado +"");


    }
    public void buscarProducto(WebAutomator automator) throws IOException {
        automator.find(busquedaInput,"Buscar caja de texto search");
        automator.type(busquedaInput, producto, "Ingresar texto " + producto +" en campo de busqueda");
        automator.pressEnter(busquedaInput);

    }



    public void seleccionarOpcion() throws IOException {
        automator.scrollToElement(opcionIphone,"Haciendo Scroll hasta el elemento"+opcionIphone);
        automator.find(opcionIphone,"Haciendo click en Iphone").click();

    }

    public void agregarProducto() throws IOException {
        automator.find(agregarAlCarro,"Buscando el elemento :"+agregarAlCarro).click();


    }

    public void ingresarCarro() throws IOException {
        automator.find(ingresandoAlcarro,"Haciendo click en  el elemento :"+agregarAlCarro).click();

    }

    public void verCarro(WebAutomator automator) throws IOException {
        WebDriver driver = automator.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(verCompra));

        automator.find(verCompra, "Ver el carro de compras").click();
    }

}
