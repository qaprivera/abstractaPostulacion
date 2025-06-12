package framework.utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer config.properties", e);
        }
    }

    public static boolean generarEvidencia() {
        return Boolean.parseBoolean(props.getProperty("generar.evidencia", "true"));
    }

    public static String navegador() {
        return props.getProperty("navegador", "chrome").toLowerCase();
    }
}
