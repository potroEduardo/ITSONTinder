package validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de Utilidad para Validaciones.
 * @author Angel Beltran
 */
public class Validaciones {

    // Constructor 
    private Validaciones() {
    }

    /**
     * Verifica si un campo de texto está vacío.
     *
     */
    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /**
     * Verifica si un correo tiene el formato de ITSON.
     * Valida que no esté vacío y que termine con "@itson.edu.mx" o "@itson.mx".
     */
    public static boolean esCorreoItson(String correo) {
        if (esCampoVacio(correo)) {
            return false;
        }
        // Hacemos la validación simple que pediste
        String correoMinusculas = correo.toLowerCase();
        return correoMinusculas.endsWith("@itson.edu.mx") || correoMinusculas.endsWith("@itson.mx");
    }

    /**
     * Valida la longitud de una contraseña.
     *
     */
    public static boolean esPasswordValida(String password, int minLongitud) {
        if (esCampoVacio(password)) {
            return false;
        }
        return password.length() >= minLongitud;
    }

    /**
     * Compara si dos campos de texto (como contraseñas) son idénticos.
     *
     */
    public static boolean sonCamposIguales(String texto1, String texto2) {
        if (texto1 == null || texto2 == null) {
            return false; // No podemos comparar si uno es nulo
        }
        return texto1.equals(texto2);
    }

    /**
     * Verifica si un texto contiene solo letras y espacios (ideal para nombres).
     *
     */
    public static boolean esSoloTexto(String texto) {
        if (esCampoVacio(texto)) {
            return false;
        }
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        Matcher matcher = patron.matcher(texto);
        return matcher.matches();
    }

    /**
     * Verifica si un texto es un número entero positivo (ideal para edades).
     */
    public static boolean esEnteroPositivo(String texto) {
        if (esCampoVacio(texto)) {
            return false;
        }
        try {
            int numero = Integer.parseInt(texto);
            return numero > 0;
        } catch (NumberFormatException e) {
            return false; // No era un número
        }
    }
}
