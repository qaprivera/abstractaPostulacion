package framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class PdfReportGenerator {
    private PDDocument document;
    private PDPageContentStream content;
    private float currentY = 750; // coordenada Y inicial
    private PDFont font;
    private String reportPath;

    public PdfReportGenerator(String reportPath) {
        this.reportPath = reportPath;
        try {
            document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);

            content = new PDPageContentStream(document, page);
            font = PDType0Font.load(document, new File("C:/Windows/Fonts/Arial.ttf")); // usa Arial del sistema

            addText("Repórte de prueba generado: " + LocalDateTime.now().toString());

        } catch (IOException e) {
            System.err.println("Error inicializando PDFBox: " + e.getMessage());
        }
    }

    private void addText(String text) throws IOException {
        content.beginText();
        content.setFont(font, 12);
        content.newLineAtOffset(50, currentY);
        content.showText(text);
        content.endText();
        currentY -= 20;
    }

    public void addStep(String description, WebDriver driver) {
        try {
            // 1. Tomar screenshot y preparar la imagen
            String screenshotPath = "screenshot_" + UUID.randomUUID() + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(screenshot, destFile);
            PDImageXObject img = PDImageXObject.createFromFileByContent(destFile, document);

            // 2. Calcular espacio requerido
            float maxWidth = 512;
            float aspectRatio = img.getHeight() / (float) img.getWidth();
            float imgWidth = maxWidth;
            float imgHeight = imgWidth * aspectRatio;

            float espacioNecesario = 20 + imgHeight + 30; // texto + imagen + espacio extra

            // 3. Verificar si cabe todo en la página actual
            if (currentY - espacioNecesario < 50) {
                // Saltar de página antes de hacer algo
                content.close();
                PDPage newPage = new PDPage(PDRectangle.LETTER);
                document.addPage(newPage);
                content = new PDPageContentStream(document, newPage);
                currentY = 750;
            }

            // 4. Agregar texto
            addText(description);

            // 5. Agregar imagen
            float x = 50;
            float y = currentY - imgHeight - 10;
            content.drawImage(img, x, y, imgWidth, imgHeight);
            content.addRect(x, y, imgWidth, imgHeight);
            content.stroke();

            // 6. Actualizar posición
            currentY = y - 20;
            destFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando paso PDFBox", e);
        }
    }





    public void closeReport() {
        try {
            if (content != null) content.close();
            if (document != null) document.save(reportPath);
            if (document != null) document.close();
        } catch (IOException e) {
            System.err.println("Error cerrando PDFBox: " + e.getMessage());
        }
    }
}
