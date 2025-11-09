/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion lanzada cuando se intenta crear una entidad que ya existe.
 * Ejemplos: correo duplicado,like duplicado-match duplicado
 *
 * @author jonyco
 */
public class DuplicadoException extends ItsonTinderException {

    private final String tipoEntidad;
    private final String campoUnico;
    private final Object valor;

    public DuplicadoException(String mensaje) {
        super(mensaje, "DUPLICADO_ERROR");
        this.tipoEntidad = null;
        this.campoUnico = null;
        this.valor = null;
    }

    public DuplicadoException(String tipoEntidad, String campoUnico, Object valor) {
        super(String.format("%s con %s '%s' ya existe en el sistema", tipoEntidad, campoUnico, valor),
                "DUPLICADO_ERROR");
        this.tipoEntidad = tipoEntidad;
        this.campoUnico = campoUnico;
        this.valor = valor;
    }

    public DuplicadoException(String mensaje, String tipoEntidad, String campoUnico, Object valor) {
        super(mensaje, "DUPLICADO_ERROR");
        this.tipoEntidad = tipoEntidad;
        this.campoUnico = campoUnico;
        this.valor = valor;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public String getCampoUnico() {
        return campoUnico;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    public String getMensajeCompleto() {
        if (tipoEntidad != null && campoUnico != null && valor != null) {
            return super.getMensajeCompleto()
                    + String.format(" [%s.%s = %s]", tipoEntidad, campoUnico, valor);
        }
        return super.getMensajeCompleto();
    }
}
