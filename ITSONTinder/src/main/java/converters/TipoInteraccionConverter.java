package converters;

import entities.TipoInteraccion;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 *Convertidor para TipoInteraccionConverter MAPPER
 * @author Angel Beltran
 */
@Converter(autoApply = true) 
public class TipoInteraccionConverter implements AttributeConverter<TipoInteraccion, String> {

    /**
     * DE JAVA A BD
     */
    @Override
    public String convertToDatabaseColumn(TipoInteraccion attribute) {
        if (attribute == null) {
            return null;
        }
        // se usa el metodo y recibe me gusta o no me gusta
        return attribute.getValorDB();
    }

    /**
     * De BD A JAVA
     */
    @Override
    public TipoInteraccion convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return TipoInteraccion.fromValorDB(dbData); 
    }
}
