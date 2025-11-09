/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion lanzada cuando no se encuentra una entidad en la base de datos.
 * Ejemplos: estudiante no encontrado, carrera no encontrada, match no
 * encontrado
 *
 * @author jonyco
 */
public class EntidadNoEncontradaException extends ItsonTinderException {

    private final String tipoEntidad;
    private final Object identificador;

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje, "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = null;
        this.identificador = null;
    }

    public EntidadNoEncontradaException(String tipoEntidad, Object identificador) {
        super(String.format("No se encontro %s con identificador: %s", tipoEntidad, identificador),
                "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = tipoEntidad;
        this.identificador = identificador;
    }

    public EntidadNoEncontradaException(String mensaje, String tipoEntidad, Object identificador) {
        super(mensaje, "ENTIDAD_NO_ENCONTRADA");
        this.tipoEntidad = tipoEntidad;
        this.identificador = identificador;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public Object getIdentificador() {
        return identificador;
    }

    @Override
    public String getMensajeCompleto() {
        if (tipoEntidad != null && identificador != null) {
            return super.getMensajeCompleto()
                    + String.format(" [Tipo: %s, ID: %s]", tipoEntidad, identificador);
        }
        return super.getMensajeCompleto();
    }
}
