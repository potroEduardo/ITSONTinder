package excepciones;

/**
 * Excepcion lanzada cuando falla la autenticacion de un usuario. 
 * Ejemplos:
 * credenciales invalidas,cuenta bloqueada,sesion expirada
 *
 * @author jonyco
 */
public class AutenticacionException extends ItsonTinderException {

    private final String usuario;
    private final TipoErrorAutenticacion tipoError;

    public enum TipoErrorAutenticacion {
        CREDENCIALES_INVALIDAS("Credenciales invalidas"),
        USUARIO_NO_ENCONTRADO("Usuario no encontrado"),
        CUENTA_BLOQUEADA("Cuenta bloqueada"),
        SESION_EXPIRADA("Sesion expirada");

        private final String descripcion;

        TipoErrorAutenticacion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public AutenticacionException(String mensaje) {
        super(mensaje, "AUTENTICACION_ERROR");
        this.usuario = null;
        this.tipoError = TipoErrorAutenticacion.CREDENCIALES_INVALIDAS;
    }

    public AutenticacionException(String usuario, TipoErrorAutenticacion tipoError) {
        super(tipoError.getDescripcion(), "AUTENTICACION_ERROR");
        this.usuario = usuario;
        this.tipoError = tipoError;
    }

    public AutenticacionException(String mensaje, String usuario, TipoErrorAutenticacion tipoError) {
        super(mensaje, "AUTENTICACION_ERROR");
        this.usuario = usuario;
        this.tipoError = tipoError;
    }

    public String getUsuario() {
        return usuario;
    }

    public TipoErrorAutenticacion getTipoError() {
        return tipoError;
    }

    @Override
    public String getMensajeCompleto() {
        if (usuario != null) {
            return super.getMensajeCompleto()
                    + String.format(" [Usuario: %s, Tipo: %s]", usuario, tipoError.name());
        }
        return super.getMensajeCompleto();
    }
}
