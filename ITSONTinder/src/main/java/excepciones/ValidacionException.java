/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion lanzada cuando falla la validacion de datos de entrada. Ejemplos:
 * campos requeridos vacios, formato invalido,valores fuera de rango
 *
 * @author jonyco
 */
public class ValidacionException extends ItsonTinderException {

    private final String campo;

    public ValidacionException(String mensaje) {
        super(mensaje, "VALIDACION_ERROR");
        this.campo = null;
    }

    public ValidacionException(String mensaje, String campo) {
        super(mensaje, "VALIDACION_ERROR");
        this.campo = campo;
    }

    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, "VALIDACION_ERROR", causa);
        this.campo = null;
    }

    public String getCampo() {
        return campo;
    }

    @Override
    public String getMensajeCompleto() {
        if (campo != null && !campo.isEmpty()) {
            return super.getMensajeCompleto() + " - Campo: " + campo;
        }
        return super.getMensajeCompleto();
    }

}
