package entities;


public enum TipoInteraccion {
    ME_GUSTA("Me gusta"),
    NO_ME_INTERESA("No me interesa");

    private final String valorDB;

    TipoInteraccion(String valorDB) {
        this.valorDB = valorDB;
    }

    public String getValorDB() {
        return valorDB;
    }

   
    public static TipoInteraccion fromValorDB(String valorDB) {
        for (TipoInteraccion tipo : values()) {
            if (tipo.valorDB.equalsIgnoreCase(valorDB)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor desconocido: " + valorDB);
    }
}
