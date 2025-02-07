package clientapp.model;

/**
 * Enum que representa las horas disponibles para las funciones de una película.
 * <p>
 * El enum {@link MovieHour} contiene las horas predefinidas a las cuales las películas pueden 
 * ser proyectadas. Estas horas son las siguientes: 16:00, 18:00, 20:00 y 22:00.
 * </p>
 * 
 * @author 2dam
 */
public enum MovieHour {

    /**
     * Representa la hora de la película a las 16:00.
     */
    HOUR_16("16:00"),

    /**
     * Representa la hora de la película a las 18:00.
     */
    HOUR_18("18:00"),

    /**
     * Representa la hora de la película a las 20:00.
     */
    HOUR_20("20:00"),

    /**
     * Representa la hora de la película a las 22:00.
     */
    HOUR_22("22:00");

    private final String hour;

    /**
     * Constructor que inicializa el valor de la hora de la película.
     * 
     * @param hour la hora asociada a la proyección de la película.
     */
    MovieHour(String hour) {
        this.hour = hour;
    }

    /**
     * Obtiene la hora asociada al valor del {@link MovieHour}.
     * 
     * @return la hora de la película en formato de cadena (HH:mm).
     */
    public String getHour() {
        return hour;
    }
}
