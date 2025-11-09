/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion lanzada cuando ocurre un error en las operaciones de base de datos.
 * Ejemplos: error de conexion,error de transaccion,error de persistencia
 *
 * @author jonyco
 */
public class DatabaseException extends ItsonTinderException {

    private final String operacion;
    private final String entidad;

    public DatabaseException(String mensaje) {
        super(mensaje, "DATABASE_ERROR");
        this.operacion = null;
        this.entidad = null;
    }

    public DatabaseException(String mensaje, Throwable causa) {
        super(mensaje, "DATABASE_ERROR", causa);
        this.operacion = null;
        this.entidad = null;
    }

    public DatabaseException(String operacion, String entidad, Throwable causa) {
        super(String.format("Error en operacion %s sobre entidad %s", operacion, entidad),
                "DATABASE_ERROR", causa);
        this.operacion = operacion;
        this.entidad = entidad;
    }

    public DatabaseException(String mensaje, String operacion, String entidad, Throwable causa) {
        super(mensaje, "DATABASE_ERROR", causa);
        this.operacion = operacion;
        this.entidad = entidad;
    }

    public String getOperacion() {
        return operacion;
    }

    public String getEntidad() {
        return entidad;
    }

    @Override
    public String getMensajeCompleto() {
        if (operacion != null && entidad != null) {
            return super.getMensajeCompleto()
                    + String.format(" [Operacion: %s, Entidad: %s]", operacion, entidad);
        }
        return super.getMensajeCompleto();
    }
}
