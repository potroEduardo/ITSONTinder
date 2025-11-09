/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Excepcion base para todas las excepciones personalizadas del sistema ItsonTinder.
 * Incluye informacion de timestamp para facilitar el debugging
 *
 * @author jonyco
 */
public class ItsonTinderException  extends Exception{

    private final LocalDateTime timestamp;
    private final String codigoError;

     public ItsonTinderException(String mensaje) {
        super(mensaje);
        this.timestamp = LocalDateTime.now();
        this.codigoError = "BONDING_ERROR";
    }

    public ItsonTinderException(String mensaje, String codigoError) {
        super(mensaje);
        this.timestamp = LocalDateTime.now();
        this.codigoError = codigoError;
    }

    public ItsonTinderException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.timestamp = LocalDateTime.now();
        this.codigoError = "BONDING_ERROR";
    }

    public ItsonTinderException(String mensaje, String codigoError, Throwable causa) {
        super(mensaje, causa);
        this.timestamp = LocalDateTime.now();
        this.codigoError = codigoError;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getMensajeCompleto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] [%s] %s",
            timestamp.format(formatter),
            codigoError,
            getMessage());
    }

    @Override
    public String toString() {
        return getMensajeCompleto();
    }
    
}