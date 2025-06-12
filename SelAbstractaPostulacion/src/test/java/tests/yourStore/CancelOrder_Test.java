package tests.yourStore;

import framework.WebAutomator;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.TestBase;
import pages.ShoppingCart_Page;
import pages.Home_Page;

import java.io.IOException;

public class CancelOrder_Test extends TestBase {

    @Test(priority = 1)
    public void validarTituloDePagina() {
        }
    @Test(priority = 1)
    public void cancelarCompra() throws IOException {

        ShoppingCart_Page carrito = new ShoppingCart_Page(automator);
        Home_Page home = new Home_Page(automator);
        home.store(automator);
        home.buscarProducto(automator);
        pdfReport.addStep("Buscando el producto en la caja  de busqueda", driver);
        home.seleccionarOpcion();
        pdfReport.addStep("Seleccionando el producto", driver);
        home.agregarProducto();
        pdfReport.addStep("Agregando el producto al carro de compras", driver);
        home.ingresarCarro();
        pdfReport.addStep("Ingresando al carro de compras", driver);
        home.verCarro(automator);
        carrito.validarCarro(automator);
        carrito.removerProducto();
        pdfReport.addStep("Eliminar producto del carro de compras", driver);
        //carrito.validarCarroVacio();
        String mensaje = carrito.validarCarroVacio(automator);
        Assert.assertTrue(mensaje.contains("Your shopping cart is empty!"), "El mensaje esperado no se encuentra.");
}

}
